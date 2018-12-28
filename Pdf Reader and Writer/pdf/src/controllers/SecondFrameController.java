package controllers;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Random;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ResourceBundle;

import java.awt.Point;

import javafx.stage.Screen;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.application.Platform;
import static javafx.application.Platform.exit;
import javafx.stage.WindowEvent;

import marvin.MarvinDefinitions;
import marvin.image.MarvinImage;
import marvin.io.MarvinImageIO;
import marvin.plugin.MarvinImagePlugin;
import marvin.util.MarvinAttributes;
import marvin.util.MarvinPluginLoader;
import static marvin.MarvinPluginCollection.*;
import model.Anchor;
/**
 *
 * @author STAR PC
 */
public class SecondFrameController implements Initializable  {


//new variable

  
public int count = 0;
public int pointToStopCounter;
public double x[];
public double y[];
public Point[] point;
public String name;



public File fileChoice;
public Image imgload;//   IT WILL HELP US WHEN THE IMAGE IS CROPPED
public Random randomname;
private FileChooser filechooser;
public  AnchorPane anchor;



public List <File> filesListOfPicsInProgram=new LinkedList(); //-------all place                                              //it contain the no of images in the program
public List <File> filesListOfPicsThatAreCroped=new LinkedList(); //-------all place                                              //it contain the no of images in the program
public List <File> deleteFileReserve=new LinkedList(); //-------all place                                              //it contain the no of images in the program

public List<Double> values = new ArrayList<Double>();
public List<Image> allImagesThatAreInTheProgram = new LinkedList();                           //list of images that are uploaed and are on displpay of scroll pane
public ObservableList<Anchor> anchors; // THE FOUR CIRCLES THAT APEARS WHEN DRAW IS PRESSED



public FileChooser filechoosertosave;

static int labelCounterForImages=0;   //IT COUNT HOW MANY IMAGES ARE THERE IN THE PROGRAM
static Image scaledImage;
static String stringFilePath;   //STRING PATH TO THE FILE
static Point[] fourCorners;                                      //Array Of 50X50 image View
private static boolean isTessilate = false;
private static boolean isDrawn;
private static boolean isCroped;
private boolean drawShape = true;

@FXML   public Group root;                                                                   //It Contain The Polygon                                                               //It Contain The Polygon
@FXML   public ImageView imageView;                                                   //main ImageView ID  
@FXML   public Polygon polygon = new Polygon();                   
@FXML   public Polyline pl = new Polyline(); 
@FXML   public Label labelId;

//new variable




    @Override
     public void initialize(URL url, ResourceBundle rb) {
        // TODO

}

