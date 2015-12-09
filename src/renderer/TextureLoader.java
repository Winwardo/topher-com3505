/* I declare that this code is my own work, except the function loadImage */
/* Topher Winward, 120134353, crwinward1@sheffield.ac.uk */
package renderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import java.util.TreeMap;
import javax.imageio.ImageIO;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.awt.ImageUtil;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

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

    private GL2                        gl;
    private int                        textureCount = 0;
    private final Map<String, Integer> textures;

    public TextureLoader(GL2 gl) {
        this.gl = gl;
        this.textures = new TreeMap<>();
    }

    public int loadTexture(String textureName, String filename) {
        return loadTexture(textureName, filename, 256, 256);
    }

    public Texture loadImage(GL2 gl, String filename) {
        // This code is Steve Maddock's
        Texture tex = null;
        try {
            File f = new File(filename);
            BufferedImage img = ImageIO.read(f);
            ImageUtil.flipImageVertically(img);
            tex = AWTTextureIO.newTexture(GLProfile.getDefault(), img, false);

            tex.setTexParameteri(gl, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
            tex.setTexParameteri(gl, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
            tex.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
            tex.setTexParameteri(gl, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
        } catch (Exception e) {
            System.out.println("Error loading texture " + filename);
        }
        return tex;
    }

    public int loadTexture(String textureName, String filename, int width,
        int height) {
        Texture tex = loadImage(gl, filename);
        int index = tex.getTextureObject();

        this.textures.put(textureName, index);
        textureCount++;

        System.out.format(
            "Loaded texture `%s` successfully. Bound to texture #%d.\n",
            textureName,
            index);

        return index;
    }

    public int get(String textureName) {
        final Integer texture = textures.get(textureName);
        if (texture == null) {
            System.out.println(
                "Tried to load non-existent texture. Using default texture.");
            final Integer defaultTexture = textures.get("default");
            if (defaultTexture == null) {
                System.err.println(
                    "Could not load default texture. Using whatever other texture is available.");
                return 0;
            }
            return defaultTexture;
        }
        return texture;
    }

    public void loadTextures() {
        loadTexture("default", "res\\purple.bmp");
        loadTexture("white", "res\\white.bmp");
        loadTexture("rendertex", "res\\white.bmp");
        loadTexture("metal", "res\\metal.bmp");
        loadTexture("nyan", "res\\texture2.bmp");
        loadTexture("white", "res\\white.bmp");
        loadTexture("black", "res\\black.bmp");
        loadTexture("eye_right", "res\\eye_right.bmp");
        loadTexture("eye_left", "res\\eye_left.bmp");
        loadTexture("hardwood", "res\\hardwood.bmp");
        loadTexture("wood2", "res\\wood2.bmp");
        loadTexture("glass", "res\\glass.bmp");
        loadTexture("plate", "res\\plate.bmp");
        loadTexture("tiles", "res\\marbletile.bmp");
        loadTexture("marble", "res\\marble.bmp");
        loadTexture("rug", "res\\carpet.bmp", 512, 512);
        loadTexture("red_wall", "res\\red_wall.bmp");
        loadTexture("chest_light", "res\\chest_light.bmp");
        loadTexture("white_noise", "res\\white_noise.bmp");
    }
}
