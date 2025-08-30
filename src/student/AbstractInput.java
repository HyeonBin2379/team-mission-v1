package student;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class AbstractInput implements Input {

    @Override
    public void loadCheck(String fileName) throws FileNotFoundException {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            throw new FileNotFoundException(fileName + " 파일이 존재하지 않아 새로운 파일을 생성합니다.");
        }
    }
}
