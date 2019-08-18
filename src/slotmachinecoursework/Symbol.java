
package slotmachinecoursework;

import java.util.Random;

public class Symbol implements ISymbol {

    private String name;
    private int value;
    
    public void setImage(String imageName) 
    {
        name = imageName;
    }
    public String getImage() 
    {
        return name;
    }

    public void setValue(int tempValue) 
    {
        value = tempValue;
    }

    public int getValue() 
    {
        return value;
    } 
}