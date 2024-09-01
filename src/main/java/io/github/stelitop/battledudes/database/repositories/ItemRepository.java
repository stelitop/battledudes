package io.github.stelitop.battledudes.database.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import io.github.stelitop.battledudes.game.entities.Dude;
import io.github.stelitop.battledudes.game.entities.Item;
import io.github.stelitop.battledudes.game.enums.Rarity;

import java.util.List;

//@Repository
public interface ItemRepository extends CrudRepository<Item, String> {
    @Transactional
    List<Item> findByNameIgnoreCase(String name);
    @Transactional
    List<Item> findByRarity(Rarity rarity);
}
