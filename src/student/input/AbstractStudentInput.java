package student.input;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import student.domain.Student;
import student.interfaces.Checker;
import student.interfaces.ObjectLoader;
import student.interfaces.ObjectWriter;
import student.interfaces.Reporter;

// 콘솔창으로 입력받은 학생별 성적을 직렬화하여 파일로 내보냄
public abstract class AbstractStudentInput implements Checker, ObjectLoader, ObjectWriter, Reporter {

    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private final List<String> subjects = Arrays.asList("국어: ", "영어: ", "수학: ", "과학: ");

    protected final String fileName;
    protected HashMap<String, Student> studentInfo;

    public AbstractStudentInput(String fileName) {
        this.fileName = fileName;
    }

    public void run() {
        readyToInput(fileName);
        while (true) {
            try {
                System.out.print("이름: ");
                String studentName = br.readLine();
                if (studentName.equals("^^")) {
                    exitInput();
                    break;
                }
                input(studentName);
            } catch (IOException | IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public void readyToInput(String fileName) {
        System.out.println("[학생 성적 입력 프로그램]");
        try {
            // student.dat 파일 불러오기 및 학생 성적 입력방법 안내
            loadCheck(fileName);
            printUsage();
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public void input(String studentName) throws IOException, IllegalArgumentException {
        List<String> record = new ArrayList<>();
        for (String subject : subjects) {
            System.out.print(subject);
            String score = br.readLine();
            record.add(score);
        }
        checkKeyAndInputData(studentName, new Student(studentName, record));
    }

    public void exitInput() throws IOException {
        System.out.println("exit\n입력을 종료합니다.");
        outputObject(fileName);
        printResult();
    }

    // 기존 파일 유무 확인 및 로드
    @Override
    public abstract void loadCheck(String fileName) throws FileNotFoundException;

    // 기존 파일 로드
    @Override
    public abstract void loadObjectFromFile(String fileName) throws IOException, ClassNotFoundException;

    // 입력된 학생명 및 점수 유효성 검사
    @Override
    public abstract void checkKeyAndInputData(String key, Student value) throws IOException;

    // 직렬화 수행
    @Override
    public abstract void outputObject(String fileName) throws IOException;
}
