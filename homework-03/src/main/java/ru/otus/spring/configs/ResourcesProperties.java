package ru.otus.spring.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "resources")
public class ResourcesProperties {
    private String csvFileName;

    public void setCsvFileName(String csvFileName) {
        this.csvFileName = csvFileName;
    }

    public String getCsvFileName() {
        return csvFileName;
    }
}
