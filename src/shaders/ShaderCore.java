/* I declare that this code is my own work. Inspired and helped by http://jogamp.org/jogl-demos/src/demos/es2/RawGL2ES2demo.java */
/* Topher Winward, 120134353, crwinward1@sheffield.ac.uk */
package shaders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES2;

public class ShaderCore {
    public static String MAX_LIGHTS   = "8";

    private final GL2    gl;
    private int          queuedShader = -1;

    public ShaderCore(GL2 gl) {
        this.gl = gl;
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

        checkThatShaderCompiledCorrectly(shader);

        return shader;
    }

    public int createFragmentShader(String fragment) {
        return createShader(fragment, GL2ES2.GL_FRAGMENT_SHADER);
    }

    public int createVertexShader(String vertex) {
        return createShader(vertex, GL2ES2.GL_VERTEX_SHADER);
    }

    private void checkThatShaderCompiledCorrectly(int shader) {
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

    public int setupShaders(String shaderName, String[] vertexParams,
        String[] fragmentParams) {
        String vertexLocation = "res\\" + shaderName + ".vs";
        String fragmentLocation = "res\\" + shaderName + ".fs";

        if (vertexParams == null) {
            vertexParams = new String[0];
        }
        if (fragmentParams == null) {
            fragmentParams = new String[0];
        }

        try {
            byte[] fileBytesVertex = Files
                .readAllBytes(Paths.get(vertexLocation));
            String vertexInfoRaw = new String(fileBytesVertex);
            String vertexInfo = String.format(vertexInfoRaw, vertexParams);

            byte[] fileBytesFragment = Files
                .readAllBytes(Paths.get(fragmentLocation));
            String fragmentInfoRaw = new String(fileBytesFragment);
            String fragmentInfo = String
                .format(fragmentInfoRaw, fragmentParams);

            return generateShader(fragmentInfo, vertexInfo);
        } catch (IOException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public int setupShaders(String shaderName) {
        return setupShaders(shaderName, null, null);
    }

    private int generateShader(String fragment, String vertex) {
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

    public int loadShaders() {
        setupShaders("phong", null, new String[] { MAX_LIGHTS, "ALBEDO" });
        setupShaders("phong", null, new String[] { MAX_LIGHTS, "AMBIENT" });
        setupShaders("phong", null, new String[] { MAX_LIGHTS, "DIFFUSE" });
        setupShaders("phong", null, new String[] { MAX_LIGHTS, "SPECULAR" });
        int all = setupShaders(
            "phong",
            null,
            new String[] { MAX_LIGHTS, "ALL" });

        queueShader(all);
        return all;
    }
}
