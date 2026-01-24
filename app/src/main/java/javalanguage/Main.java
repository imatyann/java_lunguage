package javalanguage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javalanguage.token.Token;
import javalanguage.lexer.Lexer;
import javalanguage.parser.Parser;


import javalanguage.ast.Expr;
import javalanguage.error.LangError;
import javalanguage.error.LexError;
import javalanguage.error.ParseError;
import javalanguage.error.RuntimeError;
import javalanguage.eval.Evaluator;


public class Main {

    public static void main(String[] args) throws LexError, ParseError, RuntimeError, IOException{
        Lexer lexer = new Lexer();
        Parser parser = new Parser();
        Evaluator eval = new Evaluator();
       
        String source;
        try {
            if (args.length >= 2 && args[0].equals("--file")) {
                source = Files.readString(Path.of(args[1]));

            } else {
                source = (args.length >= 1) ? String.join(" ", args) : "";
            }
                List<String> sources = Arrays.asList(source.split(";"));
                for (String s: sources){
                    List<Token> tokens = lexer.tokenize(s);
                    Expr ex = parser.parse(tokens);
                    int result = eval.eval(ex);

                    System.out.println(result);
                };       
        } catch (LangError e) {
            System.err.println("pos=" + e.pos + ": " + e.message);
            System.exit(1);
        } catch (IOException e) { 
            System.err.println("File error: " + e.getMessage());
            System.exit(1);
        } catch (RuntimeException e) {
            System.err.println("Internal error: " + e.getMessage());
            System.exit(1);
        }

    }
}