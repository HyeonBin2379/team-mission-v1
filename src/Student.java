import java.io.Serializable;
import java.util.List;

public class Student implements Serializable {
    // 요구사항에 맞게 Student 클래스를 구현
    private static final long serialVersionUID = 1L;

    private String name;
    private List<Integer> record;
    private int total;
    private double average;
    private String grade;

    public Student(String name, List<Integer> record) {
        this.name = name;
        this.record = record;
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
}
