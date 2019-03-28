/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accesside;

import animatefx.animation.FadeIn;
import animatefx.animation.SlideInDown;
import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideInRight;
import animatefx.animation.SlideInUp;
import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.dialog.FontSelectorDialog;

/**
 *
 * @author WEbSpACE
 */
public class BASEUIController implements Initializable {
    
    //Containers
    @FXML
    private BorderPane Base1;
    @FXML
    private AnchorPane Header, Footer, LogoPane, fileTray;
        
    //Buttons
    @FXML
    private JFXButton Revert, Close, Minimise,Maximise;
    
    
    //MENUS
    @FXML
    private Menu File, View, Format, About;
    //menuItems
    @FXML
    private MenuItem Open, Save, Exit, Font;    
    //Menubar
    @FXML
    private MenuBar menuTray;
    
    
    //Main Code Area
    @FXML
    private TextArea Code;
    
    @FXML
    private Text A, Webspace, Presentation;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
    }
    //Values for Dragging the Window
    private double xOffset, yOffset;
    
    //Values for Restoring
    private static double xAxis, yAxis, height, width;
    private boolean restore = true;
    private Stage stage;
    private WebEngine displayEngine;
    
    final AnchorPane webView = new AnchorPane();
    final WebView codeResult  = new WebView();
    final FileChooser fileChooser = new FileChooser();
    final FontSelectorDialog fontSelector = new FontSelectorDialog(null);
    /**
     * All UI designs, content actions, and function calls included
     * in this function block. This is the main source of editor view
     * and functioning.
     * 
     * @param url
     * @param rb 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //DisplayUI
        A.setFill(Color.WHITE);Webspace.setFill(Color.WHITE);Presentation.setFill(Color.WHITE);
        A.setStyle("-fx-font-weight:bold");Webspace.setStyle("-fx-font-weight:bold");Presentation.setStyle("-fx-font-weight:bold");
        
        //Menus
        File.setStyle("-fx-font-weight:bold");Format.setStyle("-fx-font-weight:bold");
        View.setStyle("-fx-font-weight:bold");About.setStyle("-fx-font-weight:bold");
        
        //Webview In AnchorPane
        webView.getChildren().add(codeResult);
        webView.setTopAnchor(codeResult, 0.0);webView.setLeftAnchor(codeResult, 0.0);
        webView.setBottomAnchor(codeResult, 0.0);webView.setRightAnchor(codeResult, 0.0);
        webView.setBorder(new Border(new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.NONE, CornerRadii.EMPTY, new BorderWidths(5))));
        
        //Cookies Goes Here
        File cookieToSaveReloadRevert = new File("I:\\PROJECTS\\tests\\Cookies\\revertCookie");
        try {
            cookieToSaveReloadRevert.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(BASEUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //BoederPane - Base of Revert UI
        Base1.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");   
        
        //Close Button UI and Functioning
        BackgroundFill fillClose = new BackgroundFill(Color.web("#ea3939"), CornerRadii.EMPTY, Insets.EMPTY);
        Background close = new Background(fillClose);
        Close.setBackground(close);
        Close.setTextFill(Color.ALICEBLUE);
        Close.setStyle("-fx-font-weight:bold");
        Close.setOnAction(closeEvent -> System.exit(0));

        //MaximiseUI
        BackgroundFill fillMaximise = new BackgroundFill(Color.GAINSBORO, CornerRadii.EMPTY, Insets.EMPTY);
        Background maximise = new Background(fillMaximise);
        Maximise.setBackground(maximise);
        Maximise.setText("\u2610");
        Maximise.setTextFill(Color.BLACK);
        Maximise.setStyle("-fx-font-weight:bold");
        Maximise.setOnAction(maximiseEvent -> {            
            if(restore){
                Stage stageMaximise=(Stage) Base1.getScene().getWindow();
                xAxis = stageMaximise.getX();
                yAxis = stageMaximise.getY();
                height = stageMaximise.getHeight();
                width = stageMaximise.getWidth();
                stageMaximise.setMaximized(restore);
                Maximise.setText("\u25A0");
                restore = false;
                }else{
                RestoreEditor();
                Maximise.setText("\u2610");xAxis = yAxis = height = width = 0.0;
                restore = true;
            }
        });
        
        //MinimiseUI
        BackgroundFill fillMinimise = new BackgroundFill(Color.GAINSBORO, CornerRadii.EMPTY, Insets.EMPTY);
        Background minimise = new Background(fillMinimise);
        Minimise.setBackground(minimise);
        Minimise.setTextFill(Color.BLACK);
        Minimise.setText("\uFF3F");
        Minimise.setStyle("-fx-font-weight:bold");
        Minimise.setOnAction(minimiseEvent -> {
            Stage stageMinimise=(Stage) Base1.getScene().getWindow();
                stageMinimise.setIconified(true);
                });
        
        //HeaderUI
        BackgroundFill fillHeader = new BackgroundFill(Color.web("#303133"), new CornerRadii(5) , Insets.EMPTY);
        Background header = new Background(fillHeader);
        Header.setBackground(header);        
        Header.setOnMousePressed(pressEvent-> {
                xOffset = pressEvent.getSceneX();
                yOffset = pressEvent.getSceneY();
        });
        Header.setOnMouseDragged((dragEvent) -> {
            stage = (Stage) ((Node) dragEvent.getSource()).getScene().getWindow();
                stage.setX(dragEvent.getScreenX() - xOffset);
                stage.setY(dragEvent.getScreenY() - yOffset);
        });
               
        //MenuTrayUI
        BackgroundFill fillMenuTray = new BackgroundFill(Color.web("#ffffff"), CornerRadii.EMPTY, Insets.EMPTY);
        Background MenuTray = new Background(fillMenuTray);
        menuTray.setBackground(MenuTray);
        
        //TEXTFIELDUI
        Code.setStyle("-fx-border-width:2px");
        //Code.getScrollLeft();
        
        //RevertUI      
        BackgroundFill fillRevert = new BackgroundFill(Color.web("#303133"), new CornerRadii(80), Insets.EMPTY);
        Background revert = new Background(fillRevert);
        Revert.setBackground(revert);
        Revert.setTextFill(Color.ALICEBLUE);
        Revert.setBorder(new Border(new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.NONE, new CornerRadii(80), new BorderWidths(0))));
        
        //FooterUI
        Footer.setBackground(new Background(new BackgroundFill(Color.web("#303133"), new CornerRadii(80), Insets.EMPTY)));
        Footer.setBorder(new Border(new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.NONE, new CornerRadii(80), new BorderWidths(0))));
        //RevertButton
        Revert.setOnAction(revertEvent ->{
            if(Revert.getText().equals("Revert")){                
                 
                  fileWriter(cookieToSaveReloadRevert);
                  Revert.setText("Re-Revert");
            displayEngine = codeResult.getEngine();
            displayEngine.setJavaScriptEnabled(true);
            try {
                displayEngine.loadContent(fileReader("I:\\Miscellaneous\\Extras\\projects\\LFAIwebpRACTICES\\previous\\meme.html"));
            } catch (IOException ex) {
                Logger.getLogger(BASEUIController.class.getName()).log(Level.SEVERE, null, ex);
            }       Base1.setCenter(webView);     
            new FadeIn(webView).play();
                   
         
        }else if(Revert.getText().equals("Re-Revert")){
            Revert.setText("Revert");
                  try {
                      Code.setText(fileReaderForCookie(cookieToSaveReloadRevert.getPath()));
                  } catch (IOException ex) {
                      Logger.getLogger(BASEUIController.class.getName()).log(Level.SEVERE, null, ex);
                  } 
                 new FadeIn(Code).play();
            Base1.setCenter(Code); 
        }});
        
        
        //FileMenuItems
        Open.setOnAction(openEvent-> {
            try {
                openAction();
            } catch (IOException ex) {
                Logger.getLogger(BASEUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        Exit.setOnAction(closeEvent -> System.exit(0));
        Save.setOnAction(saveEvent-> {
            try {
                saveAction();
            } catch (IOException ex) {
                Logger.getLogger(BASEUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });     
        
        //FormatMenuItems
        Font.setOnAction(fontChooser -> fontAction());
        
        //Left And Right Panes
        LogoPane.setBackground(new Background(new BackgroundFill(Color.web("#303133"), new CornerRadii(7), Insets.EMPTY)));
        LogoPane.setBorder(new Border(new BorderStroke(Color.web("#303133"), BorderStrokeStyle.NONE, new CornerRadii(10), new BorderWidths(0))));
        fileTray.setBackground(new Background(new BackgroundFill(Color.web("#303133"), new CornerRadii(7), Insets.EMPTY)));
        fileTray.setBorder(new Border(new BorderStroke(Color.web("#303133"), BorderStrokeStyle.NONE, new CornerRadii(10), new BorderWidths(0))));
    }    
    
    private void RestoreEditor(){
        Stage restoreEditor = (Stage) Base1.getScene().getWindow();
        restoreEditor.setX(xAxis);
        restoreEditor.setY(yAxis);
        restoreEditor.setHeight(height);
        restoreEditor.setWidth(width);
    }
    
    /**
     * This Function sets the Extension for loading and saving files
     * 
     * @param chooser 
     */
     private void setExtensionFilters(FileChooser chooser){
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"),
                new FileChooser.ExtensionFilter("HTML files (*.html)", "*.html")
        );
    }
    
    
    /**
     * Function Used For Saving Files
     * 
     * @throws IOException 
     */
    private void saveAction() throws IOException{
        String saveContent = Code.getText();
        setExtensionFilters(fileChooser);
                    File toSaveDoc = fileChooser.showSaveDialog(null);
                    if (toSaveDoc != null) {
                       SaveFile(saveContent, toSaveDoc);
        }
    }
    
    /**
     * Function Used For Opening Files
     * 
     * @throws IOException 
     */
    private void openAction() throws IOException{
        setExtensionFilters(fileChooser);
                    File savedDoc = fileChooser.showOpenDialog(null);
                    if (savedDoc != null) {
                        Code.setText(fileReader(savedDoc.toURI().toURL().toString()));
        }
    }
    
    /**
     * Function Used For Choosing Font Style, Size, Type, etc.
     */
    private void fontAction(){        
        fontSelector.setTitle("Font");
        fontSelector.show();
        fontSelector.setOnHiding(value -> {
            Code.setFont(fontSelector.getResult());
        });        
    }
    
    /**
     * This function supports opening files in the editor
     * 
     * @param path
     * @return
     * @throws IOException 
     */
    private String fileReader(String path) throws IOException{
        Scanner sc = new Scanner(new File(trimmer(path))); 
        String copyText = "";
        while(sc.hasNextLine()){
            copyText += sc.nextLine()+"\n";
        }
        sc.close();
        return copyText;
    }
       private String fileReaderForCookie(String path) throws IOException{
        Scanner sc = new Scanner(new File(trimmer(path))); 
        String copyText = "";
        while(sc.hasNextLine()){
            copyText += sc.nextLine();
        }
        sc.close();
        return copyText;
    } 
    
    /**
     * This function was used for overcoming the FileNotFoundException
     * due to the path format in accordance to web view.
     * 
     * @param me
     * @return 
     */   
     private String trimmer(String me){
         
         String res="";
       if(me.contains("file:/") || me.contains("file:\\")){        
        for(int i = 6; i < me.length(); i++){
            res += me.charAt(i);
        }}else{
          res += me;
       }
        return res;
    }
     /**
      * Function supporting saving of files in the editor
      * 
      * @param content
      * @param file
      * @throws IOException 
      */
     private void SaveFile(String content, File file) throws IOException{         
        try (FileWriter inDialog = new FileWriter(file)) {
        inDialog.write(content);
        inDialog.close();
        }
    }    
     private void fileWriter(File codeContent){                   
        try (PrintWriter file = new PrintWriter(codeContent)) {
        file.println(Code.getText());
        file.close();
        } catch (FileNotFoundException ex) {
        Logger.getLogger(BASEUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
}
