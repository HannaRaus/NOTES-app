package ua.goit.goitnotes.markdownConverter;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Service;
import ua.goit.goitnotes.exeptions.DataNotAvailableException;

@Service
public class MarkdownConverterCommonMarkdownImplementation implements MarkDownConverter {
    @Override
    public String convert(String markdownText) {
        if (markdownText == null) throw new DataNotAvailableException("note is null");
        Parser parser = Parser.builder().build();
        Node Note = parser.parse(markdownText);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        String resultNote = renderer.render(Note);
        return resultNote;
    }
}
