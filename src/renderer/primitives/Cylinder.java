package renderer.primitives;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.gl2.GLUT;
import renderer.Material;
import renderer.Renderable;

public class Cylinder extends Renderable {
    private static final int SUBDIVISIONS = 16;

    private final GLUT       glut;
    private final float      radius1;
    private final float      radius2;
    private final float      height;

    private final GLU        glu;
    private final GLUquadric quadric;

    public Cylinder(GL2 gl, GLUT glut, float radius, float height,
        Material mat) {
        this(gl, glut, radius, radius, height, mat);
    }

    public Cylinder(GL2 gl, GLUT glut, float radius1, float radius2,
        float height, Material mat) {
        super(gl, mat);
        this.glut = glut;
        this.radius1 = radius1;
        this.radius2 = radius2;
        this.height = height;

        this.glu = new GLU();
        this.quadric = glu.gluNewQuadric();
    }

    @Override
    public void renderImpl() {
        drawTube();

        gl.glPushMatrix();
        {
            // Top circle
            drawCircle(radius1);

            // Bottom circle
            gl.glRotatef(180, 0, 1, 0);
            gl.glTranslatef(0, 0, -height);
            drawCircle(radius2);
        }
        gl.glPopMatrix();

    }

    private void drawCircle(float radius) {
        gl.glBegin(gl.GL_TRIANGLE_FAN);
        gl.glNormal3f(0, 0, -1);
        gl.glTexCoord2f(0.5f, 0.5f);
        gl.glVertex3f(0, 0, 0);
        float twicePi = (float) Math.PI * 2;
        for (int i = 0; i <= SUBDIVISIONS; i++) {
            float x = (float) (radius * Math.cos(i * twicePi / SUBDIVISIONS));
            float y = (float) (radius * Math.sin(i * twicePi / SUBDIVISIONS));

            float texX = (x + 1) / 2;
            float texY = (y + 1) / 2;

            gl.glTexCoord2f(texX, texY);
            gl.glVertex2f(x, y);
        }
        gl.glEnd();
    }

    private void drawTube() {
        glu.gluQuadricDrawStyle(quadric, GLU.GLU_FILL);
        glu.gluQuadricNormals(quadric, GLU.GLU_SMOOTH);
        glu.gluQuadricTexture(quadric, true);
        glu.gluCylinder(quadric, radius1, radius2, height, SUBDIVISIONS, 24);
    }
}
