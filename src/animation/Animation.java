package animation;

import java.util.List;
import math.LinearInterpolation;
import scenegraph.SceneGraphNode;

public class Animation {
    private final List<Keyframe> keyframes;

    private int                  currentKeyframeId;
    private Keyframe             lastKeyframe;
    private Keyframe             currentKeyframe;
    private int                  currentFrame;

    public Animation(List<Keyframe> keyframes) {
        this.keyframes = keyframes;

        this.currentKeyframeId = 0;
        this.currentKeyframe = keyframes.get(0);
        this.lastKeyframe = keyframes.get(keyframes.size() - 1);
        this.currentFrame = 0;
    }

    public void tick() {
        currentFrame++;

        if (currentFrame >= currentKeyframe.duration) {
            currentFrame = 0;

            currentKeyframeId++;
            if (currentKeyframeId >= keyframes.size()) {
                currentKeyframeId = 0;
            }

            lastKeyframe = currentKeyframe;
            currentKeyframe = keyframes.get(currentKeyframeId);
        }
    }

    public void applyExact(SceneGraphNode node) {
        node.setPosition(currentKeyframe.position);
        node.setRotation(
            currentKeyframe.rotation,
            currentKeyframe.rotationAmount);
        node.setScaling(currentKeyframe.scale);
    }

    public void applyInterpolated(SceneGraphNode node) {
        applyLinearInterpolated(node);
    }

    public void applyLinearInterpolated(SceneGraphNode node) {
        float lerp = currentKeyframe.lerpFromFrameId(currentFrame);

        node.setPosition(
            LinearInterpolation
                .lerp(lastKeyframe.position, currentKeyframe.position, lerp));
        node.setRotation(
            LinearInterpolation
                .lerp(lastKeyframe.rotation, currentKeyframe.rotation, lerp),
            LinearInterpolation.lerp(
                lastKeyframe.rotationAmount,
                currentKeyframe.rotationAmount,
                lerp));
        node.setScaling(
            LinearInterpolation
                .lerp(lastKeyframe.scale, currentKeyframe.scale, lerp));
    }

    public void restart() {
        this.currentKeyframeId = 0;
        this.currentKeyframe = keyframes.get(0);
        this.lastKeyframe = keyframes.get(keyframes.size() - 1);
        this.currentFrame = 0;
    }
}
