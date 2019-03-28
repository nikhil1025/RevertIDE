/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author WEbSpACE
 */
import javafx.beans.value.ChangeListener;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.web.WebEngine;

public class FxWebViewExample2 extends Application
{
	public static void main(String[] args) 
	{
		Application.launch(args);
	}
	
	@Override
	public void start(final Stage stage) 
	{
		// Create the WebView
		WebView webView = new WebView();
		
		// Disable the context menu
		webView.setContextMenuEnabled(false);
		
		// Increase the text font size by 20%
		webView.setFontScale(1.20);
		
		// Set the Zoom 20%
		webView.setZoom(1.20);
		
		// Set font smoothing type to GRAY
		webView.setFontSmoothingType(FontSmoothingType.GRAY);
		
		// Create the WebEngine
		final WebEngine webEngine = webView.getEngine();
		
                webEngine.setJavaScriptEnabled(true);
               
                
                // Load the StartPage
                //Method 1
		webEngine.loadContent("<!Doctype html>\n"
                        + "<html><head>Click ME</head>\n"
                        + "<body>\n<img src=\"https://github.blog/wp-content/uploads/2011/03/vm-two-up.jpg?resize=920%2C467\" style=\"width:304px;height:228px;\"/>\n</body>\n"
                        + "</html>");
                //Method 
		/*
                webEngine.load(getClass().getResource("/index.html").toExternalForm());
                */
                
		
		// Update the stage title when a new web page title is available		
		webEngine.titleProperty().addListener(new ChangeListener<String>()
		{
		    public void changed(ObservableValue<? extends String> ov,
		            final String oldvalue, final String newvalue) 
		    {
		    	stage.setTitle(newvalue);
		    }
		});
		
		// Create the VBox
		VBox root = new VBox();
		// Add the Children to the VBox
		root.getChildren().add(webView);

		// Set the Style-properties of the VBox
		root.setStyle("-fx-padding: 10;" +
				"-fx-border-style: solid inside;" +
				"-fx-border-width: 2;" +
				"-fx-border-insets: 5;" +
				"-fx-border-radius: 5;" +
				"-fx-border-color: blue;");

		// Create the Scene
		Scene scene = new Scene(root);
		// Add  the Scene to the Stage
		stage.setScene(scene);
		// Display the Stage
		stage.show();
	}
}