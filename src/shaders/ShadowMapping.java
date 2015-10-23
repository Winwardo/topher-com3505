package shaders;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;

public class ShadowMapping {
    private final GL2 gl;
    private final GLU glu;

    private int       shadowMap;
    private final int shadowMapSize = 512;

    public ShadowMapping(GL2 gl, GLU glu) {
        this.gl = gl;
        this.glu = glu;

        gl.glBindTexture(gl.GL_TEXTURE_2D, shadowMap);
        gl.glTexImage2D(
            gl.GL_TEXTURE_2D,
            0,
            gl.GL_DEPTH_COMPONENT,
            shadowMapSize,
            shadowMapSize,
            0,
            gl.GL_DEPTH_COMPONENT,
            gl.GL_UNSIGNED_BYTE,
            null);
        gl.glTexParameteri(
            gl.GL_TEXTURE_2D,
            gl.GL_TEXTURE_MIN_FILTER,
            gl.GL_LINEAR);
        gl.glTexParameteri(
            gl.GL_TEXTURE_2D,
            gl.GL_TEXTURE_MAG_FILTER,
            gl.GL_LINEAR);
        gl.glTexParameteri(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_WRAP_S, gl.GL_CLAMP);
        gl.glTexParameteri(gl.GL_TEXTURE_2D, gl.GL_TEXTURE_WRAP_T, gl.GL_CLAMP);

    }
}
