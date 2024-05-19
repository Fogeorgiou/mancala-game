package com.demo.mancalagame.service.gamecomponents.gamerules;

import com.demo.mancalagame.entity.Game;
import com.demo.mancalagame.entity.Pit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StoneDistributionRuleTest {

    private StoneDistributionRule stoneDistributionRule;
    private Game game;

    @BeforeEach
    public void init() {

        stoneDistributionRule = new StoneDistributionRule();
        // Set up a game
        int numberOfStonesPerPit = 6;
        game = new Game(numberOfStonesPerPit);
    }

    @Test
    public void Should_DistributeStonesFromSelectedPitToFollowingPits_When_SelectedPitContainsStones() {

        // Set up game round selections
        int pitIndex = 2;
        int numberOfStones = 6;
        // Represents the player currently playing
        int playerId = 1;
        Pit pitToPlayWith = new Pit(pitIndex, numberOfStones, playerId, false);

        // Apply the stone distribution rule
        stoneDistributionRule.apply(game, pitToPlayWith);

        // After the stone distribution the number of stones in the selected pit (index 2) should be 0
        assertEquals(0, game.getBoard().getPitById(pitIndex).getNumberOfStones());

        // The number of stones in each of the following 6 pits should be increased by 1
        assertEquals(7, game.getBoard().getPitById(3).getNumberOfStones());
        assertEquals(7, game.getBoard().getPitById(4).getNumberOfStones());
        assertEquals(7, game.getBoard().getPitById(5).getNumberOfStones());
        // Index 6 represents the player's large pit
        assertEquals(7, game.getBoard().getPitById(6).getNumberOfStones());
        assertEquals(1, game.getBoard().getPitById(7).getNumberOfStones());
        assertEquals(7, game.getBoard().getPitById(8).getNumberOfStones());
        // The number of stones in the rest of the pits should not change
        assertEquals(6, game.getBoard().getPitById(9).getNumberOfStones());
        assertEquals(6, game.getBoard().getPitById(10).getNumberOfStones());
        assertEquals(6, game.getBoard().getPitById(11).getNumberOfStones());
        assertEquals(6, game.getBoard().getPitById(12).getNumberOfStones());
        // Index 13 represents the opponent's player's large pit
        assertEquals(6, game.getBoard().getPitById(13).getNumberOfStones());
        assertEquals(0, game.getBoard().getPitById(14).getNumberOfStones());
        assertEquals(6, game.getBoard().getPitById(1).getNumberOfStones());
    }

    @Test
    public void Should_DistributeStonesFromSelectedPitToFollowingPitsAndSkipOpponentLargePit_When_OpponentLargePitIsMet() {

        // Set up game round selections
        int pitIndex = 4;
        int numberOfStones = 10;
        // Represents the player currently playing
        int playerId = 1;
        Pit pitToPlayWith = new Pit(pitIndex, numberOfStones, playerId, false);

        // Apply the stone distribution rule
        stoneDistributionRule.apply(game, pitToPlayWith);

        // After the stone distribution the number of stones in the selected pit (index 4) should be 0
        assertEquals(0, game.getBoard().getPitById(pitIndex).getNumberOfStones());
        // The number of stones in each of the following 10 pits excluding the large pit of player 2
        // should be increased by 1
        assertEquals(7, game.getBoard().getPitById(5).getNumberOfStones());
        // Index 6 represents the player's large pit
        assertEquals(7, game.getBoard().getPitById(6).getNumberOfStones());
        assertEquals(1, game.getBoard().getPitById(7).getNumberOfStones());
        assertEquals(7, game.getBoard().getPitById(8).getNumberOfStones());
        assertEquals(7, game.getBoard().getPitById(9).getNumberOfStones());
        assertEquals(7, game.getBoard().getPitById(10).getNumberOfStones());
        assertEquals(7, game.getBoard().getPitById(11).getNumberOfStones());
        assertEquals(7, game.getBoard().getPitById(12).getNumberOfStones());
        // Index 13 represents the opponent's player's large pit. It is skipped and not updated.
        assertEquals(7, game.getBoard().getPitById(13).getNumberOfStones());
        assertEquals(0, game.getBoard().getPitById(14).getNumberOfStones());
        assertEquals(7, game.getBoard().getPitById(1).getNumberOfStones());
        // The number of stones in the rest of the pits should not change.
        assertEquals(6, game.getBoard().getPitById(2).getNumberOfStones());
        assertEquals(6, game.getBoard().getPitById(3).getNumberOfStones());
    }
}
