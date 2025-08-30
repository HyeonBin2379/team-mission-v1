package student.interfaces;

import java.io.FileNotFoundException;

public interface Checker {

    void loadCheck(String fileName) throws FileNotFoundException;
}
