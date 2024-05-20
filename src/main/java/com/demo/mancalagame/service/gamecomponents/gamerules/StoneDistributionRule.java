package com.demo.mancalagame.service.gamecomponents.gamerules;

import com.demo.mancalagame.entity.Game;
import com.demo.mancalagame.entity.Pit;
import com.demo.mancalagame.service.gamecomponents.GameConstants;

import java.util.List;

/**
 * Performs the distribution of stones based on the pit a player has selected to play with.
 */
public class StoneDistributionRule extends GameRule {

    public StoneDistributionRule() {
        this.ruleName = RuleName.STONE_DISTRIBUTION_RULE.toString();
    }

    @Override
    public void apply(Game game, Pit pitFromRequest) {

        // Get list of pits from the board
        List<Pit> pits = game.getBoard().getPits();

        int numberOfStonesLeftToBeDistributed = pitFromRequest.getNumberOfStones();

        // The first pit to be updated will be the one following the pit of the request
        int pitIndexToUpdate = pitFromRequest.getId() + 1;

        // The number of pits to be updated is equal to the number of stones contained in the pit of the request
        while (numberOfStonesLeftToBeDistributed > 0) {

            if (pitIndexToUpdate > pits.size()) {
                pitIndexToUpdate = 1;
            }

            Pit pitToUpdate = game.getBoard().getPitById(pitIndexToUpdate);
            // A stone cannot be placed in the opponent's large pit.
            if (!isLargePitOfTheOpponent(pitToUpdate, pitFromRequest.getPlayerId())) {
                pitToUpdate.setNumberOfStones(pitToUpdate.getNumberOfStones() + GameConstants.ONE);
                numberOfStonesLeftToBeDistributed--;
            }

            pitIndexToUpdate++;
        }

        // The pit from the request should have zero stones after the stone distribution
        game.getBoard().getPitById(pitFromRequest.getId()).setNumberOfStones(GameConstants.ZERO);

        game.setLastUpdatedPit(game.getBoard().getPitById(pitIndexToUpdate-1));

        game.setLastRuleApplied(this.ruleName);
    }

    private boolean isLargePitOfTheOpponent(Pit pit, int playerId) {
        return pit.isLargePit() && playerId != pit.getPlayerId();
    }
}
