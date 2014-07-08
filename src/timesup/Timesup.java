/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package timesup;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import scherm.KaderController;

/**
 *
 * @author Ellen
 */
public class Timesup extends Application {
    
    @Override
    public void start(Stage primaryStage) {
     primaryStage.setTitle("timesup");
     Pane myPane = null;
        try {
            myPane = (Pane)FXMLLoader.load(KaderController.class.getResource("Kader.fxml"));
        } catch (IOException ex) {
            System.err.println("fail");
        }
       Scene myScene = new Scene(myPane);
       primaryStage.setScene(myScene);
       primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
