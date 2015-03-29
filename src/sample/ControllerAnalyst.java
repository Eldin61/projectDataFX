package sample;

import java.net.URL;
import java.util.ResourceBundle;

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
import twitter4j.*;

import javax.swing.*;

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
    void initialize() {
        logOut();
        streamMessage();
        addData();
        pieChart.setData(getChart());
    }
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

        SQLconnector s = new SQLconnector();

        btnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String queryText;
                int queryAmount;
                String sentiment;
                String usernames;
                String date;
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
                        User user = status.getUser();

                        usernames = status.getUser().getScreenName();
                        date = "" + status.getCreatedAt();
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
                        s.addTweets(usernames, date, tweetMess, geo, sentiment, retweets, fav, followers);
                    }
                } catch (TwitterException te) {
                    te.printStackTrace();
                    System.exit(0);
                }
                JOptionPane.showMessageDialog(null, "Data added succesfully!");
            }
        });
    }
    private ObservableList<PieChart.Data> getChart() {
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
        list.addAll(new PieChart.Data("java",20), new PieChart.Data("c",20),
                new PieChart.Data("C++",20));
        return list;
    }
}