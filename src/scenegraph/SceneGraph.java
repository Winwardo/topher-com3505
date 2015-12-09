/* I declare that this code is my own work */
/* Topher Winward, 120134353, crwinward1@sheffield.ac.uk */
package scenegraph;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import lighting.Light;
import renderer.IRenderable;

/**
 * A wrapper class for holding a root SceneGraphNode. The constructor is used to
 * build up a tree of nodes, lights and renderables.
 * 
 * @author Topher
 *
 */
public abstract class SceneGraph {
    protected final SceneGraphNode root;

    public SceneGraph(SceneGraphNode root) {
        this.root = root;
    }

    public void render() {
        root.applyLights();
        root.render();
    }

    public SceneGraphNode root() {
        return root;
    }

    /**
     * Use to generate a tree for a JTree class for display on a JFrame GUI
     *
     */
    public DefaultMutableTreeNode createSceneGraphTree() {
        DefaultMutableTreeNode result = new DefaultMutableTreeNode();
        result.add(getCurrentAndChildren(root));
        return result;
    }

    private MutableTreeNode getCurrentAndChildren(SceneGraphNode node) {
        DefaultMutableTreeNode result = new DefaultMutableTreeNode(node);

        for (SceneGraphNode child : node.children()) {
            result.add(getCurrentAndChildren(child));
        }

        for (Light light : node.lights()) {
            result.add(new DefaultMutableTreeNode(light));
        }

        for (IRenderable renderable : node.renderables()) {
            result.add(new DefaultMutableTreeNode(renderable));
        }

        return result;
    }

    public abstract void update();
}
