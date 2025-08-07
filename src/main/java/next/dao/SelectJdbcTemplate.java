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

public class SelectJdbcTemplate extends JdbcTemplate{

    public List<User> findAll() {
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

    public User findByUserId(String userId){
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

    @Override
    void setValues(PreparedStatement pstmt) {

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

    String createQueryForFindByUserId(){
        return "SELECT userId, password, name, email FROM USERS WHERE userid=?";
    }

    String createQueryForFindAll(){
        return "SELECT userId, password, name, email FROM USERS";
    }

    void setValuesForFindByUserId(String userId, PreparedStatement pstmt){
        try {
            pstmt.setString(1, userId);
        } catch (SQLException e) {
            throw new RuntimeSQLException(e);
        }
    }

}
