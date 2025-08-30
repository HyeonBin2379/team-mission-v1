package student;

import java.io.Serializable;
import java.util.List;

public class Student implements Serializable {
    // 요구사항에 맞게 student.Student 클래스를 구현
    private static final long serialVersionUID = 1L;

    private final String name;
    private final List<Integer> record;
    private int total;
    private double average;
    private String grade;

    public Student(String name, List<String> record) {
        this.name = name;
        this.record = record.stream().map(Integer::parseInt).toList();
    }
    private void validateRecord(List<String> record) {
        if (record.stream().anyMatch(score -> score.isEmpty() || !score.matches("[0-9]*$"))) {
            throw new IllegalArgumentException("[오류] 정수로 변환할 수 없는 점수입니다.");
        }
    }

    public String getName() {
        return name;
    }

    public List<Integer> getRecord() {
        return record;
    }

    public int getTotal() {
        return total;
    }

    public double getAverage() {
        return average;
    }

    public String getGrade() {
        return grade;
    }

    public void setTotal() {
        this.total = record.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public void setAverage() {
        this.average = (double) this.total / record.size();
    }

    public void setGrade() {
        if (average >= 90) {
            this.grade = "A";
        } else if (average >= 80) {
            this.grade = "B";
        } else if (average >= 70) {
            this.grade = "C";
        } else if (average >= 60) {
            this.grade = "D";
        } else {
            this.grade = "F";
        }
    }

    @Override
    public String toString() {
        return String.format("%s (총점=%d, 평균=%.1f, 학점=%s)", name, total, average, grade);
    }

    public String sortFormat(int number) {
        String resultFormat = """
                    %d) %s
                        점수: %s
                        총점: %d, 평균: %.1f, 학점: %s
                    """;
        return String.format(resultFormat, number, name, record, total, average, grade);
    }
}
