package ru.otus.spring.gateway;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring.domain.Butterfly;
import ru.otus.spring.domain.Egg;

import java.util.Collection;

@MessagingGateway
public interface EggsButterflyGateway {

    @Gateway(requestChannel = "eggsChannel", replyChannel = "butterflyChannel")
    Collection<Butterfly> process(Collection<Egg> egg);
}
