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

        // Pit indexes for the first player
        int firstPitIndex = 1;
        int lastPitIndex = firstPitIndex + GameConstants.NUMBER_OF_SMALL_PITS_PER_PLAYER;

        for (Player player : players) {
            for (int i = firstPitIndex; i < lastPitIndex; i++) {
//                SmallPit smallPit = new SmallPit(i, numberOfStonesPerPit, player.getId());
                pits.add(new Pit(i, numberOfStonesPerPit, player.getId(), false));
            }

//            LargePit largePit = new LargePit(lastPitIndex, player.getId());
            pits.add(new Pit(lastPitIndex, GameConstants.ZERO, player.getId(), true));

            firstPitIndex = lastPitIndex + 1;
            lastPitIndex = firstPitIndex + GameConstants.NUMBER_OF_SMALL_PITS_PER_PLAYER;
        }
    }

    public Pit getPitById(int pitId) {
        Optional<Pit> pitOptional = pits.stream().filter(pit -> pitId == pit.getId()).findFirst();

        return pitOptional.isPresent() ? pitOptional.get() : null;
    }

    public Pit getLargePitByPlayerId(int playerId) {
        Optional<Pit> largePit = pits.stream()
                .filter(pit -> pit.isLargePit() && playerId == pit.getPlayerId())
                .findFirst();

        return largePit.get();
    }
}
