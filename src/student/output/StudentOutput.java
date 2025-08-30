package student.output;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import student.domain.Student;
import student.interfaces.ObjectLoader;

public class StudentOutput extends AbstractOutput implements ObjectLoader {

    private final List<Student> datas;
    private final List<String> names;

    private final String fileName;

    public StudentOutput(String fileName) {
        super();
        this.datas = new ArrayList<>();
        this.names = new ArrayList<>();
        this.fileName = fileName;
    }

    public void rearrangeData(Comparator<Student> comparator) {
        Set<String> keys = studentInfo.keySet();
        keys.stream().map(studentInfo::get)
                .sorted(comparator)
                .forEach(student -> {
                    names.add(student.getName());
                    datas.add(student);
                });
        studentInfo = (HashMap<String, Student>) IntStream.range(0, datas.size()).boxed()
                .collect(Collectors.toMap(names::get, datas::get));
    }

    @Override
    public void printResult() {
        System.out.println("[평균 오름차순 성적표]");
        IntStream.rangeClosed(1, datas.size())
                .limit(TOP_COUNT)
                .forEach(num -> {
                    Student data = datas.get(num-1);
                    System.out.println(data.sortFormat(num));
                });
    }

    public void run() {
        try {
            loadObjectFromFile(fileName);
            rearrangeData(Comparator.comparingDouble(Student::getAverage));
            printResult();
        } catch (FileNotFoundException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}