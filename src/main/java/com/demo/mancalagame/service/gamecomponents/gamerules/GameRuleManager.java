package com.demo.mancalagame.service.gamecomponents.gamerules;

import com.demo.mancalagame.entity.Game;
import com.demo.mancalagame.entity.GameStatus;

public class GameRuleManager {

    public static GameRule lastRuleApplied;

    public static GameRule getNextRuleToApply(Game game) {
        if (GameStatus.STARTED.equals(game.getGameStatus())) {
            game.setGameStatus(GameStatus.IN_PROGRESS);
            return new StoneDistributionRule();
        } else if (GameStatus.IN_PROGRESS.equals(game.getGameStatus())) {
            if (lastRuleApplied instanceof StoneDistributionRule) {
                return new LastStoneOnEmptyPitRule();
            } else if (lastRuleApplied instanceof LastStoneOnEmptyPitRule) {
                return new LastStoneOnLargePitRule();
            } else if (lastRuleApplied instanceof LastStoneOnLargePitRule) {
                return new StoneDistributionRule();
            }
        }
        return null;
    }
}
