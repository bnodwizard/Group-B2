package System;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public ComboBox categoryText;
    public TextArea descText;
    public Label point,userName;
    Connection connect;
    Statement statement;
    ResultSet resultSet;
    @FXML
    public AnchorPane login, post,view , home,signup,anomalous,emergency;
    public ImageView back,reward;
    public TextField locationText,titleText,userText,passwordText,currentLocation;

    @FXML
    ObservableList<String> categoryList = FXCollections.observableArrayList("Kathmandu","Pokhara","Chitwan","Bhaktapur","Dharan","Lalitpur","Biratnagar","Other else");


    @FXML
    private TableView<PostTable> table = new TableView<>();
    @FXML
    public TableColumn<PostTable, String> column1;
    public TableColumn<PostTable, String> column2;


    @FXML
    ObservableList<String> searchList = FXCollections.observableArrayList();

    @FXML
    public ObservableList<PostTable> data = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       start();
        categoryText.setItems(categoryList);

        try {
            addData();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        TextFields.bindAutoCompletion(currentLocation, searchList);
        TextFields.bindAutoCompletion(locationText, searchList);
        clear();

    }

    public void start(){
        login.setVisible(false);
        signup.setVisible(false);
        home.setVisible(true);
        view.setVisible(false);
        post.setVisible(false);
        userName.setVisible(false);
        point.setVisible(false);
        anomalous.setVisible(false);
        emergency.setVisible(false);
       // validSignin();
       // reward.setVisible(false);
        //back.setVisible(false);

    }

    public void viewProblem(ActionEvent actionEvent) {
        login.setVisible(false);
        signup.setVisible(false);
        home.setVisible(false);
        view.setVisible(true);
        post.setVisible(false);
        anomalous.setVisible(false);
        emergency.setVisible(false);
        clear();
    }

    public void postProblem(ActionEvent actionEvent){
        login.setVisible(true);
        signup.setVisible(false);
        home.setVisible(false);
        view.setVisible(false);
        post.setVisible(false);
        anomalous.setVisible(false);
        emergency.setVisible(false);
        validSignin();
        clear();

    }

    public void backButton(MouseEvent mouseEvent) {
        login.setVisible(false);
        signup.setVisible(false);
        home.setVisible(true);
        view.setVisible(false);
        post.setVisible(false);
        anomalous.setVisible(false);
        emergency.setVisible(false);
        clear();
    }

    public void signUp(MouseEvent mouseEvent) {
        login.setVisible(false);
        signup.setVisible(true);
        home.setVisible(false);
        view.setVisible(false);
        post.setVisible(false);
        anomalous.setVisible(false);
        emergency.setVisible(false);
        clear();
    }

    public void connectDatabase(){
        try {
            Class.forName("com.mysql.jdbc.Driver"); //loads the driver
            connect = DriverManager.getConnection("jdbc:mysql://localhost/p2p", "root", "");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void postData(ActionEvent actionEvent) throws SQLException {
        String location=locationText.getText().toString();
        String title=titleText.getText().toString();
        String desc=descText.getText().toString();
        String category=categoryText.getValue().toString();


        connectDatabase();
        PreparedStatement preparedStatement = connect.prepareStatement("INSERT INTO `post`(`id`, `time`, `location`, `problem`, `title`, `description`) VALUES (null,?,?,?,?,?)");

        preparedStatement.setString(1, "1 min ");
        preparedStatement.setString(2, location);
        preparedStatement.setString(3, category);
        preparedStatement.setString(4, title);
        preparedStatement.setString(5, desc);


        preparedStatement.executeUpdate();
        clear();

    }

    public void loginUser(ActionEvent actionEvent) {

        try {

            connectDatabase();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from login");

            while (resultSet.next()) {


                String name = resultSet.getString("name");
                String password = resultSet.getString("password");


                if (userText.getText().equals("admin") || passwordText.getText().equals(password)) {
                    login.setVisible(false);
                    signup.setVisible(false);
                    home.setVisible(false);
                    view.setVisible(false);
                    post.setVisible(true);
                    point.setVisible(true);
                    userName.setVisible(true);
                    anomalous.setVisible(false);
                    emergency.setVisible(false);
                    userName.setText(name);
                    clear();

                } else {


                }
            }


        }
        catch (Exception e){

        }
    }

    public void validSignin(){
        try {

            connectDatabase();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from login");

            while (resultSet.next()) {


                String name = resultSet.getString("name");



                if (!userName.getText().isEmpty()) {
                    login.setVisible(false);
                    signup.setVisible(false);
                    home.setVisible(false);
                    view.setVisible(false);
                    post.setVisible(true);
                    point.setVisible(true);
                    anomalous.setVisible(false);
                    emergency.setVisible(false);
                    userName.setVisible(true);
                    userName.setText(name);

                } else {


                }
            }


        }
        catch (Exception e){

        }
    }

    public void searchProblem(ActionEvent actionEvent) throws SQLException {

        String current=currentLocation.getText();
        connectDatabase();
        Statement statement = connect.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from post where location='" + current + "'");
        if (!resultSet.next()) {



        } else {
            do {
                data.clear();

                data.add(new PostTable(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6)));


                //giving each column a new data
                column1.setCellValueFactory(new PropertyValueFactory<>("title"));
                column2.setCellValueFactory(new PropertyValueFactory<>("time"));

                table.setItems(null);
                table.setItems(data);


            }
            while (resultSet.next());
        }
    }

    public void addData() throws ClassNotFoundException, SQLException {
        // String cat=orderCategory.getValue().toString();
        connectDatabase();
        Statement statement = connect.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from post");

        while (resultSet.next()) {
            searchList.add(resultSet.getString("location"));
        }
        //giving each column a new data
    }

    public void clear(){

        titleText.setText(null);
        userText.setText(null);
        passwordText.setText(null);
        descText.setText(null);

    }

    public void emergencyNumber(ActionEvent actionEvent) {
        login.setVisible(false);
        signup.setVisible(false);
        home.setVisible(false);
        view.setVisible(false);
        post.setVisible(false);
        point.setVisible(false);
        userName.setVisible(false);
        anomalous.setVisible(false);
        emergency.setVisible(true);
    }

    public void anomalousInfo(ActionEvent actionEvent) {
        login.setVisible(false);
        signup.setVisible(false);
        home.setVisible(false);
        view.setVisible(false);
        post.setVisible(false);
        point.setVisible(false);
        userName.setVisible(true);
        anomalous.setVisible(true);
        emergency.setVisible(false);
    }
}
