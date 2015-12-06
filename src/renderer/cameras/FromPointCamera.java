package renderer.cameras;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import math.Vector3;

public class FromPointCamera implements Camera {
    private static final int CATCHUP_FACTOR = 32;
    private final GL2        gl;
    private final GLU        glu;

    private Vector3          lookat;
    private Vector3          basePosition;
    private Vector3          finalPosition;

    private float            circleAngle;
    private float            heightAngle;
    private float            distance;

    /**
     * 
     * @param gl
     * @param position
     * @param distance
     * @param circleAngle
     *            In degrees
     * @param heightAngle
     *            In degrees
     */
    public FromPointCamera(GL2 gl, Vector3 position, float distance,
        float circleAngle, float heightAngle) {
        this.gl = gl;
        this.glu = new GLU();

        this.distance = distance;
        this.basePosition = position;
        this.circleAngle = circleAngle;
        this.heightAngle = heightAngle;
    }

    public void setPosition(Vector3 position) {
        this.basePosition = position;
        recalculate();
    }

    public float circleAngle() {
        return circleAngle;
    }

    public void setCircleAngle(float circleAngle) {
        this.circleAngle = circleAngle;
        recalculate();
    }

    public float heightAngle() {
        return heightAngle;
    }

    public void setHeightAngle(float heightAngle) {
        this.heightAngle = heightAngle;
        recalculate();
    }

    public void setDistance(float distance) {
        this.distance = distance;
        recalculate();
    }

    private void recalculate() {
        final float cosCircle = (float) Math.cos(Math.toRadians(circleAngle));
        final float sinCircle = (float) Math.sin(Math.toRadians(circleAngle));

        finalPosition = new Vector3(
            basePosition.x() + cosCircle * distance,
            basePosition.y(),
            basePosition.z() + sinCircle * distance);

        lookat = new Vector3(
            finalPosition.x() + cosCircle,
            finalPosition.y() + (float) Math.sin(Math.toRadians(heightAngle)),
            finalPosition.z() + sinCircle);
    }

    @Override
    public void apply() {
        glu.gluLookAt(
            finalPosition.x(),
            finalPosition.y(),
            finalPosition.z(),
            lookat.x(),
            lookat.y(),
            lookat.z(),
            0.0,
            1.0,
            0.0);
    }

    public void addRotation(float dx, float dy) {
        setCircleAngle(this.circleAngle + dx * 100);
        setHeightAngle(this.heightAngle - dy * 100);
    }
}
