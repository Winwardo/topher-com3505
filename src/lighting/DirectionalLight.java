package lighting;

import com.jogamp.opengl.GL2;

public class DirectionalLight implements ILight {
    private final GL2 gl;

    public DirectionalLight(GL2 gl) {
        this.gl = gl;
    }

    @Override
    public void apply() {
        // System.out.println("light");
    }
}
