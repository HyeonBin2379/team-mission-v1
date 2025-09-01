package student.output;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.TreeSet;
import student.domain.Student;
import student.interfaces.ObjectWriter;

// 직렬화한 학생 정보를 읽어와 과목별 점수의 평균을 기준으로 정렬하고, 그 결과를 직렬화하여 저장
public class SortedStudent extends AbstractStudentOutput implements ObjectWriter {

    private static final int LIMIT_COUNT = 10;

    private final String inputFile;
    private final String outputFile;

    private TreeSet<Student> sortedInfo;

    public SortedStudent(String inputFile, String outputFile) {
        super();
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    public void createTreeSet(Comparator<Student> comparator) {
        sortedInfo = new TreeSet<>(comparator);
        sortedInfo.addAll(studentInfo.values());
    }

    @Override
    public void printResult() {
        System.out.printf("""
                [정렬 및 저장: 평균 오름차순]
                불러온 학생 수: %1$d
                정렬 규칙: 평균 ASC, 평균 동률이면 이름 사전순 ASC
        
                저장 대상(미리보기 상위 %2$d명)
                """, Math.min(sortedInfo.size(), LIMIT_COUNT), LIMIT_COUNT);
        // 정렬결과 출력 시, 최대 10명의 학생들의 성적을 출력
        sortedInfo.stream()
                .limit(LIMIT_COUNT)
                .forEach(student -> System.out.printf("- %s (평균 %.1f)\n", student.getName(), student.getAverage()));
    }

    @Override
    public void outputObject(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path))) {
            oos.writeObject(sortedInfo);
        }
        System.out.printf("\n결과 파일: %s\n", path);
        System.out.println("[완료] 정렬된 결과를 파일로 저장했습니다.");
    }

    @Override
    public void run() {
        try {
            loadObjectFromFile(inputFile);
            createTreeSet(new StudentComparator());
            printResult();
            outputObject(outputFile);
        } catch (FileNotFoundException | ClassNotFoundException e) {
            System.out.println("[오류] " + e.getMessage());
        } catch (IOException e) {
            System.out.println("[오류] " + e.getClass() + " " + e.getMessage());
        }
    }

    // Serialized 인터페이스를 구현한 Comparator 객체를 사용해야 TreeSet인 sortedeInfo 직렬화 가능
    static class StudentComparator implements Comparator<Student>, Serializable {

        @Override
        public int compare(Student o1, Student o2) {
            boolean averageDiff = Double.compare(o1.getAverage(), o2.getAverage()) != 0;
            return averageDiff
                    ? Double.compare(o1.getAverage(), o2.getAverage())
                    : o1.getName().compareTo(o2.getName());
        }
    }
}
