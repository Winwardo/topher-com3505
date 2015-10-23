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

        SceneGraphNode head = root
            .createAttachedNodeFromSceneGraph(new Head(gl, glut));
        head.setPosition(new Vector3(0, 3, 0));

        SceneGraphNode body = root
            .createAttachedNodeFromSceneGraph(new Body(gl, glut));
        body.setPosition(new Vector3(0, 2.3f, 0));

        roller = new Roller(gl, glut);
        root.createAttachedNodeFromSceneGraph(roller);
    }

    @Override
    public void update() {
        roller.update();
    }
}
