package guru.sfg.brewery.model.events;

import guru.sfg.brewery.model.BeerOrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created by okostetskyi on 21.07.2022
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ValidateBeerOrderRequest implements Serializable {

    private static final long serialVersionUID = 2386148795903676664L;

    private BeerOrderDto beerOrderDto;
}
