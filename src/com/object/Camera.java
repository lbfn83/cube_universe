package com.object;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.utils.MouseInput;

public class Camera {

	private static final float MOUSE_SENSITIVITY = 0.2f;
	
    private final Vector3f position;
    
    private final Vector3f rotation;
    
    public Camera() {
        position = new Vector3f();
        rotation = new Vector3f();
    }
    public Camera(Vector3f position)
    {
    	this.position = position;
    	rotation = new Vector3f();
    }
    
    
    public Camera(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        position.x = x;
        position.y = y;
        position.z = z;
    }
    
    public void movePosition(float offsetX, float offsetY, float offsetZ) {
        if ( offsetZ != 0 ) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y)) * -1.0f * offsetZ;
            position.z += (float)Math.cos(Math.toRadians(rotation.y)) * offsetZ;
        }
        if ( offsetX != 0) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * offsetX;
            position.z += (float)Math.cos(Math.toRadians(rotation.y - 90)) * offsetX;
        }
        position.y += offsetY;
        
        
        if ( position.x > Background.scale - 0.5f )
        {
        	position.x =  Background.scale - 0.5f;
        }
        if ( position.x < - (Background.scale - 0.5f) )
        {
        	position.x = -( Background.scale - 0.5f);
        }
        if ( position.y > Background.scale - 0.5f )
        {
        	position.y =  Background.scale - 0.5f;
        }
        if ( position.y < - (Background.scale - 0.5f) )
        {
        	position.y = -( Background.scale - 0.5f);
        }
        if ( position.z > Background.scale - 0.5f )
        {
        	position.z =  Background.scale - 0.5f ;
        }
        if ( position.z < - (Background.scale - 0.5f) )
        {
        	position.z = -( Background.scale - 0.5f);
        }
    }

    public Vector3f getRotation() {
        return rotation;
    }
    
    public void setRotation(float x, float y, float z) {
        rotation.x = x;
        rotation.y = y;
        rotation.z = z;
    }

    public void moveRotation(float offsetX, float offsetY, float offsetZ) {
        rotation.x += offsetX;
        rotation.y += offsetY;
        rotation.z += offsetZ;
    }
    public void updateMouse(MouseInput mouseinput)
    {
    	if(mouseinput.isRightButtonPressed())
    	{
    		// x rotation or y rotation and how far should it rotate
    		Vector2f rotVec = mouseinput.getDisplVec();
    		
    		this.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
    	}    	
    }
    public void updateKeyboard(MouseInput mouseinput)
    {
		this.movePosition(mouseinput.getDisplInc().x * 0.5f, mouseinput.getDisplInc().y * 0.5f, mouseinput.getDisplInc().z * 0.5f);
//		System.out.println("Camera x :" + this.getPosition().x + ", y: " + this.getPosition().y + ", z: "+ this.getPosition().z);

    }
}