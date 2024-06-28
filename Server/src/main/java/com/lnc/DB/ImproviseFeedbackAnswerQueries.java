package com.lnc.DB;

import com.lnc.connection.JDBCConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.HashMap;

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

    public List<Map<String, List<String>>> getAnswersWithQuestion(int itemId) {
        List<Map<String, List<String>>> questionAnswers = new ArrayList<>();

        String query = "SELECT q.question_text, a.answer_text " +
                "FROM improvise_feedback_answer a " +
                "JOIN improvise_feedback_question q ON a.question_id = q.question_id " +
                "JOIN improvise_feedback_session s ON q.session_id = s.session_id " +
                "WHERE s.item_id = ? " +
                "ORDER BY a.answer_id DESC " +
                "LIMIT 10";

        try(PreparedStatement getQuestionAnswersStmt = connection.prepareStatement(query)){
            getQuestionAnswersStmt.setInt(1, itemId);

            try(ResultSet rs = getQuestionAnswersStmt.executeQuery()){
                Map<String, List<String>> questionAnswersMap = new HashMap<>();

                while(rs.next()){
                    String question = rs.getString("question_text");
                    String answer = rs.getString("answer_text");

                    if (!questionAnswersMap.containsKey(question)) {
                        questionAnswersMap.put(question, new ArrayList<>());
                    }

                    questionAnswersMap.get(question).add(answer);
                }

                for (Map.Entry<String, List<String>> entry : questionAnswersMap.entrySet()) {
                    Map<String, List<String>> questionAnswer = new HashMap<>();
                    questionAnswer.put(entry.getKey(), entry.getValue());
                    questionAnswers.add(questionAnswer);
                }
            }
        } catch (SQLException e) {
            logger.severe("Failed to get answers from the database.\n" + e.getMessage());
        }

        return questionAnswers;
    }
}
