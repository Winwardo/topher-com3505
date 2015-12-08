package scenegraph.models;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import lighting.ILight;
import math.Vector3;
import renderer.Materials;
import renderer.primitives.Cylinder;
import renderer.primitives.Sphere;
import renderer.primitives.WireSphere;
import scenegraph.SceneGraph;
import scenegraph.SceneGraphNode;

public class HangingLight extends SceneGraph implements Toggleable {
    private GL2                  gl;

    private final SceneGraphNode dullSphere;
    private final SceneGraphNode litSphere;

    public HangingLight(GL2 gl, GLUT glut, ILight spotLight,
        ILight ambientLight) {
        super(new SceneGraphNode(gl));
        this.gl = gl;

        // Attach spotlight
        root.createAttachedNode().attachLight(spotLight).setPosition(
            new Vector3(0, 0, 0));

        // Attach ambient light just below
        root.createAttachedNode().attachLight(ambientLight).setPosition(
            new Vector3(0, -3, 0));

        dullSphere = root
            .createAttachedNode()
            .attachRenderable(
                new Sphere(gl, 0.6f, Materials.get().get("dullwhite")))
            .setPosition(new Vector3(0, -.05f, 0));
        dullSphere.hide();

        litSphere = root
            .createAttachedNode()
            .attachRenderable(
                new Sphere(gl, 0.6f, Materials.get().get("brightwhite")))
            .setPosition(new Vector3(0, -.05f, 0));

        root
            .createAttachedNode()
            .attachRenderable(
                new WireSphere(gl, 0.7f, Materials.get().get("shinymetal")))
            .setPosition(new Vector3(0, -.05f, 0))
            .setRotation(new Vector3(1, 0, 0), 90);

        root
            .createAttachedNode()
            .attachRenderable(
                new Cylinder(
                    gl,
                    glut,
                    0.04f,
                    3,
                    Materials.get().get("shinymetal")))
            .setRotation(new Vector3(1, 0, 0), 270);

        root
            .createAttachedNode()
            .attachRenderable(
                new Cylinder(
                    gl,
                    glut,
                    1,
                    0.04f,
                    1,
                    Materials.get().get("shinymetal")))
            .setRotation(new Vector3(1, 0, 0), 270);
    }

    @Override
    public void update() {
    }

    @Override
    public void setIsOn(boolean isOn) {
        if (isOn) {
            dullSphere.hide();
            litSphere.unhide();
        } else {
            dullSphere.unhide();
            litSphere.hide();
        }
    }
}
