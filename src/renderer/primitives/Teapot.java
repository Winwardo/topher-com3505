package renderer.primitives;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import renderer.Material;
import renderer.Renderable;
import renderer.cameras.Cameras;

public class Teapot extends Renderable {
    private GLUT glut;

    public Teapot(GL2 gl, Material mat) {
        super(gl, mat);
        this.glut = new GLUT();
    }

    @Override
    public void renderImpl() {
        // Hack to get around first person problems
        if (Cameras.get().activeCamera() != Cameras.ROBOT_CAMERA) {
            gl.glDisable(GL2.GL_CULL_FACE);
            glut.glutSolidTeapot(1);
            gl.glEnable(GL2.GL_CULL_FACE);
        }
    }
}
