package com.demo.mancalagame.mapper;

import com.demo.mancalagame.dto.GameDto;
import com.demo.mancalagame.entity.Game;
import com.demo.mancalagame.entity.GameStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class GameMapperTest {

    @Autowired
    private GameMapper gameMapper;

    @Test
    public void shouldMapEntityToDto() {
        // given
        int numberOfStonesPerPit = 6;
        Game game = new Game(numberOfStonesPerPit);

        // when
        GameDto gameDto = gameMapper.toDto(game);

        // then
        assertThat(gameDto)
                .hasFieldOrProperty("id")
                .hasFieldOrPropertyWithValue("status", GameStatus.STARTED);
        assertEquals(game.getBoard().getPits().size(), gameDto.board().pits().size());
        for (int i=0; i<gameDto.board().pits().size(); i++) {
            assertEquals(game.getBoard().getPits().get(i).getId(), gameDto.board().pits().get(i).id());
            assertEquals(game.getBoard().getPits().get(i).getNumberOfStones(), gameDto.board().pits().get(i).numberOfStones());
            assertEquals(game.getBoard().getPits().get(i).getPlayerId(), gameDto.board().pits().get(i).playerId());
            assertEquals(game.getBoard().getPits().get(i).isLargePit(), gameDto.board().pits().get(i).largePit());
        }
        assertEquals(game.getPlayers().size(), gameDto.players().size());
        for (int i=0; i<gameDto.players().size(); i++) {
            assertEquals(game.getPlayers().get(i).getId(), gameDto.players().get(i).id());
            assertEquals(game.getPlayers().get(i).getUsername(), gameDto.players().get(i).username());
        }
        assertEquals(game.getScorePerPlayer(), gameDto.scorePerPlayer());
        assertEquals(game.getPlayerTurn(), gameDto.playerTurn());
        assertEquals(game.getGameWinner(), gameDto.winner());
    }
}
