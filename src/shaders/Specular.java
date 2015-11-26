package shaders;

/**
 * 
 * @author OpenGL
 * @see {@link} https://www.opengl.org/sdk/docs/tutorials/ClockworkCoders/
 *      lighting.php
 */
public class Specular {
    public final static String vertex   = "varying vec3 N;" + "varying vec3 v;"
        + "void main(void)" + "{" + "     gl_TexCoord[0] = gl_MultiTexCoord0;"
        + "   v = vec3(gl_ModelViewMatrix * gl_Vertex);       "
        + "   N = normalize(gl_NormalMatrix * gl_Normal);"
        + "   gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;" + "}";

    public final static String fragment = "varying vec3 N;" + "varying vec3 v;"
        + "void main(void)" + "{\n"
        + "vec3 L = normalize(gl_LightSource[0].position.xyz - v);  "
        + " vec3 E = normalize(-v); // we are in Eye Coordinates, so EyePos is (0,0,0) \n "
        + "vec3 R = normalize(-reflect(L,N));"
        + "   vec4 finalColor = vec4(0.0,0.0,0.0,0.0);"
        + " for (int i = 0; i < 3; i++) {" + "// calculate Specular Term:\n"
        + "vec4 Ispec = gl_FrontLightProduct[i].specular "
        + "* pow(max(dot(R,E),0.0),0.3*gl_FrontMaterial.shininess); "
        + "Ispec = clamp(Ispec, 0.0, 1.0); " + "finalColor += Ispec;" + "}"
        + "gl_FragColor = finalColor * 0.5;" + "}";
}
