package renderer;

import java.io.IOException;
import java.nio.ByteBuffer;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import main.IOStream;

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

    private GL2 gl;
    private int BMPtexCount   = 30;
    private int BMPtextures[] = new int[BMPtexCount];

    public TextureLoader(GL2 gl) {
        this.gl = gl;
    }

    public int loadBMP(String filename) {
        try {
            IOStream wdis = new IOStream(filename);
            wdis.skipBytes(18);
            int width = wdis.readIntW();
            int height = wdis.readIntW();
            wdis.skipBytes(28);
            byte buf2[] = new byte[wdis.available()];
            // ByteBuffer buf = BufferUtil.newByteBuffer(wdis.available());

            wdis.read(buf2);
            wdis.close();

            int index = 0;

            ByteBuffer buf = ByteBuffer.wrap(buf2);

            gl.glBindTexture(GL.GL_TEXTURE_2D, BMPtextures[index]);

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

            // return currentTextureID;
        } catch (IOException ex) {
            // Utils.msgBox("File Error\n" + fileName, "Error", Utils.MSG_WARN);
            System.out.println("HOLY SHIT");
            // return -1;
        }
        return 0;
    }
}
