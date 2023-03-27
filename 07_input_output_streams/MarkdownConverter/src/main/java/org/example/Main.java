package org.example;

import bg.sofia.uni.fmi.mjt.markdown.MarkdownConverter;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        MarkdownConverter mdc = new MarkdownConverter();

        mdc.convertMarkdown(Path.of("C:\\Users\\User1\\Desktop\\Java-Course-FMI\\07_input_output_streams\\MarkdownConverter\\src\\main\\java\\org\\example\\test.md"),
                Path.of("C:\\Users\\User1\\Desktop\\Java-Course-FMI\\07_input_output_streams\\MarkdownConverter\\src\\main\\java\\org\\example\\result.html"));
    }
}