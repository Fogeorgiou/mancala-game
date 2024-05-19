package com.demo.mancalagame.service.gamecomponents.gameroundvalidation;

import com.demo.mancalagame.entity.Game;
import com.demo.mancalagame.service.exception.*;
import com.demo.mancalagame.service.gamecomponents.GameConstants;
import com.demo.mancalagame.service.gamecomponents.GameRoundSelectionParameters;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PitValidationTest {

    private static PitValidation pitValidation;
    private static Game game;

    @BeforeAll
    public static void init() {

        pitValidation = new PitValidation();

        // Set up a game
        int numberOfStonesPerPit = 6;
        game = new Game(numberOfStonesPerPit);
    }

    @Test
    public void Should_ThrowInvalidPitException_When_SelectedPitToPlayWithIsInvalid() {

        int playerId = 1;
        int pitId = 20;
        GameRoundSelectionParameters gameRoundSelectionParameters = new GameRoundSelectionParameters(playerId, pitId);

        InvalidPitException thrown = assertThrows(
                InvalidPitException.class,
                () -> pitValidation.validate(gameRoundSelectionParameters, game),
                "Expected validate() to throw exception, but it didn't"
        );
        assertEquals(ExceptionMessage.INVALID_PIT_ID, thrown.getMessage());
    }

    @Test
    public void Should_ThrowPitOwnershipException_When_SelectedPitToPlayWithDoesNotBelongToSelectedPlayer() {

        int playerId = 1;
        int pitId = 10;
        GameRoundSelectionParameters gameRoundSelectionParameters = new GameRoundSelectionParameters(playerId, pitId);

        PitOwnershipException thrown = assertThrows(
                PitOwnershipException.class,
                () -> pitValidation.validate(gameRoundSelectionParameters, game),
                "Expected validate() to throw exception, but it didn't"
        );
        assertEquals(ExceptionMessage.INVALID_PIT_OWNERSHIP, thrown.getMessage());
    }

    @Test
    public void Should_ThrowInvalidPitTypeException_When_SelectedPitToPlayWithIsLargePit() {

        int playerId = 1;
        int pitId = 7;
        GameRoundSelectionParameters gameRoundSelectionParameters = new GameRoundSelectionParameters(playerId, pitId);

        InvalidPitTypeException thrown = assertThrows(
                InvalidPitTypeException.class,
                () -> pitValidation.validate(gameRoundSelectionParameters, game),
                "Expected validate() to throw exception, but it didn't"
        );
        assertEquals(ExceptionMessage.INVALID_PIT_TYPE, thrown.getMessage());
    }

    @Test
    public void Should_ThrowEmptyPitException_When_SelectedPitToPlayWithIsEmpty() {

        int playerId = 1;
        int pitId = 6;
        game.getBoard().getPitById(6).setNumberOfStones(GameConstants.ZERO);
        GameRoundSelectionParameters gameRoundSelectionParameters = new GameRoundSelectionParameters(playerId, pitId);

        EmptyPitException thrown = assertThrows(
                EmptyPitException.class,
                () -> pitValidation.validate(gameRoundSelectionParameters, game),
                "Expected validate() to throw exception, but it didn't"
        );
        assertEquals(ExceptionMessage.EMPTY_PIT, thrown.getMessage());
    }

    @Test
    public void Should_NotThrowException_When_SelectedPitIsValid() {

        int playerId = 1;
        int pitId = 2;
        GameRoundSelectionParameters gameRoundSelectionParameters = new GameRoundSelectionParameters(playerId, pitId);

        assertDoesNotThrow(
                () -> pitValidation.validate(gameRoundSelectionParameters, game),
                "Expected validate() to not throw exception, but it did"
        );
    }
}
