package student;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class AbstractStudentInput implements Input, Printable {

    public void readyToInput(String fileName) {
        System.out.println("[학생 성적 입력 프로그램]");
        try {
            loadCheck(fileName);
            printUsage();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // 직렬화 수행
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
