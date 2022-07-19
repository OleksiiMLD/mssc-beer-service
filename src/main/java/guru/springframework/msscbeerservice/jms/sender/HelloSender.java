package guru.springframework.msscbeerservice.jms.sender;

import guru.springframework.msscbeerservice.config.JmsConfig;
import guru.springframework.msscbeerservice.jms.model.LetsDrinkMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by okostetskyi on 2022-07-19.
 */
@RequiredArgsConstructor
@Component
public class HelloSender {

    private final JmsTemplate jmsTemplate;

    @Scheduled(fixedRate = 2000)
    public void sendMessage(){

        LetsDrinkMessage message = LetsDrinkMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Let's drink beer!")
                .build();

        jmsTemplate.convertAndSend(JmsConfig.BEER_QUEUE, message);

    }
}
