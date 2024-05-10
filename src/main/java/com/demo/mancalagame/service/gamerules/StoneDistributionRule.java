package com.demo.mancalagame.service.gamerules;

import com.demo.mancalagame.entity.Game;
import com.demo.mancalagame.entity.Pit;

import java.util.List;

public class StoneDistributionRule extends GameRule {

    @Override
    public void applyRule(Game game, Pit pitFromRequest) {

        // Get list of pits from the board
        List<Pit> pits = game.getBoard().getPits();

        // Identify the pits to which the stones will be distributed.
        // The indexes of those pits should be from pitFromRequest.getIndex()+1
        // until pitFromRequest.getIndex()+pitFromRequest.getNumberOfStones().
        // Note: The large pit of the opponent needs to be skipped when distributing the stones of the current player.
        int startPitIndex = pitFromRequest.getIndex() + 1;
        int lastPitIndex = pitFromRequest.getIndex() + pitFromRequest.getNumberOfStones();

        pits.subList(startPitIndex, lastPitIndex)
    }
}
