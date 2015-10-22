package scenegraph;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import renderer.IRenderable;

public class SceneGraph {
    private final Node root;

    public SceneGraph(Node root) {
        this.root = root;
    }

    public void render() {
        root.render();
    }

    public Node root() {
        return root;
    }

    public DefaultMutableTreeNode createSceneGraphTree() {
        DefaultMutableTreeNode result = new DefaultMutableTreeNode();
        result.add(getCurrentAndChildren(root));
        return result;
    }

    private MutableTreeNode getCurrentAndChildren(IRenderable renderable) {
        DefaultMutableTreeNode result = new DefaultMutableTreeNode(renderable);

        for (IRenderable child : renderable.children()) {
            result.add(getCurrentAndChildren(child));
        }

        return result;
    }
}
