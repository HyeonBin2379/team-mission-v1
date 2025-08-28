public interface Printable extends Output {

    default void printUsage() {
    }

    void printResult();
}
