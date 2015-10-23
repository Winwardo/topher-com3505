package renderer.primitives;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import renderer.Renderable;

public class TexturedCube extends Renderable {
    private GLUT  glut;
    private float size;

    public static int currentTextureID = 0;

    public TexturedCube(GL2 gl, GLUT glut) {
        super(gl);
        this.glut = glut;

        // currentTextureID = TextureLoader.get().loadBMP(
        // "C:\\Users\\Topher\\Documents\\GitHub\\topher-com3505\\res\\texture.bmp");
    }

    @Override
    public void renderImpl() {
        gl.glColor3d(1, 1, 1);

        float[] matAmbient = { 0.25f, 0.25f, 0.25f, 1.0f };
        float[] matDiffuse = { 0.5f, 0.5f, 0.5f, 1.0f };
        float[] matSpecular = { 1.0f, 1.0f, 1.0f, 1.0f };
        float[] matShininess = { 2.0f };
        float[] matEmission = { 0.0f, 0.0f, 0.0f, 1.0f };
        // use glMaterialfv. There is no glMaterialdv
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, matAmbient, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, matDiffuse, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, matSpecular, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SHININESS, matShininess, 0);

        // glut.glutSolidCube(1);

        gl.glPushMatrix();
        {
            gl.glScalef(0.5f, 0.5f, 0.5f);
            gl.glBegin(gl.GL_QUADS);
            // Front Face
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(-1.0f, -1.0f, 1.0f);
            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex3f(1.0f, -1.0f, 1.0f);
            gl.glTexCoord2f(1.0f, 1.0f);
            gl.glVertex3f(1.0f, 1.0f, 1.0f);
            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex3f(-1.0f, 1.0f, 1.0f);
            // Back Face
            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex3f(-1.0f, -1.0f, -1.0f);
            gl.glTexCoord2f(1.0f, 1.0f);
            gl.glVertex3f(-1.0f, 1.0f, -1.0f);
            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex3f(1.0f, 1.0f, -1.0f);
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(1.0f, -1.0f, -1.0f);
            // Top Face
            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex3f(-1.0f, 1.0f, -1.0f);
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(-1.0f, 1.0f, 1.0f);
            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex3f(1.0f, 1.0f, 1.0f);
            gl.glTexCoord2f(1.0f, 1.0f);
            gl.glVertex3f(1.0f, 1.0f, -1.0f);
            // Bottom Face
            gl.glTexCoord2f(1.0f, 1.0f);
            gl.glVertex3f(-1.0f, -1.0f, -1.0f);
            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex3f(1.0f, -1.0f, -1.0f);
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(1.0f, -1.0f, 1.0f);
            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex3f(-1.0f, -1.0f, 1.0f);
            // Right face
            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex3f(1.0f, -1.0f, -1.0f);
            gl.glTexCoord2f(1.0f, 1.0f);
            gl.glVertex3f(1.0f, 1.0f, -1.0f);
            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex3f(1.0f, 1.0f, 1.0f);
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(1.0f, -1.0f, 1.0f);
            // Left Face
            gl.glTexCoord2f(0.0f, 0.0f);
            gl.glVertex3f(-1.0f, -1.0f, -1.0f);
            gl.glTexCoord2f(1.0f, 0.0f);
            gl.glVertex3f(-1.0f, -1.0f, 1.0f);
            gl.glTexCoord2f(1.0f, 1.0f);
            gl.glVertex3f(-1.0f, 1.0f, 1.0f);
            gl.glTexCoord2f(0.0f, 1.0f);
            gl.glVertex3f(-1.0f, 1.0f, -1.0f);
            gl.glEnd();
        }
        gl.glPopMatrix();
    }
}
