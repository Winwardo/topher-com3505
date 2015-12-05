package renderer.primitives;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import renderer.DisplayList;
import renderer.Material;
import renderer.Renderable;

public class Sphere extends Renderable {
    private static final float SPHERE_DEFAULT_RADIUS = 0.5f;

    private static final int   SPHERE_SUBDIVISION    = 20;

    private final DisplayList  displayList;

    public Sphere(GL2 gl) {
        this(gl, SPHERE_DEFAULT_RADIUS, Material.empty(gl));
    }

    public Sphere(GL2 gl, float radius) {
        this(gl, radius, Material.empty(gl));
    }

    public Sphere(GL2 gl, float radius, Material mat) {
        super(gl, mat);

        displayList = new DisplayList(gl, (x) -> {
            GLU glu = new GLU();
            GLUquadric sphere = glu.gluNewQuadric();

            glu.gluQuadricDrawStyle(sphere, GLU.GLU_FILL);
            glu.gluQuadricTexture(sphere, true);
            glu.gluQuadricNormals(sphere, GLU.GLU_SMOOTH);

            glu.gluSphere(
                sphere,
                radius,
                SPHERE_SUBDIVISION,
                SPHERE_SUBDIVISION);

            glu.gluDeleteQuadric(sphere);
        });
    }

    @Override
    public void renderImpl() {
        displayList.call();
    }
}
