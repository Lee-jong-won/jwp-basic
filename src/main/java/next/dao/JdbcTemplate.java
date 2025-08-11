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
        try (Connection con = ConnectionManager.getConnection();
        PreparedStatement pstmt = con.prepareStatement(query)) {
            pss.setValues(pstmt);
            pstmt.executeUpdate();
        } catch(SQLException e){
            throw new RuntimeSQLException(e);
        }
    }

    public void update(String query, Object... objects) {
        try (Connection con = ConnectionManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            for(int i = 0; i < objects.length; i++){
                pstmt.setString(i + 1, (String)objects[i]);
            }
            pstmt.executeUpdate();
        } catch(SQLException e){
            throw new RuntimeSQLException(e);
        }
    }

    public <T> List<T> query(String query, RowMapper<T> rowMapper){
        try(Connection con = ConnectionManager.getConnection();
        PreparedStatement pstmt = con.prepareStatement(query)) {

            ResultSet rs = pstmt.executeQuery();

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

    }

    public <T> T queryForObject(String query, PreparedStatementSetter pss, RowMapper<T> rowMapper){
        try(Connection con = ConnectionManager.getConnection();
        PreparedStatement pstmt = con.prepareStatement(query)) {
            pss.setValues(pstmt);
            ResultSet rs = pstmt.executeQuery();

            T object = null;
            if(rs.next())
                object = rowMapper.mapRow(rs);

            return object;
        } catch(SQLException e){
            throw new RuntimeSQLException(e);
        }

    }

    public <T> T queryForObject(String query, RowMapper<T> rowMapper, Object... objects){
        try(Connection con = ConnectionManager.getConnection();
            PreparedStatement pstmt = con.prepareStatement(query)) {
            for(int i = 0; i < objects.length; i++){
                pstmt.setString(i + 1, (String)objects[i]);
            }
            ResultSet rs = pstmt.executeQuery();

            T object = null;
            if(rs.next())
                object = rowMapper.mapRow(rs);

            return object;
        } catch(SQLException e){
            throw new RuntimeSQLException(e);
        }

    }


}
