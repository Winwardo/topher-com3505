package renderer.cameras;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import math.Vector3;

public class RotateAroundPointCamera implements Camera {
    private static final int CATCHUP_FACTOR = 32;
    private final GL2        gl;
    private final GLU        glu;
    private Vector3          position;

    private Vector3          lookAt;
    private float            distance;
    private float            circleAngle;
    private float            heightAngle;

    /**
     * 
     * @param gl
     * @param lookAt
     * @param distance
     * @param circleAngle
     *            In degrees
     * @param heightAngle
     *            In degrees
     */
    public RotateAroundPointCamera(GL2 gl, Vector3 lookAt, float distance,
        float circleAngle, float heightAngle) {
        this.gl = gl;
        this.glu = new GLU();

        this.lookAt = lookAt;
        this.distance = distance;
        this.circleAngle = circleAngle;
        this.heightAngle = heightAngle;

        this.position = createPosition();
    }

    public void setLookAt(Vector3 lookAt) {
        this.lookAt = lookAt;
        position = createPosition();
    }

    public void setDistance(float distance) {
        this.distance = distance;
        position = createPosition();
    }

    public float circleAngle() {
        return circleAngle;
    }

    public void setCircleAngle(float circleAngle) {
        this.circleAngle = circleAngle;
        position = createPosition();
    }

    public float heightAngle() {
        return heightAngle;
    }

    public void setHeightAngle(float heightAngle) {
        this.heightAngle = heightAngle;
        position = createPosition();
    }

    private Vector3 createPosition() {
        float x = lookAt.x()
            + (float) Math.cos(Math.toRadians(circleAngle)) * distance;
        float y = lookAt.y()
            + (float) Math.sin(Math.toRadians(heightAngle)) * distance;
        float z = lookAt.z()
            + (float) Math.sin(Math.toRadians(circleAngle)) * distance;

        return new Vector3(x, y, z);
    }

    @Override
    public void apply() {
        glu.gluLookAt(
            position.x(),
            position.y(),
            position.z(),
            lookAt.x(),
            lookAt.y(),
            lookAt.z(),
            0.0,
            1.0,
            0.0);
    }

    public void addDistance(float offset) {
        setDistance(this.distance + offset);
    }

    public void addRotation(float dx, float dy) {
        setCircleAngle(this.circleAngle + dx * 100);
        setHeightAngle(this.heightAngle - dy * 100);
    }

    public void setTargetCircleAngle(float targetAngle) {
        setTargetCircleAngle(targetAngle, CATCHUP_FACTOR);
    }

    public void setTargetCircleAngle(float targetAngle, float catchupFactor) {
        float difference = targetAngle - circleAngle;
        float finalAngle = circleAngle + difference / catchupFactor;
        setCircleAngle(finalAngle);
    }
}
