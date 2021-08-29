package ua.goit.goitnotes.markdownConverterTests;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import ua.goit.goitnotes.exeptions.DataNotAvailableException;
import ua.goit.goitnotes.markdownConverter.MarkdownConverterCommonMarkdownImplementation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
public class MarkdownConverterCommonMarkdownImplementationTest {

    private final MarkdownConverterCommonMarkdownImplementation markdownConverter =
            new MarkdownConverterCommonMarkdownImplementation();

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
        String conversionResult = markdownConverter.convert(note);
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
        assertThrows(DataNotAvailableException.class, () -> markdownConverter.convert(note));
    }
}
