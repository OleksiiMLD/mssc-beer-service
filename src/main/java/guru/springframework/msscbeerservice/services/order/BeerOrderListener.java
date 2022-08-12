package guru.springframework.msscbeerservice.services.order;

import guru.sfg.brewery.model.BeerOrderDto;
import guru.sfg.brewery.model.events.ValidateOrderRequest;
import guru.sfg.brewery.model.events.ValidateOrderResult;
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
    public void listenValidateOrder(@Payload ValidateOrderRequest validateBeerOrderRequest,
                                    @Headers MessageHeaders headers, Message message) {
        log.info("Process order validation");
        BeerOrderDto beerOrderDto = validateBeerOrderRequest.getBeerOrder();

        boolean isValid = validator.validate(beerOrderDto);

        ValidateOrderResult payloadMsg = ValidateOrderResult
                .builder()
                .orderId(beerOrderDto.getId())
                .valid(isValid)
                .build();
        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESULT_QUEUE, payloadMsg);
    }
}
