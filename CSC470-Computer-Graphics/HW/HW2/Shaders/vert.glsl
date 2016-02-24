#version 120

attribute vec2 aPosition;
attribute vec4 aColor;
attribute vec3 aCenterPos;

uniform float theta;

varying vec4 vColor;

void main() {

    float s = sin( theta );
    float c = cos( theta );
    
    gl_Position = vec4(-s * aPosition.y + c * aPosition.x, s * aPosition.x + c * aPosition.y, 0, 1);
    vColor = aColor;
}
