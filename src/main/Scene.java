package main;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import lighting.Lights;
import math.Vector3;
import renderer.FBO;
import renderer.Materials;
import renderer.TextureLoader;
import renderer.cameras.Cameras;
import renderer.cameras.FromPointCamera;
import renderer.cameras.RotateAroundPointCamera;
import scenegraph.Animation2;
import scenegraph.EditSceneGraph;
import scenegraph.SceneGraph;
import scenegraph.models.HangingLight;
import scenegraph.models.room.Room;
import shaders.ShaderCore;
import shaders.ShadowMapping;

class Scene {
    private final GL2        gl;
    private final GLU        glu;
    private final GLUT       glut;

    private final SceneGraph sceneGraph;
    private ShaderCore       shaderCore;

    private int              currentShader;
    private FBO              fbo;

    private boolean          editMode = false;

    public Scene(GL2 gl) {
        this.gl = gl;
        this.glu = new GLU();
        this.glut = new GLUT();

        Cameras.setGlobal(new Cameras());
        Materials.setGlobal(new Materials(gl));
        Lights.setGlobal(new Lights(gl));

        setupGL();
        setupShaders();
        setupTextures();
        setupMaterials();
        setupFbos();
        setupCameras();

        sceneGraph = makeSceneGraph();
        setZoom(100);
    }

    private void setupCameras() {
        if (editMode) {
            Cameras.get().append(
                new RotateAroundPointCamera(gl, Vector3.zero(), 10, 10, 45));
        } else {
            Cameras.get().append(
                new RotateAroundPointCamera(
                    gl,
                    new Vector3(
                        Room.ROOM_DEPTH / 2,
                        Room.ROOM_HEIGHT / 2,
                        Room.ROOM_WIDTH / 2),
                    18,
                    217,
                    1.25f));
        }

        Cameras.get().append(
            new FromPointCamera(gl, new Vector3(0, 2.5f, 0f), 18, 217, 1.25f));
    }

    private SceneGraph makeSceneGraph() {
        if (editMode) {
            return new EditSceneGraph(
                gl,
                glut,
                new HangingLight(
                    gl,
                    glut,
                    Lights.get().addSpotLight(
                        gl,
                        new Vector3(1.0f, 0.9f, 0.8f),
                        0.65f,
                        45)));
        } else {
            return new Animation2(gl, glut);
            // return new DefaultSceneGraph(gl, glut);
        }
    }

    private void setupGL() {
        gl.glClearColor(0.39f, 0.58f, 0.92f, 1);
        gl.glClearColor(0.09f, 0.08f, 0.12f, 1);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glEnable(GL2.GL_POINT_SMOOTH);
        gl.glEnable(GL2.GL_LINE_SMOOTH);
        gl.glEnable(GL2.GL_NORMALIZE);
        gl.glEnable(GL2.GL_RESCALE_NORMAL);
        // gl.glFrontFace(GL2.GL_CCW);
        gl.glEnable(GL2.GL_CULL_FACE);
        // gl.glCullFace(GL2.GL_BACK);

        gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_FILL);

