/* I declare that this code is my own work */
/* Topher Winward, 120134353, crwinward1@sheffield.ac.uk */
package renderer.primitives;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import renderer.Material;
import renderer.Renderable;
import renderer.cameras.Cameras;

public class Teapot extends Renderable {
    private GLUT glut;

    public Teapot(GL2 gl, Material mat) {
        super(gl, mat);
        this.glut = new GLUT();
    }

    @Override
    public void renderImpl() {
        // If we're in first person, don't draw the Teapot as it is the robot's
        // head and interferes with the camera.
        // This is a hack, the actual Head object should be hidden rather than
        // the primitive knowing about cameras.
        if (Cameras.get().activeCamera() != Cameras.ROBOT_CAMERA) {
            // The teapot has a tendency to draw inside out if faces are culled.
            gl.glDisable(GL2.GL_CULL_FACE);
            glut.glutSolidTeapot(1);
            gl.glEnable(GL2.GL_CULL_FACE);
        }
    }
}
