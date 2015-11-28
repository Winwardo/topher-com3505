package renderer.primitives;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import renderer.Renderable;

public class Cylinder extends Renderable {
    private final GLUT  glut;
    private final float radius;
    private final float height;

    public Cylinder(GL2 gl, GLUT glut, float radius, float height) {
        super(gl);
        this.glut = glut;
        this.radius = radius;
        this.height = height;
    }

    @Override
    public void renderImpl() {
        glut.glutSolidCylinder(radius, height, 24, 16);
    }
}
