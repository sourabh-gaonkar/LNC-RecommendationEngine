package com.lnc.DB;

import com.lnc.Connection.JDBCConnection;
import com.lnc.model.Review;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReviewQueries {
  private final Connection connection;

  public ReviewQueries() throws SQLException {
    JDBCConnection dbInstance = JDBCConnection.getInstance();
    connection = dbInstance.getConnection();
  }

  public List<Review> getReviews() throws Exception {
    List<Review> reviews = new ArrayList<>();
    try (Statement statement = connection.createStatement()) {
      ResultSet resultSet = statement.executeQuery("SELECT review, label FROM reviews");
      while (resultSet.next()) {
        reviews.add(new Review(resultSet.getString("review"), resultSet.getString("label")));
      }
    } catch (SQLException e) {
      throw new Exception("Error fetching reviews", e);
    }
    return reviews;
  }
}
