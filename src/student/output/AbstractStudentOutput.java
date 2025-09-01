package student.output;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import student.domain.Student;
import student.interfaces.ObjectLoader;
import student.interfaces.Reporter;

// 직렬화한 학생 성적 정보를 읽어온 후 출력
public abstract class AbstractStudentOutput implements ObjectLoader, Reporter {

    protected static int topCount = 10;     // 최대 n명까지의 학생들까지 출력

    protected HashMap<String, Student> studentInfo;

    public AbstractStudentOutput() {
        this.studentInfo = new HashMap<>();
    }

    @Override
    public void loadObjectFromFile(String fileName) throws IOException, ClassNotFoundException {
        Path path = Paths.get("C:/Temp/" + fileName);
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(path))) {
            studentInfo = (HashMap<String, Student>) ois.readObject();
        }
    }

    @Override
    public abstract void printResult();

    public abstract void run();
}
