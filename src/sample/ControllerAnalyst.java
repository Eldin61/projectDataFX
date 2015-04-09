package sample;

import com.mysql.jdbc.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;
import twitter4j.*;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class ControllerAnalyst {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Label welcome;
    @FXML
    private Button cnt;
    @FXML
    private Tab newtab;
    @FXML
    private ImageView logOut;
    @FXML
    private SplitPane splitPane;
    @FXML
    private Button btnAdd;
    @FXML
    private TextArea streamArea;
    @FXML
    private  TextField fKey;
    @FXML
    private TextField fResult;
    @FXML
    private PieChart pieChart;
    @FXML
    private Button btnRefresh;
    @FXML
    private TextArea posArea;
    @FXML
    private TextArea negArea;
    @FXML
    private Button btnWeather;

    @FXML
    void initialize(){
        logOut();
        streamMessage();
        addData();
        getSentiment();
        pieChart.setData(getChart());
        refreshChart();
        //getPopMess();
        showWeather();
        weatherT();
        //posWeather();
    }
    int sentimentP;
    int sentimentN;
    ResultSet rs;
    String incMess;
    String username;
    int follower;
    int fav;
    int retweets;
    String messagesTot;
    String sentiment;
    String posMess;
    String negMess;
    String showSentData;

    String dbURL = "jdbc:mysql://sql3.freemysqlhosting.net:3306/sql372954";
    String dbUser = "sql372954";
    String dbPassWord = "kZ3*xJ2!";
    String jdbcDriver = "com.mysql.jdbc.Driver";
    Connection conn = null;

    private void logOut(){
        logOut.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int reply = JOptionPane.showConfirmDialog(null, "Do you want to log out?");
                if (reply == JOptionPane.YES_OPTION)
                {
                    UserInterface u = new UserInterface();
                    u.logOut();
                    Main m = new Main();
                    try {
                        m.start(new Stage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    private void streamMessage(){
        streamArea.setEditable(false);
        TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
        StatusListener listener = new StatusListener() {

            @Override
            public void onException(Exception arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onScrubGeo(long arg0, long arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStallWarning(StallWarning stallWarning) {

            }

            String username;
            String content;
            int retweets;
            int followers;
            String total;

            @Override
            public void onStatus(Status status) {
                User user = status.getUser();

                username = status.getUser().getScreenName();
                content = status.getText();
                followers = user.getFollowersCount();
                retweets = status.getRetweetCount();

                total = "Username: " + username + "\n\rFollowers/Friends: " + followers + "\n\rRetweets/Likes: " + retweets + "\n\rMessage: \n\r" + content + "\n\r" + "\n\r";

                streamArea.appendText(total);
            }

            @Override
            public void onTrackLimitationNotice(int arg0) {
                // TODO Auto-generated method stub
            }

        };
        FilterQuery fq = new FilterQuery();

        String keywords[] = {"blijdorp"};

        fq.track(keywords);

        twitterStream.addListener(listener);
        twitterStream.filter(fq);
    }
    private void addData() {

        final SQLconnector s = new SQLconnector();

        btnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String queryText;
                int queryAmount;
                String sentiment;
                String usernames;
                String currentdate;
                String tweetMess;
                String geo;
                int retweets;
                int fav;
                int followers;

                TwitterFactory tf = new TwitterFactory();
                Twitter twitter = tf.getInstance();
                try {
                    queryText = fKey.getText();
                    queryAmount = Integer.parseInt(fResult.getText());

                    Query query = new Query(queryText);
                    query.count(queryAmount);
                    QueryResult result = twitter.search(query);
                    for (Status status : result.getTweets()) {// print uit

                        //datum toevoegen in zelfde format als in table weather
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date today = new Date();
                        currentdate = dateFormat.format(today);

                        User user = status.getUser();
                        usernames = status.getUser().getScreenName();
                        //date = "" + status.getCreatedAt();
                        tweetMess = status.getText();
                        geo = "" + status.getGeoLocation();
                        retweets = status.getRetweetCount();
                        fav = status.getFavoriteCount();
                        followers = user.getFollowersCount();
                        usernames = usernames.replaceAll("[^a-zA-Z0-9@:/# ]", "");
                        tweetMess = tweetMess.replaceAll("[^a-zA-Z0-9@:/# ]", "");


                        if (status.getText().toLowerCase().contains("dood") ||
                                status.getText().toLowerCase().contains("roept") ||
                                status.getText().toLowerCase().contains("slecht") ||
                                status.getText().toLowerCase().contains("stom") ||
                                status.getText().toLowerCase().contains("nooit") ||
                                status.getText().toLowerCase().contains("kut")) {
                            sentiment = "negative";
                        } else {
                            sentiment = "positive";
                        }
                        s.addTweets(usernames, currentdate, tweetMess, geo, sentiment, retweets, fav, followers);
                    }
                } catch (TwitterException te) {
                    te.printStackTrace();
                    System.exit(0);
                }
                JOptionPane.showMessageDialog(null, "Data added succesfully!");
            }
        });
    }

    private void weatherT(){
        final SQLconnector s = new SQLconnector();

        btnWeather.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                OpenWeatherMap owm = new OpenWeatherMap("");

                try{
                    CurrentWeather cwd = null;
                    try {
                        cwd = owm.currentWeatherByCityName("Rotterdam, Netherlands");
                    } catch (org.json.JSONException e) {
                        e.printStackTrace();
                    }
                    String weatherDesc = cwd.getWeatherInstance(0).getWeatherDescription();
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = new Date();
                    //moet nog opgeslagen worden als date
                    String currentDate = dateFormat.format(date);
                    //haalt min en max temperatuur binnen van de huidige dag in Fahrenheit
                    float getMaxTemp = cwd.getMainInstance().getMaxTemperature();
                    float getMinTemp = cwd.getMainInstance().getMinTemperature();

                    //reken om naar celsius
                    float maxTemp = (getMaxTemp - 32)*5/9;
                    float minTemp = (getMinTemp - 32)*5/9;
                    float averageTemp = (maxTemp + minTemp) / 2;

                    System.out.println("date: " + currentDate);
                    //System.out.println("max: " + maxTemp);
                    //System.out.println("min: " + minTemp);
                    System.out.println("avg: " + averageTemp);
                    System.out.println("descriptie: " + weatherDesc);

                    s.getWeather(currentDate, averageTemp, weatherDesc );

                }catch(IOException ioe) {
                    ioe.printStackTrace();
                    System.exit(0);
                }
            }
        });
    }

    private void getSentiment(){
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(dbURL, dbUser, dbPassWord);
            Statement statement = (Statement) conn.createStatement();

            String sql = "SELECT sentiment, COUNT(ID) AS numberOfTweets FROM messages GROUP BY sentiment LIMIT 3";
            rs = statement.executeQuery(sql);
            while(rs.next()){
                String tweetsT = rs.getString("sentiment") + ": " + rs.getString("numberOfTweets");
                if(tweetsT.contains("negative")){
                    tweetsT = tweetsT.replaceAll("[^0-9]", "");
                    sentimentN = Integer.parseInt(tweetsT);
                }
                if(tweetsT.contains("positive")){
                    tweetsT = tweetsT.replaceAll("[^0-9]", "");
                    sentimentP = Integer.parseInt(tweetsT);
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    private ObservableList<PieChart.Data> getChart() {
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
        list.addAll(new PieChart.Data("Negative",tweetsNeg), new PieChart.Data("Positive",tweetsSent));
        return list;
    }
    private void refreshChart(){
        btnRefresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                getSentiment();
                pieChart.setData(getChart());
            }
        });
    }
    private void getPopMess(){
        posArea.setEditable(false);
        //negArea.setEditable(false);
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(dbURL, dbUser, dbPassWord);
            java.sql.Statement statement = conn.createStatement();
            String sql = "SELECT username, dateAdded, message, sentiment, followers, favourites, retweets FROM messages, twitter WHERE messages.ID=twitter.ID ORDER BY followers DESC";
            rs = statement.executeQuery(sql);
            while (rs.next()) {
                username = rs.getString("username");
                incMess = rs.getString("message");
                follower = rs.getInt("followers");
                fav = rs.getInt("favourites");
                retweets = rs.getInt("retweets");
                sentiment = rs.getString("sentiment");

                messagesTot = sentiment;

                if(messagesTot.contains("positive")){
                    posMess = "Username: " + username + "\n\rFollowers: " + follower + "\n\rRetweets: " + retweets + "\n\rFavourites: " + fav + "\n\rMessage: " + incMess + "\n\r" + "\n\r";
                    posArea.appendText(posMess);
                }
                if(messagesTot.contains("negative")){
                    negMess = "Username: " + username + "\n\rFollowers: " + follower + "\n\rRetweets: " + retweets + "\n\rFavourites: " + fav + "\n\rMessage: " + incMess + "\n\r \n\r";
                    negArea.appendText(negMess);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*private void showWeatherSent(){
        negArea.setEditable(false);
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(dbURL, dbUser, dbPassWord);
            java.sql.Statement statement = conn.createStatement();
        } catch(Exception e){
            e.printStackTrace();
        }
    }*/
    int total;
    static int tweetsTot;
    int pos;
    static int tweetsSent;
    static int tweetsNeg;
    String testE;
    String date;
    String temp;
    String descr;
    String sent;

    private void showWeather(){
        negArea.setEditable(false);
        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(dbURL, dbUser, dbPassWord);
            Statement statement = (Statement) conn.createStatement();

            String sql = "SELECT m.dateAdded, w.temperature, w.weatherDescription, " +
                    "(SELECT COUNT(*) FROM messages m WHERE m.sentiment='positive' AND m.dateAdded=w.dateAdded) AS sent" +
                    ", COUNT(m.dateAdded)as numberOfTweets FROM messages m, weather w " +
                    "WHERE m.dateAdded=w.dateAdded GROUP BY m.dateAdded";
            //String sql = "SELECT *, (SELECT COUNT(*) FROM messages m WHERE m.sentiment='positive') AS positief FROM messages";
            rs = statement.executeQuery(sql);
            while(rs.next()){


                testE = rs.getString("numberOfTweets");
                date = rs.getString("dateAdded");
                temp = rs.getString("temperature");
                descr = rs.getString("weatherDescription");
                sent = rs.getString("sent");
                tweetsSent = Integer.parseInt(sent);
                tweetsTot = Integer.parseInt(testE);
                tweetsNeg = (tweetsTot - tweetsSent);
                total += tweetsTot;
                System.out.println("Date: " + date + " - Messages: " + testE + " Temperature: " + temp + " " + descr + " " + sent);

                showSentData = "Date added: " + date + "\n\rTemperature: " + temp + "\n\rWeather Description: " + descr + "\n\rNumber of messages: " + testE + "\n\rNumber of positive messages: " + sent + "\n\r \n\r";
                negArea.appendText(showSentData);
                //String test = rs.getString("positief");
                //ystem.out.println(test);

            }
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("total: " + total);
    }

    /*private void posWeather(){
        try{
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(dbURL, dbUser, dbPassWord);
            Statement statement = (Statement) conn.createStatement();
            String sql = "SELECT"
            rs = statement.executeQuery(sql);
        } catch(Exception e){
            e.printStackTrace();
        }
    }*/
}