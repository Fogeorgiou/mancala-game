package com.demo.mancalagame.service.gamerules;

import com.demo.mancalagame.entity.Game;
//import com.demo.mancalagame.entity.LargePit;
import com.demo.mancalagame.entity.Pit;
//import com.demo.mancalagame.entity.SmallPit;
import com.demo.mancalagame.service.gamecomponents.gamerules.LastStoneOnLargePitRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LastStoneOnLargePitRuleTest {

    LastStoneOnLargePitRule lastStoneOnLargePitRule;

    @BeforeEach
    public void init() {
        lastStoneOnLargePitRule = new LastStoneOnLargePitRule();
    }

    @Test
    public void Should_LetCurrentPlayerPlayAgain_When_CurrentPlayerLastStoneEndsInTheirLargePit() {

        // Set up a game
        int numberOfStonesPerPit = 6;
        Game game = new Game(numberOfStonesPerPit);

        // Set up the last updated pit
        game.setLastUpdatedPit(new Pit(6, 1, 1, true));

        // Set up game round selections
        int pitIndex = 2;
        int numberOfStones = 6;
        int playerId = 1;
        Pit pitToPlayWith = new Pit(pitIndex, numberOfStones, playerId, false);

        // Apply the last stone on large pit rule
        lastStoneOnLargePitRule.apply(game, pitToPlayWith);

        // After the application of the rule, the current player should be allowed to play again
        assertEquals(1, game.getPlayerTurn());
    }

    @Test
    public void Should_ChangePlayerTurn_When_CurrentPlayerLastStoneDoesNotEndInTheirLargePit() {

        // Set up a game
        int numberOfStonesPerPit = 6;
        Game game = new Game(numberOfStonesPerPit);

        // Set up the last updated pit
        game.setLastUpdatedPit(new Pit(8, 2, 2, false));

        // Set up game round selections
        int pitIndex = 2;
        int numberOfStones = 6;
        int playerId = 1;
        Pit pitToPlayWith = new Pit(pitIndex, numberOfStones, playerId, false);

        // Apply the last stone on large pit rule
        lastStoneOnLargePitRule.apply(game, pitToPlayWith);

        // After the application of the rule, the player turn should change
        assertEquals(2, game.getPlayerTurn());
    }
}
