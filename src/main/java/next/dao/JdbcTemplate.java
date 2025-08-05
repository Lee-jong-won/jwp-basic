package next.dao;

import next.model.User;

import java.sql.PreparedStatement;

public abstract class JdbcTemplate {
    abstract void setValues(PreparedStatement pstmt);

}
