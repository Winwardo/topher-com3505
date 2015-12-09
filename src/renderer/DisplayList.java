/* I declare that this code is my own work */
/* Topher Winward, 120134353, crwinward1@sheffield.ac.uk */
package renderer;

import com.jogamp.opengl.GL2;

/**
 * DisplayList allows for convenient wrapping of generating and calling a gl
 * display list.
 * 
 * @author Topher
 *
 */
public class DisplayList {
    final GL2 gl;
    final int displayId;

    /**
     * 
     * @param gl
     * @param displayFunc
     *            A lambda which performs some OpenGL functions to be stored in
     *            this display list
     */
    public DisplayList(GL2 gl, Runnable displayFunc) {
        this.gl = gl;

        displayId = gl.glGenLists(1);
        gl.glNewList(displayId, GL2.GL_COMPILE);
        displayFunc.run();
        gl.glEndList();
    }

    public void call() {
        gl.glCallList(displayId);
    }

}
