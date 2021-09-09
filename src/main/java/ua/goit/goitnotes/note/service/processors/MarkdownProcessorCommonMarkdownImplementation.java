package ua.goit.goitnotes.note.service.processors;

import lombok.extern.slf4j.Slf4j;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Service;
import ua.goit.goitnotes.error_handling.ObjectNotFoundException;

@Slf4j
@Service
public class MarkdownProcessorCommonMarkdownImplementation implements MarkdownProcessor {
    @Override
    public String getHtml(String markdownText) {
        log.info("getHTML .");
        if (markdownText == null) throw new ObjectNotFoundException("note is null");
        Parser parser = Parser.builder().build();
        Node note = parser.parse(markdownText);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(note);
    }
}