        ShadowMapping sm = new ShadowMapping(gl, glu);
    }

    private void setupShaders() {
        shaderCore = new ShaderCore(gl);

        String maxlights = "8";

        int albedo = shaderCore.setupShaders(
            "phong",
            null,
            new String[] { maxlights, "2", "0", "0", "0" });

        int ambient = shaderCore.setupShaders(
            "phong",
            null,
            new String[] { maxlights, "0", "1", "0", "0" });

        int diffuse = shaderCore.setupShaders(
            "phong",
            null,
            new String[] { maxlights, "0", "0", "1", "0" });

        int specular = shaderCore.setupShaders(
            "phong",
            null,
            new String[] { maxlights, "0", "0", "0", "1" });

        int all = shaderCore.setupShaders(
            "phong",
            null,
            new String[] { maxlights, "1", "1", "1", "1" });

        currentShader = all;
        shaderCore.queueShader(all);
    }

    private void setupFbos() {
        fbo = new FBO(gl, TextureLoader.get().get("rendertex"), 512);
    }

    private void setupTextures() {
        TextureLoader textureLoader = new TextureLoader(gl);
        TextureLoader.setGlobal(textureLoader);
        textureLoader.loadBMP("default", "res\\purple.bmp");
        textureLoader.loadBMP("white", "res\\white.bmp");
        textureLoader.loadBMP("rendertex", "res\\white.bmp");
        textureLoader.loadBMP("metal", "res\\metal.bmp");
        textureLoader.loadBMP("nyan", "res\\texture2.bmp");
        textureLoader.loadBMP("white", "res\\white.bmp");
        textureLoader.loadBMP("black", "res\\black.bmp");
        textureLoader.loadBMP("eye_right", "res\\eye_right.bmp");
        textureLoader.loadBMP("eye_left", "res\\eye_left.bmp");
        textureLoader.loadBMP("hardwood", "res\\hardwood.bmp");
        textureLoader.loadBMP("wood2", "res\\wood2.bmp");
        textureLoader.loadBMP("glass", "res\\glass.bmp");
        textureLoader.loadBMP("plate", "res\\plate.bmp");
        textureLoader.loadBMP("tiles", "res\\marbletile.bmp");
        textureLoader.loadBMP("marble", "res\\marble.bmp");
        textureLoader.loadBMP("rug", "res\\carpet.bmp", 512, 512);
        textureLoader.loadBMP("red_wall", "res\\red_wall.bmp");
        textureLoader.loadBMP("chest_light", "res\\chest_light.bmp");
        textureLoader.loadBMP("white_noise", "res\\white_noise.bmp");
    }

    private void setupMaterials() {
        Materials materials = Materials.get();

        final float[] defaultAmbience = new float[] { 0.05f, 0.05f, 0.05f,
            1.0f };
        final float[] defaultDiffuse = new float[] { 0.8f, 0.8f, 0.8f, 1.0f };

        materials.addNew(
            "shinymetal",
            defaultAmbience,
            defaultDiffuse,
            new float[] { 1.0f, 1.0f, 1.0f, 1.0f },
            100f,
            "metal");

        materials.addNew(
            "dullmetal",
            defaultAmbience,
            defaultDiffuse,
            new float[] { 0.2f, 0.2f, 0.2f, 1.0f },
            10f,
            "metal");

        materials.addNew(
            "wood",
            defaultAmbience,
            defaultDiffuse,
            new float[] { 0.4f, 0.4f, 0.4f, 1.0f },
            127,
            "hardwood");

        materials.addNew(
            "wood2",
            defaultAmbience,
            defaultDiffuse,
            new float[] { 1.2f, 1.1f, 1.1f, 1.0f },
            127,
            "wood2");

        materials.addNew(
            "redplastic",
            defaultAmbience,
            defaultDiffuse,
            new float[] { 0.3f, 0.2f, 0.2f, 1.0f },
            .25f * 128,
            "white_noise");

        materials.addNew(
            "tvscreen",
            new float[] { 1.0f, 1.0f, 1.0f, 1.0f },
            defaultDiffuse,
            new float[] { 1.0f, 1.0f, 1.0f, 1.0f },
            100,
            "rendertex");

        materials.addNew(
            "glass",
            defaultAmbience,
            new float[] { 0.95f, 0.95f, 1.0f, 1.0f },
            new float[] { 1.0f, 1.0f, 1.0f, 1.0f },
            100f,
            "glass");

        materials.addNew(
            "plastic_plate",
            defaultAmbience,
            new float[] { 0.85f, 0.85f, 0.85f, 1.0f },
            new float[] { 0.7f, 0.7f, 0.7f, 1.0f },
            .25f * 128,
            "plate");

        materials.addNew(
            "marbletile",
            defaultAmbience,
            defaultDiffuse,
            new float[] { 0.7f, 0.7f, 0.7f, 1.0f },
            0.078125f * 128,
            "tiles");

        materials.addNew(
            "marble",
            defaultAmbience,
            defaultDiffuse,
            new float[] { 0.7f, 0.7f, 0.7f, 1.0f },
            0.078125f * 128,
            "marble");

        materials.addNew(
            "rug",
            defaultAmbience,
            defaultDiffuse,
            new float[] { 0.1f, 0.1f, 0.1f, 1.0f },
            4f,
            "rug");

        materials.addNew(
            "wall",
            defaultAmbience,
            defaultDiffuse,
            new float[] { 0.1f, 0.1f, 0.1f, 1.0f },
            128,
            "red_wall");

        materials.addNew(
            "white",
            new float[] { 0.4f, 0.4f, 0.4f, 1 },
            defaultDiffuse,
            new float[] { 1, 1, 1, 1.0f },
            128,
            "white");

        materials.addNew(
            "chest_light",
            new float[] { 1, 1, 1, 1 },
            defaultDiffuse,
            new float[] { 0.75f, 0.75f, 0.75f, 1.0f },
            128,
            "chest_light");

        materials.addNew(
            "eye_left",
            new float[] { 0.9f, 0.9f, 1.0f, 1.0f },
            new float[] { 0.6f, 0.6f, 0.6f, 1.0f },
            new float[] { 1.0f, 1.0f, 1.0f, 1.0f },
            10f,
            "eye_left");

        materials.addNew(
            "eye_right",
            new float[] { 0.9f, 0.9f, 1.0f, 1.0f },
            new float[] { 0.6f, 0.6f, 0.6f, 1.0f },
            new float[] { 1.0f, 1.0f, 1.0f, 1.0f },
            10f,
            "eye_right");

        materials.addNew("black", "black");

        materials.addNew("nyan", "nyan");
    }

    public void update() {
        sceneGraph.update();
    }

    public void render() {
        // Apply shaders
        shaderCore.queueShader(currentShader);
        shaderCore.useQueuedShader();

        renderCameraToFbo(Cameras.ROBOT_CAMERA, fbo);

        // Only multisample the final render, don't waste render time on the
        // robot camera view
        gl.glEnable(GL.GL_MULTISAMPLE);

        gl.glClearColor(1.f, 1.f, 0.f, 1.f);
        gl.glClearColor(0.f, 0.f, 0.f, 1.f);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT);
        gl.glBindTexture(gl.GL_TEXTURE_2D, 1);

        gl.glViewport(0, 0, 640 * 2, 480 * 2);

        renderCamera(Cameras.get().mainCamera());

        gl.glFlush();
        gl.glDisable(GL.GL_MULTISAMPLE);
    }

    private void renderCameraToFbo(int cameraId, FBO fbo) {
        gl.glViewport(0, 0, 512, 512);
        gl.glPushAttrib(GL2.GL_VIEWPORT_BIT);

        gl.glBindTexture(gl.GL_TEXTURE_2D, 0);
        gl.glBindFramebuffer(GL.GL_DRAW_FRAMEBUFFER, fbo.id());
        // gl.glClearColor(1.f, 0.f, 0.f, 1.f);

        renderCamera(cameraId);

        gl.glPopAttrib();
        gl.glBindFramebuffer(GL.GL_FRAMEBUFFER, 0);
    }

    private void renderCamera(int cameraId) {
        gl.glLoadIdentity();
        Cameras.get().apply(cameraId);
        gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT);
        sceneGraph.render();
    }

    public SceneGraph sceneGraph() {
        return sceneGraph;
    }

    public void setZoom(float zoom) {
        sceneGraph.root().setScaling(Vector3.all(zoom / 100));
    }

    public void setShader(int value) {
        currentShader = value * 3;
        shaderCore.queueShader(value * 3);
    }
}