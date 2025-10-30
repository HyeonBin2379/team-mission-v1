package student;

import student.input.StudentInput;
import student.output.SortedStudent;
import student.output.StudentOutput;

public class Main {

    public static void main(String[] args) {
        StudentInput studentInput = new StudentInput("student.dat");
        studentInput.run();
        StudentOutput studentOutput = new StudentOutput("student.dat");
        studentOutput.run();
        SortedStudent sortedStudent = new SortedStudent("student.dat","orderByAvg.dat");
        sortedStudent.run();
    }
}
