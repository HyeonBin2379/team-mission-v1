package student.input;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import student.Student;
import student.interfaces.Reporter;

public class StudentInput extends AbstractInput implements Reporter {

    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private final List<String> subjects = Arrays.asList("국어: ", "영어: ", "수학: ", "과학: ");

    public StudentInput(String fileName) {
        super(fileName);
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
                List<String> record = new ArrayList<>();
                for (String subject : subjects) {
                    System.out.print(subject);
                    String score = br.readLine();
                    record.add(score);
                }
                checkKeyAndInputData(studentName, new Student(studentName, record));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
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

    public void checkKeyAndInputData(String key, Student value) throws IOException {
        if (key.trim().isEmpty() || key.contains(" ")) {
            throw new IOException("[오류] 이름이 빈 문자열이거나 공백이 있습니다.");
        }
        if (studentInfo.containsKey(key)) {
            throw new IOException("[오류] 이미 존재하는 이름입니다. 다른 이름을 입력하세요.");
        }
        List<Integer> record = value.getRecord();
        if (record.stream().anyMatch(score -> score < 0 || score > 100)) {
            throw new IOException("[오류] 가능한 점수의 범위는 0~100입니다.");
        }
        insertEntry(key, value);
    }
    private void insertEntry(String key, Student value) {
        value.setTotal();
        value.setAverage();
        value.setGrade();
        studentInfo.put(key, value);
        System.out.printf("=> 저장됨: %s\n\n", value);
    }

    public void exitInput() throws IOException {
        printResult();
        outputObject(fileName);
    }

    @Override
    public void printResult() {
        System.out.println("exit\n입력을 종료합니다.");
    }
}
