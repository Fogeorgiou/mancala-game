package com.demo.mancalagame.service.gamecomponents;

import com.demo.mancalagame.entity.Game;
import com.demo.mancalagame.entity.GameStatus;
import com.demo.mancalagame.entity.Pit;
import com.demo.mancalagame.entity.Player;
import com.demo.mancalagame.service.gamecomponents.gamerules.GameRule;
import com.demo.mancalagame.service.gamecomponents.gamerules.GameRuleManager;
import org.springframework.stereotype.Component;

@Component
public class GameRoundExecutor {

    public void play(Game game, GameRoundSelectionParameters gameRoundSelectionParameters) {

        // Get specified pit from board. This is the pit from where the player will move their stones.
        Pit pitFromRequest = game.getBoard().getPitById(gameRoundSelectionParameters.getPitId());

        // Play next move
//        playNextMove(game, pitFromRequest, gameRoundSelectionParameters);

//        if (pitFromRequest.getNumberOfStones() == 0) {
//            // There are no stones in the pit, so nothing happens. Maybe return a warning?
//        } else {
            GameRule nextRuleToApply = GameRuleManager.getNextRuleToApply(game);
            while (nextRuleToApply != null && GameStatus.IN_PROGRESS.equals(game.getGameStatus())) {
                nextRuleToApply.apply(game, pitFromRequest);
                nextRuleToApply = GameRuleManager.getNextRuleToApply(game);
            }

            // Check if the game is finished, i.e. check that there are no more stones in any of the small pits of a player
            for (Player player : game.getPlayers()) {
                if (game.getBoard().noStonesInPlayerPits(player.getId())) {
                    game.setGameStatus(GameStatus.FINISHED);
                    game.setPlayerTurn(null);
                    // For each of the rest of the players take all the stones from their small pits and place them
                    // in their large pits.
                    for (Player otherPlayer : game.getPlayers()) {
                        if (otherPlayer.getId() != player.getId()) {
                            game.getBoard().getLargePitByPlayerId(otherPlayer.getId()).setNumberOfStones(
                                    game.getBoard().getLargePitByPlayerId(otherPlayer.getId()).getNumberOfStones() +
                                            game.getBoard().getAllStonesInSmallPitsByPlayerId(otherPlayer.getId())
                            );
                            game.getBoard().getAllSmallPitsByPlayerId(otherPlayer.getId())
                                    .forEach(pit -> pit.setNumberOfStones(GameConstants.ZERO));
                        }
                    }
                    break;
                }
            }

            GameScoreManager.updateScorePerPlayer(game);

            if (!GameStatus.FINISHED.equals(game.getGameStatus())) {
                game.setGameStatus(GameStatus.IN_PROGRESS);
            } else {
                // Set game winner
                game.setGameWinner(GameScoreManager.getGameWinner(game));
            }
//        }
    }

//    private void playNextMove(Game game, Pit pitFromRequest, GameRoundSelectionParameters gameRoundSelectionParameters) {
//
//        if (pitFromRequest.getNumberOfStones() == 0) {
//            // There are no stones in the pit, so nothing happens. Maybe return a warning?
//        } else {
////            // Generate list of game rules
////            List<GameRule> gameRules = new ArrayList<>();
////            gameRules.add(new StoneDistributionRule());
////            gameRules.add(new LastStoneOnLargePitRule());
////
////            for (GameRule gameRule : gameRules) {
////                gameRule.apply(game, pitFromRequest);
////            }
////            GameRuleManager gameRuleManager = new GameRuleManager();
//            GameRule nextRuleToApply = GameRuleManager.getNextRuleToApply(game);
//            while (nextRuleToApply != null && GameStatus.IN_PROGRESS.equals(game.getGameStatus())) {
//                nextRuleToApply.apply(game, pitFromRequest);
//                nextRuleToApply = GameRuleManager.getNextRuleToApply(game);
//            }
//
//            // Check if the game is finished, i.e. check that there are no more stones in any of the small pits of a player
//            for (Player player : game.getPlayers()) {
//                if (game.getBoard().noStonesInPlayerPits(player.getId())) {
//                    game.setGameStatus(GameStatus.FINISHED);
//                    // For each of the rest of the players take all the stones from their small pits and place them
//                    // in their large pits.
//                    for (Player otherPlayer : game.getPlayers()) {
//                        if (otherPlayer.getId() != player.getId()) {
//                            game.getBoard().getLargePitByPlayerId(otherPlayer.getId()).setNumberOfStones(
//                                    game.getBoard().getLargePitByPlayerId(otherPlayer.getId()).getNumberOfStones() +
//                                            game.getBoard().getAllStonesInSmallPitsByPlayerId(otherPlayer.getId())
//                            );
//                            game.getBoard().getAllSmallPitsByPlayerId(otherPlayer.getId())
//                                    .forEach(pit -> pit.setNumberOfStones(GameConstants.ZERO));
//                        }
//                    }
//                    break;
//                }
//            }
//
//            GameScoreManager.updateScorePerPlayer(game);
//
//            if (!GameStatus.FINISHED.equals(game.getGameStatus())) {
//                game.setGameStatus(GameStatus.IN_PROGRESS);
//            } else {
//                // Set game winner
//                game.setGameWinner(GameScoreManager.getGameWinner(game));
//            }
//        }
//    }
}
