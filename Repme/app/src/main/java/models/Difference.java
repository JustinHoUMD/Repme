package models;

/**
 * Created by me on 1/31/16.
 */
public class Difference implements Comparable{
    public String politician;
    public double difference;

    public Difference(String politician, double difference) {
        this.politician = politician;
        this.difference = difference;
    }

    @Override
    public int compareTo(Object another) {
        if (this.difference > ((Difference)another).difference) {
            return 1;
        } else if (this.difference < ((Difference)another).difference) {
            return -1;
        }
        return 0;
    }
}
