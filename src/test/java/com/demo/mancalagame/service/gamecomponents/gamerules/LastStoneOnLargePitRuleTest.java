package com.demo.mancalagame.service.gamecomponents.gamerules;

import com.demo.mancalagame.entity.Game;
import com.demo.mancalagame.entity.Pit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LastStoneOnLargePitRuleTest {

    private LastStoneOnLargePitRule lastStoneOnLargePitRule;
    private Game game;

    @BeforeEach
    public void init() {

        lastStoneOnLargePitRule = new LastStoneOnLargePitRule();

        // Set up a game
        int numberOfStonesPerPit = 6;
        game = new Game(numberOfStonesPerPit);
    }

    @Test
    public void Should_LetCurrentPlayerPlayAgain_When_CurrentPlayerLastStoneEndsInTheirLargePit() {

        // Set up the last updated pit
        game.setLastUpdatedPit(new Pit(6, 1, 1, true));

        // Set up game round selections
        int pitIndex = 2;
        int numberOfStones = 6;
        // Represents the player currently playing
        int playerId = 1;
        Pit pitToPlayWith = new Pit(pitIndex, numberOfStones, playerId, false);

        // Apply the last stone on large pit rule
        lastStoneOnLargePitRule.apply(game, pitToPlayWith);

        // After the application of the rule, the current player should be allowed to play again
        assertEquals(1, game.getPlayerTurn());
    }

    @Test
    public void Should_ChangePlayerTurn_When_CurrentPlayerLastStoneDoesNotEndInTheirLargePit() {

        // Set up the last updated pit
        game.setLastUpdatedPit(new Pit(8, 2, 2, false));

        // Set up game round selections
        int pitIndex = 2;
        int numberOfStones = 6;
        // Represents the player currently playing
        int playerId = 1;
        Pit pitToPlayWith = new Pit(pitIndex, numberOfStones, playerId, false);

        // Apply the last stone on large pit rule
        lastStoneOnLargePitRule.apply(game, pitToPlayWith);

        // After the application of the rule, the player turn should change
        assertEquals(2, game.getPlayerTurn());
    }

    @Test
    public void Should_LetFirstPlayerPlayAgain_When_LastMoveWasMadeByLastPlayer() {

        // Set up the last updated pit
        game.setLastUpdatedPit(new Pit(8, 2, 2, false));

        // Set up game round selections
        int pitIndex = 2;
        int numberOfStones = 6;
        // Represents the player currently playing
        int playerId = 2;
        Pit pitToPlayWith = new Pit(pitIndex, numberOfStones, playerId, false);

        // Apply the last stone on large pit rule
        lastStoneOnLargePitRule.apply(game, pitToPlayWith);

        // After the application of the rule, the player turn should change
        assertEquals(1, game.getPlayerTurn());
    }
}
