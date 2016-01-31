package models;

/**
 * Created by me on 1/31/16.
 */
public class Difference implements Comparable{
    public String politician;
    public int difference;

    public Difference(String politician, int difference) {
        this.politician = politician;
        this.difference = difference;
    }

    @Override
    public int compareTo(Object another) {
        return this.difference - ((Difference)another).difference;
    }
}
