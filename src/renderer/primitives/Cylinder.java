package renderer.primitives;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.gl2.GLUT;
import renderer.DisplayList;
import renderer.Material;
import renderer.Renderable;

public class Cylinder extends Renderable {
    private static final int  SUBDIVISIONS = 16;

    private final DisplayList displayList;

    public Cylinder(GL2 gl, GLUT glut, float radius, float height,
        Material mat) {
        this(gl, glut, radius, radius, height, mat);
    }

    public Cylinder(GL2 gl, GLUT glut, float radius1, float radius2,
        float height, Material mat) {
        super(gl, mat);

        displayList = new DisplayList(gl, (x) -> {
            drawCylinder(radius1, radius2, height);
        });
    }

    @Override
    public void renderImpl() {
        displayList.call();
    }

    private void drawCylinder(float radius1, float radius2, float height) {
        drawTube(radius1, radius2, height);

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

    private void drawTube(float radius1, float radius2, float height) {
        GLU glu = new GLU();
        GLUquadric quadric = glu.gluNewQuadric();
        glu.gluQuadricDrawStyle(quadric, GLU.GLU_FILL);
        glu.gluQuadricNormals(quadric, GLU.GLU_SMOOTH);
        glu.gluQuadricTexture(quadric, true);
        glu.gluCylinder(quadric, radius1, radius2, height, SUBDIVISIONS, 24);
    }
}
