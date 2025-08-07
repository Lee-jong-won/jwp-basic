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

    public List<User> findAll() throws SQLException {
        // TODO 구현 필요함.
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = createQueryForFindAll();
            pstmt = con.prepareStatement(sql);

            rs = pstmt.executeQuery();

            ArrayList<User> userArrayList = new ArrayList<>();
            User user = null;

            while((user = (User)mapRow(rs)) != null)
                userArrayList.add(user);

            return userArrayList;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public User findByUserId(String userId) throws SQLException {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = createQueryForFindByUserId();
            pstmt = con.prepareStatement(sql);
            setValuesForFindByUserId(userId, pstmt);

            rs = pstmt.executeQuery();

            User user = (User)mapRow(rs);
            return user;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (con != null) {
                con.close();
            }
        }

    }

    String createQueryForFindByUserId(){
        return "SELECT userId, password, name, email FROM USERS WHERE userid=?";
    }

    void setValuesForFindByUserId(String userId, PreparedStatement pstmt){
        try {
            pstmt.setString(1, userId);
        } catch (SQLException e) {
            throw new RuntimeSQLException(e);
        }
    }

    String createQueryForFindAll(){
        return "SELECT userId, password, name, email FROM USERS";
    }

    Object mapRow(ResultSet rs){
        User user = null;
        try {
            if (rs.next()) {
                user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
                        rs.getString("email"));
            }
        } catch (SQLException e) {
            throw new RuntimeSQLException(e);
        }

        return user;
    }



}
