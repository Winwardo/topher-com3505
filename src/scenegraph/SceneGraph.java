package scenegraph;

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
}
