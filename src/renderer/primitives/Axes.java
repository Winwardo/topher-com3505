package renderer.primitives;

import java.nio.IntBuffer;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES2;
import com.sun.prism.impl.BufferUtil;
import renderer.Material;
import renderer.Renderable;

/**
 * A 3D axis. Renders fullbright, ignoring lighting.
 */
public class Axes extends Renderable {
    public Axes(GL2 gl) {
        super(gl, Material.empty(gl));
    }

    @Override
    public void renderImpl() {
        renderAxes(gl);
    }

    /**
     * renderAxes is a special case, which we can use as a debug. We can call
     * Axes.renderAxes from anywhere and immediately get a useful Axes, without
     * worrying about extra debug drawing on the axes themselves.
     * 
     * @param gl
     */
    public static void renderAxes(GL2 gl) {
        boolean isLit = gl.glIsEnabled(GL2.GL_LIGHTING);
        gl.glDisable(GL2.GL_LIGHTING);

        boolean isTextured = gl.glIsEnabled(GL2.GL_TEXTURE_2D);
        gl.glDisable(GL2.GL_TEXTURE_2D);

        IntBuffer currentShaderBuffer = BufferUtil.newIntBuffer(1);
        gl.glGetIntegerv(GL2ES2.GL_CURRENT_PROGRAM, currentShaderBuffer);
        int currentShader = currentShaderBuffer.get(0);

        if (currentShader != 0) {
            gl.glUseProgram(0);
        }

        double x = 1.5, y = 1.5, z = 1.5;
        gl.glLineWidth(4);
        gl.glBegin(GL2.GL_LINES);
        gl.glColor3d(1, 0, 0);
        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(x, 0, 0);
        gl.glColor3d(0, 1, 0);
        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(0, y, 0);
        gl.glColor3d(0, 0, 1);
        gl.glVertex3d(0, 0, 0);
        gl.glVertex3d(0, 0, z);
        gl.glEnd();
        gl.glLineWidth(1);

        if (currentShader != 0) {
            gl.glUseProgram(currentShader);
        }

        if (isTextured) {
            gl.glEnable(GL2.GL_TEXTURE_2D);
        }

        if (isLit) {
            gl.glEnable(GL2.GL_LIGHTING);
        }
    }
}
