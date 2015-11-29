package renderer.primitives;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import renderer.Material;
import renderer.Renderable;

public class Plane extends Renderable {
    private GLUT  glut;

    private int   xRepeats;
    private int   yRepeats;
    private float xStep;
    private float yStep;

    public Plane(GL2 gl, GLUT glut, Material mat, int xRepeats, int yRepeats) {
        super(gl, mat);
        this.glut = glut;

        this.xRepeats = xRepeats;
        this.yRepeats = yRepeats;
        this.xStep = 1.0f / xRepeats;
        this.yStep = 1.0f / yRepeats;
    }

    public Plane(GL2 gl, GLUT glut, Material mat) {
        this(gl, glut, mat, 1, 1);
    }

    @Override
    public void renderImpl() {
        float y = 0;

        for (int y_ = 0; y_ < yRepeats; y_++) {
            float x = 0;

            for (int x_ = 0; x_ < xRepeats; x_++) {
                float x1 = x;
                float y1 = y;
                float x2 = x + xStep;
                float y2 = y + yStep;

                gl.glBegin(gl.GL_QUADS);
                {
                    // Draw square
                    // Without setting the correct normal manually, the lighting
                    // calculations will be incorrect
                    gl.glNormal3f(0, 0, -1);
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

                x += xStep;
            }

            y += yStep;
        }

    }
}
