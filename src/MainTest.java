import TI.*;
public class MainTest {
    public static void main(String[] args) {
        BoeBot.setMode(11, PinMode.Input);
        BoeBot.setMode(10, PinMode.Output);
        Servo left = new Servo(13); //left
        Servo right = new Servo(12); //right

        System.out.println("Starting....");
        while (true) {
            left.update(1500);
            right.update(1500);
        }
    }
    public static int getPulse(){
        BoeBot.digitalWrite(10, true);
        BoeBot.wait(1);
        BoeBot.digitalWrite(10, false);
        int pulse = BoeBot.pulseIn(11, true, 10000);
        System.out.println("Pulse: " + Math.round((double)pulse/58));
        return pulse;
    }

}
