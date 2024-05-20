package com.demo.mancalagame.service.gamecomponents.gamerules;

import com.demo.mancalagame.entity.Game;
import com.demo.mancalagame.entity.GameStatus;
import com.demo.mancalagame.entity.Pit;
import com.demo.mancalagame.service.gamecomponents.GameConstants;

public class LastStoneOnEmptyPitRule extends GameRule {

    public LastStoneOnEmptyPitRule() {
        this.ruleName = RuleName.LAST_STONE_ON_EMPTY_PIT_RULE.toString();
    }

    @Override
    public void apply(Game game, Pit pitFromRequest) {

        // If the last updated pit is a small pit that belongs to the player currently playing and which was previously
        // empty, then check whether the pit opposite to it (it will be a pit belonging to the other player)
        // has any stones in it. If yes, then all the stones from the pit and its opposite pit should
        // be placed in the current player's pit. If not, nothing happens.
        if (!game.getLastUpdatedPit().isLargePit() &&
                game.getLastUpdatedPit().getNumberOfStones() == GameConstants.ONE &&
                pitFromRequest.getPlayerId() == game.getLastUpdatedPit().getPlayerId()) {
            // Get the pit opposite to the last updated pit
            Pit oppositePit = game.getBoard().getPitById(game.getBoard().getPits().size() - game.getLastUpdatedPit().getId());
            if (oppositePit.getNumberOfStones() > 0) {
                Pit largePitOfCurrentPlayer = game.getBoard().getLargePitByPlayerId(pitFromRequest.getPlayerId());
                largePitOfCurrentPlayer.setNumberOfStones(
                        largePitOfCurrentPlayer.getNumberOfStones() + GameConstants.ONE + oppositePit.getNumberOfStones());
                oppositePit.setNumberOfStones(GameConstants.ZERO);
                game.getLastUpdatedPit().setNumberOfStones(GameConstants.ZERO);
                game.getBoard().getPits().set(game.getLastUpdatedPit().getId() - 1, game.getLastUpdatedPit());
            }
            game.setGameStatus(GameStatus.CURRENT_PLAYER_MOVE_FINISHED);
        }

        game.setLastRuleApplied(this.ruleName);
    }
}
