package com.demo.mancalagame.mapper;

//import com.demo.mancalagame.dto.BoardDto;
//import com.demo.mancalagame.dto.GameDto;
//import com.demo.mancalagame.dto.PitDto;
//import com.demo.mancalagame.dto.PlayerDto;
//import com.demo.mancalagame.entity.Game;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//public class GameMapper {
//
//    public GameDto map(Game game) {
//
//        List<PitDto> pitDtoList = game.getBoard().getPits().stream().map(pit -> new PitDto(pit.getId(), pit.getNumberOfStones(), pit.getPlayerId())).collect(Collectors.toList());
//        BoardDto boardDto = new BoardDto(pitDtoList);
//
//        List<PlayerDto> playerDtoList = game.getPlayers().stream().map(player -> new PlayerDto(player.getUsername())).collect(Collectors.toList());
//
//        Map<Integer, Integer> scoreDto = new HashMap<>();
//        for (Map.Entry<Integer, Integer> scorePerPlayer : game.getScorePerPlayer().entrySet()) {
//            scoreDto.put(scorePerPlayer.getKey(), scorePerPlayer.getValue());
//        }
//
////        return new GameDto(game.getGameId(), boardDto, playerDtoList, scoreDto);
//        return new GameDto.Builder()
//                .id(game.getGameId())
//                .status(game.getGameStatus())
//                .board(boardDto)
//                .players(playerDtoList)
//                .scorePerPlayer(scoreDto)
//                .playerTurn(game.getPlayerTurn())
//                .winner(game.getGameWinner())
//                .build();
//    }
//}

import com.demo.mancalagame.dto.GameDto;
import com.demo.mancalagame.entity.Game;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GameMapper {

    @Mapping(source = "gameId", target = "id")
    @Mapping(source = "gameStatus", target = "status")
    @Mapping(source = "gameWinner", target = "winner")
    GameDto toDto(Game game);
}
