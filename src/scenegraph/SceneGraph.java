package scenegraph;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import renderer.IRenderable;

public abstract class SceneGraph {
    protected final SceneGraphNode root;

    public SceneGraph(SceneGraphNode root) {
        this.root = root;
    }

    public void render() {
        root.render();
    }

    public SceneGraphNode root() {
        return root;
    }

    public DefaultMutableTreeNode createSceneGraphTree() {
        DefaultMutableTreeNode result = new DefaultMutableTreeNode();
        result.add(getCurrentAndChildren(root));
        return result;
    }

    public abstract void update();

    private MutableTreeNode getCurrentAndChildren(IRenderable renderable) {
        DefaultMutableTreeNode result = new DefaultMutableTreeNode(renderable);

        for (IRenderable child : renderable.children()) {
            result.add(getCurrentAndChildren(child));
        }

        return result;
    }
}
