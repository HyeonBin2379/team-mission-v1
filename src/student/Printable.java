package student;

public interface Printable extends Output {

    default void printUsage() {
        System.out.println("""
                - 종료하려면 이름에 ^^ 를 입력하세요.
                - 점수는 0~100 사이의 정수만 허용됩니다.
                """);
    }

    void printResult();
}
