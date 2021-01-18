package ru.otus.spring.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "resources")
public class ResourcesProperties {
    @Getter
    @Setter
    private String csvFileName;
}
