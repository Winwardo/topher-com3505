varying vec3 vN;
varying vec3 v;
varying vec3 N;
uniform sampler2D tex;

#define MAX_LIGHTS %s
#define ALBEDO_CONSTANT %s
#define AMBIENT_CONSTANT %s
#define DIFFUSE_CONSTANT %s
#define SPECULAR_CONSTANT %s

// https://stackoverflow.com/questions/11434233/shader-for-a-spotlight

float getdist(vec4 a, vec4 b) {
	return sqrt(pow(b[0]-a[0],2)+pow(b[1]-a[1],2)+pow(b[2]-a[2],2)+pow(b[3]-a[3],2));
}

void main (void) { 
        vec4 finalColor = vec4(0.0, 0.0, 0.0, 0.0);
        vec4 albedo = texture2D(tex,gl_TexCoord[0].st);

        for (int i = 0; i < MAX_LIGHTS; i++) {
                vec3 L = normalize(gl_LightSource[i].position.xyz - v); 
                vec3 E = normalize(-v); // we are in Eye Coordinates, so EyePos is (0,0,0)
                vec3 R = normalize(-reflect(L,N));


                float q = 1;

                vec3 spotlightdir = gl_LightSource[i].spotDirection;

                float spotEffect = dot(normalize(spotlightdir), normalize(-L));
                float cutoff = gl_LightSource[i].spotCosCutoff;

                float difference = spotEffect - cutoff;

                q = clamp(difference*100, 0, 5);

                vec4 Idiff = gl_FrontLightProduct[i].diffuse * gl_FrontMaterial.diffuse * max(dot(N,L), 0.0);
                // Idiff = smoothstep(0.2, 0.8, Idiff);
                Idiff = clamp(Idiff, 0.0, 1.0);
                Idiff = Idiff * DIFFUSE_CONSTANT;

                vec4 Ispec = gl_FrontLightProduct[i].specular * gl_FrontMaterial.specular * pow(max(dot(R,E),0.0),0.3*gl_FrontMaterial.shininess);                
                //Ispec = smoothstep(0.3, 0.4, Ispec);
                Ispec = smoothstep(0.3, 0.6, Ispec);
                // Ispec = clamp(Ispec, 0, 1);
                Ispec = Ispec * SPECULAR_CONSTANT;

				vec4 pos = gl_LightSource[i].position;
				vec4 vvvv = vec4(v.xyz, 0);
                float dist = getdist((pos), vvvv);
                float att =1.0/(1.0+0.1*dist+0.01*dist*dist);//= 1/(d*d);
                // att = 1;
                finalColor += (Idiff*att + Ispec*att) * q;
        }

        vec4 ambientComp = clamp(gl_FrontMaterial.ambient, 0.02, 1.0) * AMBIENT_CONSTANT;
        finalColor += ambientComp;


        if (ALBEDO_CONSTANT == 0) {
                gl_FragColor = finalColor;
        } else if (ALBEDO_CONSTANT == 2) {
                gl_FragColor = albedo;
        } else {
                gl_FragColor = albedo * finalColor;
        }
}