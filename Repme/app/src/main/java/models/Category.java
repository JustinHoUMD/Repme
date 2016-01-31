package models;

/**
 * Created by me on 1/31/16.
 */
public class Category implements Comparable {
    public String category;
    public double difference;

    public Category(String category, double difference) {
        this.category = category;
        this.difference = difference;
    }


    @Override
    public int compareTo(Object another) {
        return 0;
    }
}
