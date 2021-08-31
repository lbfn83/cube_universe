package com.level;

import org.joml.Vector3f;

import com.graphics.VertexArray;


import org.joml.Vector3f;

public class CubeItem {

    private final VertexArray mesh;// mesh가 왜 필요하지?어짜피 같은 VAO VBO에 다른 좌표만 넣어서 반복해서 넣어줄건데
    
    private final Vector3f position;
    
    private float scale;

    private final Vector3f rotation;

    public CubeItem(VertexArray va) {
        this.mesh = va;
        position = new Vector3f();
        scale = 1;
        rotation = new Vector3f();
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }
    
    public VertexArray getMesh() {
        return mesh;
    }
}
