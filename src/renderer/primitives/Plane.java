package renderer.primitives;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import renderer.Material;
import renderer.Renderable;

public class Plane extends Renderable {
    private GLUT  glut;
    private float size;

    public Plane(GL2 gl, GLUT glut, Material mat) {
        super(gl, mat);
        this.glut = glut;

        size = 1;
    }

    @Override
    public void renderImpl() {
        double x1 = 0;
        double y1 = 0;
        double x2 = 1;
        double y2 = 1;

        gl.glBegin(gl.GL_QUADS);
        {
            // Draw square
            gl.glTexCoord2f(0, 0);
            gl.glVertex3d(x1, y1, 0);
            gl.glTexCoord2f(1, 0);
            gl.glVertex3d(x2, y1, 0);
            gl.glTexCoord2f(1, 1);
            gl.glVertex3d(x2, y2, 0);
            gl.glTexCoord2f(0, 1);
            gl.glVertex3d(x1, y2, 0);
        }
        gl.glEnd();
    }
}
