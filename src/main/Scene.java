package main;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import math.Vector3;
import renderer.TextureLoader;
import scenegraph.EditSceneGraph;
import scenegraph.SceneGraph;
import scenegraph.models.robot.Robot;
import shaders.Diffuse;
import shaders.ShaderCore;

class Scene {
    private final GL2  gl;
    private final GLU  glu;
    private final GLUT glut;

    private final SceneGraph sceneGraph;
    private ShaderCore       shaderCore;

    public Scene(GL2 gl) {
        this.gl = gl;
        this.glu = new GLU();
        this.glut = new GLUT();

        setupGL();
        setupTextures();

        sceneGraph = makeSceneGraph();
        setZoom(50);
    }

    private SceneGraph makeSceneGraph() {
        // return new DefaultSceneGraph(gl, glut);
        return new EditSceneGraph(gl, glut, new Robot(gl, glut));
    }

    private void setupGL() {
        gl.glClearColor(0.39f, 0.58f, 0.92f, 1);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_NORMALIZE);
        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_FILL);
        gl.glMaterialfv(
            GL.GL_FRONT,
            GL2.GL_AMBIENT_AND_DIFFUSE,
            new float[] { 0.2f, 0.2f, 0.2f, 1.0f },
            0);

        shaderCore = new ShaderCore(gl);
        int diffuse = shaderCore
            .setupShaders(Diffuse.fragment2, Diffuse.vertex);

        shaderCore.queueShader(diffuse);

        // ShadowMapping sm = new ShadowMapping(gl, glu);
    }

    private void setupTextures() {
        TextureLoader textureLoader = new TextureLoader(gl);
        TextureLoader.setGlobal(textureLoader);
        textureLoader.loadBMP("default", "res\\purple.bmp");
        textureLoader.loadBMP("white", "res\\white.bmp");
        textureLoader.loadBMP("black", "res\\black.bmp");
        textureLoader.loadBMP("metal", "res\\metal.bmp");
        textureLoader.loadBMP("nyan", "res\\texture2.bmp");
    }

    public void update() {
        sceneGraph.update();
    }

    public void render() {
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        shaderCore.useQueuedShader();

        glu.gluLookAt(1.2, 1.0, 2.5, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0);

        gl.glColor3d(1, 1, 1);

        sceneGraph.render();
    }

    public SceneGraph sceneGraph() {
        return sceneGraph;
    }

    public void setZoom(float zoom) {
        sceneGraph.root().setScaling(Vector3.all(zoom / 100));
    }

    public void setShader(int value) {
        shaderCore.queueShader(value * 3);
    }
}