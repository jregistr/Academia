#version 120

precision highp float;

varying vec4 vColor;

void main() {
    gl_FragColor = vColor;
}