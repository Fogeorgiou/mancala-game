package com.demo.mancalagame.controller;

import com.demo.mancalagame.dto.GameDto;
import com.demo.mancalagame.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping("/new")
    public ResponseEntity<GameDto> startNewGame(@RequestParam(name = "number_of_stones_per_pit", defaultValue = "6", required = false) int numberOfStonesPerPit) {

        return new ResponseEntity(gameService.generateNewGame(numberOfStonesPerPit), HttpStatus.CREATED);
    }

    @PutMapping("/play")
    public ResponseEntity<GameDto> playGame(@RequestParam(name = "game_id") String gameId,
                         @RequestParam(name = "player_id") int playerId,
                         @RequestParam(name = "pit_id") int pitId) {

        return new ResponseEntity(gameService.playGame(gameId, playerId, pitId), HttpStatus.OK);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<GameDto> getGame(@PathVariable String gameId) {
        return new ResponseEntity(gameService.getGame(gameId), HttpStatus.OK);
    }
}
