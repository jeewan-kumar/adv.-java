package com.example.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AddProductServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String productName = request.getParameter("productName");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        String jdbcURL = "jdbc:mysql://localhost:3306/mydatabase";
        String jdbcUsername = "root";
        String jdbcPassword = "password";

        String sql = "INSERT INTO products (id, productName, description, price, quantity) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, productName);
            preparedStatement.setString(3, description);
            preparedStatement.setDouble(4, price);
            preparedStatement.setInt(5, quantity);

            int rowsInserted = preparedStatement.executeUpdate();
            PrintWriter writer = response.getWriter();

            if (rowsInserted > 0) {
                writer.write("<html><body>");
                writer.write("<h1>Product added successfully</h1>");
                writer.write("</body></html>");
            } else {
                writer.write("<html><body>");
                writer.write("<h1>Product addition failed</h1>");
                writer.write("</body></html>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}
