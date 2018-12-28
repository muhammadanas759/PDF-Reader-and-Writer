/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.sortVisualizer;
/**
 * FXML Controller class
 *
 * @author STAR PC
 */
public class SortVisualizerController implements Initializable {

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
        */
   int selectedAlgo = 0;
   static double speed = 400;
   static ArrayList<StackPane> list;
   @FXML
   HBox centerHBoxID;
   @FXML
   Slider sliderID;
   @FXML
   Text BubbleSort;
   @FXML
   Text SelectionSort;
   @FXML   
   Text  InsertionSort;
   @FXML

   Button SortButtonID;
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
     
       list = sortVisualizer.GenerateRectangle();
       centerHBoxID.setAlignment(Pos.CENTER);
      centerHBoxID.setSpacing(15);
       centerHBoxID.getChildren().addAll(list);
       
 sliderID.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
     speed = (double) newValue;
     });
        System.out.println("muhammad anas");
     
             BubbleSort.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
           public void handle(MouseEvent event) {
               selectedAlgo = 0;
               BubbleSort.setStyle("-fx-underline:true; -fx-font-size: 30;");
               SelectionSort.setStyle("-fx-underline:false;  -fx-font-size: 20;");
               InsertionSort.setStyle("-fx-underline:false; -fx-font-size: 20;");
           }
       });
        SelectionSort.setOnMouseClicked(event -> {
      
            selectedAlgo = 1;
        BubbleSort.setStyle("-fx-underline:false; -fx-font-size: 20;");
            SelectionSort.setStyle("-fx-underline:true; -fx-font-size: 30;");
            InsertionSort.setStyle("-fx-underline:false; -fx-font-size: 20;");
           });
        InsertionSort.setOnMouseClicked(event -> {
    
            selectedAlgo = 2;
               BubbleSort.setStyle("-fx-underline:false; -fx-font-size: 20;");
            SelectionSort.setStyle("-fx-underline:false;  -fx-font-size: 20;");
            InsertionSort.setStyle("-fx-underline:true; -fx-font-size: 30;");
         
 
        });
      
    } 
    @FXML
    private void GenerateButtonClick(ActionEvent event) throws IOException{
        centerHBoxID.getChildren().clear();
        list.clear();
          
       list = sortVisualizer.GenerateRectangle();
       centerHBoxID.setAlignment(Pos.CENTER);
      centerHBoxID.setSpacing(15);
       centerHBoxID.getChildren().addAll(list);
    }
     @FXML
    private void SortButtonClick(ActionEvent event) throws IOException{
   SequentialTransition sq = new SequentialTransition();
   int arr[];
   switch(selectedAlgo)
   {
       case 0:
            arr = generateArray(list);
            sq = BubbleSort(arr, list);
            break;
       case 1:
            arr = generateArray(list);
            sq = SelectionSort(arr, list);
            break;
        case 2:
            arr = generateArray(list);
            sq = insertionSort(arr, list);
            break;
            
       default:
            break;
   }
   SortButtonID.setDisable(true);
   sliderID.setDisable(true);
   sq.play();
   sq.setOnFinished((ActionEvent event1) -> {
       SortButtonID.setDisable(false);
   });
   SortButtonID.setDisable(false);
   sliderID.setDisable(false);
  
    }
        private int[] generateArray(List<StackPane> list) {
        int arr[] = new int[list.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Integer.parseInt(list.get(i).getId());
        }
        return arr;
    }
    private SequentialTransition BubbleSort(int arr[], ArrayList<StackPane> list) {
        SequentialTransition sq = new SequentialTransition();
        int temp;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 1; j < arr.length - i; j++) {
                if (arr[j - 1] < arr[j]) {
                    temp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                    sq.getChildren().add(swapMe(list.get(j - 1), list.get(j), list, speed));
                }
            }
        }
        return sq;
    }
        private ParallelTransition swapMe(StackPane l1, StackPane l2, ArrayList<StackPane> list, double speed) {
        TranslateTransition t1 = new TranslateTransition();
        TranslateTransition t2 = new TranslateTransition();
        t1.setDuration(Duration.millis(speed));
        t2.setDuration(Duration.millis(speed));
        ParallelTransition pl = new ParallelTransition();
        t1.setNode(l1);
        t2.setNode(l2);
        t1.setByX(65);
        t2.setByX(-65);
        pl.getChildren().addAll(t1, t2);
        Collections.swap(list, list.indexOf(l1), list.indexOf(l2));
        return pl;
    }

   private SequentialTransition SelectionSort(int arr[], ArrayList<StackPane> list) {
        SequentialTransition sq = new SequentialTransition();
        int i, j, minIndex, tmp;
        int n = arr.length;
        for (i = 0; i < n - 1; i++) {
            minIndex = i;
            for (j = i + 1; j < n; j++)
                if (arr[j] < arr[minIndex])
                    minIndex = j;
            if (minIndex != i) {
                tmp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = tmp;
                sq.getChildren().add(swapSelect(list.get(i), list.get(minIndex), list, speed));
            }
        }
        return sq;
    }
 private ParallelTransition swapSelect(StackPane l1, StackPane l2, ArrayList<StackPane> list, double speed) {
        int num = 1;
        StackPane sp1 = null, sp2 = null, fSp = null;
        TranslateTransition t1 = new TranslateTransition();
        TranslateTransition t2 = new TranslateTransition();
        ParallelTransition pl = new ParallelTransition();
        t1.setNode(l1);
        t2.setNode(l2);
        t1.setDuration(Duration.millis(speed));
        t2.setDuration(Duration.millis(speed));
        boolean outerBreak = false;
        for (int i = 0; i < list.size(); i++) {
            if (outerBreak) break;
            if (list.get(i) == l1 || list.get(i) == l2) {
                fSp = list.get(i);
                for (int j = list.indexOf(fSp) + 1; j < list.size(); j++) {
                    if ((list.get(j) == l1 && list.get(j) != fSp) || (list.get(j) == l2 && list.get(j) != fSp)) {
                        outerBreak = true;
                        num = j - i;
                        break;
                    }
                }
            }
        }
        num *= 65;
        t1.setByX(num);
        t2.setByX(-num);
        Collections.swap(list, list.indexOf(l1), list.indexOf(l2));
        pl.getChildren().addAll(t1, t2);
        return pl;
    }
private SequentialTransition insertionSort(int[] arr, ArrayList<StackPane> list) {
        SequentialTransition sq = new SequentialTransition();
        int temp;
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if (arr[j] < arr[j - 1]) {
                    temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                    sq.getChildren().add(swapMe(list.get(j - 1), list.get(j), list, speed));
                } else {
                    break;
                }
            }
        }
        return sq;
    }
 
  @FXML
   private void BackButtonClick(ActionEvent event) throws IOException{
         Parent SecondFrameParent = FXMLLoader.load(getClass().getResource("/view/FirstFrame.fxml"));
         Scene SecondFrameScene = new Scene(SecondFrameParent);
         
         
         
         Stage window = (Stage)((Node)event.getSource()).getScene().getWindow(); 
                   window.setTitle("PDF Scanner & Reader");

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