package com.demo.mancalagame.service.gamecomponents;

import com.demo.mancalagame.entity.Game;

import java.util.Map;

public class GameScoreManager {

    public static void updateScorePerPlayer(Game game) {

        game.getPlayers()
                .forEach(player ->
                        game.getScorePerPlayer().put(
                                player.getId(),
                                game.getBoard().getLargePitByPlayerId(player.getId()).getNumberOfStones()
                        )
                );
    }

    public static int getGameWinner(Game game) {

        int maxScore = 0;
        int winner = 0;

        for (Map.Entry<Integer, Integer> scorePerPlayer : game.getScorePerPlayer().entrySet()) {
            if (scorePerPlayer.getValue().compareTo(maxScore) > 0) {
                maxScore = scorePerPlayer.getValue();
                winner = scorePerPlayer.getKey();
            }
        }

        return winner;
    }
}
