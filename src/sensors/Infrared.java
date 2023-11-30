package sensors;

import BoebotClasses.Updatable;
import TI.BoeBot;
import TI.PinMode;
import TI.Timer;

public class Infrared implements Updatable {

    private InfraredCallback callback;
    private Timer timer;
    private boolean active;

    public Infrared(int pin, InfraredCallback callback){
        BoeBot.setMode(pin, PinMode.Input);
        this.callback = callback;
        this.timer = new Timer(1000/100); //10x in 1 seconde meten
        this.active=false;
    }
    public int signalToIRCode(int[] array){
        int number = 0;
        for (int i = array.length -1; i >= 0; i--) {
            if(array[i] < 1000 && array[i] > 0){
                number = number << 1;
            } else if(array[i] > 1000){
                number = number<<1;
                number++;
            } else {
                return -1;
            }
        }
        return number;
    }
    public int[] getIRSignal() {
        int pulseLen = BoeBot.pulseIn(0, false, 6000);
        int[] lengths = new int[0];
        if (pulseLen > 2000) {
            lengths = new int[12];
            for (int i = 0; i < 12; i++) {
                lengths[i] = BoeBot.pulseIn(0, false, 20000);
            }
            for (int length : lengths) {
                System.out.print(length + ", ");
            }
            System.out.println("");
            System.out.println(Integer.toBinaryString(signalToIRCode(lengths)));
            System.out.println(signalToIRCode(lengths));
        }
        return lengths;
    }

    @Override
    public void turnOn() {
        this.active=true;
    }

    @Override
    public void turnOff() {
        this.active=false;

    }

    @Override
    public void update() {
        if (!timer.timeout())
            return;

        callback.onInfrared(signalToIRCode(getIRSignal()));

    }
}