     /*****************
      * this function forms the polygon over the image with help of 
      * this function createStartingSquare()
     */
   static {
   
    MarvinDefinitions.setImagePluginPath("marvin\\plugins\\image\\");//dont change this Path according to your PC
}
   
   
   @FXML
    private void formPolygon(ActionEvent event) throws FileNotFoundException{
// The Java 8 way to get the response value (with lambda expression).
//result.ifPresent(name -> System.out.println("Your name: " + name));
        if(fileChoice!=null&&isDrawn==false)
        {

       List<String> choices = new ArrayList<>();
       choices.add("Automatic Draw");
       choices.add("Manual Draw");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Manual Draw", choices);
        dialog.setTitle("Choice Dialog");
        dialog.setHeaderText("Draw method selection");
        dialog.setContentText("Option : ");

// Traditional way to get the response value.
Optional<String> result = dialog.showAndWait();
if (result.isPresent()){
   if("Automatic Draw".equals(result.get())){
      isDrawn = true;
    
          drawPolygon();
   }
   else
   {
        TextInputDialog dialoged = new TextInputDialog("No Of Vertices");
        dialoged.setTitle("Number Of Vertices");
        dialog.setHeaderText(null);
        dialoged.setContentText("Enter The Number Of Vertices");

        // Traditional way to get the response value.
        Optional<String> resulted = dialoged.showAndWait();
        if (resulted.isPresent()){
           if(   null == resulted.get())
           {
               Alert alert = new Alert(AlertType.ERROR);
               alert.setTitle("Drawing Error");
               alert.setHeaderText(null);
               alert.setContentText( "The Parameter Is Wrong");

               alert.showAndWait();
           }
           else  switch (resulted.get()) {
               
                     case "3":
                         isDrawn = true;
                         drawPolygon(3);
                         break;
                     case "4":
                         isDrawn = true;
                         drawPolygon(4);
                         break;
                     case "5":
                         isDrawn = true;
                         drawPolygon(5);
                         break;
                     case "6":
                         isDrawn = true;
                         drawPolygon(6);
                         break;
                     default:
                         Alert alert = new Alert(AlertType.ERROR);
                         alert.setTitle("Drawing Error");
                         alert.setHeaderText(null);
                         alert.setContentText( "The Parameter Is Wrong");
                         alert.showAndWait();
                         break;
                 }
        }
  
   }
}

// The Java 8 way to get the response value (with lambda expression).

        } else{
           Alert alert = new Alert(AlertType.ERROR);
alert.setTitle("Drawing Error");
alert.setHeaderText(null);
alert.setContentText( "There Is No Parameter To Draw");

alert.showAndWait();
       }
    event.consume();
    }
    
    
    
    
private void drawPolygon() throws FileNotFoundException {
    
        
        Polygon square=createStartingSquare();
       polygon.getPoints().clear();
        polygon.getPoints().addAll(square.getPoints());
        polygon.setStroke(Color.FORESTGREEN);
        polygon.setStrokeWidth(4);
        polygon.setStrokeLineCap(StrokeLineCap.ROUND);
        polygon.setFill(Color.CORNSILK.deriveColor(0, 1.2, 1, 0.6));
        root.getChildren().clear();
        root.getChildren().add(polygon);
        root.getChildren().addAll(createControlAnchorsFor(polygon.getPoints()));
        polygon.setVisible(true);

        
      }

  private void drawPolygon(int numberOfVertices){
        pointToStopCounter=numberOfVertices;
        point=new Point[numberOfVertices]; 
        x = new double[numberOfVertices];
        y = new double[numberOfVertices];
        root.setVisible(true);
        imageView.setOnMouseDragged(mouseHandler);
        imageView.setOnMousePressed(mouseHandler);
        imageView.setOnMouseReleased(mouseHandler);
        
        
  }
  EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
                if (drawShape) {
                    x[count] = mouseEvent.getX();
                    y[count] = mouseEvent.getY();
                    System.out.println("X:" + mouseEvent.getX());
                    System.out.println("Y:" + mouseEvent.getY());
                }
            } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_RELEASED) {
                if (drawShape) {
                    polygon=new Polygon();
                    values.add(x[count]);
                    values.add(y[count]);
                    point[count]=new Point((int )x[count],(int) y[count]);
                                        count++;

                    polygon.getPoints().addAll(values);
                    
                 
                    polygon.setStroke(Color.FORESTGREEN);
                    polygon.setStrokeWidth(4);
                    polygon.setStrokeLineCap(StrokeLineCap.ROUND);
                    polygon.setFill(Color.CORNSILK.deriveColor(0, 1.2, 1, 0.6));
                    
                    root.getChildren().clear();
                    root.getChildren().add(polygon);
                    root.getChildren().addAll(createControlAnchorsFor(polygon.getPoints()));
                    
                    if (count == pointToStopCounter) {
                        drawShape = false;
                        count = 0;
                    }

                }

            }

        }

    };
    /*****************
      *this function makes the polygon dynamically adjustable
      *
     * @return 
     * @throws java.io.FileNotFoundException 
     */
    public  Polygon createStartingSquare() throws FileNotFoundException {
    Polygon triangle = new Polygon();
        fourCorners=Corners();
        triangle.getPoints().setAll(
        fourCorners[0].getX(),fourCorners[0].getY(),
        fourCorners[1].getX(), fourCorners[1].getY(),
        fourCorners[2].getX(), fourCorners[2].getY(),
        fourCorners[3].getX(), fourCorners[3].getY()
    );
        System.out.println(fourCorners[0].getX()+" "+fourCorners[0].getY()+" "+
        fourCorners[1].getX()+" "+ fourCorners[1].getY()+" "+
        fourCorners[2].getX()+" "+ fourCorners[2].getY()+" "+
        fourCorners[3].getX()+" "+ fourCorners[3].getY());
              
   triangle.setStroke(javafx.scene.paint.Color.BLACK);
   triangle.setStrokeWidth(1);
   triangle.setStrokeLineCap(StrokeLineCap.ROUND);
   triangle.setFill(javafx.scene.paint.Color.TEAL.deriveColor(0, 1.2, 1, 0.3));

    return triangle;
  }
 
