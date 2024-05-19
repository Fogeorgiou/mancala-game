package com.demo.mancalagame.entity;

import com.demo.mancalagame.service.gamecomponents.GameConstants;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                pits.add(new Pit(i, numberOfStonesPerPit, player.getId(), false));
            }

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
        Optional<Pit> largePitOptional = pits.stream()
                .filter(pit -> pit.isLargePit())
                .filter(pit -> playerId == pit.getPlayerId())
                .findFirst();

        return largePitOptional.isPresent() ? largePitOptional.get() : null;
    }

    public List<Pit> getAllSmallPitsByPlayerId(int playerId) {
        return pits.stream()
                .filter(pit -> !pit.isLargePit())
                .filter(pit -> playerId == pit.getPlayerId())
                .collect(Collectors.toList());
    }

    public int getAllStonesInSmallPitsByPlayerId(int playerId) {
        int totalNumberOfStones = 0;
        for (Pit pit : getAllSmallPitsByPlayerId(playerId)) {
            totalNumberOfStones = totalNumberOfStones + pit.getNumberOfStones();
        }
        return totalNumberOfStones;
    }

    public boolean noStonesInPlayerPits(int playerId) {
        List<Pit> smallPits = getAllSmallPitsByPlayerId(playerId);

        return smallPits.stream()
                .filter(pit -> pit.getNumberOfStones() == 0)
                .count() == smallPits.size();
    }
}
