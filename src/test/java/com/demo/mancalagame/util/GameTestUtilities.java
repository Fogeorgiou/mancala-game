package com.demo.mancalagame.util;

import com.demo.mancalagame.dto.BoardDto;
import com.demo.mancalagame.dto.GameDto;
import com.demo.mancalagame.dto.PitDto;
import com.demo.mancalagame.dto.PlayerDto;
import com.demo.mancalagame.entity.GameStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameTestUtilities {

    public static GameDto createStartedGameDto(int numberOfStonesPerPit) {

        String gameId = "123";

        List<PitDto> pitDtoList = new ArrayList<>();
        pitDtoList.add(new PitDto.Builder().id(1).numberOfStones(numberOfStonesPerPit).playerId(1).largePit(false).build());
        pitDtoList.add(new PitDto.Builder().id(2).numberOfStones(numberOfStonesPerPit).playerId(1).largePit(false).build());
        pitDtoList.add(new PitDto.Builder().id(3).numberOfStones(numberOfStonesPerPit).playerId(1).largePit(false).build());
        pitDtoList.add(new PitDto.Builder().id(4).numberOfStones(numberOfStonesPerPit).playerId(1).largePit(false).build());
        pitDtoList.add(new PitDto.Builder().id(5).numberOfStones(numberOfStonesPerPit).playerId(1).largePit(false).build());
        pitDtoList.add(new PitDto.Builder().id(6).numberOfStones(numberOfStonesPerPit).playerId(1).largePit(false).build());
        pitDtoList.add(new PitDto.Builder().id(7).numberOfStones(0).playerId(1).largePit(true).build());
        pitDtoList.add(new PitDto.Builder().id(8).numberOfStones(numberOfStonesPerPit).playerId(2).largePit(false).build());
        pitDtoList.add(new PitDto.Builder().id(9).numberOfStones(numberOfStonesPerPit).playerId(2).largePit(false).build());
        pitDtoList.add(new PitDto.Builder().id(10).numberOfStones(numberOfStonesPerPit).playerId(2).largePit(false).build());
        pitDtoList.add(new PitDto.Builder().id(11).numberOfStones(numberOfStonesPerPit).playerId(2).largePit(false).build());
        pitDtoList.add(new PitDto.Builder().id(12).numberOfStones(numberOfStonesPerPit).playerId(2).largePit(false).build());
        pitDtoList.add(new PitDto.Builder().id(13).numberOfStones(numberOfStonesPerPit).playerId(2).largePit(false).build());
        pitDtoList.add(new PitDto.Builder().id(14).numberOfStones(0).playerId(2).largePit(true).build());
        BoardDto boardDto = new BoardDto(pitDtoList);

        List<PlayerDto> players = new ArrayList<>();
        players.add(new PlayerDto(1, "username1"));
        players.add(new PlayerDto(2, "username2"));

        Map<Integer, Integer> scorePerPlayer = new HashMap<>();
        scorePerPlayer.put(1, 0);
        scorePerPlayer.put(2, 0);

        return new GameDto(gameId, GameStatus.STARTED, boardDto, players, scorePerPlayer, 1, null);
    }

    public static GameDto createInProgressGameDto() {

        String gameId = "123";

        List<PitDto> pitDtoList = new ArrayList<>();
        pitDtoList.add(new PitDto.Builder().id(1).numberOfStones(0).playerId(1).largePit(false).build());
        pitDtoList.add(new PitDto.Builder().id(2).numberOfStones(7).playerId(1).largePit(false).build());
        pitDtoList.add(new PitDto.Builder().id(3).numberOfStones(7).playerId(1).largePit(false).build());
        pitDtoList.add(new PitDto.Builder().id(4).numberOfStones(7).playerId(1).largePit(false).build());
        pitDtoList.add(new PitDto.Builder().id(5).numberOfStones(7).playerId(1).largePit(false).build());
        pitDtoList.add(new PitDto.Builder().id(6).numberOfStones(7).playerId(1).largePit(false).build());
        pitDtoList.add(new PitDto.Builder().id(7).numberOfStones(1).playerId(1).largePit(true).build());
        pitDtoList.add(new PitDto.Builder().id(8).numberOfStones(6).playerId(2).largePit(false).build());
        pitDtoList.add(new PitDto.Builder().id(9).numberOfStones(6).playerId(2).largePit(false).build());
        pitDtoList.add(new PitDto.Builder().id(10).numberOfStones(6).playerId(2).largePit(false).build());
        pitDtoList.add(new PitDto.Builder().id(11).numberOfStones(6).playerId(2).largePit(false).build());
        pitDtoList.add(new PitDto.Builder().id(12).numberOfStones(6).playerId(2).largePit(false).build());
        pitDtoList.add(new PitDto.Builder().id(13).numberOfStones(6).playerId(2).largePit(false).build());
        pitDtoList.add(new PitDto.Builder().id(14).numberOfStones(0).playerId(2).largePit(true).build());
        BoardDto boardDto = new BoardDto(pitDtoList);

        List<PlayerDto> players = new ArrayList<>();
        players.add(new PlayerDto(1, "username1"));
        players.add(new PlayerDto(2, "username2"));

        Map<Integer, Integer> scorePerPlayer = new HashMap<>();
        scorePerPlayer.put(1, 1);
        scorePerPlayer.put(2, 0);

        return new GameDto(gameId, GameStatus.IN_PROGRESS, boardDto, players, scorePerPlayer, 1, null);
    }
}
