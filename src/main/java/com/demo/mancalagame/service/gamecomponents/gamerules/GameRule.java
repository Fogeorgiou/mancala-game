package com.demo.mancalagame.service.gamecomponents.gamerules;

import com.demo.mancalagame.entity.Game;
import com.demo.mancalagame.entity.Pit;

public abstract class GameRule {

    String ruleName;

    public enum RuleName {
        STONE_DISTRIBUTION_RULE,
        LAST_STONE_ON_EMPTY_PIT_RULE,
        LAST_STONE_ON_LARGE_PIT_RULE
    }

    public abstract void apply(Game game, Pit pit);
}
