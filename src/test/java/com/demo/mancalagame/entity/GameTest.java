package com.demo.mancalagame.entity;

import com.demo.mancalagame.service.gamecomponents.GameConstants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    public void Should_CreateGameWithInitialSetup() {

        // given
        int numberOfStonesPerPit = 6;

        // when
        Game game = new Game(numberOfStonesPerPit);

        // then
        assertNotNull(game.getGameId());

        assertEquals(GameStatus.STARTED, game.getGameStatus());

        assertEquals(GameConstants.NUMBER_OF_PLAYERS, game.getPlayers().size());
        for (int i=0; i<=GameConstants.NUMBER_OF_PLAYERS-1; i++) {
            assertEquals(i+1, game.getPlayers().get(i).getId());
            assertEquals(new StringBuilder().append("username").append(i+1).toString(), game.getPlayers().get(i).getUsername());
        }

        assertEquals(GameConstants.TOTAL_NUMBER_OF_PITS, game.getBoard().getPits().size());
        for (int i=0; i<GameConstants.TOTAL_NUMBER_OF_PITS; i++) {
            Pit pit = game.getBoard().getPits().get(i);
            assertEquals(i+1, pit.getId());
            if (pit.getId()<=7) {
                assertEquals(game.getPlayers().get(0).getId(), pit.getPlayerId());
            } else {
                assertEquals(game.getPlayers().get(1).getId(), pit.getPlayerId());
            }
            if (pit.getId()==7 || pit.getId()==14) {
                assertEquals(GameConstants.ZERO, pit.getNumberOfStones());
                assertTrue(pit.isLargePit());
            } else {
                assertEquals(numberOfStonesPerPit, pit.getNumberOfStones());
                assertFalse(pit.isLargePit());
            }
        }

        assertEquals(game.getPlayers().size(), game.getScorePerPlayer().size());
        game.getScorePerPlayer().entrySet().forEach(entry ->
                assertEquals(GameConstants.ZERO, entry.getValue())
        );

        assertEquals(game.getPlayers().get(0).getId(), game.getPlayerTurn());

        assertNull(game.getGameWinner());
    }
}
