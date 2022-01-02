package views.callbacks;

public interface CameraMoveCallback {
    void moveX(double sens);
    void moveY(double sens);
    void moveZ(double sens);
    void rotateXY(double x, double y);
    void moveScreen(double dist);
}
