package com.demo.mancalagame.service.gameroundvalidation;

import com.demo.mancalagame.entity.Game;
import com.demo.mancalagame.util.GameRoundParameters;

public interface GameRoundParametersValidation {

    void validate(GameRoundParameters gameRoundParameters, Game game);
}
