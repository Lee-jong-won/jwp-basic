package core.web.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {

    String exeucte(HttpServletRequest req, HttpServletResponse resp) throws Exception;

}
