package guru.springframework.msscbeerservice.jms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by okostetskyi on 2022-07-19.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LetsDrinkMessage implements Serializable {

    static final long serialVersionUID = -6703826490277916847L;

    private UUID id;
    private String message;
}
