package next.dao;

import core.exception.RuntimeSQLException;
import core.jdbc.ConnectionManager;
import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SelectJdbcTemplate{

    public List<User> findAll(UserDao userDao) {
        // TODO 구현 필요함.
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = userDao.createQueryForFindAll();
            pstmt = con.prepareStatement(sql);

            rs = pstmt.executeQuery();

            ArrayList<User> userArrayList = new ArrayList<>();
            User user = null;

            while((user = (User)userDao.mapRow(rs)) != null)
                userArrayList.add(user);

            return userArrayList;
        } catch(SQLException e){
            throw new RuntimeSQLException(e);
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            }catch(SQLException e){
                throw new RuntimeSQLException(e);
            }
        }
    }

    public User findByUserId(String userId, UserDao userDao){
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = userDao.createQueryForFindByUserId();
            pstmt = con.prepareStatement(sql);
            userDao.setValuesForFindByUserId(userId, pstmt);

            rs = pstmt.executeQuery();

            User user = (User)userDao.mapRow(rs);
            return user;
        } catch(SQLException e){
            throw new RuntimeSQLException(e);
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            }catch(SQLException e){
                throw new RuntimeSQLException(e);
            }
        }

    }

}
