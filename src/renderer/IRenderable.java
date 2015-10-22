package renderer;

import java.util.List;

public interface IRenderable {
    public void render();

    public List<IRenderable> children();
}
