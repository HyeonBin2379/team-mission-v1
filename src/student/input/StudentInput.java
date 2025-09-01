package student.input;

import java.io.*;
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

    // 학생데이터 입력 전 시작 단계
    public void readyToInput(String fileName) {
        System.out.println("[학생 성적 입력 프로그램]");
        try {
            // student.dat 파일 불러오기 및 학생 성적 입력방법 안내
            loadCheck(fileName);
            printUsage();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // 학생정보 입력 단계
    public void input(String studentName) throws IOException, IllegalArgumentException {
        List<String> record = new ArrayList<>();
        for (String subject : subjects) {
            System.out.print(subject);
            String score = br.readLine();
            record.add(score);
        }
        checkKeyAndInputData(studentName, new Student(studentName, record));
    }

    // 입력 종료 단계
    public void exitInput() throws IOException {
        System.out.println("exit\n입력을 종료합니다.");
        outputObject(fileName);
        printResult();
    }

    @Override
    public void printResult() {
        System.out.printf("[완료] %d명의 정보가 %s에 저장되었습니다.\n\n", studentInfo.size(), fileName);
    }

    @Override
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
                System.out.println(e.getMessage());
            }
        }
    }
}
