package renderer.primitives;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.gl2.GLUT;
import renderer.Material;
import renderer.Renderable;

public class Cylinder extends Renderable {
    private static final int SUBDIVISIONS = 14;

    private final GLUT       glut;
    private final float      radius;
    private final float      height;

    private final GLU        glu;
    private final GLUquadric quadric;

    public Cylinder(GL2 gl, GLUT glut, float radius, float height,
        Material mat) {
        super(gl, mat);
        this.glut = glut;
        this.radius = radius;
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
            drawCircle();

            // Bottom circle
            gl.glRotatef(180, 0, 1, 0);
            gl.glTranslatef(0, 0, -height);
            drawCircle();
        }
        gl.glPopMatrix();

    }

    private void drawCircle() {
        gl.glBegin(gl.GL_TRIANGLE_FAN);
        gl.glNormal3f(0, 0, -1);
        gl.glVertex3f(0, 0, 0);
        float twicePi = (float) Math.PI * 2;
        for (int i = 0; i <= SUBDIVISIONS; i++) {
            gl.glVertex2f(
                (float) (radius * Math.cos(i * twicePi / SUBDIVISIONS)),
                (float) (radius * Math.sin(i * twicePi / SUBDIVISIONS)));
        }
        gl.glEnd();
    }

    private void drawTube() {
        glu.gluQuadricDrawStyle(quadric, GLU.GLU_FILL);
        glu.gluQuadricNormals(quadric, GLU.GLU_SMOOTH);
        glu.gluQuadricTexture(quadric, true);
        glu.gluCylinder(quadric, radius, radius, height, SUBDIVISIONS, 24);
    }
}
