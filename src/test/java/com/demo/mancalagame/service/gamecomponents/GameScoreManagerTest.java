package com.demo.mancalagame.service.gamecomponents;

import com.demo.mancalagame.entity.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameScoreManagerTest {

    private Game game;

    @BeforeEach
    public void init() {

        // Set up a game
        int numberOfStonesPerPit = 6;
        game = new Game(numberOfStonesPerPit);
    }

    @Test
    public void Should_UpdateTheScorePerPlayer() {

        // Set the number of stones contained in the large pit of each player
        int firstPlayerId = game.getPlayers().get(0).getId();
        int secondPlayerId = game.getPlayers().get(1).getId();
        game.getBoard().getLargePitByPlayerId(firstPlayerId).setNumberOfStones(10);
        game.getBoard().getLargePitByPlayerId(secondPlayerId).setNumberOfStones(5);

        // The score per player before the update should be 0
        assertEquals(0, game.getScorePerPlayer().get(firstPlayerId));
        assertEquals(0, game.getScorePerPlayer().get(secondPlayerId));

        GameScoreManager.updateScorePerPlayer(game);

        assertEquals(10, game.getScorePerPlayer().get(firstPlayerId));
        assertEquals(5, game.getScorePerPlayer().get(secondPlayerId));
    }

    @Test
    public void Should_ReturnTheIdOfThePlayerThatWonTheGame() {

        // Set the score per player
        int firstPlayerId = game.getPlayers().get(0).getId();
        int secondPlayerId = game.getPlayers().get(1).getId();
        game.getScorePerPlayer().put(firstPlayerId, 10);
        game.getScorePerPlayer().put(secondPlayerId, 5);

        int gameWinner = GameScoreManager.getGameWinner(game);

        assertEquals(firstPlayerId, gameWinner);
    }
}
