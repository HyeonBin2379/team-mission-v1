package student;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public abstract class AbstractInput implements Input, Printable {

    @Override
    public void loadCheck(String fileName) throws FileNotFoundException {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new FileNotFoundException(fileName + " 파일이 존재하지 않아 새로운 파일을 생성합니다.");
        }
    }

    @Override
    public void printUsage() {
        System.out.println("""
            - 종료하려면 이름에 ^^ 를 입력하세요.
            - 점수는 0~100 사이의 정수만 허용됩니다.
            """);
    }

    public void readyToInput(String fileName) {
        System.out.println("[학생 성적 입력 프로그램]");
        try {
            loadCheck(fileName);
            printUsage();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // 직렬화 수행(메서드 오버로딩)
    public void outputObject(String fileName, Map<String, Student> studentInfo) throws IOException {
        Path path = Paths.get(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path));
        oos.writeObject(studentInfo);
    }

    @Override
    public void printResult() {
        System.out.println("exit\n입력을 종료합니다.");
    }
}
