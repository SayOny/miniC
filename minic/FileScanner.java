package minic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*
    파일을 읽어서 파일의 내용인 소스 코드를 반환 한다.
 */
public class FileScanner {
    public static String scanFile(String filename) {
        StringBuilder fileContent = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                fileContent.append(line).append("\n"); // 라인을 읽어서 fileContent에 추가
            }
        } catch (IOException e) { // 예외 처리
            System.err.println("파일 찾기 실패: " + e.getMessage());
            return null;
        }
        return fileContent.toString();
    }
}
