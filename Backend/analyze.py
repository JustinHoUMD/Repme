import json
import requests

url = "https://hackathon.fiscalnote.com/bills?q="
keywords = ["criminal", "social", "environmental", "economic", "domestic+policy", "healthcare", "foreign+policy", "education", "immigration", "science"]
params = "&created=1/1/2014,12/31/2014&legislature=MD&count=100"
KEY = "&apikey=GQW6JFUW1LNE8B54T96WNFT34S5HCYP2"

legislator_scores = {}

bill_count = 0

def score_bills(url, keyword, params):
    r = requests.get(url + keyword + params + KEY)
    j = json.loads(r.text)
    global bill_count
    bill_count = bill_count
    for i in range(0, len(j)):
        bill_count += 1
        bill_id = str(j[i]["id"])
        bill_score = 0.0
        sponsors = None

        #Calculate the liberal/conservative score of this bill
        if "sponsors" in j[i]["current_prediction_analysis"]:
            sponsors =  j[i]["current_prediction_analysis"]["sponsors"]
            if len(sponsors):
                for s in sponsors:
                    if s["party"] == "Democratic":
                        bill_score -= 1
                    elif s["party"] == "Republican":
                        bill_score += 1
        if bill_score > 0.0:
            bill_score = 1.0
        elif bill_score < 0.0:
            bill_score = -1.0

        #Update the liberal/conservative score of every legislator based on how they  voted on this bill
        bill_votes = j[i]["bill_votes"]
        if len(bill_votes):
            if "yes_votes" in bill_votes[0]:
                yes_votes = bill_votes[0]["yes_votes"]
                for ii in range(0, len(yes_votes)):
                    leg_id = yes_votes[ii]["leg_id"]
                    if leg_id is None:
                        continue
                    if leg_id not in legislator_scores:
                        legislator_scores[leg_id] = {}
                        legislator_scores[leg_id]["overall_score"] = 0.0
                    if keyword not in legislator_scores[leg_id]:
                        legislator_scores[leg_id][keyword] = 0.0
                    legislator_scores[leg_id][keyword] += bill_score
                    legislator_scores[leg_id]["overall_score"] += bill_score
            if "no_votes" in bill_votes[0]:
                no_votes = bill_votes[0]["no_votes"]
                for ii in range(0, len(no_votes)):
                    leg_id = no_votes[ii]["leg_id"]
                    if leg_id is None:
                        continue
                    if leg_id not in legislator_scores:
                        legislator_scores[leg_id] = {}
                        legislator_scores[leg_id]["overall_score"] = 0.0
                    if keyword not in legislator_scores[leg_id]:
                        legislator_scores[leg_id][keyword] = 0.0
                    legislator_scores[leg_id][keyword] -= bill_score
                    legislator_scores[leg_id]["overall_score"] -= bill_score

for j in range(0, ord('N')+1):
    for i in range(0, len(keywords)):
        score_bills(url, keywords[i], params)

results = {"results":[legislator_scores]}
print json.dumps(results)
