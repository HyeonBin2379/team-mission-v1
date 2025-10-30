package student.interfaces;

import java.io.FileNotFoundException;
import java.io.IOException;
import student.domain.Student;

public interface Checker {

    void loadCheck(String fileName) throws FileNotFoundException;

    void checkKeyAndInputData(String key, Student value) throws IOException;
}
