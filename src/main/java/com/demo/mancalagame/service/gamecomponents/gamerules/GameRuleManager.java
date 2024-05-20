package com.demo.mancalagame.service.gamecomponents.gamerules;

import com.demo.mancalagame.entity.Game;
import com.demo.mancalagame.entity.GameStatus;

/**
 * Decides, based on the game status and the rule that was last applied, which rule should be applied next.
 */
public class GameRuleManager {

    public static GameRule getNextRuleToApply(Game game) {
        if (GameStatus.STARTED.equals(game.getGameStatus())) {
            game.setGameStatus(GameStatus.IN_PROGRESS);
            return new StoneDistributionRule();
        } else if (GameStatus.IN_PROGRESS.equals(game.getGameStatus())) {
            if (GameRule.RuleName.STONE_DISTRIBUTION_RULE.toString().equals(game.getLastRuleApplied())) {
                return new LastStoneOnEmptyPitRule();
            } else if (GameRule.RuleName.LAST_STONE_ON_EMPTY_PIT_RULE.toString().equals(game.getLastRuleApplied())) {
                return new LastStoneOnLargePitRule();
            } else if (GameRule.RuleName.LAST_STONE_ON_LARGE_PIT_RULE.toString().equals(game.getLastRuleApplied())) {
                return new StoneDistributionRule();
            }
        }
        return null;
    }
}
