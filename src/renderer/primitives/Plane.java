package renderer.primitives;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import renderer.Renderable;

public class Plane extends Renderable {
    private GLUT  glut;
    private float size;
    private int   textureId;

    public Plane(GL2 gl, GLUT glut, int textureId) {
        super(gl);
        this.glut = glut;
        this.textureId = textureId;

        size = 1;
    }

    @Override
    public void renderImpl() {
        gl.glColor3d(1, 1, 1);

        float[] matAmbient = { 0.25f, 0.25f, 0.25f, 1.0f };
        float[] matDiffuse = { 0.5f, 0.5f, 0.5f, 1.0f };
        float[] matSpecular = { 1.0f, 1.0f, 1.0f, 1.0f };
        float[] matShininess = { 2.0f };
        float[] matEmission = { 0.0f, 0.0f, 0.0f, 1.0f };
        // use glMaterialfv. There is no glMaterialdv
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, matAmbient, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, matDiffuse, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, matSpecular, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, matShininess, 0);

        gl.glBindTexture(gl.GL_TEXTURE_2D, textureId);

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
