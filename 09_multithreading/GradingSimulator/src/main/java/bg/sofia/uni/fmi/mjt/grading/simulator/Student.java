package bg.sofia.uni.fmi.mjt.grading.simulator;

import bg.sofia.uni.fmi.mjt.grading.simulator.assignment.Assignment;
import bg.sofia.uni.fmi.mjt.grading.simulator.assignment.AssignmentType;
import bg.sofia.uni.fmi.mjt.grading.simulator.grader.StudentGradingAPI;

import java.util.Random;

public class Student implements Runnable {

    private static final Random RANDOM = new Random();
    private static final AssignmentType[] ASSIGNMENT_TYPES = AssignmentType.values();
    private static final int STUDENT_MAX_SLEEP_TIME = 1_000;

    private int fn;
    private String name;
    private StudentGradingAPI studentGradingAPI;

    public Student(int fn, String name, StudentGradingAPI studentGradingAPI) {
        this.fn = fn;
        this.name = name;
        this.studentGradingAPI = studentGradingAPI;
    }

    @Override
    public void run() {
        submitAssignment();
    }

    private void submitAssignment() {
        Assignment assignment = new Assignment(fn, name, getRandomAssignmentType());

        try {
            Thread.sleep(RANDOM.nextInt(STUDENT_MAX_SLEEP_TIME));
        } catch(InterruptedException e) {
            System.err.print("Unexpected exception was thrown: " + e.getMessage());
            e.printStackTrace();
        }

        studentGradingAPI.submitAssignment(assignment);
    }

    private AssignmentType getRandomAssignmentType() {
        return ASSIGNMENT_TYPES[RANDOM.nextInt(ASSIGNMENT_TYPES.length)];
    }

    public int getFn() {
        return fn;
    }

    public String getName() {
        return name;
    }

    public StudentGradingAPI getGrader() {
        return studentGradingAPI;
    }

}