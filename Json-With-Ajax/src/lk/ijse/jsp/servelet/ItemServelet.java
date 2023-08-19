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
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String itemID = req.getParameter ("itemID");
        String itemDes = req.getParameter ("itemDes");
        Double itemUp = Double.valueOf (req.getParameter ("unitPrice"));
        int itemQty = Integer.parseInt (req.getParameter ("itemQty"));

        PrintWriter writer = resp.getWriter ();

        try {
            Class.forName ("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection ("jdbc:mysql://localhost:3306/AjaxJson", "root", "12345678");
            PreparedStatement pstm = connection.prepareStatement ("insert into item values(?,?,?,?)");

            pstm.setObject (1, itemID);
            pstm.setObject (2, itemDes);
            pstm.setObject (3, itemUp);
            pstm.setObject (4, itemQty);
            if (pstm.executeUpdate () > 0) {

                resp.addHeader("Content-Type","application/json");
                JsonObjectBuilder cussAdd=Json.createObjectBuilder ();
                cussAdd.add ("state","200");
                cussAdd.add ("massage"," Item Added Succuss");
                cussAdd.add ("data","");
                resp.setStatus (200);
                writer.print(cussAdd.build());

            }
        } catch (SQLException | ClassNotFoundException e) {

            resp.addHeader("Content-Type","application/json");
            JsonObjectBuilder obj=Json.createObjectBuilder ();
            obj.add ("state","");
            obj.add ("massage",e.getMessage ());
            obj.add ("data","");
            resp.setStatus (400);
            writer.print(obj.build());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String cusId = req.getParameter ("id");
        String cusName = req.getParameter ("name");
        String cusAddress = req.getParameter ("address");
        String cusSalary = req.getParameter ("salary");

        PrintWriter writer = resp.getWriter ();

        try {

            Class.forName ("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection ("jdbc:mysql://localhost:3306/AjaxJson", "root", "12345678");
            PreparedStatement pstm3 = connection.prepareStatement ("update Customer set name=?,address=?,salary=? where id=?");
            pstm3.setObject (4, cusId);
            pstm3.setObject (1, cusName);
            pstm3.setObject (2, cusAddress);
            pstm3.setObject (3, cusSalary);
            if (pstm3.executeUpdate () > 0) {
                resp.addHeader("Content-Type","application/json");
                JsonObjectBuilder cussAdd=Json.createObjectBuilder ();
                cussAdd.add ("state","200");
                cussAdd.add ("massage"," Customer Updated Succuss");
                cussAdd.add ("data","");
                resp.setStatus (200);
                writer.print(cussAdd.build());
            } else {
                throw new SQLException ();
            }

        } catch (SQLException | ClassNotFoundException e) {
            resp.addHeader("Content-Type","application/json");
            JsonObjectBuilder obj=Json.createObjectBuilder ();
            obj.add ("state","");
            obj.add ("massage",e.getMessage ());
            obj.add ("data","");
            resp.setStatus (400);
            writer.print(obj.build());
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String cusID=req.getParameter ("id");
        PrintWriter writer = resp.getWriter ();
        try {
            Class.forName ("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection ("jdbc:mysql://localhost:3306/AjaxJson", "root", "12345678");
            PreparedStatement pstm2 = connection.prepareStatement ("delete from Customer where id=?");
            pstm2.setObject (1, cusID);
            if (pstm2.executeUpdate () > 0) {
                resp.addHeader("Content-Type","application/json");
                JsonObjectBuilder cussAdd=Json.createObjectBuilder ();
                cussAdd.add ("state","200");
                cussAdd.add ("massage"," Customer Deleted Succuss");
                cussAdd.add ("data","");
                resp.setStatus (200);
                writer.print(cussAdd.build());
            }
        } catch (SQLException | ClassNotFoundException e) {
            resp.addHeader("Content-Type","application/json");
            JsonObjectBuilder obj=Json.createObjectBuilder ();
            obj.add ("state","");
            obj.add ("massage",e.getMessage ());
            obj.add ("data","");
            resp.setStatus (400);
            writer.print(obj.build());
        }
    }


}
