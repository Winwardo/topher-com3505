/* I declare that this code is my own work */
/* Topher Winward, 120134353, crwinward1@sheffield.ac.uk */
package renderer;

import java.nio.IntBuffer;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

/**
 * FrameBuffer Object, for rendering to before re-rendering elsewhere, either
 * with a new shader applied, or just as normal.
 * 
 * Inspired by
 * https://stackoverflow.com/questions/2653977/problem-with-jogl-and-framebuffer
 * -render-to-texture-invalid-framebuffer-operati
 * 
 * @author Topher
 *
 */
public class FrameBufferObject {
    private final GL2 gl;
    private final GLU glu;

    private final int id;
    private final int size;

    public FrameBufferObject(GL2 gl, int textureId, int size) {
        this.gl = gl;
        this.glu = new GLU();

        IntBuffer ib = makeSingleIntBuffer();
        gl.glGenFramebuffers(1, ib);
        this.id = ib.get(0);

        this.size = size;

        gl.glBindTexture(GL.GL_TEXTURE_2D, textureId);

        makeFramebuffer(textureId);
        makeRenderBuffer();
        clearBuffers();

        checkForAndDisplayError();
    }

    private void clearBuffers() {
        gl.glBindFramebuffer(GL.GL_FRAMEBUFFER, 0);
        gl.glBindRenderbuffer(GL.GL_RENDERBUFFER, 0);
        gl.glBindTexture(GL.GL_TEXTURE_2D, 0);
    }

    private void makeRenderBuffer() {
        IntBuffer ib = makeSingleIntBuffer();
        gl.glGenRenderbuffers(1, ib);
        int renderBuffer = ib.get(0);
        checkForAndDisplayError();

        gl.glBindRenderbuffer(GL.GL_RENDERBUFFER, renderBuffer);
        gl.glRenderbufferStorage(
            GL.GL_RENDERBUFFER,
            GL.GL_DEPTH_COMPONENT24,
            size,
            size);
        gl.glFramebufferRenderbuffer(
            GL.GL_FRAMEBUFFER,
            GL.GL_DEPTH_ATTACHMENT,
            GL.GL_RENDERBUFFER,
            renderBuffer);
        checkForAndDisplayError();

        gl.glTexParameteri(
            GL.GL_TEXTURE_2D,
            GL.GL_TEXTURE_MIN_FILTER,
            GL.GL_LINEAR);
        checkForAndDisplayError();
    }

    private void makeFramebuffer(int textureId) {
        gl.glBindFramebuffer(gl.GL_FRAMEBUFFER, id);
        checkForAndDisplayError();

        // Ensure the framebuffer stretches smoothly if placed on a large area,
        // instead of using a direct pixel NEAREST filter
        gl.glTexParameteri(
            gl.GL_TEXTURE_2D,
            gl.GL_TEXTURE_MIN_FILTER,
            gl.GL_LINEAR);
        gl.glTexParameteri(
            gl.GL_TEXTURE_2D,
            gl.GL_TEXTURE_MAG_FILTER,
            gl.GL_LINEAR);

        gl.glTexImage2D(
            GL.GL_TEXTURE_2D,
            0,
            GL.GL_RGBA8,
            size,
            size,
            0,
            GL.GL_RGBA,
            GL.GL_FLOAT,
            null);
        checkForAndDisplayError();

        gl.glFramebufferTexture2D(
            gl.GL_FRAMEBUFFER,
            gl.GL_COLOR_ATTACHMENT0,
            gl.GL_TEXTURE_2D,
            textureId,
            0);
        checkForAndDisplayError();
    }

    public int id() {
        return id;
    }

    private IntBuffer makeSingleIntBuffer() {
        return IntBuffer.wrap(new int[1]);
    }

    private void checkForAndDisplayError() {
        final int errorNum = gl.glGetError();
        if (errorNum != GL.GL_NO_ERROR) {
            System.out.format(
                "GL error code #%d, '%s'.\n",
                errorNum,
                glu.gluErrorString(errorNum));
        }
    }

}
