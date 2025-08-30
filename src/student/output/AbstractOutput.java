package student.output;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import student.domain.Student;
import student.interfaces.ObjectLoader;
import student.interfaces.Reporter;

public abstract class AbstractOutput implements ObjectLoader, Reporter {

    protected static final int TOP_COUNT = 10;

    protected HashMap<String, Student> studentInfo;

    public AbstractOutput() {
        this.studentInfo = new HashMap<>();
    }

    @Override
    public void loadObjectFromFile(String fileName) throws IOException, ClassNotFoundException {
        Path path = Paths.get("C:/Temp/" + fileName);
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(path))) {
            studentInfo = (HashMap<String, Student>) ois.readObject();
        }
    }

    @Override
    public abstract void printResult();
}
