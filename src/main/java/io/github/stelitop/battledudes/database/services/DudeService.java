package io.github.stelitop.battledudes.database.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.github.stelitop.battledudes.database.repositories.DudeRepository;
import io.github.stelitop.battledudes.database.repositories.MoveRepository;
import io.github.stelitop.battledudes.game.entities.Dude;
import io.github.stelitop.battledudes.game.enums.Rarity;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DudeService {

    @Autowired
    private DudeRepository dudeRepository;
    @Autowired
    private MoveRepository moveRepository;

    /**
     * Finds a dude in the repository by their unique name.
     *
     * @param name The name of the dude.
     * @return A dude object if there is such a dude and null
     * otherwise.
     */
    public Optional<Dude> getDude(String name) {
        var options = dudeRepository.findByNameIgnoreCase(name);
        if (options == null || options.isEmpty()) return Optional.empty();
        //System.out.println(options.get(0).getName() + " retrieved with " + options.get(0).getLocations().size() + " locations.");
        return Optional.of(options.get(0));
    }

    /**
     * Finds a dude in the repoitory by their unique id.
     *
     * @param id The id of the dude.
     * @return An optional object with a matching dude.
     */
    public Optional<Dude> getDude(long id) {
        var ret = dudeRepository.findById(id);
        //ret.ifPresent(dude -> dude.setLocations(new ArrayList<>(dude.getLocations().stream().distinct().toList())));
        //ret.ifPresent(dude -> System.out.println(dude.getName() + " retrieved with " + dude.getLocations().size() + " locations."));
        return ret;
    }

    /**
     * Saves a dude entity into the database, along with all their
     * traits and moves in their own respective databases
     *
     * @param dude Dude to save.
     */
    public void saveDudeWithMovesAndTraits(Dude dude) {
        //moveRepository.saveAll(dude.getMoves());
        //traitsRepository.saveAll(dude.getTraits());
        dudeRepository.save(dude);
    }

    /**
     * Gets all dudes from the database of a given rarity.
     *
     * @param rarity The rarity of the dudes.
     * @return A list of all dudes of that rarity.
     */
    public List<Dude> getDudesOfRarity(Rarity rarity) {
        return dudeRepository.findByRarity(rarity);
    }

    /**
     * Gets all dudes currently in the repository.
     *
     * @return A mutable list containing all dudes.
     */
    public List<Dude> getAllDudes() {
        List<Dude> dudes = new ArrayList<>();
        dudeRepository.findAll().forEach(dudes::add);
        //dudes.forEach(x -> x.setLocations(new ArrayList<>(x.getLocations().stream().distinct().toList())));
        return dudes;
    }

    /**
     * Saves a new or updated dude into the repository.
     *
     * @param dude Dude to save.
     */
    public void saveDude(Dude dude) {
        //System.out.println(dude.getName() + " saved with " + dude.getLocations().size() + " locations.");
        //dude.setLocations(dude.getLocations().stream().distinct().toList());
        dudeRepository.save(dude);
    }
}
