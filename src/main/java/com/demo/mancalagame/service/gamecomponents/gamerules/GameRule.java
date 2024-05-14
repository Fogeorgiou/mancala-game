package com.demo.mancalagame.service.gamecomponents.gamerules;

import com.demo.mancalagame.entity.Game;
import com.demo.mancalagame.entity.Pit;

public abstract class GameRule {

    public abstract void apply(Game game, Pit pit);
}
