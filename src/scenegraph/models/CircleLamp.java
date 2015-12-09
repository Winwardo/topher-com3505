package scenegraph.models;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import lighting.Light;
import math.Vector3;
import renderer.Materials;
import renderer.primitives.Cylinder;
import renderer.primitives.Sphere;
import renderer.primitives.WireSphere;
import scenegraph.SceneGraph;
import scenegraph.SceneGraphNode;

public class CircleLamp extends SceneGraph {
    private GL2  gl;
    private GLUT glut;

    public CircleLamp(GL2 gl, GLUT glut, Light light) {
        super(new SceneGraphNode(gl));
        this.gl = gl;
        this.glut = glut;

        root.attachLight(light);

        root.createAttachedNode().attachRenderable(
            new Sphere(gl, 1, Materials.get().get("white")));

        root
            .createAttachedNode()
            .attachRenderable(
                new WireSphere(gl, 1.05f, Materials.get().get("shinymetal")))
            .setRotation(new Vector3(1, 0, 0), 90);

        root
            .createAttachedNode()
            .attachRenderable(
                new Cylinder(gl, glut, 0.04f, 3, Materials.get().get("black")))
            .setRotation(new Vector3(1, 0, 0), 270);

    }

    @Override
    public void update() {
    }
}
