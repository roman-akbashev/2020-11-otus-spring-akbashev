package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Egg;
import ru.otus.spring.domain.Larva;

@Service
public class EggService {

    public Larva hatch(Egg egg) throws Exception {
        System.out.println("Hatching egg " + egg.getIndexNumber());
        Thread.sleep(30);
        System.out.println("Hatching larva from egg " + egg.getIndexNumber() + " done");
        return new Larva(egg.getIndexNumber());
    }
}
