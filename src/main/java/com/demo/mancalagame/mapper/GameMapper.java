package com.demo.mancalagame.mapper;

import com.demo.mancalagame.dto.BoardDto;
import com.demo.mancalagame.dto.GameDto;
import com.demo.mancalagame.dto.PitDto;
import com.demo.mancalagame.dto.PlayerDto;
import com.demo.mancalagame.entity.Game;
import com.demo.mancalagame.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GameMapper {

    public GameDto map(Game game) {

        List<PitDto> pitDtoList = game.getBoard().getPits().stream().map(pit -> new PitDto(pit.getIndex(), pit.getNumberOfStones(), pit.getPlayerId())).collect(Collectors.toList());
        BoardDto boardDto = new BoardDto(pitDtoList);

        List<PlayerDto> playerDtoList = game.getPlayers().stream().map(player -> new PlayerDto(player.getUsername())).collect(Collectors.toList());

        Map<String, Integer> scoreDto = new HashMap<>();
        for (Map.Entry<Player, Integer> scorePerPlayer : game.getScorePerPlayer().entrySet()) {
            scoreDto.put(scorePerPlayer.getKey().getUsername(), scorePerPlayer.getValue());
        }

        return new GameDto(game.getGameId(), boardDto, playerDtoList, scoreDto);
    }
}
