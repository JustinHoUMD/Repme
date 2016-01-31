package models;

import java.io.Serializable;

/**
 * Created by me on 1/31/16.
 */
public class Category implements Comparable, Serializable {
    public String category;
    public double difference;

    public Category(String category, double difference) {
        this.category = category;
        this.difference = difference;
    }


    @Override
    public int compareTo(Object another) {
        if (this.difference > ((Category)another).difference) {
            return 1;
        } else if (this.difference < ((Category)another).difference) {
            return -1;
        }
        return 0;
    }
}
