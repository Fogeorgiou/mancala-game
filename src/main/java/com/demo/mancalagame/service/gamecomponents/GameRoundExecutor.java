package com.demo.mancalagame.service.gamecomponents;

import com.demo.mancalagame.entity.Game;
import com.demo.mancalagame.entity.GameStatus;
import com.demo.mancalagame.entity.Pit;
import com.demo.mancalagame.entity.Player;
import com.demo.mancalagame.service.gamecomponents.gamerules.GameRule;
import com.demo.mancalagame.service.gamecomponents.gamerules.GameRuleManager;
import org.springframework.stereotype.Component;

/**
 * Runs a game round: applies the game rules and updates the score.
 * If at any point it identifies that the game is finished, it makes the necessary updates to the game
 * and sets the game winner.
 */
@Component
public class GameRoundExecutor {

    public void play(Game game, GameRoundSelectionParameters gameRoundSelectionParameters) {

        // Get the pit specified in the request from the game board.
        // This is the pit from where the player will move their stones.
        Pit pitFromRequest = game.getBoard().getPitById(gameRoundSelectionParameters.getPitId());

        // Apply game rules
        GameRule nextRuleToApply = GameRuleManager.getNextRuleToApply(game);
        while (nextRuleToApply != null && GameStatus.IN_PROGRESS.equals(game.getGameStatus())) {
            nextRuleToApply.apply(game, pitFromRequest);
            nextRuleToApply = GameRuleManager.getNextRuleToApply(game);
        }

        // Check if the game is finished, i.e. check that there are no more stones in any of the small pits of a player
        for (Player player : game.getPlayers()) {
            if (game.getBoard().noStonesInPlayerPits(player.getId())) {
                // A player with no stones is his pits has been found. The game is now finished.
                game.setGameStatus(GameStatus.FINISHED);
                game.setPlayerTurn(null);
                // For each of the rest of the players take all the stones from their small pits and place them
                // in their large pits. Then, set the number of stones in each of the small pits to 0.
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

        // Update game score
        GameScoreManager.updateScorePerPlayer(game);

        if (!GameStatus.FINISHED.equals(game.getGameStatus())) {
            game.setGameStatus(GameStatus.IN_PROGRESS);
        } else {
            // Set game winner
            game.setGameWinner(GameScoreManager.getGameWinner(game));
        }
    }
}
