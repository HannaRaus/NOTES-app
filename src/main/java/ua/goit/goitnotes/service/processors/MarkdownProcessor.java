package ua.goit.goitnotes.service.processors;

public interface MarkdownProcessor {

    String getHTML(String markdown);

    String getMarkdown(String html);

}
