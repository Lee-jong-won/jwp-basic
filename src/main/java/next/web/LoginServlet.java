package next.web;

import core.db.DataBase;
import next.model.User;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userId = req.getParameter("userId");
        String password = req.getParameter("password");
        User user = DataBase.findUserById(userId);
        RequestDispatcher rd;


        if(user == null || !password.equals(user.getPassword()))
            rd = req.getRequestDispatcher("/user/login_failed.html");
        else{
            rd = req.getRequestDispatcher("/index.html");
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
        }

        rd.forward(req, resp);
    }

}
