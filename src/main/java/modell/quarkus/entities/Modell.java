package modell.quarkus.entities;

import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import modell.quarkus.utility.dao.PrimaryKey;
import modell.quarkus.utility.reflection.Write;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Modell implements PrimaryKey<Long> {

    @Id
    @NotNull
    @SequenceGenerator(name = "modellSeq", sequenceName = "modell_id_seq", allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "modellSeq")
    private Long id;

    @Write
    private String stringValue;
}