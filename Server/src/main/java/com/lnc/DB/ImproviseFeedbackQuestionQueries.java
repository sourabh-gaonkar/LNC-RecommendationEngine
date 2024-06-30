package com.lnc.DB;

import com.lnc.connection.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ImproviseFeedbackQuestionQueries {
    private final Logger logger = Logger.getLogger(DiscardMenuQueries.class.getName());
    private Connection connection;

    public ImproviseFeedbackQuestionQueries() {
        try{
            JDBCConnection dbInstance = JDBCConnection.getInstance();
            this.connection = dbInstance.getConnection();
        } catch (SQLException e) {
            logger.severe("Failed to connect to the database.\n" + e.getMessage());
        }
    }

    public boolean addQuestions(int feedbackSessionID, List<String> questions) {
        boolean isQuestionsAdded = false;
        String query = "INSERT INTO improvise_feedback_question (session_id, question_text) VALUES (?, ?)";
        try (PreparedStatement addQuestionsStmt = connection.prepareStatement(query)) {
            for (String question : questions) {
                addQuestionsStmt.setInt(1, feedbackSessionID);
                addQuestionsStmt.setString(2, question);
                isQuestionsAdded = addQuestionsStmt.executeUpdate() > 0;
            }
        } catch (SQLException ex) {
            logger.severe("Failed to add questions to feedback session.\n" + ex.getMessage());
        }
        return isQuestionsAdded;
    }

    public List<Map<Integer, String>> getQuestions(int feedbackSessionID) {
        List<Map<Integer, String>> questions = new ArrayList<>();

        String query = "SELECT question_id, question_text FROM improvise_feedback_question WHERE session_id = ?";

        try(PreparedStatement getQuestionsStmt = connection.prepareStatement(query)) {
            getQuestionsStmt.setInt(1, feedbackSessionID);

            ResultSet rs = getQuestionsStmt.executeQuery();
            while (rs.next()) {
                int questionId = rs.getInt("question_id");
                String questionText = rs.getString("question_text");

                Map<Integer, String> question = new HashMap<>();
                question.put(questionId, questionText);
                questions.add(question);
            }
        } catch (SQLException ex) {
            logger.severe("Failed to get questions for feedback session.\n" + ex.getMessage());
        }

        return questions;
    }
}
