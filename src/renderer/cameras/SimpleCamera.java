package renderer.cameras;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import math.Vector3;

public class SimpleCamera implements Camera {
    private final GL2 gl;
    private final GLU glu;

    private Vector3   position;
    private Vector3   lookAt;

    public SimpleCamera(GL2 gl, Vector3 position, Vector3 lookAt) {
        this.gl = gl;
        this.glu = new GLU();
        this.position = position;
        this.lookAt = lookAt;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public void setLookAt(Vector3 lookAt) {
        this.lookAt = lookAt;
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
