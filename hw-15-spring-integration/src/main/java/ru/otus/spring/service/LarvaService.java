package ru.otus.spring.service;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Larva;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class LarvaService {

    public Larva eat(Larva larva) {
        byte times = (byte) RandomUtils.nextInt(1, 5);
        for (int i = 0; i < times; i++) {
            System.out.println("Larva " + larva.getIndexNumber() + " starting eating ");
            larva.setSize(larva.getSize() + 1);
            System.out.println("Larva " + larva.getIndexNumber() + " stopped eating, size = " + larva.getSize());
        }
        return larva;
    }

    public Collection<Larva> filter(Collection<Larva> larvae) {
        return larvae.stream().filter(c -> c.getSize() > 2).collect(Collectors.toList());
    }
}
