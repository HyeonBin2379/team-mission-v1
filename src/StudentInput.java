public interface StudentInput {

    void loadCheck(String fileName) throws Exception;
    void printUsage() throws Exception;
    void checkKeyAndInputData(String key, Student value) throws Exception;
    void saveData() throws Exception;
}
