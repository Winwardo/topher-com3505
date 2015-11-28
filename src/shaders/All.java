package shaders;

/**
 * 
 * @author OpenGL
 * @see {@link} https://www.opengl.org/sdk/docs/tutorials/ClockworkCoders/
 *      lighting.php
 */
public class All {
    public final static String vertex   = "varying vec3 N;" + "varying vec3 v;"
        + "void main(void)" + "{" + "     gl_TexCoord[0] = gl_MultiTexCoord0;"
        + "   v = vec3(gl_ModelViewMatrix * gl_Vertex);       "
        + "   N = normalize(gl_NormalMatrix * gl_Normal);"
        + "   gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;" + "}";

    public final static String vertex2  = "varying vec3 N;" + "varying vec3 v;"
        + "void main(void)  " + "{     "
        + "   v = vec3(gl_ModelViewMatrix * gl_Vertex);       "
        + "   N = normalize(gl_NormalMatrix * gl_Normal);"
        + "   gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;  " + "}";

    public final static String fragment = "varying vec3 vN;"
        + "varying vec3 v; varying vec3 N;   uniform sampler2D tex;"
        + "\n#define MAX_LIGHTS 3\n" + "void main (void) " + "{ "
        + "   vec4 finalColor = vec4(0.0, 0.0, 0.0, 0.0);" + "   "
        + "   for (int i=0;i<3;i++)" + "   {"
        + "      vec3 L = normalize(gl_LightSource[i].position.xyz - v); "
        + "      vec3 E = normalize(-v); // we are in Eye Coordinates, so EyePos is (0,0,0) \n"
        + "      vec3 R = normalize(-reflect(L,N)); " + "   "
        + "      vec4 Iamb = gl_FrontLightProduct[i].ambient; "
        + "      vec4 Idiff = gl_FrontLightProduct[i].diffuse * max(dot(N,L), 0.0);"
        + "      Idiff = clamp(Idiff, 0.0, 1.0); " + "   "
        + "      vec4 Ispec = gl_FrontLightProduct[i].specular "
        + "             * pow(max(dot(R,E),0.0),0.3*gl_FrontMaterial.shininess);"
        + "Idiff =smoothstep(0.2, 0.8, Idiff);" + ""
        + "Idiff = clamp(Idiff, 0.1, 1.0);  "
        + "Ispec = smoothstep(0.5, 1.1, Ispec); "
        + "      finalColor += Iamb + Idiff + Ispec;\n" + " " + "  }" + "   "
        + " //gl_FragColor = gl_FrontLightModelProduct.sceneColor + finalColor; \n"
        + "     gl_FragColor =  texture2D(tex,gl_TexCoord[0].st) * finalColor;"
        + "}";
}
