#version 150

#moj_import <minecraft:fog.glsl>

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;

in float vertexDistance;
in vec4 vertexColor;
in vec4 lightMapColor;
in vec4 overlayColor;
in vec2 texCoord0;

out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, texCoord0);
    #ifdef ALPHA_CUTOUT
    if (color.a < ALPHA_CUTOUT) {
        discard;
    }
    #endif
    float percent = vertexColor.a;
    float gray = color.r * 0.299 + color.g * 0.587 + color.b * 0.114;
    color.rgb = vec3(color.r * (1 - percent) + gray * percent, color.g * (1 - percent) + gray * percent, color.b * (1 - percent) + gray * percent);
    color *= ColorModulator;
    #ifndef NO_OVERLAY
    color.rgb = mix(overlayColor.rgb, color.rgb, overlayColor.a);
    #endif
    #ifndef EMISSIVE
    color *= lightMapColor;
    #endif
    fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}