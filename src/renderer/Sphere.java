package renderer;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

public class Sphere extends Renderable {
    private static final float SPHERE_DEFAULT_RADIUS = 0.5f;

    private static final int SPHERE_SUBDIVISION = 20;

    private GLUT  glut;
    private float radius;

    public Sphere(GL2 gl, GLUT glut) {
        this(gl, glut, SPHERE_DEFAULT_RADIUS);
    }

    public Sphere(GL2 gl, GLUT glut, float radius) {
        super(gl);
        this.glut = glut;
        this.radius = radius;
    }

    @Override
    public void renderImpl() {
        gl.glColor3d(1, 1, 1);

        float[] matAmbient = { 0.25f, 0.25f, 0.25f, 1.0f };
        float[] matDiffuse = { 0.5f, 0.75f, 0.5f, 1.0f };
        float[] matSpecular = { 1.0f, 1.0f, 1.0f, 1.0f };
        float[] matShininess = { 4.0f };
        float[] matEmission = { 0.0f, 0.0f, 0.0f, 1.0f };
        // use glMaterialfv. There is no glMaterialdv
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, matAmbient, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, matDiffuse, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, matSpecular, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, matShininess, 0);

        glut.glutSolidSphere(radius, SPHERE_SUBDIVISION, SPHERE_SUBDIVISION);
    }
}
