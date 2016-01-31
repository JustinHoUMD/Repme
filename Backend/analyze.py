import json
import requests

url = "https://hackathon.fiscalnote.com/bills?q="
keywords = ["criminal", "social", "environmental", "economic", "domestic+policy", "healthcare", "foreign+policy", "education", "immigration", "science"]
legislature = "&legislature=MD"
KEY = "&apikey=GQW6JFUW1LNE8B54T96WNFT34S5HCYP2"

bill_scores = {}
legislator_scores = {}

def score_bills(url, keyword, legislature):
    r = requests.get(url + keyword + legislature + KEY)
    j = json.loads(r.text)

    for i in range(0, len(j)):
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
                bill_score = bill_score/len(sponsors)
        bill_scores[bill_id] = bill_score

        #Update the liberal/conservative score of every legislator based on how they  voted on this bill
        bill_votes = j[i]["bill_votes"]
        if len(bill_votes):
            if "yes_votes" in bill_votes[0]:
                yes_votes = bill_votes[0]["yes_votes"]
                for ii in range(0, len(yes_votes)):
                    leg_id = yes_votes[ii]["leg_id"]
                    name = yes_votes[ii]["name"]
                    if leg_id not in legislator_scores:
                        legislator_scores[leg_id] = {}
                        legislator_scores[leg_id]["overall_score"] = 0
                    legislator_scores[leg_id]["name"] = name
                    if keyword not in legislator_scores[leg_id]:
                        legislator_scores[leg_id][keyword] = 0
                    legislator_scores[leg_id][keyword] += bill_score
                    legislator_scores[leg_id]["overall_score"] += bill_score
            if "no_votes" in bill_votes[0]:
                no_votes = bill_votes[0]["no_votes"]
                for ii in range(0, len(no_votes)):
                    leg_id = no_votes[ii]["leg_id"]
                    name = no_votes[ii]["name"]
                    if leg_id not in legislator_scores:
                        legislator_scores[leg_id] = {}
                        legislator_scores[leg_id]["overall_score"] = 0
                    legislator_scores[leg_id]["name"] = name
                    if keyword not in legislator_scores[leg_id]:
                        legislator_scores[leg_id][keyword] = 0
                    legislator_scores[leg_id][keyword] -= bill_score
                    legislator_scores[leg_id]["overall_score"] -= bill_score

for i in range(0, len(keywords)):
    score_bills(url, keywords[i], legislature)

results = {"results":[legislator_scores]}
print json.dumps(results)
