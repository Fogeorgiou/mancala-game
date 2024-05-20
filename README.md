# Mancala Game Application

## Overview
This project is a back-end implementation of the Mancala Game using Java and Spring Boot.

## Authors
Foteini Georgiou

## Mancala game basics

### Setup
The current implementation assumes that the game will be played by 2 players.
The game contains a board which is made up of two rows (one for each player) of six pits each.
By default, six stones are placed in each of the 12 pits.
Each player has a large pit (called "store" or “Mancala”) to the right of the six pits.

### Objective
The objective of the game is to collect the most stones by the end of the game.

### Game Rules
1. The game begins with one player picking up all the stones from any of his own six pits.
2. Moving counter-clockwise, the player sows the stones one in each of the following pits until the stones run out.
3. If the player runs into his own Mancala, then the player can sow one stone in it. If the player runs into his opponent's Mancala, the player needs to skip it and
   continue moving to the next pit.
4. If the last stone is sowed in the player's Mancala, the player takes another turn.
5. If the last stone is sowed in an empty pit on the player's side, the player takes that stone and any stones in the pit directly
   opposite and places them in his own Mancala.
6. The game ends when all six pits on one side of the board are empty.
8. The player who still has stones on his side of the board when the game ends, takes all those stones and places them in his Mancala.
9. When the game is finished, the stones in each Mancala get counted. The winner of the game is the player with the most stones.

## API Documentation
The current implementation supports 3 endpoints:
1. 'Generate new game' endpoint - it starts a new game
2. 'Play game' endpoint - it is used every time a player wants to play and make his next move
3. 'Get game' endpoint - it returns the details of the requested game

A Postman collection containing the calls to these endpoints is provided in the directory `postman`.
It can be imported and used for familiarisation with the API and testing.

## Install & Run

### Database setup
For the application to work properly, the `mancala_game_db` db needs to be created and populated.
The `mancala_game_db` db can be created using the sql query found in: [mancala_game_db-creation-query.sql](https://github.com/Fogeorgiou/mancala-game/blob/master/src/main/resources/static/db_queries/mancala_game_db-creation-query.sql)
Then, the `game` table needs to be created using the sql queries found in: [game-table-creation-queries.sql](https://github.com/Fogeorgiou/mancala-game/blob/master/src/main/resources/static/db_queries/game-table-creation-queries.sql)

### Run
1. Clone the git repository:
```
$ git clone https://github.com/Fogeorgiou/mancala-game.git
```
2. Go to the project directory:
```
$ cd mancala-game
```
3. Start the web server by running:
```
$ mvn spring-boot:run
```
or by running the `MancalaGameApplication` class. The app should then be available at `http://localhost:9090`.
Using the endpoints mentioned above, the game can be played.

## Notes
The file [example-game-flow.txt](https://github.com/Fogeorgiou/mancala-game/blob/master/src/main/resources/static/example-game-flow.txt) contains the steps of a simplified version of the game (with 4 stones per pit) that 
lead easily and quickly to a finished game.