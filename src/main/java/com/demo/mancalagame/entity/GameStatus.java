package com.demo.mancalagame.entity;

/**
 * Represents the status of a game
 * STARTED: When a new game gets generated
 * IN_PROGRESS: When a game is in progress
 * CURRENT_PLAYER_MOVE_FINISHED: When the appropriate game rules have run and it is time
 * for the next move to happen (either from the same player or the opponent)
 * FINISHED: When a game is finished and no more moves are allowed
 */
public enum GameStatus {

    STARTED, IN_PROGRESS, CURRENT_PLAYER_MOVE_FINISHED, FINISHED;
}
