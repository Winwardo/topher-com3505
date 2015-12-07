package renderer.cameras;

import java.util.ArrayList;
import java.util.List;

/**
 * Cameras provides a Singleton access to save on programming costs. Preferably
 * a service locator would be used, injected into the model primitives via some
 * factory, but that's beyond the scope of this assignment.
 *
 *
 * 
 * @author Topher
 *
 */
public class Cameras {
    public static int      MAIN_CAMERA  = 0;
    public static int      ROBOT_CAMERA = 1;

    private static Cameras _cameras;

    public static void setGlobal(Cameras cameras) {
        Cameras._cameras = cameras;
    }

    public static Cameras get() {
        if (_cameras != null) {
            return _cameras;
        } else {
            throw new RuntimeException("Cameras has not been set yet.");
        }
    }

    private final List<Camera> cameras;
    private int                activeCameraId;

    public Cameras() {
        this.cameras = new ArrayList<>();
    }

    public int append(Camera camera) {
        cameras.add(camera);
        return cameras.size() - 1;
    }

    public Camera get(int cameraId) {
        return cameras.get(cameraId);
    }

    public void apply(int cameraId) {
        if (cameraId < cameras.size() && cameraId >= 0) {
            final Camera camera = get(cameraId);
            if (camera != null) {
                activeCameraId = cameraId;
                camera.apply();
            }
        }
    }

    public int activeCamera() {
        return activeCameraId;
    }
}
