package com.demo.mancalagame.repository;

import com.demo.mancalagame.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, String>  {

//    Optional<Game> findByGameId(String gameId);
}
