package student.input;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import student.domain.Student;
import student.interfaces.Checker;
import student.interfaces.ObjectLoader;
import student.interfaces.ObjectWriter;

// 콘솔창으로 입력받은 학생별 성적을 직렬화하여 파일로 내보냄
public abstract class AbstractStudentInput implements Checker, ObjectLoader, ObjectWriter {

    protected final String fileName;
    protected HashMap<String, Student> studentInfo;

    public AbstractStudentInput(String fileName) {
        this.fileName = fileName;
    }

    // 기존 파일 유무 확인 및 로드
    @Override
    public void loadCheck(String fileName) throws FileNotFoundException {
        try {
            File file = new File("C:/Temp/" + fileName);
            // student.dat 파일이 존재하지 않으면 신규 생성
            if (file.createNewFile()) {
                studentInfo = new HashMap<>();
                return;
            }
            // student.dat 파일이 이미 존재하면 기존 파일을 로드
            loadObjectFromFile(file.getName());
        } catch (IOException | ClassNotFoundException e) {
            throw new FileNotFoundException("[오류] 파일의 데이터를 직렬화할 수 없습니다.");
        }
    }

    // 기존 파일 로드
    @Override
    public void loadObjectFromFile(String fileName) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
        studentInfo = (HashMap<String, Student>) ois.readObject();
    }

    // 입력된 학생명 및 점수 유효성 검사
    @Override
    public void checkKeyAndInputData(String key, Student value) throws IOException {
        // 학생 이름 유효성 검사
        if (key.trim().isEmpty() || key.contains(" ")) {
            throw new IOException("[오류] 이름이 빈 문자열이거나 공백이 있습니다.\n");
        }
        if (!key.matches("[a-zA-Z가-힣]+$")) {
            throw new IOException("[오류] 이름은 한글과 영문으로만 입력 가능합니다.\n");
        }
        if (studentInfo.containsKey(key)) {
            throw new IOException("[오류] 이미 존재하는 이름입니다. 다른 이름을 입력하세요.\n");
        }

        // 학생 점수 검사
        List<Integer> record = value.getRecord();
        if (record.stream().anyMatch(score -> score < 0 || score > 100)) {
            throw new IOException("[오류] 가능한 점수의 범위는 0~100입니다.\n");
        }
        // 이름과 점수 모두 유효하면 HashMap에 저장
        insertData(key, value);
    }
    private void insertData(String key, Student value) {
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
    }

    public abstract void run();
}
