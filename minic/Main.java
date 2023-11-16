package minic;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // 콘솔로부터 입력을 받기 위한 Scanner 객체 생성
        System.out.println("파일 경로: ");
        String inputText = scanner.nextLine(); // 입력한 파일 경로 읽기

        // 파일 스캔 해서 소스 코드 얻어 오기
        String sourceCode = FileScanner.scanFile(inputText);

        //스캐너 객체 만들기
        Lexer lexer = new Lexer(sourceCode);

        // 소스 코드 토큰화 하기
        lexer.tokenize();
    }
}
