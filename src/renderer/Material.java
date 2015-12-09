/* I declare that this code is my own work */
/* Topher Winward, 120134353, crwinward1@sheffield.ac.uk */
package renderer;

import com.jogamp.opengl.GL2;

/**
 * Contains ambient, diffuse, specular, shininess and image data, with a
 * convenience binding function.
 * 
 * @author Topher
 *
 */
public class Material {
    private final GL2     gl;

    private final float[] ambient;
    private final float[] diffuse;
    private final float[] specular;
    private final float[] shininess;
    private final int     textureNum;

    public Material(GL2 gl, float[] ambient, float[] diffuse, float[] specular,
        float[] shininess, String textureName) {
        this.gl = gl;

        this.ambient = ambient;
        this.diffuse = diffuse;
        this.specular = specular;
        this.shininess = shininess;
        this.textureNum = TextureLoader.get().get(textureName);
    }

    public Material(GL2 gl, String textureName) {
        this(
            gl,
            new float[] { 1, 1, 1, 1 },
            new float[] { 1, 1, 1, 1 },
            new float[] { 1, 1, 1, 1 },
            new float[] { 100 },
            textureName);
    }

    public static Material empty(GL2 gl) {
        return new Material(gl, "default");
    }

    public void bind() {
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, ambient, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, diffuse, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, specular, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, shininess, 0);

        gl.glBindTexture(gl.GL_TEXTURE_2D, textureNum);
    }
}
