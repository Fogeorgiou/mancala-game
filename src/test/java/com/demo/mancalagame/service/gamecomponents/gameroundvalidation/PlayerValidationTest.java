package com.demo.mancalagame.service.gamecomponents.gameroundvalidation;

import com.demo.mancalagame.entity.Game;
import com.demo.mancalagame.service.exception.ExceptionMessage;
import com.demo.mancalagame.service.exception.InvalidPlayerException;
import com.demo.mancalagame.service.exception.WrongPlayerTurnException;
import com.demo.mancalagame.service.gamecomponents.GameRoundSelectionParameters;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerValidationTest {

    private static PlayerValidation playerValidation;
    private static Game game;

    @BeforeAll
    public static void init() {

        playerValidation = new PlayerValidation();

        // Set up a game
        int numberOfStonesPerPit = 6;
        game = new Game(numberOfStonesPerPit);
    }

    @Test
    public void Should_ThrowInvalidPlayerException_When_SelectedPlayerToPlayIsInvalid() {

        int playerId = 20;
        int pitIndex = 2;
        GameRoundSelectionParameters gameRoundSelectionParameters = new GameRoundSelectionParameters(playerId, pitIndex);

        InvalidPlayerException thrown = assertThrows(
                InvalidPlayerException.class,
                () -> playerValidation.validate(gameRoundSelectionParameters, game),
                "Expected validate() to throw exception, but it didn't"
        );
        assertEquals(ExceptionMessage.INVALID_PLAYER_ID, thrown.getMessage());
    }

    @Test
    public void Should_ThrowWrongPlayerTurnException_When_SelectedPlayerToPlayIsInvalid() {

        int playerId = 2;
        int pitIndex = 10;
        GameRoundSelectionParameters gameRoundSelectionParameters = new GameRoundSelectionParameters(playerId, pitIndex);

        WrongPlayerTurnException thrown = assertThrows(
                WrongPlayerTurnException.class,
                () -> playerValidation.validate(gameRoundSelectionParameters, game),
                "Expected validate() to throw exception, but it didn't"
        );
        assertEquals(ExceptionMessage.WRONG_PLAYER_TURN, thrown.getMessage());
    }

    @Test
    public void Should_NotThrowException_When_SelectedPlayerIsValid() {

        int playerId = 1;
        int pitIndex = 2;
        GameRoundSelectionParameters gameRoundSelectionParameters = new GameRoundSelectionParameters(playerId, pitIndex);

        assertDoesNotThrow(
                () -> playerValidation.validate(gameRoundSelectionParameters, game),
                "Expected validate() to not throw exception, but it did"
        );
    }
}
