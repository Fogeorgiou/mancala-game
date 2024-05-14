package com.demo.mancalagame.service.gamecomponents.gamerules;

import com.demo.mancalagame.entity.Game;
import com.demo.mancalagame.entity.Pit;
import com.demo.mancalagame.util.GameConstants;

import java.util.List;

public class StoneDistributionRule extends GameRule {

    @Override
    public void apply(Game game, Pit pitFromRequest) {

        // Get list of pits from the board
        List<Pit> pits = game.getBoard().getPits();

        int numberOfStonesLeftToBeDistributed = pitFromRequest.getNumberOfStones();

        // The first pit to be updated will be the one following the pit of the request
        int pitIndexToUpdate = pitFromRequest.getId() + 1;

        // The number of pits to be updated is equal to the number of stones contained in the pit of the request
        while (numberOfStonesLeftToBeDistributed > 0) {

            if (pitIndexToUpdate >= pits.size()) {
                pitIndexToUpdate = 1;
            }

            Pit pitToUpdate = game.getBoard().getPitById(pitIndexToUpdate);
            if (!isLargePitOfTheOpponent(pitToUpdate, pitFromRequest.getPlayerId())) {
                pitToUpdate.setNumberOfStones(pitToUpdate.getNumberOfStones() + GameConstants.ONE);
                numberOfStonesLeftToBeDistributed--;
            }

            pitIndexToUpdate++;
        }

        // The pit from the request should have zero stones after the stone distribution
        game.getBoard().getPitById(pitFromRequest.getId()).setNumberOfStones(GameConstants.ZERO);

        game.setLastUpdatedPit(game.getBoard().getPitById(pitIndexToUpdate-1));

        GameRuleManager.lastRuleApplied = this;
    }

    private boolean isLargePitOfTheOpponent(Pit pit, int playerId) {
        return pit.isLargePit() && playerId != pit.getPlayerId();
    }
}