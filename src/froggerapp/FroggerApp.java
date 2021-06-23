/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package froggerapp;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
/**
 *
 * @author Atanas
 */
public class FroggerApp extends Application {
   
    private AnimationTimer timer;
    private Pane root;
    private List<Node> cars = new ArrayList<>();
    private Node Frog;
   
    private Parent createContent() {
        root = new Pane();
        root.setPrefSize(800, 600);
        
        Frog = initFrog();
        root.getChildren().add(Frog);
        timer = new AnimationTimer(){
           public void handle(long now){
               onUpdate();
           }
       };
        timer.start();
        return root;
    }
    private Node initFrog(){
        Rectangle rect = new Rectangle(38, 38, Color.GREEN);
    rect.setTranslateY(600 - 39);
   
     return rect;
    }
    
    private Node spawnCar() {
   Rectangle rect = new Rectangle(40, 40, Color.RED);
   rect.setTranslateY((int)(Math.random() * 14) * 40);
   root.getChildren().add(rect);
   
     return rect;
   }
    private void onUpdate(){
    for(Node car : cars)
    car.setTranslateX(car.getTranslateX() + Math.random()* 10);
    if (Math.random() < 0.075) {
        cars.add(spawnCar());
    }
    checkState();
    }
    private void checkState(){
        for (Node car : cars){
            if (car.getBoundsInParent().intersects(Frog.getBoundsInParent())){
           Frog.setTranslateX(0);
           Frog.setTranslateY(600- 39);
           return;
            }
       }
        if (Frog.getTranslateY() <= 0){
            timer.stop();
            String win = "YOU WIN";
           
            HBox hBox = new HBox();
            hBox.setTranslateX(300);
            hBox.setTranslateY(250);
            root.getChildren().add(hBox);
            
            for (int i = 0; i < win.toCharArray().length; i++){
                char letter = win.charAt(i);
                
                Text text = new Text (String.valueOf(letter));
                text.setFont(Font.font(48));
                text.setOpacity(0);
                
                hBox.getChildren().add(text);
                
                FadeTransition ft = new FadeTransition(Duration.seconds(0.66), text);
                ft.setToValue(1);
                ft.setDelay(Duration.seconds(i * 0.15));
                ft.play();
            }
        }
    }
   public void start(Stage stage) throws Exception{
        stage.setScene(new Scene(createContent()));
        stage.getScene().setOnKeyPressed(event -> {
            switch (event.getCode()) {
               case W:
                   Frog.setTranslateY(Frog.getTranslateY() - 40);
                  break;
               case S:
                 Frog.setTranslateY(Frog.getTranslateY() + 40);
                   break;
               case A:
                    Frog.setTranslateX(Frog.getTranslateX() - 40);
                   break;
               case D:
                   Frog.setTranslateX(Frog.getTranslateX() + 40);
                   break;
               default:
                   break;
            }
        });
        stage.show();
    }
public static void main(String[] args){
        launch(args);   
    }
}
