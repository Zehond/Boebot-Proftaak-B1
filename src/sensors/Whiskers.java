package sensors;

import BoebotClasses.Updatable;

public class Whiskers implements Updatable {
    private WhiskerCallback callback;
    private int pin;

    public Whiskers(int pin, WhiskerCallback callback){
        this.pin=pin;
        this.callback=callback;
    }

    @Override
    public void turnOn() {

    }

    @Override
    public void turnOff() {

    }

    @Override
    public void update() {

    }
}
