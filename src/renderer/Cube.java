package renderer;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

public class Cube extends Renderable {
    private GLUT  glut;
    private float size;

    public Cube(GL2 gl, GLUT glut) {
        super(gl);
        this.glut = glut;
    }

    @Override
    public void renderImpl() {
        gl.glColor3d(1, 1, 1);
        glut.glutWireCube(1);
    }
}
