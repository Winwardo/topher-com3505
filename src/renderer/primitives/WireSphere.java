/* I declare that this code is my own work */
/* Topher Winward, 120134353, crwinward1@sheffield.ac.uk */
package renderer.primitives;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import renderer.DisplayList;
import renderer.Material;
import renderer.Renderable;

public class WireSphere extends Renderable {
    private static final float SPHERE_DEFAULT_RADIUS = 0.5f;

    private static final int   SPHERE_SUBDIVISION    = 14;

    private final DisplayList  displayList;

    public WireSphere(GL2 gl) {
        this(gl, SPHERE_DEFAULT_RADIUS, Material.empty(gl));
    }

    public WireSphere(GL2 gl, float radius) {
        this(gl, radius, Material.empty(gl));
    }

    public WireSphere(GL2 gl, float radius, Material mat) {
        super(gl, mat);

        displayList = new DisplayList(gl, () -> {
            GLUT glut = new GLUT();
            // A WireSphere doesn't need GLU Quadrics as we don't actually need
            // proper texturing for it.
            glut.glutWireSphere(radius, SPHERE_SUBDIVISION, SPHERE_SUBDIVISION);
        });
    }

    @Override
    public void renderImpl() {
        displayList.call();
    }
}
