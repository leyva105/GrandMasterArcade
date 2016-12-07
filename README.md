# GrandMasterArcade
Multi-Threading Program w/ Text Based Games


Grand Master Arcade: Arcade for the Masters

Plan is to utilize multithreading and create multiple threads for the clients connecting to the Master's server as well as threads for the individual games themselves. (As of now)
    - TicTacToe
    - Hangman

**Essentially each game will have an array where the clients are added to, to manage the clients**
TicTacToe will consist of:

    - Two clients connecting to the server to begin the game
    - Clients will take turns playing marking spots on the grid.
    - Once game finishes, will prompt user if they want to play again or not.
    - Will try to use a GUI but if not will stick to text based style.


Hangman wiil consist of:
    - Two clients connecting to the server
    - One client will input the word with a general hint or category.
    - Second client has to guess the word given the hints that the first client gives
    - In order for hints to be given, the two clients will have a chat server opened for them as well in case more hints want to be given
    - So aside from them having to start the game, there were also be a messenger that runs so they can converse with each other.

Each program will be needed the javac compiler to run. 


Modifications that were made and Notable errors for each program:

    - Originally I had intended to created the game of war with TCP implementation as well as Battleship with TCP implementation. Since we were on a bit of a time crunch I had to scrap both ideas and use two that would be ideal for the time frame given.
    - The TicTacToe game has a problem when it comes to viewing the individual marks for each player. The "x's" and "o's" do not appear. I will be fixing that this weekend (Dec.2 - 4).
    - New Hangman Game will be added this weekend ^^^ as well, with only game implementation. The messenger will come hopefully that monday so it is fully functioning.
    - I've yet to decide how I will go about making a main screen for the games or if it will be just something simple where both games are easily accessible and nothing too fancy. (Soon to come).
