// File reading code from https://howtodoinjava.com/java/io/java-read-file-to-string-examples/
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;


public class MarkdownParse {
    public static ArrayList<String> getLinks(String markdown) {
        ArrayList<String> toReturn = new ArrayList<>();
        // find the next [, then find the ], then find the (, then take up to
        // the next )
        int currentIndex = 0;
        while(currentIndex < markdown.length()) {
            int nextOpenBracket = markdown.indexOf("[", currentIndex);
            int nextCloseBracket = markdown.indexOf("]", nextOpenBracket);
            int openParen = markdown.indexOf("(", nextCloseBracket);
            int closeParen = markdown.indexOf(")", openParen);
            //fix image bug
            if(closeParen == -1)
            {
                return toReturn;
            }
            if(markdown.charAt(nextOpenBracket - 1) == '!')
            {
                currentIndex = closeParen + 1;
                continue;
            }
            // fix missing paren bug
            if(closeParen > markdown.indexOf("\n",openParen) && markdown.indexOf("\n", openParen) != -1) {
                currentIndex = markdown.indexOf("\n",openParen + 1);
                continue;
            }
            //fix parenthesis/brackets within link bug
            if(closeParen+1 != markdown.length()) {
                // looks for newline while making sure that
                // closing parenthesis index is not right before markdown length
                while(closeParen+1 < markdown.length() && markdown.indexOf("\n",closeParen) != closeParen + 1) { 
                    if(markdown.indexOf("\n",closeParen) != -1 && markdown.indexOf("\n",closeParen) < markdown.indexOf("[", closeParen))
                    {
                        break;
                    }
                    //updates closeParen if new line is not right after closing parenthesis
                    closeParen = markdown.indexOf(")", closeParen+1);                     
                }
            }
            toReturn.add(markdown.substring(openParen + 1, closeParen));
            currentIndex = closeParen + 1;
            
        }
        return toReturn;
    }
    public static void main(String[] args) throws IOException {
        File fileOrDir = new File(args[0]);
        Path fileName = Path.of(args[0]);
        String contents = Files.readString(fileName);
        ArrayList<String> links = getLinks(contents);
        System.out.println(links);

    }
}
