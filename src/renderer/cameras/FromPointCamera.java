/* I declare that this code is my own work */
/* Topher Winward, 120134353, crwinward1@sheffield.ac.uk */
package renderer.cameras;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import math.Vector3;

/**
 * FromPointCamera provides a camera that has some fixed position and can look
 * around, much like a human head.
 * 
 * @author Topher
 *
 */
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

        lookat = Vector3.zero();
        basePosition = Vector3.one();
        recalculateEyePositionAndLookat();
    }

    public void setPosition(Vector3 position) {
        this.basePosition = position;
        recalculateEyePositionAndLookat();
    }

    public float circleAngle() {
        return circleAngle;
    }

    public void setCircleAngle(float circleAngle) {
        this.circleAngle = circleAngle;
        recalculateEyePositionAndLookat();
    }

    public float heightAngle() {
        return heightAngle;
    }

    public void setHeightAngle(float heightAngle) {
        this.heightAngle = heightAngle;
        recalculateEyePositionAndLookat();
    }

    public void setDistance(float distance) {
        this.distance = distance;
        recalculateEyePositionAndLookat();
    }

    private void recalculateEyePositionAndLookat() {
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
