package renderer;

import javax.swing.tree.DefaultMutableTreeNode;

public abstract class Renderable implements IRenderable {

    @Override
    public void insertIntoSceneGraphTree(
        DefaultMutableTreeNode sceneGraphTree) {
        sceneGraphTree.add(new DefaultMutableTreeNode(this));
    }

}
