package com.demo.mancalagame.service.gamecomponents.gamerules;

import com.demo.mancalagame.entity.Game;
//import com.demo.mancalagame.entity.LargePit;
import com.demo.mancalagame.entity.GameStatus;
import com.demo.mancalagame.entity.Pit;

public class LastStoneOnLargePitRule extends GameRule {

    @Override
    public void apply(Game game, Pit pitFromRequest) {

        // If the last updated pit is a large pit that belongs to the player currently playing,
        // then the same player will play again.
        if (game.getLastUpdatedPit().isLargePit() &&
                pitFromRequest.getPlayerId() == game.getLastUpdatedPit().getPlayerId()) {
            game.setPlayerTurn(pitFromRequest.getPlayerId());
        } else {
            int nextPlayerId;
            if (pitFromRequest.getPlayerId() == game.getPlayers().size()) {
                // The next player to play should be the first one from the list of players
//                nextPlayerId = 1;
                nextPlayerId = game.getPlayers().get(0).getId();
            } else {
                nextPlayerId = pitFromRequest.getPlayerId() + 1;
            }
            game.setPlayerTurn(nextPlayerId);
        }

        GameRuleManager.lastRuleApplied = this;

        game.setGameStatus(GameStatus.CURRENT_PLAYER_MOVE_FINISHED);
    }
}
