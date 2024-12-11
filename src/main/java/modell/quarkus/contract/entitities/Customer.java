package modell.quarkus.contract.entitities;

import io.github.agache41.annotator.annotations.Position;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import modell.quarkus.contract.interfaces.HeaderInfo;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity()
public class Customer {

    @Position(0)
    @HeaderInfo(name = "Company Name",sortable = true)
    @Column(name = "name")
    private String name;

    @Position(1)
    @HeaderInfo(name = "Company Domain",sortable = true)
    @Column(name = "type")
    private String type;

    @Position(20)
    @HeaderInfo(name = "Adress",sortable = true)
    @Column(name = "address")
    private String address;

    @Position(3)
    @HeaderInfo(name = "Telephone",sortable = true)
    @Column(name = "telNumber")
    private String telNumber;

    @Position(4)
    @HeaderInfo(name = "Registration Number",sortable = true)
    @Column(name = "regNumber")
    private Long regNumber;

    @Position(5)
    @HeaderInfo(name = "Yearly Revenue",sortable = true)
    @Column(name = "value")
    private BigDecimal value;
}
