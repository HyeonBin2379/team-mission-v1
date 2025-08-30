package student;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public interface Output {

    default void outputObject(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path));
        oos.defaultWriteObject();
    }
}
