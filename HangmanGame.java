import java.util.*;
/**
 * Title: Hangman.java
 * Author: Carlos Leyva
 * Date: 12/10/2016
 *
 */
public class HangmanGame {

	public static void main(String[] args) {
		
		//String variables
		String word = getWord();
		String guess = "";
		
		//Integer variables.
		int guesses = 4;
		int filled = 0;
		int place = 0;
		int choice = 0;
		
		//Scanner object
		Scanner keyboard = new Scanner(System.in);
		
		//Character variables.
		char white = '_';
		char hint;
		char char1 = '\0';
		
		//boolean variables.
		boolean atLeastAlpha = guess.matches(".*[a-zA-Z]+.*");//[a-zA-Z] makes input all case.
		boolean valid = false;
		boolean isThere = false;
		
		
		//Putting the string into an array list, to later compare values to.
		ArrayList<Character> charlist = new ArrayList<Character>();
		for(int i = 0; i < word.length(); i++)
		{
			charlist.add(Character.valueOf(word.charAt(i)));
		}
		
		
		//Creating the game board
		ArrayList<Character> board = new ArrayList<Character>();
		for(int i = 0; i < word.length();i++)
		{
			if(Character.isWhitespace(word.charAt(i)))
				board.add('#');
			else
			{
				board.add('_');
			}
				
		}
		
		//Outputting the gameboard
		System.out.print("So far, the word is:");
		for(int i = 0; i < board.size(); i++)
		{	
			System.out.print(board.get(i) + " ");
		}
		
			//Main Game Loop
		while(guesses != 0){
			
			//A do-while loop to validate a correct choice
			do{
				try{
					
					System.out.println();
					System.out.println("You have " + guesses + " incorrect guesses left");
					System.out.print("Enter either 1 for guessing or 2 for hint:");
					choice = keyboard.nextInt();
					System.out.print("You entered " + choice);
					valid = true;
						
				}
				catch(Exception e)
				{
					keyboard.nextLine();
					System.out.println("Incorrect input");
				}
			}while(valid != true);
			
			keyboard.nextLine();
			
			//loop for if the user chose 1
			if(choice == 1)
			{
				
				
				//making sure the length is of size 1 and it is only a letter
				while(guess.length() != 1 && atLeastAlpha == false)
				{	
					//keyboard.nextLine();
					System.out.println();
					System.out.println("Enter your guess:");
					
					guess = keyboard.nextLine();
					
					char1 = guess.charAt(0);
					//if the conditions are not meet, loop it
					if(guess.length() > 1)
					{	
						keyboard.nextLine();
						System.out.println("Incorrect input");
					}
					
				
					//System.out.println("input is:" + guess);
				}
				
				
				
				//checking if the letter is the arraylist and saving the index
				for(int i = 0; i < charlist.size();i++){
					
					if(charlist.get(i).equals(new Character(char1)))
					{
						 board.set(i, char1);
						isThere = true;
						filled++;
						
					}
					
				}
				
				
				//if 'isThere' is false, that means the letter is not part of the actual word
				if(isThere == false)
				{
					System.out.println("Sorry " + guess + " isn't part of the word");
					guesses = guesses - 1;
					if(guesses == 0)
					{
						System.out.print("You failed. The word was ");
						for(int i = 0; i < charlist.size();i++)
						{
							System.out.print(charlist.get(i));
						}
						
						ender();
					}
					
				}//Else statement to print to the user notifying them that their guess is part of the word.
				else if(isThere == true)
				{
					System.out.println("That's right " + guess + " is in the word!");
				}
				
			
				
				//replacing the index of the board with the letter
				
				for(int i = 0; i < board.size(); i++)
				{	
					System.out.print(board.get(i) + " ");
				}
				//If statement to check 
				if(filled == charlist.size())
				{
					System.out.println();
					System.out.print("Congratulations!");
					
					for(int i = 0; i < board.size(); i++)
					{	
						System.out.print(board.get(i) + " ");
					}
					
					System.out.print(" is the word!");
					
					ender();
				}
			}
			
			//Resetting back the variables to their default values.
			isThere = false;
			guess = "";
			
			//If statement if the user chooses the second choice.
			//If statement to handle the hints the user has.
		     if(choice == 2)
			{
				if(guesses != 0)
				{
					guesses--;
					filled++;
					System.out.println();
					
					//Getting the next available character from the word
					while(!board.get(place).equals(white))
					{
						place++;
					}
					
					hint = charlist.get(place);
					
					System.out.print("OK! The hint is " + hint);
					
					board.set(place,hint);
					System.out.println();
					
					for(int i = 0; i < board.size(); i++)
					{	
						System.out.print(board.get(i) + " ");
					}
					
					System.out.println();
					
					System.out.println("But since you used the hint, you can guess "  + guesses + " more times.");
					
				
					//If statemen to run once the number of guesses equals 0.
					if(guesses == 0)
					{
						System.out.print("You failed. The word was ");
						for(int i = 0; i < charlist.size();i++)
						{
							System.out.print(charlist.get(i));
						}
						
						System.exit(0);
					}
					
				}
			
				
			}
				
		}
		
	}
	
//Method to get user input
public static String getWord(){
		
		System.out.println("--------- Welcome to Hangman ---------");
		Scanner input = new Scanner(System.in);
		System.out.print("Enter a word:");
	    return input.nextLine();
	}


}