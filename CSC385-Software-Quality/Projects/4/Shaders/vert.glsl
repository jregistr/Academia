#version 120

attribute vec2 aPosition;
attribute vec4 aColor;
attribute vec2 aCenter;

uniform float uTheta;
uniform mat4 uView;
uniform mat4 uProj;

varying vec4 vColor;

void main() {

    mat3 toCenter = mat3(vec3(1,0,0),vec3(0,1,0),vec3(-aCenter.x, -aCenter.y, 1));
    mat3 rot = mat3(vec3(cos(uTheta), sin(uTheta), 0),vec3(-sin(uTheta), cos(uTheta), 0),vec3(0,0,1));
    mat3 back = mat3(vec3(1,0,0),vec3(0,1,0),vec3(aCenter.x, aCenter.y, 1));
    
    mat3 netMat = back * rot * toCenter;
    #mat3 netMat = toCenter * rot * back;
    
    vec4 pos = vec4((netMat * vec3(aPosition, 1)).xy, 0.8, 1);

    #gl_Position = vec4((uProj * uView * pos).xy, 0.8, 1);
    
    #gl_Position = vec4((uProj * uView * vec4(aPosition, 0.8, 1)).xyz, 1);
    
    #gl_Position = pos;
    
    
    gl_Position = uProj * uView * vec4(vec2(pos), 1.5, 1);
    #gl_Position = uProj * uView * pos;
    vColor = aColor;
}
