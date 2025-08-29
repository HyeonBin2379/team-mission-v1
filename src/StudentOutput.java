import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class StudentOutput implements Input, Printable {

    private HashMap<String, Student> studentInfo;
    private List<Student> datas;
    private List<String> names;

    public void rearrangeData(Comparator<Student> comparator) {
    }

    @Override
    public void loadObjectFromFile(String fileName) throws IOException, ClassNotFoundException {

    }

    @Override
    public void printResult() {

    }

    @Override
    public void outputObject(String fileName) throws IOException {

    }
}
