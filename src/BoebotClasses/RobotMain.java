package BoebotClasses;

import TI.BoeBot;
import sensors.Infrared;
import sensors.InfraredCallback;
import sensors.Ultrasound;
import sensors.UltrasoundCallback;

import java.util.ArrayList;


public class RobotMain implements InfraredCallback, UltrasoundCallback {

    private ServoControl rightWheel;
    private ServoControl leftWheel;
    private Infrared infrared;
    private Ultrasound ultrasound;
    private ArrayList<Updatable> updatables;



//bij 200 snelheid (dus 1700 rechts en links 1300 je snapt wel) dan doet die 1 rotatie met het wiel bij een wait van 870.

    public static void main(String[] args) {
        RobotMain robotMain = new RobotMain();
        robotMain.init();
        robotMain.run();
    }

    private void run() {
        while(true){
            for (Updatable updatable : updatables) {
                updatable.update();
            }
            BoeBot.wait(1);
        }
    }

    private void init() {
        this.updatables = new ArrayList<>();
        this.updatables.add(this.rightWheel = new ServoControl(12));
        this.updatables.add(this.leftWheel = new ServoControl(13));
        this.updatables.add(this.infrared = new Infrared(0, this));
        this.updatables.add(this.ultrasound = new Ultrasound(11,10,this));

        this.rightWheel.turnOn();
        this.leftWheel.turnOn();
        this.ultrasound.turnOn();

    }

    @Override
    public void onInfrared(int IRSignalCode) {
        if(IRSignalCode == 223){ //Emergency break (Green button with cube)
            System.out.println("fakke meer bitches");
            leftWheel.setDirectionAndSpeed(3,0);
            rightWheel.setDirectionAndSpeed(3,0);
        } else if(IRSignalCode == 244){ //Go forward (Green arrow up)
            System.out.println("fakka bitches");
            leftWheel.setDirectionAndSpeed(0,-100);
            rightWheel.setDirectionAndSpeed(0,100);
        } else if(IRSignalCode == 179){ //turn right (Green arrow right) //TODO TURN RIGHT

        } else if(IRSignalCode == 180){ //turn left (Green arrow left) //TODO TURN LEFT

        } else if(IRSignalCode == 245){ //Go backwards (Green arrow down)

        }
        //gripper but it no work (OK button between green arrows)


    }

    @Override
    public void onUltrasound(int pulse) {
        if (pulse > 40) {
            //rijden met snelheid 150
            setWheels(0,150);
        } else if (pulse >30){
            //rijden met snelheid 120
            setWheels(0,120);
        }else if (pulse >20){
            //rijden met snelheid 80
            setWheels(0, 80);
        }else if (pulse >10){
            //rijden met snelheid 50
            setWheels(0, 50);
        }else{
            //tot stilstand komen
            setWheels(0,0);
        }
    }
    public void setWheels(int directionChoice, int targetSpeed){
        leftWheel.setDirectionAndSpeed(directionChoice,-targetSpeed);
        rightWheel.setDirectionAndSpeed(directionChoice, targetSpeed);
    }
}

