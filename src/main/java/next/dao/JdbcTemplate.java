package next.dao;

import next.model.User;

import java.sql.PreparedStatement;

public abstract class JdbcTemplate {
    abstract void setValues(User user, PreparedStatement pstmt);
    abstract String createQuery();

}
