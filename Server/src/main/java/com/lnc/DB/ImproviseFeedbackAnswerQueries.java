package com.lnc.DB;

import com.lnc.connection.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Logger;

public class ImproviseFeedbackAnswerQueries {
    private final Logger logger = Logger.getLogger(DiscardMenuQueries.class.getName());
    private Connection connection;

    public ImproviseFeedbackAnswerQueries() {
        try{
            JDBCConnection dbInstance = JDBCConnection.getInstance();
            this.connection = dbInstance.getConnection();
        } catch (SQLException e) {
            logger.severe("Failed to connect to the database.\n" + e.getMessage());
        }
    }

    public boolean addAnswer(String employeeId, Map<String, String> answer){
        String query = "INSERT INTO improvise_feedback_answer (question_id, employee_id, answer_text) VALUES (?, ?, ?)";

        try(PreparedStatement addAnswerStmt = connection.prepareStatement(query)){
            for (Map.Entry<String, String> entry : answer.entrySet()) {
                int questionId = Integer.parseInt(entry.getKey());
                if(ifAnsweredByEmployee(employeeId, questionId)){
                    return false;
                }
                addAnswerStmt.setInt(1, questionId);
                addAnswerStmt.setString(2, employeeId);
                addAnswerStmt.setString(3, entry.getValue());
                addAnswerStmt.addBatch();
            }
            int[] result = addAnswerStmt.executeBatch();
            return result.length == answer.size();
        } catch (SQLException e) {
            logger.severe("Failed to add answer to the database.\n" + e.getMessage());
            return false;
        }
    }

    private boolean ifAnsweredByEmployee(String employeeId, int questionId){
        String query = "SELECT * FROM improvise_feedback_answer WHERE employee_id = ? AND question_id = ?";
        try(PreparedStatement checkAnswerStmt = connection.prepareStatement(query)){
            checkAnswerStmt.setString(1, employeeId);
            checkAnswerStmt.setInt(2, questionId);
            return checkAnswerStmt.executeQuery().next();
        } catch (SQLException e) {
            logger.severe("Failed to check if answer exists in the database.\n" + e.getMessage());
            return false;
        }
    }
}
