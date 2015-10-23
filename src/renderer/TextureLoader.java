package renderer;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

/**
 * TextureLoader provides a Singleton access to save on programming costs.
 * Preferably a service locator would be used, injected into the model
 * primitives via some factory, but that's beyond the scope of this assignment.
 *
 *
 * https://stackoverflow.com/questions/2109046/texture-loading-at-jogl
 * 
 * @author Topher
 *
 */
public class TextureLoader {
    private static TextureLoader textureLoader;

    public static void setGlobal(TextureLoader textureLoader) {
        TextureLoader.textureLoader = textureLoader;
    }

    public static TextureLoader get() {
        if (textureLoader != null) {
            return textureLoader;
        } else {
            throw new RuntimeException("TextureLoader has not been set yet.");
        }
    }

    private GL2       gl;
    private final int BMPtexCount  = 30;
    private int       textureCount = 0;
    private int       textures[]   = new int[BMPtexCount];

    public TextureLoader(GL2 gl) {
        this.gl = gl;
    }

    public int loadBMP(String filename) {
        return loadBMP(filename, 256, 256);
    }

    public int loadBMP(String filename, int width, int height) {
        try {
            DataInputStream dataStream = new DataInputStream(
                new FileInputStream(filename));
            dataStream.skipBytes(18 + 2 + 28); // why?

            byte buf2[] = new byte[dataStream.available()];

            dataStream.read(buf2);
            dataStream.close();

            int index = textureCount;
            textureCount++;

            ByteBuffer buf = ByteBuffer.wrap(buf2);

            gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);

            gl.glTexImage2D(
                GL.GL_TEXTURE_2D,
                0,
                3,
                width,
                height,
                0,
                GL.GL_BGR,
                GL.GL_UNSIGNED_BYTE,
                buf);

            gl.glTexParameteri(
                GL.GL_TEXTURE_2D,
                GL.GL_TEXTURE_MAG_FILTER,
                GL.GL_LINEAR);
            gl.glTexParameteri(
                GL.GL_TEXTURE_2D,
                GL.GL_TEXTURE_MIN_FILTER,
                GL.GL_LINEAR);

            return index;
        } catch (IOException ex) {
            // default to white
            ex.printStackTrace();
            return 0;
        }
    }
}
