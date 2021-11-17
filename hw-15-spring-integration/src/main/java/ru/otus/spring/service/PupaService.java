package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Larva;
import ru.otus.spring.domain.Pupa;

@Service
public class PupaService {

    public Pupa pupate(Larva larva) throws Exception {
        System.out.println("Pupation " + larva.getIndexNumber());
        Thread.sleep(3000);
        System.out.println("Pupation from larva " + larva.getIndexNumber() + " done");
        return new Pupa(larva.getIndexNumber());
    }
}
