import static org.junit.Assert.*;
import org.junit.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;

public class MarkdownParseTest {
    @Test
    public void addition() {
        assertEquals(2, 1 + 1);
    }
    // javac -cp ".;lib\junit-4.13.2.jar;lib\hamcrest-core-1.3.jar" MarkdownParseTest.java
    // java -cp ".;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar" org.junit.runner.JUnitCore MarkdownParseTest

    @Test
    public void testFile1() throws IOException {
        String filename = "test-file.md";
        ArrayList<String> links = MarkdownParse.getLinks(Files.readString(Path.of("test-file.md")));
        assertEquals(List.of("https://something.com", "some-page.html"), links);
    }

    @Test
    public void testSnippet1() throws IOException {
        String markdown = Files.readString(Path.of("test-snippet1.md"));
        assertEquals(List.of("`google.com", "google.com", "ucsd.edu"), MarkdownParse.getLinks(markdown));
    }

    @Test
    public void testSnippet2() throws IOException {
        String markdown = Files.readString(Path.of("test-snippet2.md"));
        assertEquals(List.of("a.com",
                "a.com(())",
                "example.com"), MarkdownParse.getLinks(markdown));
    }

    @Test
    public void testSnippet3() throws IOException {
        String markdown = Files.readString(Path.of("test-snippet3.md"));
        assertEquals(List.of("https://www.twitter.com", "https://ucsd-cse15l-w22.github.io/", "https://cse.ucsd.edu/"),
                MarkdownParse.getLinks(markdown));
    }
}