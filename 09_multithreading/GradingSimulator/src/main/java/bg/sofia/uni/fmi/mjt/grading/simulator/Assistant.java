package bg.sofia.uni.fmi.mjt.grading.simulator;

import bg.sofia.uni.fmi.mjt.grading.simulator.assignment.Assignment;
import bg.sofia.uni.fmi.mjt.grading.simulator.grader.AdminGradingAPI;

import java.util.concurrent.atomic.AtomicInteger;

public class Assistant extends Thread {

    private String name;
    private AdminGradingAPI grader;
    private int numberOfGradedAssignments;

    public Assistant(String name, AdminGradingAPI grader) {
        this.name = name;
        this.grader = grader;
    }

    @Override
    public void run() {
        gradeAssignment();
    }

    private void gradeAssignment() {
        Assignment assignment;

        while ((assignment = grader.getAssignment()) != null) {
            try {
                Thread.sleep(assignment.type().getGradingTime());
                numberOfGradedAssignments++;
            } catch (InterruptedException e) {
                System.err.print("Unexpected exception was thrown: " + e.getMessage());
                e.printStackTrace();
            }
        }

        System.out.println("Assistant " + name + " graded " + numberOfGradedAssignments + " assignments");
    }

    public int getNumberOfGradedAssignments() {
        return numberOfGradedAssignments;
    }

}