package com.demo.mancalagame.mapper;

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
