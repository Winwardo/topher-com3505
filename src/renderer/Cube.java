package renderer;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

public class Cube extends Renderable implements IRenderable {
    private GL2   gl;
    private GLUT  glut;
    private float size;

    public Cube(GL2 gl, GLUT glut, float size) {
        this.gl = gl;
        this.glut = glut;
        this.size = size;
    }

    @Override
    public void render() {
        gl.glColor3d(1, 1, 1);
        glut.glutWireCube(size);
    }
}
