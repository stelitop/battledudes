package io.github.stelitop.battledudes.database.repositories;

import org.springframework.data.repository.CrudRepository;
import io.github.stelitop.battledudes.game.entities.UserProfile;
import org.springframework.stereotype.Repository;

//@Repository
public interface UserProfileRepository extends CrudRepository<UserProfile, Long> {

}
