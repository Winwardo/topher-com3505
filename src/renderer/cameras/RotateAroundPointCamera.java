package renderer.cameras;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import math.Vector3;

public class RotateAroundPointCamera implements Camera {
    private final GL2 gl;
    private final GLU glu;
    private Vector3   position;

    private Vector3   lookAt;
    private float     distance;
    private float     circleAngle;
    private float     heightAngle;

    /**
     * 
     * @param gl
     * @param lookAt
     * @param distance
     * @param angle
     *            From 0 - 360
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

    public void setCircleAngle(float circleAngle) {
        this.circleAngle = (float) Math.toRadians(circleAngle % 360);
        position = createPosition();
    }

    public void setHeightAngle(float heightAngle) {
        this.heightAngle = (float) Math.toRadians(heightAngle % 360);
        position = createPosition();
    }

    private Vector3 createPosition() {
        float x = (float) Math.cos(circleAngle) * distance;
        float y = (float) Math.cos(heightAngle) * distance;
        float z = (float) Math.sin(circleAngle) * distance;

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
}
