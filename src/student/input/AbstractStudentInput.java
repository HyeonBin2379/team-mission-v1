package student.input;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import student.domain.Student;
import student.interfaces.Checker;
import student.interfaces.ObjectWriter;

// 콘솔창으로 입력받은 학생별 성적을 직렬화하여 파일로 내보냄
public abstract class AbstractStudentInput implements Checker, ObjectWriter {

    protected final String fileName;
    protected HashMap<String, Student> studentInfo;

    public AbstractStudentInput(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public abstract void loadCheck(String fileName) throws FileNotFoundException;

    @Override
    public abstract void checkKeyAndInputData(String key, Student value) throws IOException;

    // 직렬화 수행
    @Override
    public void outputObject(String fileName) throws IOException {
        Path path = Paths.get("C:/Temp/" + fileName);
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path))) {
            oos.writeObject(studentInfo);
        }
        System.out.printf("[완료] %d명의 정보가 %s에 저장되었습니다.\n\n", studentInfo.size(), fileName);
    }

    public abstract void run();
}
