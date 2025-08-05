package next.dao;

import next.model.User;

public class InsertJdbcTemplate {

    private User user;
    private UserDao userDao;

    public InsertJdbcTemplate(User user, UserDao userDao){
        this.user = user;
        this.userDao = userDao;
    }

    public void insert(User user, UserDao userDao){

    }
}
