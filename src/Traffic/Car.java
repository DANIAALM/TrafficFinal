/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Traffic;

import javafx.scene.paint.*;

/** State of a car on the road */
public class Car {

    /** Position of this car on the road (i.e. how far down the road it is) in pixels */
    private double position;
    /** Current speed in pixels per second */
    private double speed;
    /** Lane that this car is on */
    private int lane;
    /** Colour of this car's display */
    private Color color;
    private String name;

    public Car(double position, double speed, int lane, Color color,String name) {
        this.position = position;
        this.speed = speed;
        this.lane = lane;
        this.color = color;
        this.name=name;
    }
    
    public double getSpeed(){
        return this.speed;
    }
    public String getName()
    {
        return this.name;
    }

    /** @return a new Car object with the same state as this one */
    public Car clone() {
        return new Car(position, speed, lane, color,name);
    }

    /** Update this car after `elapsed' seconds have passed */
    public void tick(Environment environment, double elapsed) {
        position += speed * elapsed;
    }
    
    public void setSpeed(double sp){
        this.speed=sp;
    }
    
    public double getPosition() {
        return position;
    }
    public void setPosition(double pos){
        this.position=pos;
    }
    public void setLane(int lan){
        this.lane=lan;
    }
    public int getLane() {
        return lane;
    }

    public Color getColor() {
        return color;
    }
}

