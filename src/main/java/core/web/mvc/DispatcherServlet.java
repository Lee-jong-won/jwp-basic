package core.web.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger log = LoggerFactory.getLogger(DispatcherServlet.class);

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Controller controller = RequestMapping.getController(req.getRequestURI());
        String viewName;

        if(controller == null)
            controller = new ForwardController(req.getRequestURI());

        try {
            viewName = controller.exeucte(req, resp);
            move(viewName, req, resp);
        } catch (Exception e) {
            log.error("Exception : {}", e);
            throw new RuntimeException(e);
        }

    }

    private void move(String viewName, HttpServletRequest req,
                      HttpServletResponse resp) throws IOException, ServletException {

        if(viewName.startsWith(DEFAULT_REDIRECT_PREFIX))
            resp.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
        else
        {
            RequestDispatcher rd = req.getRequestDispatcher(viewName);
            rd.forward(req, resp);
        }
    }
}
