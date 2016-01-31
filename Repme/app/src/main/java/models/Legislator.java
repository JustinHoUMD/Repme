package models;

import java.io.Serializable;

/**
 * Created by me on 1/31/16.
 */
public class Legislator implements Serializable {
    public String id;
    public String state;
    public String full_name;
    public String job_title;
    public String party;
    public String photo_url;
    public String office_phone;
    public String email;
    public boolean vote;
}
