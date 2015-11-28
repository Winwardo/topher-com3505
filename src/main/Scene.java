package main;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import math.Vector3;
import renderer.Cameras;
import renderer.FBO;
import renderer.TextureLoader;
import scenegraph.EditSceneGraph;
import scenegraph.SceneGraph;
import scenegraph.models.robot.Robot;
import shaders.Albedo;
import shaders.All;
import shaders.Diffuse;
import shaders.ShaderCore;
import shaders.ShadowMapping;
import shaders.Specular;

class Scene {
    private final GL2        gl;
    private final GLU        glu;
    private final GLUT       glut;

    private final SceneGraph sceneGraph;
    private ShaderCore       shaderCore;

    private int              currentShader;
    private FBO              fbo;
    private int              texRender_RB;

    public Scene(GL2 gl) {
        this.gl = gl;
        this.glu = new GLU();
        this.glut = new GLUT();

        Cameras.setGlobal(new Cameras());

        setupGL();
        setupShaders();
        setupTextures();
        setupFbos();

        sceneGraph = makeSceneGraph();
        setZoom(50);
    }

    private SceneGraph makeSceneGraph() {
        // return new DefaultSceneGraph(gl, glut);
        return new EditSceneGraph(gl, glut, new Robot(gl, glut));
    }

    private void setupGL() {
        gl.glClearColor(0.39f, 0.58f, 0.92f, 1);
        gl.glClearColor(0.09f, 0.08f, 0.12f, 1);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glEnable(GL2.GL_MULTISAMPLE);
        gl.glEnable(GL2.GL_POINT_SMOOTH);
        gl.glEnable(GL2.GL_LINE_SMOOTH);
        gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_FILL);
        gl.glMaterialfv(
            GL.GL_FRONT_AND_BACK,
            GL2.GL_AMBIENT_AND_DIFFUSE,
            new float[] { 0.2f, 0.2f, 0.2f, 1.0f },
            0);

        ShadowMapping sm = new ShadowMapping(gl, glu);
    }

    private void setupShaders() {
        shaderCore = new ShaderCore(gl);
        shaderCore.setupShaders(Albedo.fragment, Albedo.vertex);
        shaderCore.setupShaders(Diffuse.fragment2, Diffuse.vertex);
        shaderCore.setupShaders(Specular.fragment, Specular.vertex);
        int all = shaderCore.setupShaders(All.fragment, All.vertex);

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
    }

    public void update() {
        sceneGraph.update();
    }

    public void render() {
        // Apply shaders
        shaderCore.queueShader(currentShader);
        shaderCore.useQueuedShader();

        renderCameraToFbo(1, fbo);

        // Only multisample the final render, don't waste render time on the
        // robot camera view
        gl.glEnable(GL.GL_MULTISAMPLE);

        gl.glClearColor(1.f, 1.f, 0.f, 1.f);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT);
        gl.glBindTexture(gl.GL_TEXTURE_2D, 1);

        gl.glViewport(0, 0, 640 * 2, 480 * 2);

        renderCamera(0);

        gl.glFlush();
        gl.glDisable(GL.GL_MULTISAMPLE);
    }

    private void renderCameraToFbo(int cameraId, FBO fbo) {
        gl.glViewport(0, 0, 512, 512);
        gl.glPushAttrib(GL2.GL_VIEWPORT_BIT);

        gl.glBindTexture(gl.GL_TEXTURE_2D, 0);
        gl.glBindFramebuffer(GL.GL_DRAW_FRAMEBUFFER, fbo.id());
        gl.glClearColor(1.f, 0.f, 0.f, 1.f);

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