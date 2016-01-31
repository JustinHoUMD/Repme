package models;

/**
 * Created by me on 1/30/16.
 */
public class Question {
    public String question;
    public String type;
    public String party;

    public Question(String question, String type, String party) {
        this.question = question;
        this.type = type;
        this.party = party;
    }
}
