/* I declare that this code is my own work */
/* Topher Winward, 120134353, crwinward1@sheffield.ac.uk */
// Vertex shader
// With help from
// https://www.opengl.org/sdk/docs/tutorials/ClockworkCoders/lighting.php
// 
// We need to generate some values to send forwards to the fragment shader

varying vec3 normal;
varying vec3 vertexModelview;

void main(void) {
	// Look at the bound texture for drawing images
	gl_TexCoord[0] = gl_MultiTexCoord0;

	// Transform the current vertex into the model view
	vertexModelview = vec3(gl_ModelViewMatrix * gl_Vertex);

	// Get the vertex normal for later interpolation
	normal = normalize(gl_NormalMatrix * gl_Normal);

	// Get the actual position of the vertex
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
}