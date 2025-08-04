package core.web.mvc;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {

    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Controller controller = RequestMapping.getController(req.getRequestURI());
        String viewName;

        if(controller == null)
            controller = new ForwardController(req.getRequestURI());

        try {
            viewName = controller.exeucte(req, resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if(viewName.startsWith(DEFAULT_REDIRECT_PREFIX))
            resp.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
        else
        {
            RequestDispatcher rd = req.getRequestDispatcher(viewName);
            rd.forward(req, resp);
        }
    }
}
