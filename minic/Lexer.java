package minic;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {

    // 예약어 테이블
    private static final Map<String, Integer> RESERVED_WORDS_TOKENS = Map.ofEntries(
            Map.entry("if", 40), Map.entry("while", 41),
            Map.entry("for", 42), Map.entry("const", 43),
            Map.entry("int", 44), Map.entry("float", 45),
            Map.entry("else", 46), Map.entry("return", 47),
            Map.entry("void", 48), Map.entry("break", 49),
            Map.entry("continue", 50), Map.entry("char", 51),
            Map.entry("then", 52)
    );

    // 연산자 테이블
    private static final Map<String, Integer> OPERATORS = Map.ofEntries(
            Map.entry("+", 10), Map.entry("-", 11),
            Map.entry("*", 12), Map.entry( "/", 13),
            Map.entry("%", 14), Map.entry("=", 15),
            Map.entry("!", 16), Map.entry("&&", 17),
            Map.entry("||", 18), Map.entry("==", 19),
            Map.entry("!=", 20), Map.entry("<", 21),
            Map.entry(">", 22), Map.entry("<=", 23),
            Map.entry(">=", 24)
    );

    // 기호 테이블
    private static final Map<String, Integer> SPECIAL_SYMBOLS = Map.of(
            "[", 30, "]", 31, "{", 32, "}", 33, "(", 34,
            ")", 35, ",", 36, ";", 37, "\"", 38, "'", 39
    );

    // 사용자 정의어 패턴 정의(앞글자 소문자, 이후 숫자 혹은 알파벳)
    private static final Pattern IDENTIFIER_PATTERN = Pattern.compile("[a-zA-Z][a-zA-Z0-9]*");

//    // 상수 패턴 인식
//    private static final Pattern CONSTANT_PATTERN = Pattern.compile("[A-Z0-9_\\\\s]+");

    // 소수 패턴 정의
    private static final Pattern DECIMAL_PATTERN = Pattern.compile("^[1-9][0-9]*");

    // 8진수 패턴 정의
    private static final Pattern OCTAL_PATTERN = Pattern.compile("^0[0-7]+");

    // 16진수 패턴 정의
    private static final Pattern HEXADECIMAL_PATTERN = Pattern.compile("^0[xX][0-9a-fA-F]+");

    // 실수 패턴 정의
    private static final Pattern REAL_PATTERN = Pattern.compile("^[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?");

    private String input;
    private int position;
    private Map<String, String> symbolTable = new HashMap<>(); // 사용자 예약어 테이블
    private int symbolId = 0;

    /*
        소스코드를 받아서 보관
        주석을 제외
     */
    public Lexer(String input) {
        this.input = input
                // 여러 줄 주석 제외
                .replaceAll("(?s)/\\*.*?\\*/", "")
                // (코드 중간에 있기도 한) 한줄 주석 제외
                .replaceAll("(?m)([^\"\\n\\r]+|\"[^\"]*\")//.*", "$1");
        this.position = 0; // 소스 코드를 토큰 단위로 읽은 후 그 다음으로 포지션 유지
    }

//    /*
//        소스 코드 읽어 와서 실행 될 때마다 한글자 한글자 읽고 토큰을 만드는 것
//        매칭이 되면! 리턴
//        position 이라는 어떠한 위치를 lexer 객체가 가지고 있음
//        토큰이 만들어져 return 되면 그 다음 위치 부터 다음 토큰을 찾게 된다.
//        토큰을 확실하게 준다기 보다는, 소스 코드에서 하나의 토큰을 찾아서 반환 하는 과정이라고 볼 수 있다.
//     */
//    private String nextToken() {
//        if (position >= input.length()) { // 이미 토큰을 다 읽으면 null 반환
//            return null;
//        }
//
//        // 공백 건너뛰기
//        while (position < input.length() && Character.isWhitespace(input.charAt(position))) {
//            position++;
//        }
//
//        if (position >= input.length()) {
//            return null;
//        }
//
//        // 연산자와 기호 먼저 매치하기 (한 글자)
//        for (String op : OPERATORS.keySet()) {
//            if (input.startsWith(op, position)) {
//                position += op.length();
//                return op;
//            }
//        }
//
//        for (String sym : SPECIAL_SYMBOLS.keySet()) {
//            if (input.startsWith(sym, position)) {
//                position += sym.length();
//                return sym;
//            }
//        }
//
//        // literal, identifier, number, real number를 위한 코드
//        String remainingInput = input.substring(position);
//        Matcher m;
//
//        // 토큰이 예약어, 사용자 지정어 라면
//        m = IDENTIFIER_PATTERN.matcher(remainingInput);
//        if (m.lookingAt()) {
//            String idOrReserved = m.group();
//            position += idOrReserved.length();
//            if (RESERVED_WORDS_TOKENS.containsKey(idOrReserved)) { // 예약어가 맞는지 확인
//                return idOrReserved; // 예약어 반환
//            } else { // 예약어가 아니라면
//                return idOrReserved; // 사용자 지정어 반환
//            }
//        }
//
////        // 상수 인식
////        m = CONSTANT_PATTERN.matcher(remainingInput);
////        if (m.lookingAt()) {
////            String constant = m.group();
////            position += constant.length();
////            return constant;
////        }
//
//        // real number 라면
//        m = REAL_PATTERN.matcher(remainingInput);
//        if (m.lookingAt()) {
//            String real = m.group();
//            position += real.length();
//            return real;
//        }

//        // 소수
//        m = DECIMAL_PATTERN.matcher(remainingInput);
//        if (m.lookingAt()) {
//            String decimal = m.group();
//            position += decimal.length();
//            return decimal;
//        }
//
//        // 8진수
//        m = OCTAL_PATTERN.matcher(remainingInput);
//        if (m.lookingAt()) {
//            String octal = m.group();
//            position += octal.length();
//            return octal;
//        }
//
//        // 16진수
//        m = HEXADECIMAL_PATTERN.matcher(remainingInput);
//        if (m.lookingAt()) {
//            String hexadecimal = m.group();
//            position += hexadecimal.length();
//            return hexadecimal;
//        }
//
//        // 토큰이 발견되지 않으면, 에러
//        throw new IllegalArgumentException("잘못된 코드 발견: " + input.charAt(position));
//
//    }

    //    /*
//        소스 코드 읽어 와서 실행 될 때마다 한글자 한글자 읽고 토큰을 만드는 것
//        매칭이 되면! 리턴
//        position 이라는 어떠한 위치를 lexer 객체가 가지고 있음
//        토큰이 만들어져 return 되면 그 다음 위치 부터 다음 토큰을 찾게 된다.
//        토큰을 확실하게 준다기 보다는, 소스 코드에서 하나의 토큰을 찾아서 반환 하는 과정이라고 볼 수 있다.
//     */
    private String nextToken() {
        // 입력이 끝났는지 확인
        if (position >= input.length()) {
            return null;
        }

        // 현재 문자 확인
        char currentChar = input.charAt(position++);

        // 공백은 건너뛴다.
        if (Character.isWhitespace(currentChar)) {
            return nextToken();
        }

        // 연산자, 특수 기호 확인
        String token = String.valueOf(currentChar);
        if (OPERATORS.containsKey(token) || SPECIAL_SYMBOLS.containsKey(token)) {
            // 두 글자 연산자 확인 (예: ==, &&)
            if (position < input.length()) {
                String twoCharToken = token + input.charAt(position);
                if (OPERATORS.containsKey(twoCharToken) || SPECIAL_SYMBOLS.containsKey(twoCharToken)) {
                    position++;
                    return twoCharToken;
                }
            }
            return token;
        }

        // 숫자나 식별자 시작인지 확인
        if (Character.isDigit(currentChar) || Character.isLetter(currentChar)) {
            StringBuilder sb = new StringBuilder(token);
            while (position < input.length() && (Character.isDigit(input.charAt(position)) || Character.isLetter(input.charAt(position)))) {
                sb.append(input.charAt(position++));
            }
            return sb.toString();
        }

        // 알 수 없는 문자
        throw new IllegalArgumentException("잘못된 코드 발견: " + currentChar);
    }


    /*
        이 메소드에서는 nextToken()에서 반환한 토큰들이 어떤 토큰인지 확인한 후, 토큰을 출력한다.
        null 값을 반환할 때까지 반복해서 출력한다.
     */
    public void tokenize() {
        String token;
        while ((token = nextToken()) != null) { // nextToken()에서 한 토큰이 리턴된다. 토큰이 더이상 없는 경우 null이 반환된다.
            if (RESERVED_WORDS_TOKENS.containsKey(token.toLowerCase())) { // 먼저 예약어 인지 확인한다.
                System.out.println(token + " (예약어, Token: (" + RESERVED_WORDS_TOKENS.get(token.toLowerCase()) + ", 0)");
            } else if (OPERATORS.containsKey(token)) { // 연산자 인지 확인한다.
                System.out.println(token + " (연산자, Token: (" + OPERATORS.get(token) + ", 0)");
            } else if (SPECIAL_SYMBOLS.containsKey(token)) { // 기호 인지 확인한다.
                System.out.println(token + " (특수 기호, Token: (" + SPECIAL_SYMBOLS.get(token) + ", 0)");
            } else if (token.matches("^[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?$")) { // 실수인지 확인한다.
                System.out.println(token + " (실수, Token: (6,"+token+")");
            } else if (token.matches("^[1-9][0-9]*")) { // 정수인지 확인한다.
                System.out.println(token + " (정수, Token: (5,"+token+")");
            } else if (token.matches("^0[0-7]+")) { // 8진수인지 확인한다.
                System.out.println(token + " (8진수, Token: (5,"+token+")");
            } else if (token.matches("^0[xX][0-9a-fA-F]+")) { // 16진수인지 확인한다.
                System.out.println(token + " (16진수, Token: (5," +token+ ")");
//            } else if (token.matches("^[A-Z0-9_\\\\s]+")) { // 상수를 처리한다.
//                System.out.println(token + " (Constant, Token: 4)");
            } else if (IDENTIFIER_PATTERN.matcher(token).matches()) {
                // 최종적으로 사용자 정의어인지 확인한다.
                if (!RESERVED_WORDS_TOKENS.containsKey(token.toLowerCase())) {
                    // 만약 테이블에 없을 경우, 넣는다.
                    if (!symbolTable.containsKey(token)) {
                        symbolTable.put(token, String.format("00,%02d", symbolId++));
                    }// 있으면 가져 온다.
                    System.out.println(token + " (사용자 정의어, Token: (" + symbolTable.get(token) + "))");
                }
            } else { // 만약 모든 곳에서도 해당되지 않으면 에러가 발생한다.
                System.err.println("알 수 없는 토큰 발생: " + token);
            }
        }
    }
}
