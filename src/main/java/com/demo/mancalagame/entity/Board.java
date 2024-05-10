package com.demo.mancalagame.entity;

import com.demo.mancalagame.util.GameConstants;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
public class Board {

    private List<Pit> pits;

    public Board(int numberOfStonesPerPit, List<Player> players) {

        pits = new ArrayList<>();

        players.forEach(player -> {
            for (int i = 0; i < GameConstants.NUMBER_OF_SMALL_PITS_PER_PLAYER; i++) {
                pits.add(new SmallPit(i, numberOfStonesPerPit, player.getId()));
            }

            pits.add(new LargePit(GameConstants.NUMBER_OF_SMALL_PITS_PER_PLAYER, player.getId()));
        });
    }

    public Pit getPitByIndex(int pitIndex) {
        Optional<Pit> pitOptional = pits.stream().filter(pit -> pitIndex == pit.getIndex()).findFirst();

        return pitOptional.isPresent() ? pitOptional.get() : null;
    }
}
