package core.web.mvc;

import next.controller.*;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
    private static Map<String, Controller> controllers = new HashMap<>();

    static {
        controllers.put("/users/create", new CreateUserController());
        controllers.put("/", new HomeController());
        controllers.put("/users/", new ListUserController());
        controllers.put("/users/login", new LoginController());
        controllers.put("/users/logout", new LogoutController());
        controllers.put("/users/profile", new ProfileController());
        controllers.put("/users/updateForm", new UpdateFormUserController());
        controllers.put("/users/update", new UpdateUserController());
    }

    public static Controller getController(String requestUrl) {
        return controllers.get(requestUrl);
    }

}
