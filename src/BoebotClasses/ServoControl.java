package BoebotClasses;

import TI.BoeBot;
import TI.PinMode;
import TI.Servo;
import TI.Timer;

public class ServoControl implements Updatable {
    private Servo servo;
    private Timer timer;
    private int pin;
    private int currentSpeed;
    private int targetSpeed;
    private int directionChoice;
    private boolean active;
    private int speedRight;
    private int speedLeft;

    public ServoControl(int pin) {
        this.servo = new Servo(pin);
        this.pin = pin;
        this.currentSpeed = 1500;
        this.directionChoice = -1;
        timer = new Timer(50);
        this.active = false;
    }

    //No option = -1, vooruit = 0, achteruit = 1, draaien links = 2, draaien rechts = 3;
    public void setDirectionAndSpeed(int directionChoice, int targetSpeed) {
        this.directionChoice = directionChoice;
        this.targetSpeed = 1500 + targetSpeed;

    }

//    public void updateLeftAndRight(int updateValueLeft, int updateValueRight) {
//        links.update(updateValueLeft);
//        rechts.update(updateValueRight);
//    }

//    public void straight(int speed) {
//
//        rechts.update(1500 + speed);
//        speedRight = 1500 + speed;
//        links.update(1500 - speed);
//        speedLeft = 1500 - speed;
//        currentSpeed = speed;
//    }

//    public void noodRem() {
//        updateLeftAndRight(1500, 1500);
//    }

//    public void gaNaarSnelheid(int snelheid, int wait) {
//
//        if (currentSpeed < snelheid) {
//            for (int i = currentSpeed; i <= snelheid; i += 10) {
//                straight(i);
//                System.out.println(currentSpeed);
//                BoeBot.wait(wait);
//            }
//        } else if (currentSpeed > snelheid) {
//            for (int i = currentSpeed; i >= snelheid; i -= 10) {
//                straight(i);
//                System.out.println(currentSpeed);
//                BoeBot.wait(wait);
//            }
//        }
//        currentSpeed = snelheid;
//    }

//    public void gaNaarSnelheid(int snelheid) {
//        gaNaarSnelheid(snelheid, 100);
//    }
//
//    public void draaien(int draaiSnelheid, int graden) {
//        if (draaiSnelheid > 0) {
//            rechts.update(speedRight - draaiSnelheid);
//        } else {
//            links.update(speedLeft - draaiSnelheid);
//        }
//        BoeBot.wait(graden);
//        straight(currentSpeed);
//    }

//    public void draaien(int draaiSnelheid) {
//        draaien(draaiSnelheid, 500);
//    }

//    public void remmen() {
//        if (currentSpeed < 0) {
//            for (int i = currentSpeed; i <= 0; i += 10) {
//                straight(i);
//                System.out.println(currentSpeed);
//                BoeBot.wait(100);
//
//            }
//        } else if (currentSpeed > 0) {
//            for (int i = currentSpeed; i >= 0; i -= 10) {
//                straight(i);
//                System.out.println(currentSpeed);
//                BoeBot.wait(100);
//
//            }
//        }
//    }
//
//    public void speedTimer(int snelheid) {
//        if (currentSpeed < snelheid) {
//            System.out.println("it started");
//            speedLeft = (int) Math.round(1500 - (snelheid * 0.022));
//            speedRight = (int) Math.round(1500 + (snelheid * 0.022));
//            bothServos(speedRight, speedLeft);
//            BoeBot.wait(200);
//            speedRight = (int) Math.round(1500 + (snelheid * 0.068));
//            speedLeft = (int) Math.round(1500 - (snelheid * 0.068));
//            bothServos(speedRight, speedLeft);
//            BoeBot.wait(600);
//            speedLeft = (int) Math.round(1500 - (snelheid * 0.22));
//            speedRight = (int) Math.round(1500 + (snelheid * 0.22));
//            bothServos(speedRight, speedLeft);
//            BoeBot.wait(1000);
//            speedRight = (int) Math.round(1500 + (snelheid * 0.68));
//            speedLeft = (int) Math.round(1500 - (snelheid * 0.68));
//            bothServos(speedRight, speedLeft);
//            BoeBot.wait(500);
//            System.out.println("it has ended");
//        }
//    }


//    public void bothServos(int snelheidRechts, int snelheidLinks) {
//        rechts.update(snelheidRechts);
//        links.update(snelheidLinks);
//    }
//
//    public void bothServosTurn(int snelheid, int tijd) {
//        rechts.update(1500 + snelheid);
//        links.update(1500 - snelheid);
//        BoeBot.wait(tijd);
//    }
    public void updateServo(int speed){
        this.servo.update(speed);
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
        if (currentSpeed == targetSpeed) {
            this.directionChoice = -1;
            return;
        }

        if (!timer.timeout())
            return;

        if(!this.active)
            return;

        int localTargetSpeed = this.targetSpeed;
        int localCurrentSpeed = this.currentSpeed;
        //vooruit rijden
        if (directionChoice == 0) {
            if (this.pin == 13){ //leftwheel
                int speedDifference = localTargetSpeed - localCurrentSpeed;
                if (speedDifference > 5)
                    speedDifference = 5;
                else if (speedDifference < -5)
                    speedDifference = -5;

                this.currentSpeed += speedDifference;
                System.out.println(localCurrentSpeed + "\n" + this.currentSpeed);
                updateServo(this.currentSpeed);
            } else if(this.pin == 12){ //rightwheel
                int speedDifference = localTargetSpeed - localCurrentSpeed;
                if (speedDifference > 5)
                    speedDifference = 5;
                else if (speedDifference < -5)
                    speedDifference = -5;

                this.currentSpeed += speedDifference;
                updateServo(this.currentSpeed);
            }

        } else if(directionChoice == 1) { //achteruit rijden
            int speedDifference = targetSpeed - currentSpeed;
            if (speedDifference > 10)
                speedDifference = 10;
            else if (speedDifference < -10)
                speedDifference = -10;

            currentSpeed -= speedDifference;
        } else if(directionChoice == 3) {//override (Emergency break)
            System.out.println("fakka nog meer bitches");
            this.servo.update(1500);
            this.currentSpeed = 1500;
            this.directionChoice = -1;
        }

    }
}
