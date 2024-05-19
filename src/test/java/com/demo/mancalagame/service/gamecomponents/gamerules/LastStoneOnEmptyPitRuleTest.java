package com.demo.mancalagame.service.gamecomponents.gamerules;

import com.demo.mancalagame.entity.Game;
import com.demo.mancalagame.entity.Pit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LastStoneOnEmptyPitRuleTest {

    private LastStoneOnEmptyPitRule lastStoneOnEmptyPitRule;
    private Game game;

    @BeforeEach
    public void init() {

        lastStoneOnEmptyPitRule = new LastStoneOnEmptyPitRule();

        // Set up a game
        int numberOfStonesPerPit = 6;
        game = new Game(numberOfStonesPerPit);
    }

    @Test
    public void Should_MoveStonesFromLastUpdatedPitAndOppositePitToPlayerLargePit_When_CurrentPlayerLastStoneEndsInEmptyPit() {

        // Set up the last updated pit
        game.setLastUpdatedPit(new Pit(5, 1, 1, false));

        // Set up game round selections
        int pitIndex = 2;
        int numberOfStones = 6;
        // Represents the player currently playing
        int playerId = 1;
        Pit pitToPlayWith = new Pit(pitIndex, numberOfStones, playerId, false);

        // Stone distribution before the application of the rule
        assertEquals(0, game.getBoard().getLargePitByPlayerId(1).getNumberOfStones());
        assertEquals(1, game.getLastUpdatedPit().getNumberOfStones());
        Pit oppositePit = game.getBoard().getPitById(game.getBoard().getPits().size() - game.getLastUpdatedPit().getId());
        assertEquals(6, oppositePit.getNumberOfStones());

        // Apply the last stone on empty pit rule
        lastStoneOnEmptyPitRule.apply(game, pitToPlayWith);

        // After the application of the rule, the stones from the last updated pit and the stones from its opposite pit
        // are placed into the large pit of player 1
        assertEquals(7, game.getBoard().getLargePitByPlayerId(1).getNumberOfStones());
        assertEquals(0, game.getLastUpdatedPit().getNumberOfStones());
        assertEquals(0, game.getBoard().getPitById(game.getLastUpdatedPit().getId()).getNumberOfStones());
        assertEquals(0, oppositePit.getNumberOfStones());
    }

    @Test
    public void Should_MakeNoChangesToTheStoneDistribution_When_CurrentPlayerLastStoneEndsInEmptyPit_And_OppositePitIsEmpty() {

        // Set up the last updated pit
        game.setLastUpdatedPit(new Pit(5, 1, 1, false));

        // Put 0 stones in the pit opposite to last updated pit
        game.getBoard().getPitById(9).setNumberOfStones(0);

        // Set up game round selections
        int pitIndex = 2;
        int numberOfStones = 6;
        // Represents the player currently playing
        int playerId = 1;
        Pit pitToPlayWith = new Pit(pitIndex, numberOfStones, playerId, false);

        // Stone distribution before the application of the rule
        assertEquals(0, game.getBoard().getLargePitByPlayerId(1).getNumberOfStones());
        assertEquals(1, game.getLastUpdatedPit().getNumberOfStones());
        Pit oppositePit = game.getBoard().getPitById(game.getBoard().getPits().size() - game.getLastUpdatedPit().getId());
        assertEquals(0, oppositePit.getNumberOfStones());

        // Apply the last stone on empty pit rule
        lastStoneOnEmptyPitRule.apply(game, pitToPlayWith);

        // After the application of the rule, nothing changes
        assertEquals(0, game.getBoard().getLargePitByPlayerId(1).getNumberOfStones());
        assertEquals(1, game.getLastUpdatedPit().getNumberOfStones());
        assertEquals(0, oppositePit.getNumberOfStones());
    }
}
