package com.demo.mancalagame.service.gamecomponents.gamerules;

import com.demo.mancalagame.entity.Game;
import com.demo.mancalagame.entity.GameStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameRuleManagerTest {

    private static Game game;

    @BeforeAll
    public static void init() {

        // Set up a game
        int numberOfStonesPerPit = 6;
        game = new Game(numberOfStonesPerPit);
    }

    @Test
    public void Should_ReturnStoneDistributionRuleAsNextRuleToApply_When_GameStatusIsStarted() {

        game.setGameStatus(GameStatus.STARTED);

        GameRule nextRuleToApply = GameRuleManager.getNextRuleToApply(game);

        assertTrue(nextRuleToApply instanceof StoneDistributionRule);
    }

    @Test
    public void Should_ReturnLastStoneOnEmptyPitRuleAsNextRuleToApply_When_GameStatusIsInProgress_And_LastRuleAppliedWasStoneDistributionRule() {

        game.setGameStatus(GameStatus.IN_PROGRESS);
        GameRuleManager.lastRuleApplied = new StoneDistributionRule();

        GameRule nextRuleToApply = GameRuleManager.getNextRuleToApply(game);

        assertTrue(nextRuleToApply instanceof LastStoneOnEmptyPitRule);
    }

    @Test
    public void Should_ReturnLastStoneOnLargePitRuleAsNextRuleToApply_When_GameStatusIsInProgress_And_LastRuleAppliedWasLastStoneOnEmptyPitRule() {

        game.setGameStatus(GameStatus.IN_PROGRESS);
        GameRuleManager.lastRuleApplied = new LastStoneOnEmptyPitRule();

        GameRule nextRuleToApply = GameRuleManager.getNextRuleToApply(game);

        assertTrue(nextRuleToApply instanceof LastStoneOnLargePitRule);
    }

    @Test
    public void Should_ReturnStoneDistributionRuleAsNextRuleToApply_When_GameStatusIsInProgress_And_LastRuleAppliedWasLastStoneOnLargePitRule() {

        game.setGameStatus(GameStatus.IN_PROGRESS);
        GameRuleManager.lastRuleApplied = new LastStoneOnLargePitRule();

        GameRule nextRuleToApply = GameRuleManager.getNextRuleToApply(game);

        assertTrue(nextRuleToApply instanceof StoneDistributionRule);
    }

    @Test
    public void Should_ReturnNull_When_GameStatusIsCurrentPlayerMoveFinished() {

        game.setGameStatus(GameStatus.CURRENT_PLAYER_MOVE_FINISHED);

        GameRule nextRuleToApply = GameRuleManager.getNextRuleToApply(game);

        assertNull(nextRuleToApply);
    }

    @Test
    public void Should_ReturnNull_When_GameStatusIsFinished() {

        game.setGameStatus(GameStatus.FINISHED);

        GameRule nextRuleToApply = GameRuleManager.getNextRuleToApply(game);

        assertNull(nextRuleToApply);
    }
}