public Point[] Corners() throws FileNotFoundException{
    // Load plug-in
    
    System.out.println(stringFilePath);
    
     MarvinImage image = MarvinImageIO.loadImage(name);
    
    MarvinImage img = new MarvinImage(image.getWidth(), image.getHeight());
   
     MarvinImagePlugin moravec = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.color.grayScale.jar");

     moravec.process(image, img);
    moravec = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.edge.edgeDetector.jar");

    moravec.process(img.clone(), img);
    

    moravec = MarvinPluginLoader.loadImagePlugin("org.marvinproject.image.corner.moravec.jar");
 
    MarvinAttributes attr = new MarvinAttributes();
  //   Load image
   // MarvinImage image = MarvinImageIO.loadImage("IMG-20170517-WA0010.jpg");

    // Process and save output image
    moravec.setAttribute("threshold", 1000);
    moravec.setAttribute("matrixSize", 7);
		
    moravec.process(image, null, attr);
    Point[] boundaries = boundaries(attr);


    // Print rotation angle
    double angle =  (Math.atan2((boundaries[1].y*-1)-(boundaries[0].y*-1),boundaries[1].x-boundaries[0].x) * 180 / Math.PI);
    angle =  angle >= 0 ? angle : angle + 360;
    System.out.println("Rotation angle:"+angle);
return boundaries;
}

