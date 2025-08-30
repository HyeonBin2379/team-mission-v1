package student.input;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import student.domain.Student;
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
            File file = new File("C:/Temp/" + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new FileNotFoundException(fileName + " 파일이 존재하지 않아 새로운 파일을 생성합니다.\n");
        }
    }

    @Override
    public void checkKeyAndInputData(String key, Student value) throws IOException {
        if (key.trim().isEmpty() || key.contains(" ")) {
            throw new IOException("[오류] 이름이 빈 문자열이거나 공백이 있습니다.\n");
        }
        if (studentInfo.containsKey(key)) {
            throw new IOException("[오류] 이미 존재하는 이름입니다. 다른 이름을 입력하세요.\n");
        }

        List<Integer> record = value.getRecord();
        if (record.stream().anyMatch(score -> score < 0 || score > 100)) {
            throw new IOException("[오류] 가능한 점수의 범위는 0~100입니다.\n");
        }
        insertEntryToMap(key, value);
    }

    public void insertEntryToMap(String key, Student value) {
        value.setTotal();
        value.setAverage();
        value.setGrade();
        studentInfo.put(key, value);
        System.out.printf("=> 저장됨: %s\n\n", value);
    }

    // 직렬화 수행
    @Override
    public void outputObject(String fileName) throws IOException {
        Path path = Paths.get("C:/Temp/" + fileName);
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path))) {
            oos.writeObject(studentInfo);
        }
        System.out.printf("[완료] %d명의 정보가 %s에 저장되었습니다.\n\n", studentInfo.size(), fileName);
    }
}
