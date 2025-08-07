package next.dao;

import core.exception.RuntimeSQLException;
import core.jdbc.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertJdbcTemplate extends JdbcTemplate {
    public void insert(String query) {
        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = query;
            pstmt = con.prepareStatement(sql);
            setValues(pstmt);
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
    void setValues(PreparedStatement pstmt) {}
}
