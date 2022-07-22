package guru.springframework.msscbeerservice.services.order;

import guru.sfg.brewery.model.BeerOrderDto;
import guru.sfg.brewery.model.events.ValidateBeerOrderRequest;
import guru.sfg.brewery.model.events.ValidateBeerOrderResult;
import guru.springframework.msscbeerservice.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Message;

/**
 * Created by okostetskyi on 22.07.2022
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderListener {

    private final JmsTemplate jmsTemplate;
    private final BeerOrderValidator validator;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
    public void listenValidateOrder(@Payload ValidateBeerOrderRequest validateBeerOrderRequest,
                                    @Headers MessageHeaders headers, Message message) {
        log.info("Process order validation");
        BeerOrderDto beerOrderDto = validateBeerOrderRequest.getBeerOrderDto();

        boolean invalid = validator.validate(beerOrderDto);

        ValidateBeerOrderResult payloadMsg = ValidateBeerOrderResult
                .builder()
                .orderId(beerOrderDto.getId())
                .isValid(invalid)
                .build();
        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESULT_QUEUE, payloadMsg);
    }
}
