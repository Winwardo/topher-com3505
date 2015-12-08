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

public class HangingLight extends SceneGraph {
    private GL2 gl;

    public HangingLight(GL2 gl, GLUT glut, ILight light) {
        super(new SceneGraphNode(gl));
        this.gl = gl;

        root.attachLight(light);

        root
            .createAttachedNode()
            .attachRenderable(
                new Sphere(gl, 0.6f, Materials.get().get("white")))
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
}
