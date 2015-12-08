package renderer;

import com.jogamp.opengl.GL2;

public class DisplayList {
    final GL2 gl;
    final int displayId;

    public DisplayList(GL2 gl, Runnable displayFunc) {
        this.gl = gl;

        displayId = gl.glGenLists(1);
        gl.glNewList(displayId, GL2.GL_COMPILE);
        displayFunc.run();
        gl.glEndList();

        System.out.println(System.currentTimeMillis());
    }

    public void call() {
        gl.glCallList(displayId);
    }

}
