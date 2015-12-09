package scenegraph.models.robot;

import com.jogamp.opengl.GL2;
import math.Vector3;
import renderer.Materials;
import renderer.primitives.Plane;
import renderer.primitives.TexturedCube;
import scenegraph.SceneGraph;
import scenegraph.SceneGraphNode;
import scenegraph.models.Toggleable;

public class ChestLight extends SceneGraph implements Toggleable {
    private SceneGraphNode chestLight;

    public ChestLight(GL2 gl) {
        super(new SceneGraphNode(gl));

        SceneGraphNode twister = root.createAttachedNode();

        twister
            .createAttachedNode()
            .attachRenderable(
                new TexturedCube(gl, Materials.get().get("shinymetal")))
            .setPosition(new Vector3(-0.5f, -0.31f, -0.1f))
            .setScaling(new Vector3(1, 0.5f, 1));

        chestLight = twister
            .createAttachedNode()
            .attachRenderable(new Plane(gl, Materials.get().get("chest_light")))
            .setRotation(new Vector3(1, 0, 0), 90)
            .setPosition(new Vector3(-1, -0.6f, -0.6f));
        Robot.CHEST_LIGHT = this;

        twister.setRotation(new Vector3(0, 1, 0), 90);
        twister.setPosition(new Vector3(-0.5f, 0, -0.5f));
    }

    @Override
    public void update() {
    }

    @Override
    public void setIsOn(boolean isOn) {
        if (isOn) {
            chestLight.unhide();
        } else {
            chestLight.hide();
        }
    }
}
