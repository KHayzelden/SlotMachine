
package slotmachinecoursework;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

public class GUI extends JFrame implements ActionListener
{
    
    static JButton betOneButton = new JButton("Bet One");
    static JButton betMaxButton = new JButton("Bet Max");
    static JButton addCoinButton = new JButton("Add Coin");
    static JButton resetBetButton = new JButton("Reset Bet");
    static JButton spinButton = new JButton("SPIN!");
    static JButton statsButton = new JButton("Statistics");
    static JButton saveStatisticsButton = new JButton("Save");
    static JButton closeButton = new JButton("Close");
    
    static JButton slot1 = new JButton("?");
    static JButton slot2 = new JButton("?");
    static JButton slot3 = new JButton("?");
    
    static JLabel creditsRemaining = new JLabel("10");
    static JLabel creditsText = new JLabel("Credits Remaining");
    static JLabel betText = new JLabel("Your Bet:");
    static JLabel betAmount = new JLabel("0");  
    static JLabel resultsText = new JLabel("win/lose");
    
    static Reel reel1 = new Reel();
    static Reel reel2 = new Reel();
    static Reel reel3 = new Reel(); 
 
    static Thread thread1, thread2, thread3;
    
    Boolean spinButtonEnabled = false, statsButtonEnabled = false, resetBetButtonEnabled = false;
    Boolean betOneButtonEnabled = true, betMaxButtonEnabled = true;
         
    //for stats
    int netCredits = 0, totalGamesPlayed = 0, gamesWon = 0, gamesLost = 0 ;
    
    public GUI()
    { 
        //setting up the GUI
        JFrame frame = new JFrame("Slot Machine");
        frame.setVisible(true);
        frame.setSize(1000,420);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //setting up the main panel and the grid
        JPanel panel = new JPanel(new GridBagLayout());
        frame.getContentPane().add(panel, BorderLayout.NORTH);
        
        //seting the padding and constraints
        GridBagConstraints position = new GridBagConstraints();
        
        position.insets = new Insets(5,10,5,10); //top left bottom right
        
        position.gridx = 0;
        position.gridy = 0;
        panel.add(creditsText, position);
        
        position.gridx = 2;
        position.gridy = 0;
        panel.add(betText, position);
                
        betOneButton.setSize(100, 50);
        position.gridx = 4;
        position.gridy = 0;
        panel.add(betOneButton, position);
        betOneButton.setActionCommand("betOne");
        betOneButton.addActionListener(this);
        
        //
        creditsRemaining.setFont (creditsRemaining.getFont ().deriveFont (30.0f));
        position.gridx = 0;
        position.gridy = 1;
        panel.add(creditsRemaining, position);

        betAmount.setFont (betAmount.getFont ().deriveFont (40.0f));
        position.gridx = 2;
        position.gridy = 1;
        panel.add(betAmount, position);
        
        position.gridx = 4;
        position.gridy = 1;
        panel.add(betMaxButton, position);
        betMaxButton.setActionCommand("betMax");
        betMaxButton.addActionListener(this);
        
        //      

        position.gridx = 0;
        position.gridy = 2;
        panel.add(addCoinButton, position);
        addCoinButton.setActionCommand("addCoin");
        addCoinButton.addActionListener(this);
        
        resultsText.setFont (betAmount.getFont ().deriveFont (20.0f));
        resultsText.setVisible(false);
        position.gridwidth = 3;
        position.gridx = 1;
        position.gridy = 2;
        panel.add(resultsText, position); 

        position.gridwidth = 1;
        position.gridx = 4;
        position.gridy = 2;
        panel.add(resetBetButton, position);
        resetBetButton.setActionCommand("resetBet");
        resetBetButton.addActionListener(this);
        
        //
        
        slot1.setFont (slot1.getFont ().deriveFont (100.0f)); //makes the ??s larger
        slot1.setPreferredSize(new Dimension(200, 200));
        position.gridx = 1;
        position.gridy = 3;
        panel.add(slot1, position);

        slot2.setFont (slot2.getFont ().deriveFont (100.0f));
        slot2.setPreferredSize(new Dimension(200, 200));
        position.gridx = 2;
        position.gridy = 3;
        panel.add(slot2, position);

        slot3.setFont (slot3.getFont ().deriveFont (100.0f));
        slot3.setPreferredSize(new Dimension(200, 200));
        position.gridx = 3;
        position.gridy = 3;
        panel.add(slot3, position);
        
        //
        
        position.gridx = 2;
        position.gridy = 4;
        panel.add(spinButton, position);
        spinButton.setActionCommand("spinButton");
        spinButton.addActionListener(this);
        
        position.gridx = 4;
        position.gridy = 4;
        panel.add(statsButton, position);
        statsButton.setActionCommand("showStatistics");
        statsButton.addActionListener(this);
        
        checkButtons();
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("addCoin")) //retrieves the label credit amount and adds 1
        {
            if (resultsText.isShowing()) //if a game just ended you can bet max again
            { 
                betMaxButtonEnabled = true;
                betAmount.setText("0");
                resultsText.setVisible(false);
                betText.setText("Your Bet:");
            } 
            
            creditsRemaining.setText(Integer.toString(Integer.parseInt(creditsRemaining.getText())+1));
            
            checkButtons(); //checks the remaining credit and disables or enables betting buttons accordingly      
        }
        
