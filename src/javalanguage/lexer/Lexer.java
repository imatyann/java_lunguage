package javalanguage.lexer;
// String sourceを精査し、Tokenリストに変換する

import java.util.ArrayList;
import java.util.List;


import javalanguage.error.LexError;
import javalanguage.token.Token;
import javalanguage.token.TokenType;

public final class Lexer {
    
    // Tokenリストを生成する
    public List<Token> tokenize(String source) throws LexError {
        //　まずEOFだけのリストを生成する
        List<Token> tokens = new ArrayList<>();
        
        tokens.add(new Token(TokenType.EOF, 0));
        return tokens;
    }

}
