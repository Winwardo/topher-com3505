package renderer;

import java.util.function.Consumer;
import com.jogamp.opengl.GL2;

public class DisplayList {
    final GL2 gl;
    final int displayId;

    public DisplayList(GL2 gl, Consumer<Boolean> displayFunc) {
        this.gl = gl;

        displayId = gl.glGenLists(1);
        gl.glNewList(displayId, GL2.GL_COMPILE);
        displayFunc.accept(true);
        gl.glEndList();
    }

    public void call() {
        gl.glCallList(displayId);
    }

}
