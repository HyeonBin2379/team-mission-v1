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

public class SortedStudent extends AbstractOutput implements ObjectWriter {

    private final String inputFile;
    private final String outputFile;

    private TreeSet<Student> sortedInfo;

    public SortedStudent(String inputFile, String outputFile) {
        super();
        this.inputFile = inputFile;
        this.outputFile = outputFile;
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
                """, Math.min(sortedInfo.size(), TOP_COUNT), TOP_COUNT);
        sortedInfo.stream()
                .limit(TOP_COUNT)
                .forEach(student -> System.out.printf("- %s (평균 %.1f)\n", student.getName(), student.getAverage()));
    }

    @Override
    public void outputObject(String fileName) throws IOException {
        Path path = Paths.get("C:/Temp/" + fileName);
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path))) {
            oos.writeObject(sortedInfo);
        }
        System.out.printf("\n결과 파일: ./%s\n", fileName);
        System.out.println("[완료] 정렬된 결과를 파일로 저장했습니다.");
    }

    private static class StudentComparator implements Comparator<Student>, Serializable {
        @Override
        public int compare(Student o1, Student o2) {
            boolean averageDiff = Double.compare(o1.getAverage(), o2.getAverage()) != 0;
            return averageDiff
                    ? Double.compare(o1.getAverage(), o2.getAverage())
                    : o1.getName().compareTo(o2.getName());
        }
    }
}
