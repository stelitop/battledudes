package io.github.stelitop.battledudes.database.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import io.github.stelitop.battledudes.game.entities.Trait;

//@Repository
public interface TraitsRepository extends CrudRepository<Trait, String> {

}
