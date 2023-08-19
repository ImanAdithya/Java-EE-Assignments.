package lk.ijse.jsp.servelet;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(urlPatterns = {"/pages/item"})
public class ItemServelet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Class.forName ("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection ("jdbc:mysql://localhost:3306/AjaxJson", "root", "12345678");
            PreparedStatement pstm = connection.prepareStatement ("select * from Item");
            ResultSet rst = pstm.executeQuery ();
            PrintWriter writer = resp.getWriter ();
            resp.addHeader ("Content-Type", "application/json");

            JsonArrayBuilder allItem = Json.createArrayBuilder ();

            while (rst.next ()) {
                String id = rst.getString (1);
                String des = rst.getString (2);
                String up = rst.getString (3);
                String qty = (rst.getString (4));

                JsonObjectBuilder item = Json.createObjectBuilder ();

                item.add ("itemId", id);
                item.add ("itemDes", des);
                item.add ("itemUp", up);
                item.add ("itemQty", qty);

                allItem.add (item.build ());
            }
            writer.print (allItem.build ());

        } catch (ClassNotFoundException e) {
            throw new RuntimeException (e);
        } catch (SQLException e) {
            throw new RuntimeException (e);
        }

    }
}
