package servlet.chatroom;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import db.PSQLConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/chat-room")
public class ChatRoom extends HttpServlet {
    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html;charset=utf-8");
        PrintWriter out = res.getWriter();

        HttpSession session = req.getSession();
        String login = String.valueOf(session.getAttribute("login"));

        if(login.equals("null")){
            res.sendRedirect("./pages/auth/login.html");
            return;
        }

        out.print(
            "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<title>ChatNow - Chat Room</title>" +
                "<link rel=\"stylesheet\" href=\"assets/css/styles.css\">" +
                "<link rel=\"stylesheet\" href=\"assets/css/chatroom.css\">" +
                "<link rel=\"preload\" href=\"assets/font/Poppins.ttf\" as=\"font\" type=\"font/woff2\" crossorigin>" +
                "<script src=\"assets/js/main.js\" defer></script>" +
                "</head>"
        );
    
        String msg = "";

        out.print
        (
            "<body>" +
                "<main>" +
                    "<div class=\"msg-content\">" + 
                        msg +
                    "</div>" +
                    "<form action=\"./chat-room\" method=\"post\">" +
                        "<input type=\"text\" name=\"msg\" placeholder=\"Votre Message...\">" +
                        "<button type=\"submit\">Envoyer</button>" +
                    "</form>" +
                "</main>" +
            "</body>"
        );
    }
}
