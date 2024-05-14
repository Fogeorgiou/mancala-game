package com.demo.mancalagame.service.gamecomponents.gamerules;

import com.demo.mancalagame.entity.Game;
import com.demo.mancalagame.entity.GameStatus;
import com.demo.mancalagame.util.GameRoundSelectionParameters;
import lombok.Data;

@Data
public class GameRuleManager {

    static GameRule lastRuleApplied;
    static GameRule nextRuleToApply;

    public static GameRule getNextRuleToApply(Game game, GameRoundSelectionParameters gameRoundSelectionParameters) {
        if (GameStatus.STARTED.equals(game.getGameStatus())) {
            game.setGameStatus(GameStatus.IN_PROGRESS);
            return new StoneDistributionRule();
        } else if (GameStatus.IN_PROGRESS.equals(game.getGameStatus())) {
            if (lastRuleApplied instanceof StoneDistributionRule) {
                return new LastStoneOnEmptyPitRule();
            } else if (lastRuleApplied instanceof LastStoneOnEmptyPitRule) {
                return new LastStoneOnLargePitRule();
            } else if (lastRuleApplied instanceof LastStoneOnLargePitRule) {
//                game.setGameStatus(GameStatus.CURRENT_PLAYER_MOVE_FINISHED);
                return new StoneDistributionRule();
            }
        }
        return null;
    }
}
