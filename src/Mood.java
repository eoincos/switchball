/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Eoin Costelloe
 */
public class Mood
{
    //mood consists of a name and a weight
    private String name;
    private double weight;

    Mood(String tmpName)
    {
        name = tmpName;
        weight = 0;
    }

    //set and get methods for values we want the user access to
    void setWeight(double tmpWeight)
    {
        weight = tmpWeight;
    }

    double getWeight()
    {
        return weight;
    }

    String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        String currentString = "";

        currentString += "Name: " + name + "\n";
        currentString += "Weight: " + weight + "\n";

        return currentString;
    }
}
