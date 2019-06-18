package Main;

import com.ayman.MessagesSystem.Code.Dao.UsersDao;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class messages extends Application {

    public static Stage window;

    @Override
    public void start(Stage primaryStage) throws IOException {
        window = primaryStage;
        AnchorPane root = FXMLLoader.load(getClass().getResource("/com/ayman/MessagesSystem/View/Register/RegisterFXML.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Login");
        primaryStage.setResizable(false); 
        primaryStage.setScene(scene);
        primaryStage.onShownProperty().set(e ->{
            try {
                UsersDao.onConn();
            } catch (SQLException ex) {
                Logger.getLogger(messages.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
