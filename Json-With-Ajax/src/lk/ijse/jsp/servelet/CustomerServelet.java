package lk.ijse.jsp.servelet;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/pages/customer"})
public class CustomerServelet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cusId = req.getParameter ("cusID");
        String cusName = req.getParameter ("cusName");
        String cusAddress = req.getParameter ("cusAddress");
        double salary = Double.parseDouble (req.getParameter ("cusSalary"));

        try {
            Class.forName ("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection ("jdbc:mysql://localhost:3306/AjaxJson", "root", "12345678");
            PreparedStatement pstm = null;

            pstm = connection.prepareStatement ("insert into Customer values(?,?,?,?)");

            pstm.setObject (1, cusId);
            pstm.setObject (2, cusName);
            pstm.setObject (3, cusAddress);
            pstm.setObject (4, salary);
            if (pstm.executeUpdate () > 0) {
                System.out.println ("Customer Added Succuss");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException (e);
        }

        }
    }
