package student;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.TreeSet;

public class SortedStudent extends AbstractOutput implements Input {

    private static final int TOP_COUNT = 10;

    private HashMap<String, Student> studentInfo;
    private TreeSet<Student> sortedInfo;

    private final String inputFile;
    private final String outputFile;

    public SortedStudent(String inputFile, String outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    public void run() {
        try {
            loadObjectFromFile(inputFile);
            createTreeSet(new StudentComparator());
            printResult();
            outputObject(outputFile);
        } catch (FileNotFoundException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createTreeSet(Comparator<Student> comparator) {
        sortedInfo = new TreeSet<>(comparator);
        sortedInfo.addAll(studentInfo.values());
    }

    @Override
    public void loadObjectFromFile(String fileName) throws IOException, ClassNotFoundException {
        Path path = Paths.get(fileName);
        ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(path));
        this.studentInfo = (HashMap<String, Student>) ois.readObject();
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
        Path path = Paths.get(fileName);
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
