/* I declare that this code is my own work */
/* Topher Winward, 120134353, crwinward1@sheffield.ac.uk */
package main;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import animation.Animations;
import lighting.Lights;
import math.Vector3;
import renderer.FrameBufferObject;
import renderer.Materials;
import renderer.TextureLoader;
import renderer.cameras.Cameras;
import renderer.cameras.FromPointCamera;
import renderer.cameras.RotateAroundPointCamera;
import scenegraph.MainScene;
import scenegraph.SceneGraph;
import scenegraph.models.room.Room;
import shaders.ShaderCore;

/**
 * Scene is the authority holding the main SceneGraph, and updates and renders
 * it as needed.
 * 
 * @author Topher
 *
 */
class Scene {
    private final GL2         gl;

    private final SceneGraph  sceneGraph;
    private ShaderCore        shaderCore;

    private int               currentShader;
    private FrameBufferObject fbo;

    public Scene(GL2 gl) {
        this.gl = gl;

        setupGlobalRegisters();
        setupGL();
        setupShaders();
        setupTextures();
        setupMaterials();
        setupFbos();
        setupCameras();

        sceneGraph = makeSceneGraph();
    }

    private void setupGlobalRegisters() {
        Cameras.setGlobal(new Cameras());
        TextureLoader.setGlobal(new TextureLoader(gl));
        Materials.setGlobal(new Materials(gl));
        Lights.setGlobal(new Lights(gl));
        Animations.setGlobal(new Animations());
    }

    private void setupCameras() {
        Cameras.get().append(
            new RotateAroundPointCamera(
                gl,
                new Vector3(
                    Room.ROOM_DEPTH / 2,
                    Room.ROOM_HEIGHT / 2,
                    Room.ROOM_WIDTH / 2),
                20,
                200,
                -3));

        Cameras.get().append(
            new FromPointCamera(gl, new Vector3(0, 2.5f, 0f), 18, 217, 1.25f));
    }

    private SceneGraph makeSceneGraph() {
        return new MainScene(gl);
    }

    private void setupGL() {
        gl.glClearColor(0, 0, 0, 1);

        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glEnable(GL2.GL_POINT_SMOOTH);
        gl.glEnable(GL2.GL_LINE_SMOOTH);
        gl.glEnable(GL2.GL_NORMALIZE);
        gl.glEnable(GL2.GL_RESCALE_NORMAL);
        gl.glEnable(GL2.GL_CULL_FACE);

        gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_FILL);
    }

    private void setupShaders() {
        shaderCore = new ShaderCore(gl);
        currentShader = shaderCore.loadShaders();
    }

    private void setupFbos() {
        fbo = new FrameBufferObject(
            gl,
            TextureLoader.get().get("rendertex"),
            512);
    }

    private void setupTextures() {
        TextureLoader.get().loadTextures();
    }

    private void setupMaterials() {
        Materials.get().setupMaterials();
    }

    public void update() {
        Animations.get().tick();
        sceneGraph.update();
    }

    public void render() {
        // Apply shaders
        shaderCore.queueShader(currentShader);
        shaderCore.useQueuedShader();

        // Render all needed cameras to textures / frame buffer objects
        renderCameraToFbo(Cameras.ROBOT_CAMERA, fbo);

        // Only multisample the final render, don't waste render time on the
        // robot camera view
        gl.glEnable(GL.GL_MULTISAMPLE);

        gl.glClear(GL.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT);
        gl.glViewport(
            0,
            0,
            Assignment.CURRENT_WIDTH,
            Assignment.CURRENT_HEIGHT);

        renderCamera(Cameras.get().mainCamera());

        gl.glFlush();
        gl.glDisable(GL.GL_MULTISAMPLE);
    }

    private void renderCameraToFbo(int cameraId, FrameBufferObject fbo) {
        gl.glViewport(0, 0, 512, 512);
        gl.glPushAttrib(GL2.GL_VIEWPORT_BIT);

        gl.glBindTexture(gl.GL_TEXTURE_2D, 0);
        gl.glBindFramebuffer(GL.GL_DRAW_FRAMEBUFFER, fbo.id());

        renderCamera(cameraId);

        gl.glBindFramebuffer(GL.GL_FRAMEBUFFER, 0);
        gl.glPopAttrib();
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

    public void setShader(int value) {
        currentShader = value * 3;
        shaderCore.queueShader(currentShader);
    }
}