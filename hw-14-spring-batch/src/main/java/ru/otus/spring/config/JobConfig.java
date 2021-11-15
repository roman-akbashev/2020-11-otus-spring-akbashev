package ru.otus.spring.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.model.documents.AuthorMongo;
import ru.otus.spring.model.documents.BookMongo;
import ru.otus.spring.model.documents.GenreMongo;
import ru.otus.spring.model.entities.AuthorJpa;
import ru.otus.spring.model.entities.BookJpa;
import ru.otus.spring.model.entities.GenreJpa;
import ru.otus.spring.repository.jpa.AuthorRepositoryJpa;
import ru.otus.spring.repository.jpa.BookRepositoryJpa;
import ru.otus.spring.repository.jpa.GenreRepositoryJpa;
import ru.otus.spring.service.ConverterService;

import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
public class JobConfig {
    private static final int CHUNK_SIZE = 5;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final BookRepositoryJpa bookRepositoryJpa;
    private final AuthorRepositoryJpa authorRepositoryJpa;
    private final GenreRepositoryJpa genreRepositoryJpa;

    @Bean
    public Job dataMigrationJob(Step stepAuthors, Step stepGenres, Step stepBooks) {
        return jobBuilderFactory.get("dataMigrationJob")
                .start(stepAuthors)
                .next(stepGenres)
                .next(stepBooks)
                .build();
    }

    @Bean
    public MongoItemReader<BookMongo> readerBook(MongoTemplate template) {
        return new MongoItemReaderBuilder<BookMongo>()
                .name("bookItemReader")
                .template(template)
                .jsonQuery("{}")
                .targetType(BookMongo.class)
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public MongoItemReader<AuthorMongo> readerAuthor(MongoTemplate template) {
        return new MongoItemReaderBuilder<AuthorMongo>()
                .name("authorItemReader")
                .template(template)
                .jsonQuery("{}")
                .targetType(AuthorMongo.class)
                .sorts(new HashMap<>())
                .build();
    }

    @StepScope
    @Bean
    public MongoItemReader<GenreMongo> readerGenre(MongoTemplate template) {
        return new MongoItemReaderBuilder<GenreMongo>()
                .name("genreItemReader")
                .template(template)
                .jsonQuery("{}")
                .targetType(GenreMongo.class)
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public ItemProcessor<BookMongo, BookJpa> processorBook(ConverterService converterService) {
        return converterService::convertBook;
    }

    @Bean
    public ItemProcessor<AuthorMongo, AuthorJpa> processorAuthor(ConverterService converterService) {
        return converterService::convertAuthor;
    }

    @Bean
    public ItemProcessor<GenreMongo, GenreJpa> processorGenre(ConverterService converterService) {
        return converterService::convertGenre;
    }

    @Bean
    public RepositoryItemWriter<AuthorJpa> writerAuthor() {
        return new RepositoryItemWriterBuilder<AuthorJpa>()
                .methodName("save")
                .repository(authorRepositoryJpa)
                .build();
    }

    @Bean
    public RepositoryItemWriter<GenreJpa> writerGenre() {
        return new RepositoryItemWriterBuilder<GenreJpa>()
                .methodName("save")
                .repository(genreRepositoryJpa)
                .build();
    }

    @Bean
    public RepositoryItemWriter<BookJpa> writerBook() {
        return new RepositoryItemWriterBuilder<BookJpa>()
                .methodName("save")
                .repository(bookRepositoryJpa)
                .build();
    }

    @Bean
    public Step stepAuthors(MongoItemReader<AuthorMongo> reader, ItemProcessor<AuthorMongo, AuthorJpa> itemProcessor, RepositoryItemWriter<AuthorJpa> writer) {
        return stepBuilderFactory.get("stepAuthors")
                .<AuthorMongo, AuthorJpa>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step stepGenres(MongoItemReader<GenreMongo> reader, ItemProcessor<GenreMongo, GenreJpa> itemProcessor, RepositoryItemWriter<GenreJpa> writer) {
        return stepBuilderFactory.get("stepGenres")
                .<GenreMongo, GenreJpa>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step stepBooks(MongoItemReader<BookMongo> reader, ItemProcessor<BookMongo, BookJpa> itemProcessor, RepositoryItemWriter<BookJpa> writer) {
        return stepBuilderFactory.get("stepBooks")
                .<BookMongo, BookJpa>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .build();
    }
}