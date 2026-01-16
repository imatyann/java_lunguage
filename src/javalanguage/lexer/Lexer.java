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
        List<Token> tokens = new ArrayList<>();
        
        int l = source.length();
        int i = 0;
        
        // source中の文字に対してそれぞれ実行する
        while (i < l) {
            char word = source.charAt(i);
            // 空白を除去する
            if (Character.isWhitespace(word)){
                i++;
                continue;
            }

            // 整数リテラルのチェック
            if (Character.isDigit(word)){
                // 整数に出会ったら、初めて整数以外に出会うかiがlを超すまで繰り返す
                int value = 0;
                int startI = i;
                while (Character.isDigit(source.charAt(i)) && (i < l) ) {
                    int n = word - '0';
                    value = value * 10 + n ;
                    i ++;
                }

                tokens.add(new Token(TokenType.INT, startI, value));
                continue;
            }

            // 演算子と括弧のチェック
            switch (word) {
                case '+':
                    tokens.add(new Token(TokenType.PLUS, i));
                case '-':
                    tokens.add(new Token(TokenType.MINUS, i));
                case '*':
                    tokens.add(new Token(TokenType.MUL, i));
                case '/':
                    tokens.add(new Token(TokenType.DIV, i));
                case '(':
                    tokens.add(new Token(TokenType.LPAREN, i));
                case ')':
                    tokens.add(new Token(TokenType.RPAREN, i));
                default:
                    // 未知の文字が生じた場合のエラー
                    throw new LexError(i, "未知の文字：" + word);
                }
        }

        tokens.add(new Token(TokenType.EOF, l));
        return tokens;
    }

}
