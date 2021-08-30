package ua.goit.goitnotes.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import ua.goit.goitnotes.markdownConverter.MarkdownConverterCommonMarkdownImplementation;

@TestConfiguration
public class CustomTestConfiguration {
@Bean
    public MarkdownConverterCommonMarkdownImplementation markdownConverterCommonMarkdownImplementation(){
    return new MarkdownConverterCommonMarkdownImplementation();
}
import ua.goit.goitnotes.service.ValidationService;

@TestConfiguration
public class CustomTestConfiguration {
    @Bean
    public ValidationService validationService(){
        return new ValidationService();
    }
}
