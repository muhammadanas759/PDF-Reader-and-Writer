/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author STAR PC
 */
public class FirstFrameController implements Initializable {
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    
    @FXML
    private void PDFReaderButtonClick(ActionEvent event) throws IOException{
         Parent SecondFrameParent = FXMLLoader.load(getClass().getResource("/view/ThirdFrame.fxml"));
         Scene SecondFrameScene = new Scene(SecondFrameParent);
         
         Stage window = (Stage)((Node)event.getSource()).getScene().getWindow(); 
         window.setTitle("PDF Reader");
         Screen screen = Screen.getPrimary();
         Rectangle2D bounds = screen.getVisualBounds();

         window.setX(bounds.getMinX());
         window.setY(bounds.getMinY());
         window.setWidth(bounds.getWidth());
         window.setHeight(bounds.getHeight());
         window.setScene(SecondFrameScene);
         window.setMaximized(true);
         window.show();
    }
    @FXML
    private void ScannerButtonClick(ActionEvent event) throws IOException{
         Parent SecondFrameParent = FXMLLoader.load(getClass().getResource("/view/SecondFrame.fxml"));
         Scene SecondFrameScene = new Scene(SecondFrameParent);
        
         
         Stage window = (Stage)((Node)event.getSource()).getScene().getWindow(); 
         window.setTitle("PDF Scanner");
         Screen screen = Screen.getPrimary();
         Rectangle2D bounds = screen.getVisualBounds();

         window.setX(bounds.getMinX());
         window.setY(bounds.getMinY());
         window.setWidth(bounds.getWidth());
         window.setHeight(bounds.getHeight());          
              window.setScene(SecondFrameScene);
         window.show();
    }
        @FXML
    private void SortVisualizerButtonClick(ActionEvent event) throws IOException{
         Parent SecondFrameParent = FXMLLoader.load(getClass().getResource("/view/SortVisualizer.fxml"));
         Scene SecondFrameScene = new Scene(SecondFrameParent);
        
       
         Stage window = (Stage)((Node)event.getSource()).getScene().getWindow(); 
         window.setTitle("Sort Visualizer"); 
         Screen screen = Screen.getPrimary();
         Rectangle2D bounds = screen.getVisualBounds();

         window.setX(bounds.getMinX());
         window.setY(bounds.getMinY());
         window.setWidth(bounds.getWidth());
         window.setHeight(bounds.getHeight());          
              window.setScene(SecondFrameScene);
  
         window.show();
    }
}
