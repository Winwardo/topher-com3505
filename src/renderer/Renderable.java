package renderer;

import java.util.Collections;
import java.util.List;

public abstract class Renderable implements IRenderable {

    @Override
    public List<IRenderable> children() {
        return Collections.emptyList();
    }
}
