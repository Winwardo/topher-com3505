#version 120

/* I declare that this code is my own work */
/* Topher Winward, 120134353, crwinward1@sheffield.ac.uk */

// Fragment shader. With help from
// https://www.opengl.org/sdk/docs/tutorials/ClockworkCoders/lighting.php
// https://stackoverflow.com/questions/11434233/shader-for-a-spotlight
// http://www.tomdalling.com/blog/modern-opengl/07-more-lighting-ambient-specular-attenuation-gamma/
// https://en.wikipedia.org/wiki/Blinn-Phong_shading_model
// 
// Here we're generating each fragment (approximately one pixel) of the screen.
// Multiple fragments may actually be used if we're multisampling to produce
// smoother edges.

// Collect inputs from the vertex shader
varying vec3 vertexModelview;
varying vec3 normal;
uniform sampler2D tex;


#define ALBEDO 0
#define AMBIENT 1
#define DIFFUSE 2
#define SPECULAR 3
#define ALL 4

#define MAX_LIGHTS %s
#define SHADER_TYPE %s

void main (void) { 
        vec4 finalColor = vec4(0.0, 0.0, 0.0, 0.0);

        // Albedo is the raw texture image for drawing, like some wood image.
        vec4 albedo = texture2D(tex,gl_TexCoord[0].st);

        // The ambient component is calculated from the material.
        // A more advanced shader might look up from an ambient texture map.
        // By clamping the ambient to 0.05 minimum, we will always be able to slightly
        // see in the room, even when all lights are off.
        vec4 ambientComp = clamp(gl_FrontMaterial.ambient, 0.05, 1.0);
        #if SHADER_TYPE == AMBIENT || SHADER_TYPE == ALL
                finalColor += ambientComp;
        #endif

        // Apply Lambertian diffuse and specular per light
        for (int i = 0; i < MAX_LIGHTS; i++) {
                vec3 lightDirection = normalize(gl_LightSource[i].position.xyz - vertexModelview);

                // The diffuse component is generated by considering how directly the vertex
                // is facing the incoming light. If the vertex is greater than 90 degrees,
                // provide a black diffuse, as no light would be reaching it.
                // gl_FrontLightProduct = gl_LightSource * gl_FrontMaterial
                float lambertian = max(dot(normal, lightDirection), 0.0);

                // If the pixel would be facing away from the light source, ignore it totally
                if (lambertian > 0.0) {
                        // Create diffuse
                        vec4 diffuse = gl_FrontLightProduct[i].diffuse * lambertian;

                        // Check if we're within spotlight radius (if this isn't a point light)
                        float spotEffect = dot(normalize(gl_LightSource[i].spotDirection), normalize(-lightDirection));
                        float difference = spotEffect - gl_LightSource[i].spotCosCutoff;
                        if (difference < 0) {
                                // Early out if this pixel can't get any light from this source
                                continue;
                        }
                        // Smooth out the light
                        float spotlightFactor = clamp(difference*100, 0, 3);

                        // Generate specular value
                        vec3 viewDirection = normalize(-vertexModelview);
                        vec3 halfDir = normalize(lightDirection + viewDirection);
                        float specAngle = max(dot(halfDir, normal), 0.0);

                        vec4 preSpecular = vec4(pow(specAngle, 0.3*gl_FrontMaterial.shininess));
                        vec4 specular = gl_FrontLightProduct[i].specular * preSpecular;


                        // Generate attenuation, so that far away lights don't light up the pixel
                        // as much as nearby pixels
                        float dist = distance(gl_LightSource[i].position, vec4(vertexModelview, 0));

                        float constantAttenuation = 1.0;
                        float linearAttenuation = 0.1;
                        float quadraticAttenuation = 0.01;
                        float attenuation = 1.0 / (
                                (constantAttenuation) +
                                (linearAttenuation * dist) +
                                (quadraticAttenuation * dist * dist)
                        );

                        // Combine the diffuse and specular, modulated by the attenuation
                        // and whether or not we're inside the spotlight radius
                        #if SHADER_TYPE == DIFFUSE || SHADER_TYPE == ALL
                                finalColor += (diffuse) * attenuation * spotlightFactor;
                        #endif

                        #if SHADER_TYPE == SPECULAR || SHADER_TYPE == ALL
                                finalColor += (specular) * attenuation * spotlightFactor;
                        #endif
                }
        }



        #if SHADER_TYPE == ALBEDO
                finalColor = albedo;
        #endif
        #if SHADER_TYPE == ALL
                finalColor = finalColor * albedo;
        #endif

        gl_FragColor = finalColor;        
}