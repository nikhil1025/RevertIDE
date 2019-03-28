
import javafx.application.Application;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import static javafx.stage.StageStyle.TRANSPARENT;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author WEbSpACE
 */
public class TransparentWindow extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Label lbl = new Label("LABEL");
            VBox p = new VBox(lbl);

            //make the background of the label white and opaque
            lbl.setStyle("-fx-background-color: rgba(255, 255, 255, 1);");

            //add some borders to visualise the element' locations
            lbl.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, null, null)));
            p.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, null, null)));

            Scene scene = new Scene(p);
            stage.setScene(scene);

            //this is where the transparency is achieved:
            //the three layers must be made transparent
            //(i)  make the VBox transparent (the 4th parameter is the alpha)
            p.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
            //(ii) set the scene fill to transparent
            scene.setFill(null);
            //(iii) set the stage background to transparent
            stage.initStyle(TRANSPARENT);

            stage.setWidth(200);
            stage.setHeight(100);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String args[]){
        launch(args);
    }
}

