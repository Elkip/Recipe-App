package springframework.guru.recipe.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"recipe"})
@Entity
public class Notes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Recipe recipe;

    // a long String can be very large
    // use @Lob to create the object
    // as a Large Object Storage
    @Lob
    private String notes;

}
