package com.arturo.task4.repositories;
import com.arturo.task4.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    private  class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User usr = new User(
                    hasColumn( rs, "name") ? rs.getString("name") : "",
                    hasColumn( rs, "mail") ? rs.getString("mail") : "",
                    hasColumn( rs, "position") ? rs.getString("position") : "",
                    hasColumn( rs, "lastLogin") ? rs.getDate("lastLogin")  : new Date(10000000),
                    hasColumn( rs, "registrationDate") ? rs.getDate("registrationDate")  : new Date(10000000),
                    hasColumn(rs, "blocked") && rs.getBoolean("blocked"),
                    hasColumn( rs, "password") ? rs.getString("password") : "",
                    hasColumn(rs, "admin") && rs.getBoolean("admin")
            );

            return usr;
        }

    }
    private boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            if (columnName.equals(metaData.getColumnName(i))) {
                return true;
            }
        }
        return false;
    }

    public User checkAdmin(String mail, String pass) {
        String sql = "SELECT name,mail,password,admin from users WHERE mail=? AND password=? AND blocked=0";
        try {
            return Objects.requireNonNull(jdbcTemplate.queryForObject(sql, new Object[]{mail, pass }, new UserRowMapper()));
        }catch (EmptyResultDataAccessException e){
            return new User(null,null,null,null,null,false,null,false);
        }


    }

    public List<User> getAllUsers(){
        String sql = "SELECT name,mail,position,lastLogin,blocked from users";
        return  jdbcTemplate.query(sql,new UserRowMapper());

    }
    public int register(String name, String pass, String position,String mail){
        String sql = "INSERT INTO users (name,password,mail,position) values (?,?,?,?)";
        return  jdbcTemplate.update(sql,new Object[]{name,pass,mail,position});
    }

    public int deleteUsers(List<User> users) {
        if (users == null || users.isEmpty()) {
            return 0;
        }
        List<String> emails = users.stream()
                .map(User::getMail)
                .collect(Collectors.toList());
        String placeholders = emails.stream()
                .map(email -> "?")
                .collect(Collectors.joining(","));
        String sql = "DELETE FROM users WHERE mail IN (" + placeholders + ")";
        return jdbcTemplate.update(sql, emails.toArray());
    }

    public int blockUsers(List<User> users ) {
        if (users == null || users.isEmpty()) {
            return 0;
        }
        List<String> emails = users.stream()
                .map(User::getMail)
                .collect(Collectors.toList());
        String placeholders = emails.stream()
                .map(email -> "?")
                .collect(Collectors.joining(","));
        String sql = "UPDATE users SET blocked = 1 WHERE mail IN (" + placeholders + ")";
        return jdbcTemplate.update(sql,emails.toArray());
    }
    public int unblockUsers(List<User> users) {
        if (users == null || users.isEmpty()) {
            return 0;
        }
        List<String> emails = users.stream()
                .map(User::getMail)
                .collect(Collectors.toList());
        String placeholders = emails.stream()
                .map(email -> "?")
                .collect(Collectors.joining(","));
        String sql = "UPDATE users SET blocked = 0 WHERE mail IN (" + placeholders + ")";
        return jdbcTemplate.update(sql,emails.toArray());
    }


}