package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Butterfly;
import ru.otus.spring.domain.Pupa;

@Service
public class ButterflyService {

    public Butterfly hatch(Pupa pupa) throws Exception {
        System.out.println("Hatching pupa " + pupa.getIndexNumber());
        Thread.sleep(30);
        System.out.println("Hatching butterfly from pupa " + pupa.getIndexNumber() + " done");
        return new Butterfly("Butterfly from pupa " + pupa.getIndexNumber());
    }
}
