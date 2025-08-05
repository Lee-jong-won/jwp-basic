package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.db.DataBase;
import core.mvc.Controller;
import next.dao.UserDao;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class LoginController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String userId = req.getParameter("userId");
        String password = req.getParameter("password");
        UserDao userDao = new UserDao();
        String viewName = null;

        try{
            User user = userDao.findByUserId(userId);
            if (user == null) {
                req.setAttribute("loginFailed", true);
                viewName = "/user/login.jsp";
            }
            if (user.matchPassword(password)) {
                HttpSession session = req.getSession();
                session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
                viewName = "redirect:/";
            } else {
                req.setAttribute("loginFailed", true);
                viewName = "/user/login.jsp";
            }

        }catch(SQLException e){
            log.error(e.getMessage());
        }

        return viewName;
    }
}
