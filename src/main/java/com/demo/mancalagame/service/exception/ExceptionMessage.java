package com.demo.mancalagame.service.exception;

public final class ExceptionMessage {

    public static final String GAME_NOT_FOUND = "No game with the specified id was found";

    // Exception messages related to player validation
    public static final String INVALID_PLAYER_ID = "The specified player id does not belong to a player of this game.";
    public static final String WRONG_PLAYER_TURN = "It is not the specified player's turn to play.";

    // Exception messages related to pit validation
    public static final String INVALID_PIT_ID = "The specified pit does not exist in the board.";
    public static final String INVALID_PIT_OWNERSHIP = "The specified pit does not belong to the specified player.";
    public static final String INVALID_PIT_TYPE = "The specified pit is a large pit. You can only move stones from a small pit.";
}
