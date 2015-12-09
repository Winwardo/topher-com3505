package animation;

import java.util.ArrayList;
import java.util.List;
import math.Vector3;

/* I declare that this code is my own work */
/* Topher Winward, 120134353, crwinward1@sheffield.ac.uk */
/**
 * Animations provides a Singleton access to save on programming costs.
 * Preferably a service locator would be used, injected into the model
 * primitives via some factory, but that's beyond the scope of this assignment.
 *
 * Animations holds a reference to many Animation objects, and can tick / pause
 * / play / restart all of them at once.
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

    public void createAnimations() {
        append(makeRobotMainAnimation());
        append(makeRobotArmAnimation());
        append(makeRobotClawAnimation());
    }

    private Animation makeRobotArmAnimation() {
        ArrayList<Keyframe> frames = new ArrayList<>();

        frames.add(makeArmKeyframe(0, 280));
        frames.add(makeArmKeyframe(-70, 80));
        frames.add(makeArmKeyframe(-70, 120));
        frames.add(makeArmKeyframe(0, 80));
        frames.add(makeArmKeyframe(0, 100));
        frames.add(makeArmKeyframe(-70, 100));
        frames.add(makeArmKeyframe(0, 100));
        frames.add(makeArmKeyframe(0, 800));
        frames.add(makeArmKeyframe(-70, 100));
        frames.add(makeArmKeyframe(-70, 20));
        frames.add(makeArmKeyframe(0, 180));
        frames.add(makeArmKeyframe(0, 240));

        return new Animation(frames);
    }

    private Animation makeRobotClawAnimation() {
        ArrayList<Keyframe> frames = new ArrayList<>();

        frames.add(makeClawKeyframe(0, 280));
        frames.add(makeClawKeyframe(-70, 80));
        frames.add(makeClawKeyframe(-70, 120));
        frames.add(makeClawKeyframe(0, 80));
        frames.add(makeClawKeyframe(0, 100));
        frames.add(makeClawKeyframe(-70, 100));
        frames.add(makeClawKeyframe(0, 100));
        frames.add(makeClawKeyframe(0, 800));
        frames.add(makeClawKeyframe(-70, 100));
        frames.add(makeClawKeyframe(-70, 20));
        frames.add(makeClawKeyframe(0, 180));
        frames.add(makeClawKeyframe(0, 240));

        return new Animation(frames);
    }

    private Keyframe makeArmKeyframe(float angle, int duration) {
        return new Keyframe(
            Vector3.zero(),
            new Vector3(0, 0, 1),
            angle,
            Vector3.one(),
            duration);
    }

    private Keyframe makeClawKeyframe(float angle, int duration) {
        return new Keyframe(
            Vector3.zero(),
            new Vector3(0, 1, 0),
            angle,
            Vector3.one(),
            duration);
    }

    private Animation makeRobotMainAnimation() {
        ArrayList<Keyframe> frames = new ArrayList<>();
        frames.add(new Keyframe(new int[] { 25, 0, 7, -90, 100 }));
        frames.add(new Keyframe(new int[] { 25, 0, 7, -140, 100 }));
        frames.add(new Keyframe(new int[] { 14, 0, 12, -140, 100 }));
        frames.add(new Keyframe(new int[] { 14, 0, 12, -200, 100 }));
        frames.add(new Keyframe(new int[] { 14, 0, 12, -200, 100 }));
        frames.add(new Keyframe(new int[] { 14, 0, 12, -90, 100 }));
        frames.add(new Keyframe(new int[] { 15, 0, 25, -80, 100 }));
        frames.add(new Keyframe(new int[] { 15, 0, 25, -80, 100 }));
        frames.add(new Keyframe(new int[] { 15, 0, 25, 10, 100 }));
        frames.add(new Keyframe(new int[] { 30, 0, 23, 10, 100 }));
        frames.add(new Keyframe(new int[] { 30, 0, 23, -60, 100 }));
        frames.add(new Keyframe(new int[] { 32, 0, 26, 0, 100 }));
        frames.add(new Keyframe(new int[] { 41, 0, 25, 40, 100 }));
        frames.add(new Keyframe(new int[] { 41, 0, 24, 90, 100 }));
        frames.add(new Keyframe(new int[] { 41, 0, 13, 100, 100 }));
        frames.add(new Keyframe(new int[] { 41, 0, 13, 138, 100 }));
        frames.add(new Keyframe(new int[] { 35, 0, 10, 140, 100 }));
        frames.add(new Keyframe(new int[] { 35, 0, 10, 90, 100 }));
        frames.add(new Keyframe(new int[] { 35, 0, 10, 170, 100 }));
        frames.add(new Keyframe(new int[] { 25, 0, 8, 150, 100 }));
        frames.add(new Keyframe(new int[] { 25, 0, 7, -90, 200 }));
        return new Animation(frames);
    }
}
