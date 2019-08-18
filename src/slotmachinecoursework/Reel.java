
package slotmachinecoursework;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Reel {

    final int numberOfSymbols = 6;
    
    Symbol[] reelArray = new Symbol[numberOfSymbols]; //creats an array of symbols
    
    String possibleSymbols[] = { "bell", "cherry", "lemon", "plum", "redseven", "watermelon"};
    int correspondingValues[] = { 6, 2, 3, 4, 7, 5 }; //coresponds to the order above
    
    public Reel() 
    {
        //initalizes the reel with the potential symbol names and values
        for (int i = 0; i < (numberOfSymbols); i++)
        {
            reelArray[i] = new Symbol(); //initializes the symbols
            reelArray[i].setImage(possibleSymbols[i]);
            reelArray[i].setValue(correspondingValues[i]);
        }

    }   
    
    public Symbol Spin() 
    {        
        Symbol symbol = new Symbol();
        
        symbol = reelArray[new Random().nextInt(reelArray.length)];
        
        return symbol;
    }
    
    public String getImagePath(Symbol randomSymbol)
    {
        String imagePath = "";
        
        switch (randomSymbol.getImage())
        {
            case "bell" :
                imagePath = "/Users/Barney/Desktop/Images/bell.png";
                break;
            case "cherry" :
                imagePath = "/Users/Barney/Desktop/Images/cherry.png";
                break;
            case "lemon" :
                imagePath = "/Users/Barney/Desktop/Images/lemon.png";
                break;
            case "plum" :
                imagePath = "/Users/Barney/Desktop/Images/plum.png";
                break;
            case "redseven" :
                imagePath = "/Users/Barney/Desktop/Images/redseven.png";
                break;
            case "watermelon" :
                imagePath = "/Users/Barney/Desktop/Images/watermelon.png";
                break;
        }
        
        return imagePath;
    }  
}