        if(e.getActionCommand().equals("betOne")) //retrieves the label bet amount and adds 1
        {
            if (betOneButtonEnabled)
            {
                if (resultsText.isShowing()) //if a game just ended you can bet max again
                { 
                        betMaxButtonEnabled = true;
                    betAmount.setText("0");
                    resultsText.setVisible(false);
                    betText.setText("Your Bet:");
                } 

                if(Integer.parseInt(creditsRemaining.getText()) >= 1)
                {
                    creditsRemaining.setText(Integer.toString(Integer.parseInt(creditsRemaining.getText())-1));
                    betAmount.setText(Integer.toString(Integer.parseInt(betAmount.getText())+1));
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, "You have no more credits to bet :( Try adding a coin.");
            }
            
            checkButtons(); //checks the remaining credit and disables or enables betting buttons accordingly
        }
        
        if(e.getActionCommand().equals("betMax")) //retrieves the label bet amount and adds 3
        {
            if (betMaxButtonEnabled)
            {
                if (resultsText.isShowing())
                { 
                    betAmount.setText("0");
                    resultsText.setVisible(false);
                    betText.setText("Your Bet:");
                } 

                if(Integer.parseInt(creditsRemaining.getText()) >= 3)
                {
                    creditsRemaining.setText(Integer.toString(Integer.parseInt(creditsRemaining.getText())-3));
                    betAmount.setText(Integer.toString(Integer.parseInt(betAmount.getText())+3));
                }
            }
            else 
            {
                JOptionPane.showMessageDialog(null, "You can not bet max anymore.");
            }

            checkButtons(); //checks the remaining credit and disables or enables betting buttons accordingly
            betMaxButtonEnabled = false; //so it can only be clicked once it will be disabled on click until round resets
        }
        
        if(e.getActionCommand().equals("resetBet")) //resets the bet amount to 0 and adds the credits back
        {
            if (resetBetButtonEnabled)
            {
                creditsRemaining.setText(Integer.toString(Integer.parseInt(creditsRemaining.getText()) + Integer.parseInt(betAmount.getText())));
                betAmount.setText("0");
                betMaxButtonEnabled = true;
            }
            else
            {
                JOptionPane.showMessageDialog(null, "There is no bet to reset.");
            }
            
            checkButtons(); //checks the remaining credit and disables or enables betting buttons accordingly
        }
        
