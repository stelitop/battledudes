package io.github.stelitop.battledudes.game.entities;


import jakarta.persistence.*;
import lombok.*;
import io.github.stelitop.battledudes.game.enums.ElementalType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Move")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Move {

    /**
     * The name of the move.
     */
    @Id
    @Column(nullable = false)
    private String name;

    /**
     * The description of the move.
     */
    @Column(nullable = false, length = 500)
    private String description;

    /**
     * The elemental types of the move.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<ElementalType> types = new ArrayList<>();

    /**
     * The dudes that have this move.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @Singular("owningDude")
    private List<Dude> dudesThatHave;

}
