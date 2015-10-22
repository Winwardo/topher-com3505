package renderer;

import com.jogamp.opengl.GL2;

public class Axes implements Renderable {
    private final GL2 gl;

    public Axes(GL2 gl) {
        this.gl = gl;
    }

    @Override
    public void render() {
        double x = 1.5, y = 1.5, z = 1.5;
        gl.glLineWidth(4);
        gl.glBegin(GL2.GL_LINES);
        gl.glColor3d(1, 0, 0);
        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(x, 0, 0);
        gl.glColor3d(0, 1, 0);
        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(0, y, 0);
        gl.glColor3d(0, 0, 1);
        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(0, 0, z);
        gl.glEnd();
        gl.glLineWidth(1);
    }
}