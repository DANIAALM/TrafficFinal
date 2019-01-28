/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Traffic;

/**
 *
 */
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.scene.control.*;
import java.util.*;
import javafx.scene.paint.*;
import javafx.application.*;
import javafx.stage.*;
import javax.swing.JOptionPane;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    /* Hello world */
    public void start(Stage stage) {

        final Environment environment = new Environment();
        final Display display = new Display(environment);
        environment.setDisplay(display);
        

        VBox box = new VBox();

        stage.setTitle("Traffic");
        stage.setScene(new Scene(box, 800, 600));

        HBox controls = new HBox();
        Button restart = new Button("Restart");
        Button setSpeeds = new Button("Set Speeds");
        Button setNumOfLanes = new Button("Set Lanes");
        Button setSpeedLimit = new Button("Set speed limit");
        Button rubberNecking = new Button("Avoid Rubbernecking");
        Button undertake = new Button("Avoid Undertake");
        Button brkEff=new Button("Set Brake Efficiency");
        Button allowCollision=new Button("Allow Collision");
        
        controls.getChildren().add(restart);
        controls.getChildren().add(setNumOfLanes);
        controls.getChildren().add(setSpeeds);
        controls.getChildren().add(setSpeedLimit);
        controls.getChildren().add(rubberNecking);
        controls.getChildren().add(undertake);
        controls.getChildren().add(brkEff);
        controls.getChildren().add(allowCollision);
        box.getChildren().add(controls);

        restart.setOnMouseClicked(e -> {
                environment.clear();
                display.reset();
                addCars(environment);
            });
        setNumOfLanes.setOnMouseClicked(e -> {
            environment.setLane();
        });
        setSpeeds.setOnMouseClicked(e -> {
            environment.setSpeeds();
        });
        setSpeedLimit.setOnMouseClicked(e -> {
            double sl=Double.parseDouble(JOptionPane.showInputDialog("Enter speed limit: "));
            while(sl<0){
                sl=Double.parseDouble(JOptionPane.showInputDialog("Please Enter positive speed limit only: "));
            }
            environment.setSpeedLimit(sl);
        });
        
        brkEff.setOnMouseClicked(e -> {
            double eff=Double.parseDouble(JOptionPane.showInputDialog("Enter braking efficiency: "));
            while(eff<0 || eff>environment.getSpeedLimit()){
                eff=Double.parseDouble(JOptionPane.showInputDialog("Invalid input, enter only in range(0-"+environment.getSpeedLimit()+"): "));
            }
            environment.setBrakeEfficiency(eff);
        });
        
        undertake.setOnMouseClicked(e -> {
            if(undertake.getText()=="Avoid Undertake")
            {
                undertake.setText("Allow Undertake");
                environment.setUndertakePermission(false);
            }
            else
            {
                undertake.setText("Avoid Undertake");
                environment.setUndertakePermission(true);
                
            }
        });
        
        rubberNecking.setOnMouseClicked(e -> {
            if(rubberNecking.getText()=="Avoid RubberNecking")
            {
                rubberNecking.setText("Allow RubberNecking");
                environment.setRubberNeckPermission(false);
            }
            else
            {
                rubberNecking.setText("Avoid RubberNecking");
                environment.setRubberNeckPermission(true);
                
            }
        });
        
        allowCollision.setOnMouseClicked(e->{
            if(allowCollision.getText()=="Allow Collision"){
                allowCollision.setText("Avoid Collision");
                environment.setCollision(true);
            }
            else
            {
                allowCollision.setText("Allow Collision");
                environment.setCollision(false);
            }
        });
        
        box.getChildren().add(display);

        addCars(environment);

        stage.show();
    }

    /** Add the required cars to an environment.
     *  @param e Environment to use.
     */
    private static void addCars(Environment e) {
        /* Add an `interesting' set of cars */
        Random r = new Random();
        e.add(new Car(  0, 63, 1, Color.BLACK,"BLACK"));
        e.add(new Car( 48, 79, 3,  Color.BLUE,"BLUE"));
        e.add(new Car(144, 60, 3,  Color.BROWN,"BROWN"));
        e.add(new Car(192, 74, 3,  Color.CHOCOLATE,"CHOCOLATE"));
        e.add(new Car(240, 12, 1,  Color.DARKGRAY,"DARKGREY"));
        e.add(new Car(288, 77, 0,  Color.DARKGREEN,"DARKGREEN"));
        e.add(new Car(336, 28, 1,  Color.YELLOWGREEN,"YELLOWGREEN"));
        e.add(new Car(384, 32, 1,  Color.YELLOW,"YELLOW"));
        e.add(new Car(432, 16, 1,  Color.PALEGREEN,"PALEGREEN"));
    }
};

