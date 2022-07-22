package guru.springframework.msscbeerservice.services.order;

import guru.sfg.brewery.model.BeerDto;
import guru.sfg.brewery.model.BeerOrderDto;
import guru.sfg.brewery.model.BeerOrderLineDto;
import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.repositories.BeerRepository;
import guru.springframework.msscbeerservice.services.BeerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

/**
 * Created by okostetskyi on 22.07.2022
 */
@RequiredArgsConstructor
@Component
public class BeerOrderValidator {

    private final BeerRepository beerRepository;
    private final BeerService beerService;

    public boolean validate(BeerOrderDto beerOrderDto) {
        return beerOrderDto.getBeerOrderLines().stream().noneMatch(invalidOrderLine());
    }

    private Predicate<? super BeerOrderLineDto> invalidOrderLine() {
        return orderLine -> {
            Beer beer = beerRepository.findByUpc(orderLine.getUpc());
            if (beer == null) {
                return true;
            }
            BeerDto fullBeer = beerService.getById(beer.getId(), true);
            return orderLine.getOrderQuantity() > fullBeer.getQuantityOnHand();
        };
    }
}
