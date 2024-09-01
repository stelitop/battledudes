package io.github.stelitop.battledudes.database.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import io.github.stelitop.battledudes.game.entities.Move;

//@Repository
public interface MoveRepository extends CrudRepository<Move, String> {

}
