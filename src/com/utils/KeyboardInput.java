package com.utils;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector3f;

import com.graphics.Window;

public class KeyboardInput {
    private final Vector3f displInc;
    
    private final Vector3f rotationRate;
	
    private int scaleInc;
    
    public KeyboardInput()
    {
    	displInc = new Vector3f();
    	rotationRate = new Vector3f();
    }
    public void input(Window window) {

    	// Keyboard Input process, will be reflected to the movement of camera	
    	displInc.x = 0;
    	displInc.y = 0;
    	displInc.z = 0;

    	if (window.isKeyPressed(GLFW_KEY_Z)) {
    		displInc.y = 1;
    	} else if (window.isKeyPressed(GLFW_KEY_X)) {
    		displInc.y = -1;
    	} else if (window.isKeyPressed(GLFW_KEY_A)) {
    		displInc.x = -1;
    	} else if (window.isKeyPressed(GLFW_KEY_D)) {
    		displInc.x = 1;
    	} 
    	else if (window.isKeyPressed(GLFW_KEY_W)) {
    		displInc.z = -1;
    	} else if (window.isKeyPressed(GLFW_KEY_S)) {
    		displInc.z = 1;
    	} 


    	// Cube's manual movement is restricted to rotational move 
    	// translation move is only enabled in Camera class which is acutally processed in MouseInput class
    	scaleInc = 0;

    	rotationRate.x = 0;
    	rotationRate.y = 0;
    	rotationRate.z = 0;

    	if (window.isKeyPressed(GLFW_KEY_DOWN)) {
    		rotationRate.x = 1;
    	}else if (window.isKeyPressed(GLFW_KEY_UP)) {
    		rotationRate.x = -1;
    	}
    	// should be a d
    	else if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
    		rotationRate.y = 1;
    	}else if (window.isKeyPressed(GLFW_KEY_LEFT)) {
    		rotationRate.y = -1;
    	}
    }

    public Vector3f getRotationRate() {
		return rotationRate;
	}
	public Vector3f getDisplInc() {
    	return displInc;
    }
}
