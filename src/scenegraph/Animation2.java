package scenegraph;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;
import scenegraph.models.room.Room;

public class Animation2 extends SceneGraph {
    private final Room room;

    public Animation2(GL2 gl, GLUT glut) {
        super(new SceneGraphNode(gl));

        room = new Room(gl, glut);
        root.createAttachedNodeFromSceneGraph(room);
    }

    @Override
    public void update() {
        room.update();
    }
}
