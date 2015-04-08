package sample;
import javafx.stage.Stage;

import javax.swing.*;
import java.sql.*;

public class SQLconnector {
    Connection conn = null;
    ResultSet rs = null;
    String dbPass = null;
    String firstName = null;
    String userType = null;
    String dbURL = "jdbc:mysql://sql3.freemysqlhosting.net:3306/sql372954";
    String dbUser = "sql372954";
    String dbPassWord = "kZ3*xJ2!";
    String jdbcDriver = "com.mysql.jdbc.Driver";

    public boolean checkCred(String userName1, String passWord1) throws Exception {
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(dbURL, dbUser, dbPassWord);
            Statement statement = conn.createStatement();
            String sql;
            sql = "SELECT * FROM employees WHERE username='" + userName1 + "'";
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                dbPass = rs.getString("password");
                firstName = rs.getString("voornaam");
                userType = rs.getString("authority");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (dbPass.equals(passWord1)) {
            if (userType.equals("Admin")) {
                Admin a = new Admin();
                a.start(new Stage());
                System.out.println("login succesfull");
            } else {
                UserInterface u = new UserInterface();
                u.start(new Stage());
                System.out.println("Rito plz buff analyst");
            }
            return true;
        } else {
            return false;
        }
    }

    public void addAccount(String username2, String password2, String firstName2, String lastName2, String authority) {
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(dbURL, dbUser, dbPassWord);
            Statement statement = conn.createStatement();
            statement.executeUpdate("INSERT INTO employees(username,password, voornaam, achternaam, authority) VALUES ('" + username2 + "','" + password2 + "','" + firstName2 + "','" + lastName2 + "','" + authority + "')");
            JOptionPane.showMessageDialog(null, "Account added succesfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getPassword(String username3, String firstName3, String lastName3) {
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(dbURL, dbUser, dbPassWord);
            Statement statement = conn.createStatement();
            String sql = "SELECT password FROM employees WHERE voornaam='" + firstName3 + "'AND achternaam='" + lastName3 + "'AND username='" + username3 + "'";
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                dbPass = rs.getString("password");
                JOptionPane.showMessageDialog(null, "Uw wachtwoord is: " + dbPass);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public void selUserType(String username1){
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(dbURL, dbUser, dbPassWord);
            Statement statement = conn.createStatement();

            String sql = "SELECT authority FROM employees WHERE

            rs = statement.executeQuery(sql);

        } catch (Exception e){
            e.printStackTrace();
        }
    } */
    int test;
    public void addTweets(String username, String currentdate, String message, String geo, String sentiment, int retweet, int fav, int followers) {
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(dbURL, dbUser, dbPassWord);
            Statement statement = conn.createStatement();

            String sql =    "INSERT INTO messages(username, dateAdded, message, geo, sentiment)" +
                            "VALUES('" + username + "','" + currentdate + "','" + message + "','" + geo + "','" + sentiment +"')";
            statement.executeUpdate(sql);
            String sqlTw =  "SELECT ID FROM messages WHERE username='"+ username + "'";
            rs = statement.executeQuery(sqlTw);
            while(rs.next()){
                test = rs.getInt("ID");
            }
            String sqltest =  "INSERT INTO twitter(ID, retweets, favourites, followers) VALUES('" + test + "','" + retweet + "','" + fav + "','"+ followers +"')";

            statement.executeUpdate(sqltest);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void getWeather(String date, float temp, String description){
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(dbURL, dbUser, dbPassWord);
            Statement statement = conn.createStatement();

            String sql =    "INSERT INTO weather(dateAdded, temperature, weatherDescription)" +
                            "VALUES('" + date +"','" + temp +"','" + description +"')";
            statement.executeUpdate(sql);
        } catch(Exception e){
            e.printStackTrace();
            System.out.println("Weatherdata was already added scrub");
        }
    }


    public void getPopMess(){
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(dbURL, dbUser, dbPassWord);
            Statement statement = conn.createStatement();
            String sql = "SELECT username, dateAdded, message, sentiment, followers, favourites FROM messages, twitter WHERE messages.ID=twitter.ID ORDER BY followers DESC";
            rs = statement.executeQuery(sql);
            while (rs.next()) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**public void testMethod(){
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(dbURL, dbUser, dbPassWord);
            Statement statement = conn.createStatement();

            String sql = "SELECT sentiment, COUNT(ID) AS numberOfTweets FROM tweets GROUP BY sentiment";

            rs = statement.executeQuery(sql);
            admin a = new admin();
            while(rs.next()){
                String tweetsT = rs.getString("sentiment") + ": " + rs.getString("numberOfTweets");
                if(tweetsT.contains("negative")){
                   // a.getNeg(tweetsT);
                }
                if(tweetsT.contains("positive")){
                    //a.getPos(tweetsT);
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }*/

    public void addStreamTw(String username, String message, String follower, int amount){
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(dbURL, dbUser, dbPassWord);
            Statement statement = conn.createStatement();

            String sql = "INSERT INTO testtable(username, message, follower, followercount)VALUES ('" + username + "','"+ message +"','"+ follower + "','" + amount + "')";

            statement.executeUpdate(sql);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void sortPop(){
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(dbURL, dbUser, dbPassWord);
            Statement statement = conn.createStatement();
            String sql = "SELECT messages.username, messages.message, messages.sentiment, twitter.retweets FROM messages LEFT JOIN twitter ON messages.ID=twitter.ID ORDER BY twitter.retweets DESC";
            String username;
            String message;
            String sentiment;
            int retweets;
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                username = rs.getString("username");
                message = rs.getString("message");
                sentiment = rs.getString("sentiment");
                retweets = rs.getInt("retweets");
                System.out.println(username + "\n\r" + message + "\n\r" + sentiment + "\n\r" + retweets);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
