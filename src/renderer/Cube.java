package renderer;

import com.jogamp.opengl.util.gl2.GLUT;

public class Cube implements Renderable {
    private GLUT  glut;
    private float size;

    public Cube(GLUT glut, float size) {
        super();
        this.glut = glut;
        this.size = size;
    }

    @Override
    public void render() {
        glut.glutWireCube(size);
    }
}
