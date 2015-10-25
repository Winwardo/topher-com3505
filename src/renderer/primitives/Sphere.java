package renderer.primitives;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import renderer.Renderable;

public class Sphere extends Renderable {
    private static final float SPHERE_DEFAULT_RADIUS = 0.5f;

    private static final int SPHERE_SUBDIVISION = 20;

    private float radius;
    private int   mysphereID;

    public Sphere(GL2 gl) {
        this(gl, SPHERE_DEFAULT_RADIUS, 0);
    }

    public Sphere(GL2 gl, float radius) {
        this(gl, radius, 0);
    }

    public Sphere(GL2 gl, float radius, int texture) {
        super(gl);
        this.radius = radius;

        GLU glu = new GLU();

        GLUquadric sphere = glu.gluNewQuadric();

        glu.gluQuadricDrawStyle(sphere, glu.GLU_FILL);
        glu.gluQuadricTexture(sphere, true);
        glu.gluQuadricNormals(sphere, glu.GLU_SMOOTH);
        // Making a display list
        mysphereID = gl.glGenLists(1);
        gl.glNewList(mysphereID, gl.GL_COMPILE);

        gl.glBindTexture(GL.GL_TEXTURE_2D, texture);

        glu.gluSphere(sphere, radius, SPHERE_SUBDIVISION, SPHERE_SUBDIVISION);
        gl.glEndList();
        glu.gluDeleteQuadric(sphere);
    }

    @Override
    public void renderImpl() {

        gl.glCallList(mysphereID);
    }
}