        if(e.getActionCommand().equals("spinButton")) //resets the bet amount to 0 and adds the credits back
        {
            if (spinButtonEnabled)
            {   
                totalGamesPlayed += 1;

                int winType = 0; //win types are defined as 0 = loss 1= one pair 2 = all three match
                Symbol winningSymbol = new Symbol(); //win image used to calculate score

                slot1.setText(""); //clears ???'s
                slot2.setText("");
                slot3.setText("");

                Symbol slot1Image, slot2Image, slot3Image;

                slot1Image = reel1.Spin();//gets the three random symbol classes 
                slot2Image = reel2.Spin();
                slot3Image = reel3.Spin();

                String slot1ImagePath, slot2ImagePath, slot3ImagePath;

                slot1ImagePath = reel1.getImagePath(slot1Image);
                slot2ImagePath = reel2.getImagePath(slot2Image);
                slot3ImagePath = reel3.getImagePath(slot3Image);

                slot1.setIcon(new ImageIcon(new ImageIcon(slot1ImagePath).getImage().getScaledInstance(190, 190, Image.SCALE_DEFAULT)));
                slot2.setIcon(new ImageIcon(new ImageIcon(slot2ImagePath).getImage().getScaledInstance(190, 190, Image.SCALE_DEFAULT)));
                slot3.setIcon(new ImageIcon(new ImageIcon(slot3ImagePath).getImage().getScaledInstance(190, 190, Image.SCALE_DEFAULT)));

                checkButtons(); //checks the remaining credit and disables or enables betting buttons accordingly

                if    (slot1Image.getImage().equals(slot2Image.getImage()) 
                    && slot2Image.getImage().equals(slot3Image.getImage())) 
                {    
                    gamesWon += 1;
                    winType = 2; //all three match 
                    winningSymbol = slot1Image; //any would work as they're all the same
                }            
                else if (slot1Image.getImage().equals(slot2Image.getImage()))
                {
                    gamesWon += 1;
                    winType = 1; //one pair
                    winningSymbol = slot1Image; //or 2
                }            
                else if (slot1Image.getImage().equals(slot3Image.getImage()))
                {
                    gamesWon += 1;
                    winType = 1; //one pair
                    winningSymbol = slot1Image; //or 3
                }           
                else if (slot2Image.getImage().equals(slot3Image.getImage()))
                {
                    gamesWon += 1;
                    winType = 1; //one pair
                    winningSymbol = slot2Image; //or 3
                }
                else
                {
                    gamesLost += 1;
                    winType = 0; //no win
                }

                int winAmount; //used to calculate the winnings based on symbol value times bet

                switch (winType)
                {
                    case 0:
                        resultsText.setText("You lost, get rekt.");
                        resultsText.setVisible(true);

                        netCredits -= Integer.parseInt(betAmount.getText()); //the bet credits count as a loss

                        betText.setText("Credits won: ");
                        betAmount.setText("None!");

                        checkButtons();
                    break;

                    case 1:
                        resultsText.setText("You got a pair of " + winningSymbol.getImage() + "s!");
                        resultsText.setVisible(true);                    

                        winAmount = Integer.parseInt(betAmount.getText()) * winningSymbol.getValue();

                        netCredits += winAmount;

                        creditsRemaining.setText(Integer.toString(Integer.parseInt(creditsRemaining.getText()) + winAmount));

                        betText.setText("Credits won: ");
                        betAmount.setText("" + winAmount + " !");

                        checkButtons();
                    break;

                    case 2:
                        resultsText.setText("JACKPOT! 3 " + winningSymbol.getImage() + "s!");
                        resultsText.setVisible(true);

                        winAmount = 2 * (Integer.parseInt(betAmount.getText()) * winningSymbol.getValue());

                        netCredits += winAmount;

                        creditsRemaining.setText(Integer.toString(Integer.parseInt(creditsRemaining.getText()) + winAmount));

                        betText.setText("Credits won: ");
                        betAmount.setText("" + winAmount + " credits!");

                        checkButtons();
                    break; 
                } 
            }
            else 
            {
                JOptionPane.showMessageDialog(null, "You must bet to spin.");
            }
        }
        
