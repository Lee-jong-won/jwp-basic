package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.exception.RuntimeSQLException;
import core.jdbc.ConnectionManager;
import next.model.User;
import org.h2.command.dml.Select;

public class UserDao {

    public void insert(User user) {
        InsertJdbcTemplate insertJdbcTemplate = new InsertJdbcTemplate(){
            void setValues(PreparedStatement pstmt){
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
        insertJdbcTemplate.insert("INSERT INTO USERS VALUES (?, ?, ?, ?)");
    }

    public void update(User user) {
       UpdateJdbcTemplate updateJdbcTemplate = new UpdateJdbcTemplate(){
           void setValues(PreparedStatement pstmt){
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
       updateJdbcTemplate.update("UPDATE USERS SET PASSWORD = ?, NAME = ?, EMAIL = ? WHERE USERID = ?");
    }

    public List<User> findAll() {
        // TODO 구현 필요함.
        SelectJdbcTemplate selectJdbcTemplate = new SelectJdbcTemplate();
        return selectJdbcTemplate.findAll();
    }

    public User findByUserId(String userId) {
        SelectJdbcTemplate selectJdbcTemplate = new SelectJdbcTemplate();
        return selectJdbcTemplate.findByUserId(userId);
    }



}
