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

        glut.glutSolidCube(1);
    }
}
