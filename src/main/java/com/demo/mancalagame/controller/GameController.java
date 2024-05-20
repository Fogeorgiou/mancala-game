package com.demo.mancalagame.controller;

import com.demo.mancalagame.dto.GameDto;
import com.demo.mancalagame.service.GameService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Provides the endpoints to generate a new game, play a game or get a game from the database
 */
@RestController
@RequestMapping("/games")
@Validated
public class GameController {

    @Autowired
    private GameService gameService;

    /**
     *
     * @param numberOfStonesPerPit The number of stones that each pit should contain during initial game setup
     * @return a response containing the newly created game
     */
    @PostMapping("/new")
    public ResponseEntity<GameDto> startNewGame(@RequestParam(name = "number_of_stones_per_pit", defaultValue = "6", required = false) int numberOfStonesPerPit) {

        return new ResponseEntity(gameService.generateNewGame(numberOfStonesPerPit), HttpStatus.CREATED);
    }

    /**
     *
     * @param gameId The id of the game to play with
     * @param playerId The id of the player to play with
     * @param pitId The id of the pit to play with
     * @return a response containing the updated game (after a player's move)
     */
    @PutMapping("/play")
    public ResponseEntity<GameDto> playGame(@RequestParam(name = "game_id") @NotBlank String gameId,
                         @RequestParam(name = "player_id") @Min(1) int playerId,
                         @RequestParam(name = "pit_id") @Min(1) int pitId) {

        return new ResponseEntity(gameService.playGame(gameId, playerId, pitId), HttpStatus.OK);
    }

    /**
     *
     * @param gameId The id of the game to be fetched from the database
     * @return a response containing the requested game
     */
    @GetMapping("/{gameId}")
    public ResponseEntity<GameDto> getGame(@PathVariable @NotBlank String gameId) {
        return new ResponseEntity(gameService.getGame(gameId), HttpStatus.OK);
    }
}
