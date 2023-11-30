package sensors;

import BoebotClasses.Updatable;
import TI.BoeBot;
import TI.PinMode;
import TI.Timer;

public class Ultrasound implements Updatable {

    private UltrasoundCallback callback;
    private int triggerPinNumber;
    private int echoPinNumber;
    private boolean active;
    private Timer timer;

    public Ultrasound(int echoPin, int triggerPin, UltrasoundCallback callback){
        this.echoPinNumber=echoPin;
        this.triggerPinNumber=triggerPin;
        BoeBot.setMode(echoPin, PinMode.Input);
        BoeBot.setMode(triggerPin, PinMode.Output);
        this.callback=callback;
        this.timer=new Timer(1000/4); //checkt 4keer in een seconde
        this.active=false;
    }

    public int getPulse(){
        BoeBot.digitalWrite(this.triggerPinNumber, true);
        BoeBot.wait(1);
        BoeBot.digitalWrite(this.triggerPinNumber, false);
        int pulse = BoeBot.pulseIn(this.echoPinNumber, true, 10000);

        return pulse/58;
    }

    @Override
    public void turnOn() {
        this.active = true;
    }

    @Override
    public void turnOff() {
        this.active = false;
    }

    @Override
    public void update() {
        if (!timer.timeout())
            return;
        if(!this.active)
            return;

        int pulse = getPulse();
        System.out.println("Pulse: " + pulse);
        callback.onUltrasound(pulse);
    }
}
