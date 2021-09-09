package ua.goit.goitnotes.note.service.markdownProcessorTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.goit.goitnotes.config.CustomTestConfiguration;
import ua.goit.goitnotes.error_handling.ObjectNotFoundException;
import ua.goit.goitnotes.note.service.processors.MarkdownProcessorCommonMarkdownImplementation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CustomTestConfiguration.class)
public class MarkdownProcessorCommonMarkdownImplementationTest {

    @Autowired
    private MarkdownProcessorCommonMarkdownImplementation markdownConverter;

    @Test
    void testMarkdownConverterCommonMarkdownImplementationShouldConvertCorrectly() {
        //given
        String note = """
                # Header 1

                ## Header 2

                 that\s

                 would\s

                 be

                 paragraphs

                and some **bold** and _italic_ text""";
        //when
        String conversionResult = markdownConverter.getHtml(note);
        //then
        assertThat(conversionResult).isEqualTo("""
                <h1>Header 1</h1>
                <h2>Header 2</h2>
                <p>that</p>
                <p>would</p>
                <p>be</p>
                <p>paragraphs</p>
                <p>and some <strong>bold</strong> and <em>italic</em> text</p>
                """);
    }

    @Test
    void testMarkDownConverterCommonMarkdownImplementationShouldThrowDataNotAvailableException() {
        //given
        String note = null;
        //when
        //then
        assertThrows(ObjectNotFoundException.class, () -> markdownConverter.getHtml(note));
    }
}
