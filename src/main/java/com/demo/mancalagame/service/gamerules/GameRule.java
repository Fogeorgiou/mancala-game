package com.demo.mancalagame.service.gamerules;

import com.demo.mancalagame.entity.Game;
import com.demo.mancalagame.entity.Pit;

public abstract class GameRule {

    public abstract void applyRule(Game game, Pit pit);
}
