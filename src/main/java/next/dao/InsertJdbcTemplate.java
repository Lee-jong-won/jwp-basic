package next.dao;

import core.exception.RuntimeSQLException;
import core.jdbc.ConnectionManager;
import next.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertJdbcTemplate extends JdbcTemplate {
    public void insert(User user) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = createQuery();
            pstmt = con.prepareStatement(sql);
            setValues(user, pstmt);
            pstmt.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeSQLException(e);
        }
        finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (con != null) {
                    con.close();
                }
            }catch(SQLException e) {
                throw new RuntimeSQLException(e);
            }
        }
    }


    @Override
    void setValues(User user, PreparedStatement pstmt) {}

    @Override
    String createQuery() {return null;}
}
