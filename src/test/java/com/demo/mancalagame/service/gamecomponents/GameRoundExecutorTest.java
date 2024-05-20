package com.demo.mancalagame.service.gamecomponents;

import com.demo.mancalagame.entity.Game;
import com.demo.mancalagame.entity.GameStatus;
import com.demo.mancalagame.service.gamecomponents.gamerules.GameRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GameRoundExecutorTest {

    private static GameRoundExecutor gameRoundExecutor;

    @BeforeAll
    public static void init() {
        gameRoundExecutor = new GameRoundExecutor();
    }

    @Test
    public void Should_PlayGameRound_When_GameStatusIsStarted() {

        // given
        int numberOfStonesPerPit = 6;
        Game game = new Game(numberOfStonesPerPit);

        int playerId = 1;
        int pitId = 1;
        GameRoundSelectionParameters gameRoundSelectionParameters = new GameRoundSelectionParameters(playerId, pitId);

        // when
        gameRoundExecutor.play(game, gameRoundSelectionParameters);

        // then
        assertEquals(GameStatus.IN_PROGRESS, game.getGameStatus());

        // We expect the 6 stones from pit 1 to be distributed to pits 2 - 7
        assertEquals(0, game.getBoard().getPitById(pitId).getNumberOfStones());
        assertEquals(7, game.getBoard().getPitById(2).getNumberOfStones());
        assertEquals(7, game.getBoard().getPitById(3).getNumberOfStones());
        assertEquals(7, game.getBoard().getPitById(4).getNumberOfStones());
        assertEquals(7, game.getBoard().getPitById(5).getNumberOfStones());
        assertEquals(7, game.getBoard().getPitById(6).getNumberOfStones());
        assertEquals(1, game.getBoard().getPitById(7).getNumberOfStones());

        // Since the last stone ends in the player's large pit, the same player will play again
        assertEquals(1, game.getPlayerTurn());

        assertEquals(1, game.getScorePerPlayer().get(playerId));
    }

    @Test
    public void Should_PlayGameRound_When_GameStatusIsInProgress() {

        // given
        int numberOfStonesPerPit = 6;
        int firstPlayerId = 1;
        int secondPlayerId = 2;

        // Set up game as if player 1 has already played with pit 2
        Game game = new Game(numberOfStonesPerPit);

        game.setGameStatus(GameStatus.IN_PROGRESS);

        game.getBoard().getPitById(2).setNumberOfStones(0);
        game.getBoard().getPitById(3).setNumberOfStones(7);
        game.getBoard().getPitById(4).setNumberOfStones(7);
        game.getBoard().getPitById(5).setNumberOfStones(7);
        game.getBoard().getPitById(6).setNumberOfStones(7);
        game.getBoard().getPitById(7).setNumberOfStones(1);
        game.getBoard().getPitById(8).setNumberOfStones(7);

        game.setPlayerTurn(secondPlayerId);

        // The score for player 1 is set to 1 as there is 1 stone in their large pit
        game.getScorePerPlayer().put(firstPlayerId, 1);

        game.setLastRuleApplied(GameRule.RuleName.LAST_STONE_ON_LARGE_PIT_RULE.toString());

        int pitId = 9;
        GameRoundSelectionParameters gameRoundSelectionParameters = new GameRoundSelectionParameters(secondPlayerId, pitId);

        // when
        gameRoundExecutor.play(game, gameRoundSelectionParameters);

        // then
        assertEquals(GameStatus.IN_PROGRESS, game.getGameStatus());

        // We expect the 6 stones from pit 9 to be distributed to pits 10 - 14 and 1
        assertEquals(0, game.getBoard().getPitById(pitId).getNumberOfStones());
        assertEquals(7, game.getBoard().getPitById(10).getNumberOfStones());
        assertEquals(7, game.getBoard().getPitById(11).getNumberOfStones());
        assertEquals(7, game.getBoard().getPitById(12).getNumberOfStones());
        assertEquals(7, game.getBoard().getPitById(13).getNumberOfStones());
        assertEquals(1, game.getBoard().getPitById(14).getNumberOfStones());
        assertEquals(7, game.getBoard().getPitById(1).getNumberOfStones());

        // We expect the player turn to change from player 2 to player 1
        assertEquals(firstPlayerId, game.getPlayerTurn());

        assertEquals(1, game.getScorePerPlayer().get(secondPlayerId));
    }

    @Test
    public void Should_PlayGameRoundAndFinishGame_When_RoundEndsWithEmptyPitsOnAPlayerSide() {

        // given
        int numberOfStonesPerPit = 6;
        int firstPlayerId = 1;
        int secondPlayerId = 2;

        // Set up game as if it's turn for player 1 to play and player 1 has one last move to make
        // before all his pits get empty.
        Game game = new Game(numberOfStonesPerPit);

        game.setGameStatus(GameStatus.IN_PROGRESS);

        game.getBoard().getPitById(1).setNumberOfStones(0);
        game.getBoard().getPitById(2).setNumberOfStones(0);
        game.getBoard().getPitById(3).setNumberOfStones(0);
        game.getBoard().getPitById(4).setNumberOfStones(0);
        game.getBoard().getPitById(5).setNumberOfStones(0);
        game.getBoard().getPitById(6).setNumberOfStones(1);
        game.getBoard().getPitById(7).setNumberOfStones(4);

        game.setPlayerTurn(firstPlayerId);

        // The score for player 1 is set to 1 as there is 1 stone in their large pit
        game.getScorePerPlayer().put(firstPlayerId, 5);

        game.setLastRuleApplied(GameRule.RuleName.LAST_STONE_ON_LARGE_PIT_RULE.toString());

        int pitId = 6;
        GameRoundSelectionParameters gameRoundSelectionParameters = new GameRoundSelectionParameters(firstPlayerId, pitId);

        // when
        gameRoundExecutor.play(game, gameRoundSelectionParameters);

        // then
        assertEquals(GameStatus.FINISHED, game.getGameStatus());

        // We expect the 1 stone from pit 6 to be moved to pit 7
        assertEquals(0, game.getBoard().getPitById(pitId).getNumberOfStones());
        assertEquals(5, game.getBoard().getPitById(7).getNumberOfStones());
        // We expect the stones from pit 8 - 13 to be moved to pit 14. Each of those pits had 6 stones in it,
        // so 36 stones are expected to end in pit 14.
        assertEquals(0, game.getBoard().getPitById(8).getNumberOfStones());
        assertEquals(0, game.getBoard().getPitById(9).getNumberOfStones());
        assertEquals(0, game.getBoard().getPitById(10).getNumberOfStones());
        assertEquals(0, game.getBoard().getPitById(11).getNumberOfStones());
        assertEquals(0, game.getBoard().getPitById(12).getNumberOfStones());
        assertEquals(0, game.getBoard().getPitById(13).getNumberOfStones());
        assertEquals(36, game.getBoard().getPitById(14).getNumberOfStones());

        assertNull(game.getPlayerTurn());

        assertEquals(5, game.getScorePerPlayer().get(firstPlayerId));
        assertEquals(36, game.getScorePerPlayer().get(secondPlayerId));

        assertEquals(secondPlayerId, game.getGameWinner());
    }
}
