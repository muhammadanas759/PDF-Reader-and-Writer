/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.geometry.Insets;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 *
 * @author STAR PC
 */
public class sortVisualizer {
    
    /**
     *
     * @return
     */
    static public ArrayList<StackPane> GenerateRectangle()
    {
       ArrayList<StackPane> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 12; i++) {
            int num = random.nextInt(10);
            Rectangle rectangle = new Rectangle(50, (num * 10) + 50);//(width,height)
            rectangle.setFill(Color.valueOf("#fffafa"));
            Text text = new Text(String.valueOf(num));
            StackPane stackPane = new StackPane();
            stackPane.setPrefSize(rectangle.getWidth(), rectangle.getHeight());
            stackPane.setId(String.valueOf(num));
            stackPane.getChildren().addAll(rectangle, text);
           
            
            list.add(stackPane);
        }
        return list;
    
    }
    
}
