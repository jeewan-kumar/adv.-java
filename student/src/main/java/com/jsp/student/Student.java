package com.jsp.student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Student extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String firstname = req.getParameter("firstname");
            String lastname = req.getParameter("lastname");
            long contactno = Long.parseLong(req.getParameter("contactno")); // Parse contactno to long
            String email = req.getParameter("email");
            String address = req.getParameter("address");

            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/student", "postgres", "root");

            // Using prepared statement to prevent SQL injection
            String query = "INSERT INTO stu (firstname, lastname, contactno, email, address) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, firstname);
            statement.setString(2, lastname);
            statement.setLong(3, contactno); // Set contactno as a long
            statement.setString(4, email);
            statement.setString(5, address);

            // Execute the query
            statement.executeUpdate();

            // Close resources
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }
}
