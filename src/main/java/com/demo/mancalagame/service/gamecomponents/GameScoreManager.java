package com.demo.mancalagame.service.gamecomponents;

import com.demo.mancalagame.entity.Game;

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
}
