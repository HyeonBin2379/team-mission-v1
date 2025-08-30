package student.interfaces;

import java.io.IOException;

public interface ObjectLoader {

    void loadObjectFromFile(String fileName) throws IOException, ClassNotFoundException;
}
