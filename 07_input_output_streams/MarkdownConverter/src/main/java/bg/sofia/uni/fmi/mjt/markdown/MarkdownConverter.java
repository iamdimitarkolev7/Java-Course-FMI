package bg.sofia.uni.fmi.mjt.markdown;

import bg.sofia.uni.fmi.mjt.markdown.converter.*;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class MarkdownConverter implements MarkdownConverterAPI {
    private final String OPENING_HTML_TAGS = """
            <body>
                <head>""";

    private final String CLOSING_HTML_TAGS = """
                </head>
            </body>""";

    @Override
    public void convertMarkdown(Reader source, Writer output) {
        BufferedReader reader = new BufferedReader(source);
        PrintWriter writer = new PrintWriter(output);

        writer.println(OPENING_HTML_TAGS);

        String line;
        Converter headerConverter = new HeaderConverter();
        List<Converter> formattingConverters = List.of(new BoldConverter(), new CodeConverter(), new ItalicConverter());

        try {
            while ((line = reader.readLine()) != null) {
                if (line.strip().equals("")) {
                    writer.println(line);
                    continue;
                }

                if (headerConverter.isApplicable(line)) {
                    line = headerConverter.apply(line);
                } else {
                    for (Converter converter : formattingConverters) {
                        if (converter.isApplicable(line)) {
                            line = converter.apply(line);
                        }
                    }
                }

                writer.println(line);
            }

            writer.println(CLOSING_HTML_TAGS);
        } catch(IOException ioException) {
            throw new RuntimeException("Error while processing markdown file", ioException);
        }
    }

    @Override
    public void convertMarkdown(Path from, Path to) {
        try (var fileReader = new FileReader(from.toString());
             var fileWriter = new FileWriter(to.toString())) {
            convertMarkdown(fileReader, fileWriter);
        } catch (IOException e) {
            throw new RuntimeException("Error occurred while converting markdown file " + from.getFileName(), e);
        }
    }

    @Override
    public void convertAllMarkdownFiles(Path sourceDir, Path targetDir) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourceDir, "*.md")) {
            for (Path file : stream) {
                String sourceFileName = file.getFileName().toString();
                String sourceFileNameWithoutExtension = sourceFileName.substring(0, sourceFileName.indexOf("."));
                Path targetFile = targetDir.resolve(sourceFileNameWithoutExtension + ".html");
                convertMarkdown(file, targetFile);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error occurred while converting markdown files from dir: " + sourceDir, e);
        }
    }
}
