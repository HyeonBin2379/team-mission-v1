package student.input;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import student.domain.Student;
import student.interfaces.Reporter;

public class StudentInput extends AbstractStudentInput implements Reporter {

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
                inputScore(studentName);
            } catch (IOException | IllegalArgumentException e) {
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

    public void inputScore(String studentName) throws IOException, IllegalArgumentException {
        List<String> record = new ArrayList<>();
        for (String subject : subjects) {
            System.out.print(subject);
            String score = br.readLine();
            record.add(score);
        }
        checkKeyAndInputData(studentName, new Student(studentName, record));
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
