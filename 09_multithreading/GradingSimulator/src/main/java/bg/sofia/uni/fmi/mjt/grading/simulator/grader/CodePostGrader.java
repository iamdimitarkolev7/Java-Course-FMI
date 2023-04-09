package bg.sofia.uni.fmi.mjt.grading.simulator.grader;

import bg.sofia.uni.fmi.mjt.grading.simulator.Assistant;
import bg.sofia.uni.fmi.mjt.grading.simulator.assignment.Assignment;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CodePostGrader implements AdminGradingAPI {

    private List<Assistant> assistants = new ArrayList<>();
    private Queue<Assignment> assignmentsForGrading = new LinkedList<>();
    private AtomicInteger totalSubmittedAssignments = new AtomicInteger();

    private boolean deadlineHasPassed = false;

    public CodePostGrader(int numberOfAssistants) {
        for (int i = 0; i < numberOfAssistants; i++) {
            Assistant assistant = new Assistant("Assistant" + i, this);
            assistants.add(assistant);
            assistant.start();
        }
    }

    @Override
    public synchronized Assignment getAssignment() {
        while (assignmentsForGrading.isEmpty() && !deadlineHasPassed) {
            try {
                this.wait();
            } catch(InterruptedException e) {
                System.err.print("Unexpected exception was thrown: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return assignmentsForGrading.poll();
    }

    @Override
    public int getSubmittedAssignmentsCount() {
        return totalSubmittedAssignments.get();
    }

    @Override
    public synchronized void finalizeGrading() {
        deadlineHasPassed = true;
        this.notifyAll();
    }

    @Override
    public List<Assistant> getAssistants() {
        return Collections.unmodifiableList(assistants);
    }

    @Override
    public void submitAssignment(Assignment assignment) {
        if (!deadlineHasPassed) {
            totalSubmittedAssignments.incrementAndGet();

            synchronized (this) {
                assignmentsForGrading.add(assignment);
                this.notifyAll();
            }
        }
    }
}
