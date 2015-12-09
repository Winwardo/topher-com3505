package animation;

import java.util.ArrayList;
import java.util.List;

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
    private boolean               isPaused = false;

    public Animations() {
        this.animations = new ArrayList<>();
    }

    public int append(Animation animation) {
        animations.add(animation);
        return animations.size() - 1;
    }

    public Animation get(int animationId) {
        return animations.get(animationId);
    }

    public void tick() {
        if (!isPaused) {
            for (Animation animation : animations) {
                animation.tick();
            }
        }
    }

    public void setPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void restartAll() {
        for (Animation animation : animations) {
            animation.restart();
        }
    }
}
