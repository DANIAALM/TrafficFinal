/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Traffic;

/**
 *
 */
import java.awt.Color;
import java.util.ArrayList;
import javafx.animation.*;
import javax.swing.JOptionPane;

/*
 *
 * A class which represents the environment that we are working in.  In other words
 *  this class describes the road and the cars that are on the road.
 *
*/

public class Environment implements Cloneable {

    /** All the cars that are on our road */
    private ArrayList<Car> cars = new ArrayList<Car>();
    /** The Display object that we are working with */
    private Display display;
    /** Number of lanes to have on the road */
    private int lanes = 4;
    private long last;
    private boolean doOvt;
    private boolean doUndt;
    private double speedLimit=100;
    private boolean canUnderTake=true;
    private boolean canRubberNeck=true;
    private double brakeEfficiency=20;
    private boolean collPermission=false;
    
    /** Set the Display object that we are working with.  This must be called
     *  before anything will happen.
     */
    
    public void setLane(){
        int lan=Integer.parseInt(JOptionPane.showInputDialog("Enter number of lanes: "));
        lanes=lan;
    }
    public void setUndertakePermission(boolean und){
        this.canUnderTake=und;
    }
    public void setRubberNeckPermission(boolean rub){
        this.canRubberNeck=rub;
    }
    
    public void setBrakeEfficiency(double brk){
        this.brakeEfficiency=brk;
    }
    public double getSpeedLimit(){
        return this.speedLimit;
    }
    public void setSpeeds(){
        for (Car car : cars) {
            double sp=Double.parseDouble(JOptionPane.showInputDialog("Enter speed for "+car.getName()+" car: "));
            while(sp<0 || sp>speedLimit){
                sp=Double.parseDouble(JOptionPane.showInputDialog("Not valid speed, only enter in range (0-"+speedLimit+"): "));
            }
            car.setSpeed(sp);
        }
    }
    public void setCollision(boolean col){
        this.collPermission=col;
    }
    public void setSpeedLimit(double sl){
        this.speedLimit=sl;
    }
    
    public void setDisplay(Display display) {
        this.display = display;

        /* Start a timer to update things */
        new AnimationTimer() {
            public void handle(long now) {
                if (last == 0) {
                    last = now;
                }
                for(Car i: cars){
                    Car temp=nextCar(i);
                    doOvt=true;
                    doUndt=true;
                    if(temp!=null)
                        if((temp.getPosition()-i.getPosition())<=50){
                            for(Car j: cars){
                                if((j.getLane()-i.getLane())==1 && (Math.abs(i.getPosition()-j.getPosition()))<50){
                                    doOvt=false;
                                }
                            }
                            for(Car j: cars){
                                if((i.getLane()-j.getLane())==1 && (Math.abs(i.getPosition()-j.getPosition()))<50){
                                    doUndt=false;
                                }
                            }
                            
                            if(doOvt)
                            {
                                if(i.getLane()<(lanes-1))
                                    i.setLane(i.getLane()+1);
                                else
                                    doOvt=false;
                            }
                            if(!doOvt && doUndt){
                                if(i.getLane()>0 && canUnderTake){
                                    i.setLane(i.getLane()-1);
                                }
                                else
                                    doUndt=false;
                            }
                            if(!collPermission)
                            {
                                if(!doOvt && !doUndt)
                                    i.setPosition(i.getPosition()-brakeEfficiency);
                            }
                            else
                            {
                                if(!doOvt && !doUndt)
                                {
                                    
                                    i.setSpeed(0);
                                }
                            }
                        }
                }
                /* Update the model */
                tick((now - last) * 1e-9);

                /* Update the view */
                double furthest = 0;
                for (Car i: cars) {
                    if (i.getPosition() > furthest) {
                        furthest = i.getPosition();
                    }
                }
                display.setEnd((int) furthest);
                display.draw();
                last = now;
            }
        }.start();
    }

    /** Return a copy of this environment */
    public Environment clone() {
        Environment c = new Environment();
        for (Car i: cars) {
            c.cars.add(i.clone());
        }
        return c;
    }

    /** Draw the current state of the environment on our display */
    public void draw() {
        for (Car i: cars) {
            display.car((int) i.getPosition(), i.getLane(), i.getColor());
        }
    }

    /** Add a car to the environment.
     *  @param car Car to add.
     */
    public void add(Car car) {
        cars.add(car);
    }

    public void clear() {
        cars.clear();
    }

    /** @return length of each car (in pixels) */
    public double carLength() {
        return 40;
    }

    /** Update the state of the environment after some short time has passed */
    private void tick(double elapsed) {
        Environment before = Environment.this.clone();
        for (Car i: cars) {
            i.tick(before, elapsed);
        }
    }

    /** @param behind A car.
     *  @return The next car in front of @ref behind in the same lane, or null if there is nothing in front on the same lane.
     */
    public Car nextCar(Car behind) {
        Car closest = null;
        for (Car i: cars) {
            if (i != behind && i.getLane() == behind.getLane() && i.getPosition() > behind.getPosition() && (closest == null || i.getPosition() < closest.getPosition())) {
                closest = i;
            }
        }
        return closest;
    }

    /** @return Number of lanes */
    public int getLanes() {
        return lanes;
    }
}
