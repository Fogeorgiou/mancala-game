package com.demo.mancalagame.util;

import com.demo.mancalagame.entity.Game;
import com.demo.mancalagame.entity.GameStatus;
import com.demo.mancalagame.entity.Pit;
import com.demo.mancalagame.service.gamecomponents.GameScoreManager;
import com.demo.mancalagame.service.gamecomponents.gameroundvalidation.PitValidation;
import com.demo.mancalagame.service.gamecomponents.gameroundvalidation.PlayerValidation;
import com.demo.mancalagame.service.gamecomponents.gamerules.GameRule;
import com.demo.mancalagame.service.gamecomponents.gamerules.GameRuleManager;
import org.springframework.stereotype.Component;

@Component
public class GameRoundExecutor {

    public void play(Game game, GameRoundSelectionParameters gameRoundSelectionParameters) {

        // Check game status
//        if (game is not finished) {
            // Checks related to the specified player
            PlayerValidation playerValidation = new PlayerValidation();
            playerValidation.validate(gameRoundSelectionParameters, game);

            // Checks related to the specified pit
            PitValidation pitValidation = new PitValidation();
            pitValidation.validate(gameRoundSelectionParameters, game);
//        }

        // Get specified pit from board. This is the pit from where the player will move their stones.
        Pit pitFromRequest = game.getBoard().getPitById(gameRoundSelectionParameters.getPitIndex());

        // Play next move
        playNextMove(game, pitFromRequest, gameRoundSelectionParameters);
    }

    private void playNextMove(Game game, Pit pitFromRequest, GameRoundSelectionParameters gameRoundSelectionParameters) {

        if (pitFromRequest.getNumberOfStones() == 0) {
            // There are no stones in the pit, so nothing happens. Maybe return a warning?
        } else {
//            // Generate list of game rules
//            List<GameRule> gameRules = new ArrayList<>();
//            gameRules.add(new StoneDistributionRule());
//            gameRules.add(new LastStoneOnLargePitRule());
//
//            for (GameRule gameRule : gameRules) {
//                gameRule.apply(game, pitFromRequest);
//            }
//            GameRuleManager gameRuleManager = new GameRuleManager();
            GameRule nextRuleToApply = GameRuleManager.getNextRuleToApply(game, gameRoundSelectionParameters);
            while (nextRuleToApply != null && GameStatus.IN_PROGRESS.equals(game.getGameStatus())) {
                nextRuleToApply.apply(game, pitFromRequest);
                nextRuleToApply = GameRuleManager.getNextRuleToApply(game, gameRoundSelectionParameters);
            }

            GameScoreManager.updateScorePerPlayer(game);
        }
    }
}
