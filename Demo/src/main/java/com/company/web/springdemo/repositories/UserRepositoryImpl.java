package com.company.web.springdemo.repositories;

import com.company.web.springdemo.exceptions.EntityNotFoundException;
import com.company.web.springdemo.models.Style;
import com.company.web.springdemo.models.User;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Repository
@PropertySource("classpath:application.properties")
public class UserRepositoryImpl implements UserRepository{
    private final String dbUrl, dbUsername, dbPassword;

    public UserRepositoryImpl(Environment env) {
        this.dbUrl = env.getProperty("database.url");
        this.dbUsername = env.getProperty( "database.username");
        this.dbPassword = env.getProperty("database.password") ;
    }

    @Override
    public User create(User user) {
     return null;
    }

    @Override
    public List<User> getAll() {
        String query = "SELECT * FROM users";
        try(
                Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
        ){
            return getUsers(resultSet);

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getById(int id) {
        String query = "SELECT * " +
                "FROM users " +
                "WHERE user_id = ? ";

        try(
                Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
                PreparedStatement statement = connection.prepareStatement(query);
        ){
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery()){

                List<User> result = getUsers(resultSet);
                if(result.size() == 0){
                    throw new EntityNotFoundException("User", id);
                }
                return result.get(0);

            }catch(SQLException e){
                throw new RuntimeException(e);
            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getByUsername(String username) {
        String query = "SELECT * " +
                "FROM users " +
                "WHERE username = ? ";

        try(
                Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
                PreparedStatement statement = connection.prepareStatement(query);
        ){
            statement.setString(1, username);
            try(ResultSet resultSet = statement.executeQuery()){

                List<User> result = getUsers(resultSet);
                if(result.size() == 0){
                    throw new EntityNotFoundException("User", "username", username);
                }
                return result.get(0);

            }catch(SQLException e){
                throw new RuntimeException(e);
            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    private List<User> getUsers(ResultSet resultSet) throws SQLException{
        List<User> users = new ArrayList<>();
        while(resultSet.next()){
            User user = new User();
            user.setId(resultSet.getInt("user_id"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setFirstName(resultSet.getString("first_name"));
            user.setLastName(resultSet.getString("last_name"));
            user.setEmail(resultSet.getString("email"));
            user.setAdmin(resultSet.getBoolean("is_admin"));
            users.add(user);
        }
        return users;
    }
}
