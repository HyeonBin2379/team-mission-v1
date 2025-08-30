package student;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StudentOutput {

    private final String fileName;

    public StudentOutput(String fileName) {
        this.fileName = fileName;
    }

    public void run() {
        try {
            InnerClass innerClass = new InnerClass();
            innerClass.loadObjectFromFile(fileName);
            innerClass.rearrangeData(Comparator.comparingDouble(Student::getAverage));
            innerClass.printResult();
        } catch (FileNotFoundException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private class InnerClass implements Input, Printable {

        private HashMap<String, Student> studentInfo;
        private final List<Student> datas;
        private final List<String> names;

        public InnerClass() {
            this.datas = new ArrayList<>();
            this.names = new ArrayList<>();
        }

        @Override
        public void loadObjectFromFile(String fileName) throws IOException, ClassNotFoundException {
            Path path = Paths.get(fileName);
            ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(path));
            studentInfo = (HashMap<String, Student>) ois.readObject();
        }

        @Override
        public void printResult() {
            System.out.println("[평균 오름차순 성적표]");
            IntStream.range(0, datas.size()).forEach(idx -> {
                Student data = datas.get(idx);
                System.out.println(data.sortFormat(idx+1));
            });
        }

        public void rearrangeData(Comparator<Student> comparator) {
            Set<String> keys = studentInfo.keySet();
            keys.stream()
                    .map(key -> studentInfo.get(key))
                    .sorted(comparator)
                    .forEach(student -> {
                        names.add(student.getName());
                        datas.add(student);
                    });
            studentInfo = (HashMap<String, Student>) IntStream.range(0, datas.size())
                    .boxed()
                    .collect(Collectors.toMap(names::get, datas::get));
        }
    }
}