package ru.otus.spring;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.GenericSelector;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.spring.domain.Butterfly;
import ru.otus.spring.domain.Egg;
import ru.otus.spring.domain.Larva;
import ru.otus.spring.gateway.EggsButterflyGateway;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@IntegrationComponentScan
@SuppressWarnings({"resource", "Duplicates", "InfiniteLoopStatement"})
@ComponentScan
@Configuration
@EnableIntegration
public class App {
    private static int indexNumber = 0;

    public static void main(String[] args) throws Exception {
        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(App.class);

        // here we works with cafe using interface
        EggsButterflyGateway eggsButterflyGateway = ctx.getBean(EggsButterflyGateway.class);

        while (true) {
            Thread.sleep(6000);

            ForkJoinPool pool = ForkJoinPool.commonPool();
            pool.execute(() -> {
                Collection<Egg> eggs = generateButterfliesEggs();
                System.out.println("New eggs: " +
                        eggs.stream()
                                .map(Egg::getIndexNumber)
                                .map(serial -> Integer.toString(serial))
                                .collect(Collectors.joining(",")) + ", size = " + eggs.size());
                Collection<Butterfly> butterflies = eggsButterflyGateway.process(eggs);
                System.out.println(
                        "Eggs size = " + eggs.size() +
                                "\n butterflies size = " + butterflies.size()
                                + "\n Returned butterflies: " + butterflies
                                .stream()
                                .map(Butterfly::getName)
                                .collect(Collectors.joining(",")));
            });
        }
    }

    private static Collection<Egg> generateButterfliesEggs() {
        List<Egg> items = new ArrayList<>();
        for (int i = 0; i < RandomUtils.nextInt(10, 20); ++i) {
            items.add(new Egg(++indexNumber));
        }
        return items;
    }

    @Bean
    public QueueChannel eggsChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public PublishSubscribeChannel butterflyChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(100).maxMessagesPerPoll(2).get();
    }

    @Bean
    public IntegrationFlow larvaFlow() {
        return IntegrationFlows.from("eggsChannel")
                .split()
                .handle("eggService", "hatch")
                .handle("larvaService", "eat")
                .aggregate()
                .handle("larvaService", "filter")
                .channel("larvaChannel")
                .get();
    }

    @Bean
    public IntegrationFlow pupaFlow() {
        return IntegrationFlows.from("larvaChannel")
                .filter(filterPupaeByBatchSize())
                .split()
                .handle("pupaService", "pupate")
                .aggregate()
                .channel("pupaChannel")
                .get();
    }

    private GenericSelector<List<Larva>> filterPupaeByBatchSize() {
        return pupae -> pupae.size() > 5;
    }

    @Bean
    public IntegrationFlow butterflyFlow() {
        return IntegrationFlows.from("pupaChannel")
                .split()
                .handle("butterflyService", "hatch")
                .aggregate()
                .channel("butterflyChannel")
                .get();
    }
}
