package sensors;

public interface WhiskerCallback {
    void onWhiskerPress(Whiskers whichWhisker);
    void onWhiskerRelease(Whiskers whichWhisker);
}
