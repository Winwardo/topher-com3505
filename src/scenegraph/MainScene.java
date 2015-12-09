package scenegraph;

import com.jogamp.opengl.GL2;
import scenegraph.models.room.Room;

public class MainScene extends SceneGraph {
    private final Room room;

    public MainScene(GL2 gl) {
        super(new SceneGraphNode(gl));

        room = new Room(gl);
        root.createAttachedNodeFromSceneGraph(room);
    }

    @Override
    public void update() {
        room.update();
    }
}
