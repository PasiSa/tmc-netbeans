package fi.helsinki.cs.tmc.data;

import com.google.gson.annotations.SerializedName;
import java.util.Collections;
import java.util.List;

public class SubmissionResult {

    public static enum Status {
        OK,
        FAIL,
        ERROR
    }
    
    private Status status;
    
    private String error; // e.g. compile error
    
    @SerializedName("test_cases")
    private List<TestCaseResult> testCases;
    
    @SerializedName("solution_url")
    private String solutionUrl;
    
    private List<String> points;
    
    @SerializedName("feedback_questions")
    private List<FeedbackQuestion> feedbackQuestions;
    
    @SerializedName("feedback_answer_url")
    private String feedbackAnswerUrl;
    
    public SubmissionResult() {
        status = Status.ERROR;
        error = null;
        testCases = Collections.emptyList();
        points = Collections.emptyList();
        feedbackQuestions = Collections.emptyList();
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<TestCaseResult> getTestCases() {
        return testCases;
    }

    public void setTestCases(List<TestCaseResult> testCases) {
        this.testCases = testCases;
    }

    public String getSolutionUrl() {
        return solutionUrl;
    }

    public void setSolutionUrl(String solutionUrl) {
        this.solutionUrl = solutionUrl;
    }

    public List<String> getPoints() {
        return points;
    }

    public void setPoints(List<String> points) {
        this.points = points;
    }

    public List<FeedbackQuestion> getFeedbackQuestions() {
        return feedbackQuestions;
    }

    public void setFeedbackQuestions(List<FeedbackQuestion> feedbackQuestions) {
        this.feedbackQuestions = feedbackQuestions;
    }

    public String getFeedbackAnswerUrl() {
        return feedbackAnswerUrl;
    }

    public void setFeedbackAnswerUrl(String feedbackAnswerUrl) {
        this.feedbackAnswerUrl = feedbackAnswerUrl;
    }
    
    public boolean allTestCasesFailed() {
        for (TestCaseResult tcr : testCases) {
            if (tcr.isSuccessful()) {
                return false;
            }
        }
        return true;
    }
}
