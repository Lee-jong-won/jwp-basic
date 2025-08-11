package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import core.exception.RuntimeSQLException;
import core.jdbc.ConnectionManager;
import next.model.User;
import org.h2.command.dml.Select;

public class UserDao {

    public void insert(User user) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement pstmt){
                try {
                    pstmt.setString(1, user.getUserId());
                    pstmt.setString(2, user.getPassword());
                    pstmt.setString(3, user.getName());
                    pstmt.setString(4, user.getEmail());
                } catch (SQLException e) {
                    throw new RuntimeSQLException(e);
                }
            }
        };
        jdbcTemplate.update("INSERT INTO USERS VALUES (?, ?, ?, ?)", pss);
    }

    public void update(User user) {

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement pstmt) throws SQLException {
                try {
                    pstmt.setString(1, user.getPassword());
                    pstmt.setString(2, user.getName());
                    pstmt.setString(3, user.getEmail());
                    pstmt.setString(4, user.getUserId());
                } catch (SQLException e) {
                    throw new RuntimeSQLException(e);
                }
            }
        };
       jdbcTemplate.update("UPDATE USERS SET PASSWORD = ?, NAME = ?, EMAIL = ? WHERE USERID = ?", pss);
    }

    public List<User> findAll() {
        // TODO 구현 필요함.
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT userId, password, name, email FROM USERS";
        RowMapper<User> rowMapper = new RowMapper(){
            @Override
            public Object mapRow(ResultSet rs) throws SQLException {
                return new User(
                        rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")
                );
            }
        };

        return jdbcTemplate.query(sql, rowMapper);
    }

    public User findByUserId(String userId) {
        PreparedStatementSetter pss = new PreparedStatementSetter(){
            @Override
            public void setValues(PreparedStatement pstmt) {
                try {
                    pstmt.setString(1, userId);
                } catch (SQLException e) {
                    throw new RuntimeSQLException(e);
                }
            }
        };

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String query = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
        RowMapper<User> rowMapper = new RowMapper(){

            @Override
            public Object mapRow(ResultSet rs) throws SQLException {
                return new User(
                        rs.getString("userId"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email")
                );
            }
        };


        return jdbcTemplate.queryForObject(query, pss, rowMapper);
    }

}
