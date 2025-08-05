package next.dao;

import next.model.User;

public class UpdateJdbcTemplate {

    private User user;
    private UserDao userDao;

    public UpdateJdbcTemplate(User user, UserDao userDao){
        this.user = user;
        this.userDao = userDao;
    }

    public void update(User user, UserDao userDao){


    }

}
