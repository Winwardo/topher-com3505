package renderer.primitives;

import com.jogamp.opengl.GL2;
import renderer.DisplayList;
import renderer.Material;
import renderer.Renderable;

public class Plane extends Renderable {
    private final DisplayList displayList;

    public Plane(GL2 gl, Material mat, int xSubdivides, int ySubdivides,
        float xTexRepeats, float yTexRepeats) {
        super(gl, mat);

        displayList = new DisplayList(gl, () -> {
            generateAndDraw(xSubdivides, ySubdivides, xTexRepeats, yTexRepeats);
        });
    }

    public Plane(GL2 gl, Material mat) {
        this(gl, mat, 1, 1, 1, 1);
    }

    @Override
    protected void renderImpl() {
        displayList.call();
    }

    public void generateAndDraw(int xSubdivides, int ySubdivides,
        float xTexRepeats, float yTexRepeats) {
        float xStep = 1.0f / xSubdivides;
        float yStep = 1.0f / ySubdivides;

        float y = 0;

        for (int y_ = 0; y_ < ySubdivides; y_++) {
            float x = 0;

            for (int x_ = 0; x_ < xSubdivides; x_++) {
                float xVertexStart = x;
                float yVertexStart = y;
                float xVertexEnd = x + xStep;
                float yVertexEnd = y + yStep;

                float xTextureStep = xTexRepeats / xSubdivides;
                float xTextureStart = x_ * xTextureStep;
                float xTextureEnd = xTextureStart + xTextureStep;

                float yTextureStep = yTexRepeats / ySubdivides;
                float yTextureStart = y_ * yTextureStep;
                float yTextureEnd = yTextureStart + yTextureStep;

                gl.glBegin(gl.GL_QUADS);
                {
                    // Draw square
                    // Without setting the correct normal manually, the lighting
                    // calculations will be incorrect
                    gl.glNormal3f(0, 0, 1);

                    // a
                    gl.glTexCoord2f(xTextureStart, yTextureStart);
                    gl.glVertex3d(xVertexStart, yVertexStart, 0);

                    // b
                    gl.glTexCoord2f(xTextureEnd, yTextureStart);
                    gl.glVertex3d(xVertexEnd, yVertexStart, 0);

                    // c
                    gl.glTexCoord2f(xTextureEnd, yTextureEnd);
                    gl.glVertex3d(xVertexEnd, yVertexEnd, 0);

                    // d
                    gl.glTexCoord2f(xTextureStart, yTextureEnd);
                    gl.glVertex3d(xVertexStart, yVertexEnd, 0);
                }
                gl.glEnd();

                x += xStep;
            }

            y += yStep;
        }
    }
}
