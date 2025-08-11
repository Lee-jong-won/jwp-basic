package next.dao;

import core.exception.RuntimeSQLException;
import core.jdbc.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {

    public void update(String query, PreparedStatementSetter pss) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            String sql = query;
            pstmt = con.prepareStatement(sql);
            pss.setValues(pstmt);
            pstmt.executeUpdate();
        } catch(SQLException e){
            throw new RuntimeSQLException(e);
        } finally {
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
            } catch(SQLException e){
                throw new RuntimeSQLException(e);
            }
        }
    }

    public List<Object> query(String query, RowMapper rowMapper){

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(query);

            rs = pstmt.executeQuery();

            ArrayList<Object> objects = new ArrayList<>();
            Object object = null;

            while(rs.next()) {
                object = rowMapper.mapRow(rs);
                objects.add(object);
            }

            return objects;
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

    public Object queryForObject(String query, PreparedStatementSetter pss, RowMapper rowMapper){
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(query);
            pss.setValues(pstmt);

            rs = pstmt.executeQuery();

            Object object = null;
            if(rs.next())
                object = rowMapper.mapRow(rs);

            return object;
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
