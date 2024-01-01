package modell.quarkus.entities;

import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.structured.api.quarkus.dao.PrimaryKey;
import org.structured.api.quarkus.reflection.Write;

@Data
@Builder
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
    private String name;

    @Write(notNull = false)
    private String street;

    @Write(notNull = false)
    private Integer no;
}