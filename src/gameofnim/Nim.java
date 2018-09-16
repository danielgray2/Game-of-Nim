/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameofnim;
import java.util.Random;
import java.util.Scanner;
/**
 *
 * @author grayd
 * This class runs the game of Nim. In order to use it, simply create a new object,
 * call the playGame() method, and run it. Follow the instructions in the console 
 * in order to play against the computer.
 */
public class Nim {
    /*
    These are the instance variables
    */
    Random rand = new Random();
    Scanner reader = new Scanner(System.in);
    Integer numMarbles;
    Integer turn;
    Integer difficultyLevel;
    
    /*
    This is the constructor that handles the set up. It sets the number of marbles,
    who goes first, and the difficulty level.
    */
    public Nim(){
        this.numMarbles = rand.nextInt(91) + 10;
        this.turn = rand.nextInt(2);
        this.difficultyLevel = this.rand.nextInt(2);
        if(difficultyLevel == 0){
            System.out.println("The computer will play easy.");
        }else{
            System.out.println("The computer will play hard.");
        }
    }
   
    /*
      This function handles the computer decision. If the computer is playing on
      easy mode, then the computer will make a random decision. If the computer is
      playing on hard mode, then it will remove a number of marbles that reduces
      the pile size to 2^x - 1. It uses logarithmic properties in order to do this.
      Additional code was added to handle the special cases of removing zero from 
      a pile, and when the pile size is equal to 2 to some power of x - 1.
    */
    private Integer generateComputerDecision(){
        Integer decision;
        if(this.difficultyLevel == 0){
            if(numMarbles > 1){
                decision = this.rand.nextInt(this.numMarbles/2) + 1;
            }else{
                decision = 1;
            }
        }else{
           double powerOfTwo = Math.round(Math.log((double)numMarbles)/Math.log(2));
           double twoToThePower = Math.pow(2, powerOfTwo);
           if(numMarbles < twoToThePower-1){
               powerOfTwo -= 1;
               twoToThePower = Math.pow(2, powerOfTwo);
           }
           
           if(numMarbles == twoToThePower-1){
               decision = this.rand.nextInt(this.numMarbles/2) + 1; 
           }else{  
               decision = (int)numMarbles - (int)twoToThePower + 1;
           }
           
           if(decision == 0){
               decision = 1;
           }
        }
        return decision;
    }
    
    /*
    This method handles the player input and checks to make sure that his inputs
    are valid.
    */
    private Integer getPlayerDecision(){
        boolean validInput = false;
        System.out.println("Enter how many marbles you would like to remove:");
        Integer playerDecision = reader.nextInt();
        if(playerDecision > 0 && playerDecision <= numMarbles/2 || numMarbles == 1 && playerDecision == 1){
            validInput = true;
        }
        while(!validInput){
            System.out.println("Whoah there chief. Choose a number greater than zero and less than or equal to "
                    + "the number of marbles divided by two.");
            playerDecision = reader.nextInt();
            if(playerDecision > 0 && playerDecision <= numMarbles/2 || numMarbles == 1 && playerDecision == 1){
                validInput = true;
            }              
        }
        return playerDecision;
    }
    
    private boolean checkForWin(){
        if(this.numMarbles == 0){
            if(this.turn == 1){
                System.out.println("The game is over. The human won.");
            }else{
                System.out.println("The game is over. The computer won.");
            }
            return true;
        }else{
            return false;
        }
    }
    
    private Integer handleDecision(){
        Integer decision;
        if(turn == 0){
            System.out.println("It is the computer's turn.");
            decision = generateComputerDecision();
            System.out.println("The computer removed " + decision + " marbles from the pile");
            turn = 1;
        }else{
            System.out.println("It is the player's turn.");
            decision = getPlayerDecision();
            System.out.println("The player removed " + decision + " marbles from the pile");
            turn = 0; 
        }
        return decision;
    }
    
    
    /*
    This is the game method. It runs the game and brings all of the functions together
    in this class in order to play the game of Nim.
    */
    public void playGame(){
        Integer decision = 0;
        System.out.println("There are " + this.numMarbles + " in the original marble stack");
        while(!checkForWin()){
            System.out.println("There are " + this.numMarbles + " marbles in the current marble stack");
            decision = handleDecision();
            numMarbles -= decision;
        }
    }
}
