varying vec3 vN;
varying vec3 v;
varying vec3 N;
uniform sampler2D tex;

#define MAX_LIGHTS %s
#define ALBEDO_CONSTANT %s
#define AMBIENT_CONSTANT %s
#define DIFFUSE_CONSTANT %s
#define SPECULAR_CONSTANT %s

void main (void) { 
        vec4 finalColor = vec4(0.0, 0.0, 0.0, 0.0);
        vec4 albedo = texture2D(tex,gl_TexCoord[0].st);

        for (int i = 0; i < MAX_LIGHTS; i++) {
                vec3 L = normalize(gl_LightSource[i].position.xyz - v); 
                vec3 E = normalize(-v); // we are in Eye Coordinates, so EyePos is (0,0,0)
                vec3 R = normalize(-reflect(L,N));

                vec4 Iamb = gl_FrontLightProduct[i].ambient;
                Iamb = Iamb * AMBIENT_CONSTANT;

                vec4 Idiff = gl_FrontLightProduct[i].diffuse * max(dot(N,L), 0.0);
                Idiff = smoothstep(0.2, 0.8, Idiff);
                Idiff = clamp(Idiff, 0.1, 1.0);
                Idiff = Idiff * DIFFUSE_CONSTANT;

                vec4 Ispec = gl_FrontLightProduct[i].specular * pow(max(dot(R,E),0.0),0.3*gl_FrontMaterial.shininess);                
                //Ispec = smoothstep(0.3, 0.4, Ispec);
                Ispec = smoothstep(0.3, 0.7, Ispec);
                Ispec = Ispec * SPECULAR_CONSTANT;

                finalColor += Iamb + Idiff + Ispec;
        }

        if (ALBEDO_CONSTANT == 0) {
                gl_FragColor = finalColor;
        } else if (ALBEDO_CONSTANT == 2) {
                gl_FragColor = albedo;
        } else {
                gl_FragColor = albedo * finalColor;
        }
}