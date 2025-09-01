package student.input;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import student.domain.Student;
import student.interfaces.Reporter;

public class StudentInput extends AbstractStudentInput implements Reporter {

    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private final List<String> subjects = Arrays.asList("국어: ", "영어: ", "수학: ", "과학: ");

    public StudentInput(String fileName) {
        super(fileName);
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

    @Override
    public void loadCheck(String fileName) throws FileNotFoundException {
        try {
            // student.dat 파일이 없다면 새로 생성
            File file = new File("C:/Temp/" + fileName);
            if (file.createNewFile()) {
                studentInfo = new HashMap<>();
                return;
            }
            // student.dat 파일이 존재하면 해당 파일에서 로드
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            studentInfo = (HashMap<String, Student>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new FileNotFoundException("[오류] 파일로부터 데이터를 불러올 수 없습니다.");
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
        insertData(key, value);
    }
    private void insertData(String key, Student value) {
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
