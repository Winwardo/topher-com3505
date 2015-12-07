package animation;

import java.util.ArrayList;
import java.util.List;
import com.jogamp.opengl.GL2;

/**
 * Animations provides a Singleton access to save on programming costs.
 * Preferably a service locator would be used, injected into the model
 * primitives via some factory, but that's beyond the scope of this assignment.
 *
 *
 *
 * @author Topher
 *
 */
public class Animations {
    private static Animations _animations;

    public static void setGlobal(Animations animations) {
        Animations._animations = animations;
    }

    public static Animations get() {
        if (_animations != null) {
            return _animations;
        } else {
            throw new RuntimeException("Animations has not been set yet.");
        }
    }

    private final List<Animation> animations;

    public Animations(GL2 gl) {
        this.animations = new ArrayList<>();
    }

    public int append(Animation animation) {
        animations.add(animation);
        return animations.size() - 1;
    }

    public Animation get(int animationId) {
        return animations.get(animationId);
    }
}