        if(e.getActionCommand().equals("showStatistics")) //resets the bet amount to 0 and adds the credits back
        {            
            if (statsButtonEnabled)
            {
                int averageCredits;

                averageCredits = netCredits / totalGamesPlayed;

                Object[] options = {"Close", "Save"}; //button options

                // I chose a JOptionPane to display the statistics so it would only pop up when the user
                // wanted to see it by clicking the button, then it has built in features to dismiss the 
                // popup or to add a button that I could then use for the save feature of the program

                int choice = JOptionPane.showOptionDialog
                (
                    this,
                    "\nTotal Games: " + printHistogram(totalGamesPlayed)
                  + "\nWins: " + printHistogram(gamesWon)
                  + "\nLoses: " + printHistogram(gamesLost)
                  + "\nAverage Credits Won: " + printHistogram(averageCredits),
                    "\nStatistics",
                    JOptionPane.DEFAULT_OPTION, 
                    JOptionPane.QUESTION_MESSAGE, 
                    new ImageIcon(new ImageIcon("/Users/Barney/Desktop/Images/redseven.png").getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT)),
                    options,
                    options[0]
                );

                //choice 0 does nothing as it's close the window closes automatically
                if (choice == 1)
                {
                    SimpleDateFormat timeStamp = new SimpleDateFormat("dd-MM-yy HH-mm-ss");
                    Date date = new Date();

                    try 
                    {
                        PrintWriter writer = new PrintWriter(timeStamp.format(date) + ".txt");
                        writer.println("Toatl Games Played: " + totalGamesPlayed);
                        writer.println("Games Won: " + gamesWon);
                        writer.println("Games Lost: " + gamesLost);
                        writer.println("Average Credits: " + averageCredits);
                        writer.close(); // doing this flushes the data to a file.
                    } 
                    catch (FileNotFoundException ex) 
                    {
                        System.out.println("sad");
                    }
                }    
            }
            else
            {
                JOptionPane.showMessageDialog(null, "There are no stats to show yet.");
            }
            
            checkButtons(); //checks the remaining credit and disables or enables betting buttons accordingly
        }
    }
    
    public void checkButtons () //always is used to check buttons and enable/disable them when approprate
    {
        if (Integer.parseInt(creditsRemaining.getText()) < 1)
        {
            betOneButtonEnabled = false;
        }
        else
        {
            betOneButtonEnabled = true;
        }
        
        if (betMaxButton.isEnabled() && Integer.parseInt(creditsRemaining.getText()) < 3)
        {
            betMaxButtonEnabled = false;
        }
        else if (betMaxButton.isEnabled() && Integer.parseInt(creditsRemaining.getText()) >= 3)
        {
            betMaxButtonEnabled = true;
        }
            
        if (Integer.parseInt(creditsRemaining.getText()) > 3 && resultsText.isShowing())
        {
            betMaxButtonEnabled = true;
        }
        
        if (resultsText.isShowing()) //meaning a match has just ended and the bet amount will be showing the winnings not an actual bet amount
        {
            spinButtonEnabled = false;
        }
        else if (Integer.parseInt(betAmount.getText()) > 0) //if the bet is at least one credit
        {
            spinButtonEnabled = true;
        }
        
        if (totalGamesPlayed < 1)
        {
            statsButtonEnabled = false;
        }
        else
        {
            statsButtonEnabled = true;
        }
        
        if (resultsText.isShowing()) //meaning a match has just ended and the bet amount will be showing the winnings not an actual bet amount
        {
            resetBetButtonEnabled = false;
        }
        else if (Integer.parseInt(betAmount.getText()) < 1) //there has to be at lest one credit to reset
        {
            resetBetButtonEnabled = false;
        }
        else
        {
            resetBetButtonEnabled = true;
        }
    }
    
    public String printHistogram (int number)
    {
        String histogram = "";
        
        if (number >= 0) //average credits can be negative
        {
            for (int i = 0; i < number ; i ++)
            {
                histogram = histogram + "*";
            }
        }
        else
        {
            histogram = histogram + "NEGATIVE :( ";
            
            for (int i = 0; i > number ; i --)
            {
                histogram = histogram + "*";
            }
        }
        
        return histogram;
    }
}
