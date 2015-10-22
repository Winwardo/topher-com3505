package scenegraph.models.robot;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import math.Vector3;
import scenegraph.SceneGraph;
import scenegraph.SceneGraphNode;

public class Robot extends SceneGraph {
    private GL2  gl;
    private GLUT glut;

    private final SceneGraph roller;

    public Robot(GL2 gl, GLUT glut) {
        super(new SceneGraphNode(gl));
        this.gl = gl;
        this.glut = glut;

        SceneGraphNode head = new Head(gl, glut).root();
        head.setPosition(new Vector3(0, 3, 0));
        root.attachNode(head);

        SceneGraphNode body = new Body(gl, glut).root();
        body.setPosition(new Vector3(0, 2.25f, 0));
        root.attachNode(body);

        roller = new Roller(gl, glut);
        root.attachNode(roller.root());
    }

    @Override
    public void update() {
        roller.update();
    }
}
