package lighting;

import java.util.ArrayList;
import java.util.List;
import com.jogamp.opengl.GL2;
import math.Vector3;

/**
 * Lights provides a Singleton access to save on programming costs. Preferably a
 * service locator would be used, injected into the model primitives via some
 * factory, but that's beyond the scope of this assignment.
 *
 *
 * 
 * @author Topher
 *
 */
public class Lights {
    private static Lights _lights;

    public static void setGlobal(Lights lights) {
        Lights._lights = lights;
    }

    public static Lights get() {
        if (_lights != null) {
            return _lights;
        } else {
            throw new RuntimeException("Lights has not been set yet.");
        }
    }

    private final List<ILight> lights;

    public Lights(GL2 gl) {
        this.lights = new ArrayList<>();

        // Set all lights to pitch black
        // return;
        int lightId = GL2.GL_LIGHT0;
        while (lightId <= GL2.GL_LIGHT7) {
            float[] zero = new float[] { 0, 0, 0, 1.0f };

            gl.glLightfv(lightId, GL2.GL_POSITION, zero, 0);
            gl.glLightfv(lightId, GL2.GL_AMBIENT, zero, 0);
            gl.glLightfv(lightId, GL2.GL_DIFFUSE, zero, 0);
            gl.glLightfv(lightId, GL2.GL_SPECULAR, zero, 0);
            gl.glDisable(lightId);

            lightId++;
        }
    }

    public int newLightId() {
        return GL2.GL_LIGHT0 + lights.size();
    }

    public int append(ILight light) {
        lights.add(light);
        return lights.size() - 1;
    }

    public ILight get(int lightId) {
        return lights.get(lightId);
    }

    public void apply(int lightId) {
        if (lightId < lights.size() && lightId >= 0) {
            final ILight light = get(lightId);
            if (light != null) {
                light.apply();
            }
        }
    }

    public Light addPointLight(GL2 gl, Vector3 colour, float brightness) {
        return addSpotLight(gl, colour, brightness, 180);
    }

    public Light addSpotLight(GL2 gl, Vector3 colour, float brightness,
        float angle) {
        final Light light = new Light(gl, colour, brightness, angle);
        light.setLightId(newLightId());
        light.enable();
        append(light);

        return light;
    }
}
