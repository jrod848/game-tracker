package com.eureka.gametracker.repo;

import com.eureka.gametracker.entity.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepository extends MongoRepository<Game, String> {
}