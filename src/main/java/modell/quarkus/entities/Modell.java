package modell.quarkus.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.structured.api.quarkus.dataAccess.PrimaryKey;
import org.structured.api.quarkus.reflection.Update;

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

    @Update
    private String name;

    @Update(notNull = false)
    private String street;

    @Update(notNull = false)
    private Integer no;
}