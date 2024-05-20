package com.demo.mancalagame.service.gamecomponents.gamerules;

import com.demo.mancalagame.entity.Game;
import com.demo.mancalagame.entity.GameStatus;
import com.demo.mancalagame.entity.Pit;

/**
 * Checks whether the last stone of a player ended in his large pit (Mancala).
 */
public class LastStoneOnLargePitRule extends GameRule {

    public LastStoneOnLargePitRule() {
        this.ruleName = RuleName.LAST_STONE_ON_LARGE_PIT_RULE.toString();
    }

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
                // If the last player (from the list of players) has played, the next player to play
                // should be the first one from that list.
                nextPlayerId = game.getPlayers().get(0).getId();
            } else {
                nextPlayerId = pitFromRequest.getPlayerId() + 1;
            }
            game.setPlayerTurn(nextPlayerId);
        }

        game.setLastRuleApplied(this.ruleName);

        game.setGameStatus(GameStatus.CURRENT_PLAYER_MOVE_FINISHED);
    }
}
