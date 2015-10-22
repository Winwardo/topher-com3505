package renderer;

import javax.swing.tree.DefaultMutableTreeNode;

public interface IRenderable {
    public void render();

    public void insertIntoSceneGraphTree(DefaultMutableTreeNode sceneGraphTree);
}
