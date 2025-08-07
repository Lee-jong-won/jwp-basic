package next.dao;

import core.exception.RuntimeSQLException;
import core.jdbc.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SelectJdbcTemplate<T> extends JdbcTemplate{

    private final RowMapper<T> rowMapper;

    public SelectJdbcTemplate(RowMapper<T> rowMapper) {
        this.rowMapper = rowMapper;
    }

    public List<T> query(String query){

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(query);

            rs = pstmt.executeQuery();

            ArrayList<T> objects = new ArrayList<>();
            T object = null;

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

    public T queryForObject(String query){
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = ConnectionManager.getConnection();
            pstmt = con.prepareStatement(query);
            setValues(pstmt);

            rs = pstmt.executeQuery();

            T object = null;
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



    @Override
    void setValues(PreparedStatement pstmt) {

    }



}
