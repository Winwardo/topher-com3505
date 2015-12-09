/* I declare that this code is my own work */
/* Topher Winward, 120134353, crwinward1@sheffield.ac.uk */
package renderer.primitives;

import com.jogamp.opengl.GL2;
import math.Vector3;
import renderer.DisplayList;
import renderer.Material;
import renderer.Renderable;

public class Cuboid extends Renderable {
    private DisplayList displayList;

    public Cuboid(GL2 gl, Vector3 scale, Material mat) {
        this(gl, scale, mat, 1, 1, 1, 1, 1, 1);
    }

    public Cuboid(GL2 gl, Vector3 scale, Material mat, int xSubdivides,
        int ySubdivides, int zSubdivides, float xTexRepeats, float yTexRepeats,
        float zTexRepeats) {
        super(gl, mat);

        Plane leftRightPlane = new Plane(
            gl,
            mat,
            xSubdivides,
            ySubdivides,
            xTexRepeats,
            yTexRepeats);
        Plane frontBackPlane = new Plane(
            gl,
            mat,
            zSubdivides,
            ySubdivides,
            zTexRepeats,
            yTexRepeats);
        Plane topBottomPlane = new Plane(
            gl,
            mat,
            xSubdivides,
            zSubdivides,
            xTexRepeats,
            zTexRepeats);

        displayList = new DisplayList(gl, () -> {
            generateAndDraw(
                scale,
                leftRightPlane,
                frontBackPlane,
                topBottomPlane);
        });
    }

    public void generateAndDraw(Vector3 scale, Plane leftRightPlane,
        Plane frontBackPlane, Plane topBottomPlane) {
        // Draw each side of the cuboid, rotating the appropriate plane each
        // time

        //
        // Left
        gl.glPushMatrix();
        {
            gl.glTranslatef(scale.x() / 2, -scale.y() / 2, -scale.z() / 2);
            gl.glRotatef(180, 0, 1, 0);
            gl.glScaled(scale.x(), scale.y(), scale.z());
            leftRightPlane.render();
        }
        gl.glPopMatrix();

        // Right
        gl.glPushMatrix();
        {
            gl.glTranslatef(-scale.x() / 2, -scale.y() / 2, scale.z() / 2);
            gl.glRotatef(0, 0, 1, 0);
            gl.glScaled(scale.x(), scale.y(), scale.z());
            leftRightPlane.render();
        }
        gl.glPopMatrix();

        //
        // Front
        gl.glPushMatrix();
        {
            gl.glTranslatef(scale.x() / 2, -scale.y() / 2, scale.z() / 2);
            gl.glRotatef(90, 0, 1, 0);
            gl.glScaled(scale.z(), scale.y(), scale.x());
            frontBackPlane.render();
        }
        gl.glPopMatrix();

        // Back
        gl.glPushMatrix();
        {
            gl.glTranslatef(-scale.x() / 2, -scale.y() / 2, -scale.z() / 2);
            gl.glRotatef(270, 0, 1, 0);
            gl.glScaled(scale.z(), scale.y(), scale.x());
            leftRightPlane.render();
        }
        gl.glPopMatrix();

        //
        // Top
        gl.glPushMatrix();
        {
            gl.glTranslatef(-scale.x() / 2, scale.y() / 2, scale.z() / 2);
            gl.glRotatef(270, 1, 0, 0);
            gl.glScaled(scale.x(), scale.z(), scale.y());
            topBottomPlane.render();
        }
        gl.glPopMatrix();

        // Bottom
        gl.glPushMatrix();
        {
            gl.glTranslatef(-scale.x() / 2, -scale.y() / 2, -scale.z() / 2);
            gl.glRotatef(90, 1, 0, 0);
            gl.glScaled(scale.x(), scale.z(), scale.y());
            topBottomPlane.render();
        }
        gl.glPopMatrix();
    }

    @Override
    public void renderImpl() {
        displayList.call();
    }
}
