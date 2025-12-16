package servlet.auth;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import db.PSQLConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/pages/auth/login")
public class Login extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=utf-8");
        PrintWriter out = res.getWriter();
        HttpSession session = req.getSession(true);

        String username = req.getParameter("username");
        String clearPassword = req.getParameter("password");


        try {
            String query = "select * from members where username = ?";
            PSQLConnection psql = new PSQLConnection();
            Connection connection = psql.getConnection();
            PreparedStatement queryStatement = connection.prepareStatement(query);

            queryStatement.setString(1, username);
            ResultSet result = queryStatement.executeQuery();

            if(!result.next()){
                String insert = "insert into members values (?,MD5(?))";
                PreparedStatement insertStatement = connection.prepareStatement(insert);
                insertStatement.setString(1, username);
                insertStatement.setString(2, clearPassword);
                insertStatement.execute();
            } else {
                String passwordQuery = "select md5(?) as password";
                PreparedStatement passwordStatement = connection.prepareStatement(passwordQuery);
                passwordStatement.setString(1, clearPassword);
                ResultSet passwordResult = passwordStatement.executeQuery();

                boolean isEquals = passwordResult.next() && result.getString("password").equals(passwordResult.getString("password"));

                if(!isEquals){
                    res.sendRedirect("./login.html?auth=false");
                    return;
                }
            }

            session.setMaxInactiveInterval(3600);
            session.setAttribute("login", username);
            res.sendRedirect("../../chat-room");

        } catch (Exception e) {
            out.print(e.getMessage());
        }
    }
}
