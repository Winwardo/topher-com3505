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

    public Material addNew(String materialName, String textureName) {
        Material newMaterial = new Material(gl, textureName);
        materials.put(materialName, newMaterial);
        return newMaterial;
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

    public void setupMaterials() {
        final float[] defaultAmbience = new float[] { 0.05f, 0.05f, 0.05f,
            1.0f };
        final float[] defaultDiffuse = new float[] { 0.8f, 0.8f, 0.8f, 1.0f };

        this.addNew(
            "shinymetal",
            defaultAmbience,
            defaultDiffuse,
            new float[] { 1.0f, 1.0f, 1.0f, 1.0f },
            100f,
            "metal");

        this.addNew(
            "dullmetal",
            defaultAmbience,
            defaultDiffuse,
            new float[] { 0.2f, 0.2f, 0.2f, 1.0f },
            10f,
            "metal");

        this.addNew(
            "wood",
            defaultAmbience,
            defaultDiffuse,
            new float[] { 0.4f, 0.4f, 0.4f, 1.0f },
            127,
            "hardwood");

        this.addNew(
            "wood2",
            defaultAmbience,
            defaultDiffuse,
            new float[] { 1.2f, 1.1f, 1.1f, 1.0f },
            127,
            "wood2");

        this.addNew(
            "redplastic",
            defaultAmbience,
            defaultDiffuse,
            new float[] { 0.3f, 0.2f, 0.2f, 1.0f },
            .25f * 128,
            "white_noise");

        this.addNew(
            "tvscreen",
            new float[] { 1.0f, 1.0f, 1.0f, 1.0f },
            defaultDiffuse,
            new float[] { 1.0f, 1.0f, 1.0f, 1.0f },
            100,
            "rendertex");

        this.addNew(
            "glass",
            defaultAmbience,
            new float[] { 0.95f, 0.95f, 1.0f, 1.0f },
            new float[] { 1.0f, 1.0f, 1.0f, 1.0f },
            100f,
            "glass");

        this.addNew(
            "plastic_plate",
            defaultAmbience,
            new float[] { 0.85f, 0.85f, 0.85f, 1.0f },
            new float[] { 0.7f, 0.7f, 0.7f, 1.0f },
            .25f * 128,
            "plate");

        this.addNew(
            "marbletile",
            defaultAmbience,
            defaultDiffuse,
            new float[] { 0.7f, 0.7f, 0.7f, 1.0f },
            0.078125f * 128,
            "tiles");

        this.addNew(
            "marble",
            defaultAmbience,
            defaultDiffuse,
            new float[] { 0.7f, 0.7f, 0.7f, 1.0f },
            0.078125f * 128,
            "marble");

        this.addNew(
            "rug",
            defaultAmbience,
            defaultDiffuse,
            new float[] { 0.1f, 0.1f, 0.1f, 1.0f },
            4f,
            "rug");

        this.addNew(
            "wall",
            defaultAmbience,
            defaultDiffuse,
            new float[] { 0.1f, 0.1f, 0.1f, 1.0f },
            128,
            "red_wall");

        this.addNew(
            "white",
            new float[] { 0.4f, 0.4f, 0.4f, 1 },
            defaultDiffuse,
            new float[] { 1, 1, 1, 1.0f },
            128,
            "white");

        this.addNew(
            "dullwhite",
            new float[] { 0.2f, 0.2f, 0.2f, 1 },
            defaultDiffuse,
            new float[] { 1, 1, 1, 1.0f },
            128,
            "white");

        this.addNew(
            "brightwhite",
            new float[] { 1, 1, 1, 1 },
            defaultDiffuse,
            new float[] { 1, 1, 1, 1.0f },
            128,
            "white");

        this.addNew(
            "chest_light",
            new float[] { 1, 1, 1, 1 },
            defaultDiffuse,
            new float[] { 0.75f, 0.75f, 0.75f, 1.0f },
            128,
            "chest_light");

        this.addNew(
            "eye_left",
            new float[] { 0.9f, 0.9f, 1.0f, 1.0f },
            new float[] { 0.6f, 0.6f, 0.6f, 1.0f },
            new float[] { 1.0f, 1.0f, 1.0f, 1.0f },
            10f,
            "eye_left");

        this.addNew(
            "eye_right",
            new float[] { 0.9f, 0.9f, 1.0f, 1.0f },
            new float[] { 0.6f, 0.6f, 0.6f, 1.0f },
            new float[] { 1.0f, 1.0f, 1.0f, 1.0f },
            10f,
            "eye_right");

        this.addNew("black", "black");

        this.addNew("nyan", "nyan");
    }
}
