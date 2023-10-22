package modell.quarkus.dao;

import lombok.*;

/**
 * Base class for Entities or Transfer Objects that only need to send the id.
 *
 * @param <KEY_T>
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class BaseEntity<KEY_T> implements BaseEntityInterface<KEY_T> {
    private KEY_T id;
}
