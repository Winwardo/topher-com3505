package renderer;

import java.util.Map;
import java.util.TreeMap;
import com.jogamp.opengl.GL2;

/**
 * Materials provides a Singleton access to save on programming costs.
 * Preferably a service locator would be used, injected into the model
 * primitives via some factory, but that's beyond the scope of this assignment.
 * 
 * @author Topher
 *
 */
public class Materials {
    private static Materials _materials;

    public static void setGlobal(Materials materials) {
        Materials._materials = materials;
    }

    public static Materials get() {
        if (_materials != null) {
            return _materials;
        } else {
            throw new RuntimeException("Materials has not been set yet.");
        }
    }

    private GL2                         gl;
    private final Map<String, Material> materials;

    public Materials(GL2 gl) {
        this.gl = gl;
        this.materials = new TreeMap<>();
    }

    public Material addNew(String materialName, float[] ambient,
        float[] diffuse, float[] specular, float shininess,
        String textureName) {
        Material newMaterial = new Material(
            gl,
            ambient,
            diffuse,
            specular,
            new float[] { shininess },
            textureName);
        materials.put(materialName, newMaterial);
        return newMaterial;
    }

    public Material get(String materialName) {
        final Material material = materials.get(materialName);
        if (material == null) {
            System.out.println(
                "Tried to load non-existent material. Using default material.");
            return Material.empty(gl);
        }
        return material;
    }
}
