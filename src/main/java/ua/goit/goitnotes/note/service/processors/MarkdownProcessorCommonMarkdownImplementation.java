package ua.goit.goitnotes.note.service.processors;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Service;
import ua.goit.goitnotes.exceptions.DataNotAvailableException;
import ua.goit.goitnotes.interfaces.MarkdownProcessor;

@Service
public class MarkdownProcessorCommonMarkdownImplementation implements MarkdownProcessor {
    @Override
    public String getHtml(String markdownText) {
        if (markdownText == null) throw new DataNotAvailableException("note is null");
        Parser parser = Parser.builder().build();
        Node note = parser.parse(markdownText);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(note);
    }
}
