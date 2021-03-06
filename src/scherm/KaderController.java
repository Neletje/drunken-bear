package scherm;

import inhoud.Model;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import spel.Spel;

/**
 * FXML Controller class
 *
 * @author Ellen
 */
public class KaderController implements Initializable, InvalidationListener {

    @FXML
    private MenuItem pauze;
    @FXML
    private MenuItem handleiding;
    @FXML
    private MenuItem sluit;
    @FXML
    private MenuItem info;
    @FXML
    private StackPane centrum;

    private Parent spel, start, stat;
    private Parent[] delen;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        spel = fxmlLoadDeel("Spel.fxml");
        centrum.getChildren().add(spel);
        start = fxmlLoadDeel("Start.fxml");
        centrum.getChildren().add(start);
        stat = fxmlLoadDeel("Statistiek.fxml");
        centrum.getChildren().add(stat);
        delen = new Parent[]{spel, start, stat};
        maakZichtbaar(start);
        Spel.getInstance().addListener(this);

    }

    private void maakZichtbaar(Parent p) {
        for (Parent d : delen) {
            if (d != p) {
                d.setVisible(false);
                d.setDisable(true);
            } else {
                d.setVisible(true);
                d.setDisable(false);
            }
        }
    }

    private Parent fxmlLoadDeel(String filename) {
        Parent myPane = null;
        try {
            myPane = (Pane) FXMLLoader.load(StartController.class.getResource(filename));
        } catch (IOException ex) {
            System.err.println("fail");
        }
        return myPane;
    }

    int i = 0;

    @FXML
    private void pauzeer(ActionEvent event) {
         //maakZichtbaar(delen[i]);
         //i++; if(i==3)i=0;
        System.out.println("pauzeer");
    }

    @FXML
    private void sluiten(ActionEvent event) {
        System.out.println("sluiten");
    }

    @FXML
    private void helpen(ActionEvent event) {
        Model.getInstance().print();
        System.out.println("helpen");
    }

    @FXML
    private void informeren(ActionEvent event) {
        System.out.println("info");
    }

    @Override
    public void invalidated(Observable observable) {
        switch(Spel.getInstance().getStatus()){
            case START: 
                maakZichtbaar(start);
                break;
            case PAUZE: 
                maakZichtbaar(stat);
                break;
            case RONDE: 
                maakZichtbaar(spel);
                break;
            case KLAAR: 
                maakZichtbaar(stat);
                break;
            default:
                maakZichtbaar(null);
        }
        

    }

}
