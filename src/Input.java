import java.io.FileNotFoundException;
import java.io.IOException;

public interface Input {

    default void loadCheck(String fileName) throws FileNotFoundException {
    }

    void loadObjectFromFile(String fileName) throws IOException, ClassNotFoundException;
}
