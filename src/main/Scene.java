package main;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import math.Vector3;
import scenegraph.DefaultSceneGraph;
import scenegraph.SceneGraph;
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

        sceneGraph = makeSceneGraph();
        setZoom(50);
    }

    private SceneGraph makeSceneGraph() {
        return new DefaultSceneGraph(gl, glut);
    }

    private void setupGL() {
        gl.glClearColor(0, 0, 0, 1);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glEnable(GL2.GL_NORMALIZE);
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