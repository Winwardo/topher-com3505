package shaders;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES2;

public class ShaderCore {
    private final GL2 gl;
    private int       queuedShader = -1;

    public ShaderCore(GL2 gl) {
        this.gl = gl;
    }

    public int setupShaders(String fragment, String vertex) {
        int fragShader = createFragmentShader(fragment);
        int vertShader = createVertexShader(vertex);

        int shaderProgram = gl.glCreateProgram();
        gl.glAttachShader(shaderProgram, vertShader);
        gl.glAttachShader(shaderProgram, fragShader);

        gl.glLinkProgram(shaderProgram);

        gl.glDetachShader(shaderProgram, vertShader);
        gl.glDetachShader(shaderProgram, fragShader);

        gl.glDeleteShader(shaderProgram);
        return shaderProgram;
    }

    private void useShader(int shaderProgram) {
        gl.glUseProgram(shaderProgram);

        int error = gl.glGetError();
        if (error > 0) {
            System.err.format(
                "Shader error, GL#%d. Most likely a missing shader number `%d`.\n",
                error,
                shaderProgram);
        }
    }

    private int createShader(String source, int type) {
        int shader = gl.glCreateShader(type);

        gl.glShaderSource(
            shader,
            1,
            new String[] { source },
            new int[] { source.length() },
            0);
        gl.glCompileShader(shader);

        assertCompiled(shader);

        return shader;
    }

    public int createFragmentShader(String fragment) {
        return createShader(fragment, GL2ES2.GL_FRAGMENT_SHADER);
    }

    public int createVertexShader(String vertex) {
        return createShader(vertex, GL2ES2.GL_VERTEX_SHADER);
    }

    private void assertCompiled(int shader) {
        int[] compiledStatus = { 0 };
        gl.glGetShaderiv(shader, GL2ES2.GL_COMPILE_STATUS, compiledStatus, 0);

        if (compiledStatus[0] == 0) {
            int[] infoLogLength = { 0 };

            gl.glGetShaderiv(
                shader,
                GL2ES2.GL_INFO_LOG_LENGTH,
                infoLogLength,
                0);

            byte[] shaderInfoLog = new byte[infoLogLength[0]];
            gl.glGetShaderInfoLog(
                shader,
                infoLogLength[0],
                (int[]) null,
                0,
                shaderInfoLog,
                0);

            System.err.format(
                "Shader failed to compile: %s\n",
                new String(shaderInfoLog));
            System.exit(1);
        }
    }

    public void queueShader(int shader) {
        queuedShader = shader;
    }

    public void useQueuedShader() {
        if (queuedShader > -1) {
            useShader(queuedShader);
            queuedShader = -1;
        }
    }
}
