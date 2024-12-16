package modell.quarkus.contract.entitities;

import io.github.agache41.annotator.annotations.Position;
import io.github.agache41.rest.contract.update.Update;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import modell.quarkus.contract.interfaces.HeaderInfo;

import java.math.BigDecimal;

import static modell.quarkus.contract.interfaces.Filter.*;

@HeaderInfo(
        flex = 1,
        cellClass = "cell-wrap-text",
        autoHeight = true,
        filter = TEXT)
@Data
@NoArgsConstructor
@Entity
@Update
public class Customer {

    @Id
    @Update.excluded
    @SequenceGenerator(name = "idSequence", sequenceName = "idSequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idSequence")
    @Column(name = "id", updatable = false, insertable = false)
    private Long id;

    @Position(0)
    @HeaderInfo(headerName = "Company Name", filter = NONE)
    @Column(name = "name")
    private String name;

    @Position(1)
    @HeaderInfo(headerName = "Company Domain", sortable = false, filter = TEXT)
    @Column(name = "type")
    private String type;

    @Position(20)
    @HeaderInfo(headerName = "Adress")
    @Column(name = "address")
    private String address;

    @Position(3)
    @HeaderInfo(headerName = "Telephone", filter = SET)
    @Column(name = "telNumber")
    private String telNumber;

    @Position(4)
    @HeaderInfo(headerName = "Registration Number", filter = NUMBER)
    @Column(name = "regNumber")
    private Long regNumber;

    @Position(5)
    @HeaderInfo(headerName = "Yearly Revenue")
    @Column(name = "yearRev")
    private BigDecimal yearRev;


}