private Point[] boundaries(MarvinAttributes attr){
    Point upLeft = new Point(-1,-1);
    Point upRight = new Point(-1,-1);
    Point bottomLeft = new Point(-1,-1);
    Point bottomRight = new Point(-1,-1);
    double ulDistance=9999,blDistance=9999,urDistance=9999,brDistance=9999;
    double tempDistance=-1;
    
    int[][] cornernessMap = (int[][]) attr.get("cornernessMap");
    System.out.println(cornernessMap[0].length);
    for(int x=0; x<cornernessMap.length; x++){
        for(int y=0; y<cornernessMap[0].length; y++){
            if(cornernessMap[x][y] > 0){
                if((tempDistance = Point.distance(x, y, 0, 0)) < ulDistance){
                    upLeft.x = x; upLeft.y = y*15;
                    ulDistance = tempDistance;
                } 
                if((tempDistance = Point.distance(x, y, cornernessMap.length, 0)) < urDistance){
                    upRight.x = x; upRight.y = y*15;
                    urDistance = tempDistance;
                }
                if((tempDistance = Point.distance(x, y, 0, cornernessMap[0].length)) < blDistance){
                    bottomLeft.x = x; bottomLeft.y = (int)(y*0.8);
                    blDistance = tempDistance;
                }
                if((tempDistance = Point.distance(x, y, cornernessMap.length, cornernessMap[0].length)) < brDistance){
                    bottomRight.x = x; bottomRight.y = (int)(y*0.8);
                    brDistance = tempDistance;
                }
            }
        }
    }
    return new Point[]{upLeft, upRight, bottomRight, bottomLeft};
}
    private ObservableList<Anchor> createControlAnchorsFor(final ObservableList<Double> points) {
     anchors = FXCollections.observableArrayList();

    for (int i = 0; i < points.size(); i+=2) {
      final int idx = i;

      DoubleProperty xProperty = new SimpleDoubleProperty(points.get(i));
      DoubleProperty yProperty = new SimpleDoubleProperty(points.get(i + 1));

      xProperty.addListener((ObservableValue<? extends Number> ov, Number oldX, Number x1) -> {
          points.set(idx, (double) x1);
      });

      yProperty.addListener((ObservableValue<? extends Number> ov, Number oldY, Number y1) -> {
          points.set(idx + 1, (double) y1);
      });

      anchors.add(new Anchor(javafx.scene.paint.Color.GOLD, xProperty, yProperty));
    }

    return anchors;
  }


     @FXML
    private void UploadImageButtonClick(ActionEvent event) throws FileNotFoundException, IOException
    {
        isCroped=false;
        isDrawn=false;

        clearAll();
        imageView.setViewport(Rectangle2D.EMPTY);
        imageView.setImage(null);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow(); 
        filechooser=new FileChooser();
        filechooser.getExtensionFilters().addAll(

                new ExtensionFilter("Image File","*.jpeg","*.JPG","*.png"),

                new ExtensionFilter("JPEG","*.jpeg",".jpg"), //----------

                new ExtensionFilter("PNG","*.png")  //------------
        );
        filechooser.setTitle("Upload Image");//----------
        fileChoice=filechooser.showOpenDialog(window);

        if (fileChoice==null) {
       
            System.out.println("no selection is made");
            Alert alert=new Alert(AlertType.INFORMATION,"You Haven't selected any thing");
            alert.setTitle("File Warning");
            alert.show();
        }
        else{
             stringFilePath = fileChoice.toPath().toString();//--------------------------
     
            Image img =new Image(new FileInputStream(stringFilePath));
            
            img.isPreserveRatio();
           
                imageView.setImage(img); 
            
                Bounds bounds = imageView.getBoundsInParent();
            MarvinImage image = MarvinImageIO.loadImage(fileChoice.getAbsolutePath());
           
            scale(image.clone(),image,(int)bounds.getWidth() ,(int)bounds.getHeight());
            Random n=new Random();

            name="scaled"+9+n.nextInt()+9+n.nextInt()+(int)bounds.getHeight()+".png";
            
            MarvinImageIO.saveImage(image, name);
           deleteFileReserve.add(new File(name));
             imageView.setImage(img);
        
        }
    }
    /*****************
      * this function add image to temporary list
      * 
      */
     @FXML
    private void AddButtonClick(ActionEvent event) throws IOException{
            if(fileChoice==null){

            }
            else//IF USER ONLY DRAW THE CIRLCE AND ALSO CROPPED IT 
                if(isCroped)
            {  
                clearAll();
                imageView.setImage(null);
                fileChoice=null;
//                name=null;
                
               allImagesThatAreInTheProgram.add(imgload);
               filesListOfPicsInProgram.add(new File(name));
               filesListOfPicsThatAreCroped.add(new File(name));
                
               polygon.setVisible(false);
                               labelCounterForImages++;

               labelId.setText("Images:"+labelCounterForImages);
                event.consume();

            }
            else
                  
                //IF USER ONLY DRAW THE CIRLCE NOT CROPPED IT 
            if(isDrawn&&isCroped!=true){
              clearAll();
//              fileChoice=null;
               // name=null;
                 imgload=imageView.getImage(); 
                                 imageView.setImage(null);
 
                 root.getChildren().remove(anchor);
                      filesListOfPicsThatAreCroped.add(new File(name));
                      filesListOfPicsInProgram.add(new File(name));

                  polygon.setVisible(false);
                  labelCounterForImages++;

                  labelId.setText("Images:"+labelCounterForImages);
                  
                  
            //      labelId.setFill(Color.WHITE);


                 event.consume();

            }  else
                {
                  clearAll();
                 imgload=imageView.getImage(); 

                  imageView.setImage(null);
                  filesListOfPicsInProgram.add(new File(name));

                labelCounterForImages++;
                labelId.setText("Images:"+labelCounterForImages);
                root.getChildren().remove(anchor);//purpose.................................................................
//                allImagesThatAreInTheProgram.add(new Image(new FileInputStream(fileChoice)));

                polygon.setVisible(false);

           

                 event.consume();
                }
    }        
        
    
     
    
     /*
      * this function makes the imageView of 50X50 dynamically selected on mouse click event
      * 
         */
     
      
 
    @FXML
    private void handleDrag(DragEvent event) {
          
         
        if(event.getDragboard().hasFiles())
        {
           
            event.acceptTransferModes(TransferMode.ANY);
        }
        event.consume();
    }

    @FXML
    private void handleDrop(DragEvent event) throws FileNotFoundException, IOException {
    
        isCroped=false;
        isDrawn=false;
      List <File> files=event.getDragboard().getFiles();
        stringFilePath = files.get(0).getPath();
         Image img =new Image(new FileInputStream(stringFilePath));
         imageView.autosize();
        
         imageView.setFitWidth(800);
         imageView.setFitHeight(535);

     
         imageView.setPreserveRatio(true);
         imageView.setSmooth(true);
         imageView.setCache(true);
         imageView.setImage(img);
        //if data Transfer not Done
     
        event.consume();
 
    }
    
 
   @FXML
    private void BackButtonClick(ActionEvent event) throws IOException{
        deleteFileReserve.forEach((File f) -> {
f.delete();
});
deleteFileReserve.clear(); 
        Parent SecondFrameParent = FXMLLoader.load(getClass().getResource("/view/FirstFrame.fxml"));
         Scene SecondFrameScene = new Scene(SecondFrameParent);
         
         
         
         Stage window = (Stage)((Node)event.getSource()).getScene().getWindow(); 
          window.setTitle("ONLINE BANKING SYSTEM");
         Screen screen = Screen.getPrimary();
         Rectangle2D bounds = screen.getVisualBounds();

         window.setX(bounds.getMinX());
         window.setY(bounds.getMinY());
         window.setWidth(bounds.getWidth());
         window.setHeight(bounds.getHeight());       
         
         window.setScene(SecondFrameScene);
         filesListOfPicsInProgram.clear();
         clearAll();
         window.show();
    }
 
  
 /*****************
      * this function Clears images FROM ITS MEMORY
      * 
     */
 
 @FXML
 private void cropTheImage(ActionEvent event) throws FileNotFoundException{
        if(isTessilate){ 
            isTessilate = false;
            root.getChildren().remove(pl);
            pl.getPoints().clear();
        }
        if(isDrawn){
   
   
            
        
       double width= Math.sqrt(Math.pow((anchors.get(1).getCenterX()-anchors.get(0).getCenterX()),2)+(Math.pow((anchors.get(1).getCenterY()-anchors.get(0).getCenterY()),2)));
       double height= Math.sqrt(Math.pow((anchors.get(3).getCenterX()-anchors.get(0).getCenterX()),2)+(Math.pow((anchors.get(3).getCenterY()-anchors.get(0).getCenterY()),2)));

//Rectangle2D croppedPortion = new Rectangle2D(anchors.get(0).getCenterX(), anchors.get(0).getCenterY(), width, height);
//
//// target width and height:
//
//
  System.out.println("width"+width+"height"+height);

//IMAGE IS CROPPED
MarvinImage image = MarvinImageIO.loadImage(name);
crop(image.clone(), image, (int) anchors.get(0).getCenterX(),(int) anchors.get(0).getCenterY(), (int) width, (int) height);
Random number=new Random();
int no=9+number.nextInt();
name="file"+no+height+2569+".png";
MarvinImageIO.saveImage(image, name);
//MARVINiMAGE AND JAVA.SCENE.IMAGE IS NOT CONVERTABLE THATS WHY
System.out.println(name);
imgload=new Image(new FileInputStream(name));
deleteFileReserve.add(new File(name));         
root.getChildren().removeAll(anchors);
polygon.getPoints().removeAll(polygon.getPoints());
polygon.setVisible(false);
//polygon.setDisable(false);


imageView.setImage(imgload);


isCroped=true;
 }
        else
        {
          Alert alert = new Alert(AlertType.ERROR,
         "There Is No Parameter To Crop"
     
        
        );
          alert.setTitle("Cropping Error");
          alert.showAndWait();
        }
clearAll();
 }
 @FXML
 private void CreatePDFButtonClick(ActionEvent event) throws BadElementException, IOException, DocumentException 
 {
      Stage window = (Stage)((Node)event.getSource()).getScene().getWindow(); 
        filechooser=new FileChooser();
        Stage stage =new Stage();
        filechooser.getExtensionFilters().addAll(
       
                new ExtensionFilter("PDF file","*.pdf")
            
               
       );
       filechooser.setTitle("Upload Image");//----------
     File fileChoice=filechooser.showSaveDialog(window);
         com.itextpdf.text.Image img = com.itextpdf.text.Image.getInstance(filesListOfPicsInProgram.get(0).toString());
     

     Document document = new Document(img);

     PdfWriter.getInstance(document, new FileOutputStream(fileChoice.getAbsolutePath()));
  
     document.open();
     for (File file: filesListOfPicsInProgram) {
       
         try {
             img = com.itextpdf.text.Image.getInstance(file.toString());
         } catch (IOException ex) {
             Logger.getLogger(SecondFrameController.class.getName()).log(Level.SEVERE, null, ex);
         }
         document.setPageSize(img);
         document.newPage();
       img.setAlignment(50);
         img.setAbsolutePosition(0, 0);
         document.add(img);
     }
 document.close();
 filesListOfPicsInProgram.forEach((file) -> {
     file.delete();
        });
filesListOfPicsInProgram.clear();
deleteFileReserve.forEach((f) -> {
f.delete();
});
deleteFileReserve.clear();
 labelId.setText("");
 }
 @FXML
 private void discardButtonClick(ActionEvent event){//----------------------------------------
     if(isTessilate){ 
            isTessilate = false;
            root.getChildren().remove(pl);
            pl.getPoints().clear();
        }
     imageView.setImage(null);
     fileChoice=null;
   
     clearAll();
     if(isDrawn)
     deleteFile();
 }
 
 public void deleteFile(){
    try{

    		File file=new File(name);
    		if(file.delete()){
    			System.out.println(file.getName() + " is deleted!");
    		}else{
    			System.out.println("Delete operation is failed.");
    		}

    	}catch(Exception e){
    	}
 }
 public void clearAll(){
     if(anchors!=null)
       {    if(isTessilate){ 
            isTessilate = false;
            root.getChildren().remove(pl);
            pl.getPoints().clear();
        }
            values.clear();
            anchors.clear();
            polygon.getPoints().removeAll(polygon.getPoints());
            root.getChildren().clear();

            drawShape = true;
            root.getChildren().removeAll(anchors);
           polygon.setDisable(false);
           polygon.setVisible(false);
            root.getChildren().removeAll(polygon.getPoints());        
       }    
    }
  
   @FXML
  public void tessellateButtonClick(ActionEvent event) throws IOException, InterruptedException
 {
          
             pl.setFill(Color.TRANSPARENT);
             pl.setStroke(Color.FORESTGREEN);
             pl.setStrokeWidth(4);
             pl.setStrokeLineJoin(StrokeLineJoin.ROUND);
                
 if(isDrawn&&!isTessilate)
 {    
     isTessilate = true;
     int length = (polygon.getPoints().size());
     switch (length) {
         case 8:
             tesselate(length);
             break;
              case 10:
                tesselate(length);

              break;
                case 12:
                    tesselate(length);

                break;
             default:
             throw new AssertionError();
     }
     
    
     root.getChildren().add(pl);

  root.addEventHandler(MouseEvent.MOUSE_CLICKED, (final MouseEvent arg0) -> {
      isTessilate = false;
      
      root.getChildren().remove(pl);
      pl.getPoints().clear();
     });  
        
 }else if(isDrawn&&isTessilate)
 {
              isTessilate = false;
              root.getChildren().remove(pl);
              pl.getPoints().clear();
 }
 }

  
public void addFirstPoint(){
     pl.getPoints().add(polygon.getPoints().get(0));
     pl.getPoints().add(polygon.getPoints().get(1));
                   
}
public void tesselate(int length){
int flag=0;

for (int i = 2; i <length-2; i+=2) {
        
    if(flag%2==0)             
    {addFirstPoint();}   
        
        flag++;         
        pl.getPoints().add(polygon.getPoints().get(i));
        pl.getPoints().add(polygon.getPoints().get(i+1));
        
}

addFirstPoint();     
}
}