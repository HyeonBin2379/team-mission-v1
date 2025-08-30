package student.input;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import student.Student;
import student.interfaces.Checker;
import student.interfaces.ObjectWriter;

public abstract class AbstractInput implements Checker, ObjectWriter {

    protected final String fileName;
    protected final HashMap<String, Student> studentInfo;

    public AbstractInput(String fileName) {
        this.fileName = fileName;
        this.studentInfo = new HashMap<>();
    }

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

    // 직렬화 수행
    @Override
    public void outputObject(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path))) {
            oos.writeObject(studentInfo);
        }
        System.out.printf("[완료] %d명의 정보가 %s에 저장되었습니다.\n\n", studentInfo.size(), fileName);
    }
}
