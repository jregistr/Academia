#version 120

attribute vec2 aPosition;
attribute vec4 aColor;
attribute vec2 aCenter;

uniform float uTheta;

varying vec4 vColor;

void main() {

    mat3 toCenter = mat3(vec3(1,0,0),vec3(0,1,0),vec3(-aCenter.x, -aCenter.y, 1));
    mat3 rot = mat3(vec3(cos(uTheta), sin(uTheta), 0),vec3(-sin(uTheta), cos(uTheta), 0),vec3(0,0,1));
    mat3 back = mat3(vec3(1,0,0),vec3(0,1,0),vec3(aCenter.x, aCenter.y, 1));
    
    mat3 netMat = back * rot * toCenter;
    #mat3 netMat = toCenter * rot * back;
    
    gl_Position = vec4((netMat * vec3(aPosition, 1)).xy, 0, 1);

    vColor = aColor;
}
