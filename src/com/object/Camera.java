package com.object;

import org.joml.Matrix3f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import com.utils.KeyboardInput;
import com.utils.MouseInput;

public class Camera {

	private static final float MOUSE_SENSITIVITY = 0.1f;
	
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
    // Only Yaw rotation is implemented so far
    // Multiply the view matrix(Translation by a rotation matrix
    public void movePosition(float offsetX, float offsetY, float offsetZ) {
    	//sin is y coordinate, cos is x coordinate
    	//https://gamedev.stackexchange.com/questions/90208/how-to-calculate-a-direction-vector-for-camera
    	//이건 아무래도 rotation을 먹여주는 거거든
    	//https://mathworld.wolfram.com/RotationMatrix.html
    	

        Matrix3f rotToTrslation = new Matrix3f();
        rotToTrslation.identity().rotate((float)Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
        .rotate((float)Math.toRadians(rotation.y), new Vector3f(0, 1, 0));

        position.x += rotToTrslation.m00 * offsetX  + rotToTrslation.m01 * offsetY + rotToTrslation.m02 * offsetZ;
        position.y += rotToTrslation.m10 * offsetX  + rotToTrslation.m11 * offsetY + rotToTrslation.m12 * offsetZ;
    	position.z += rotToTrslation.m20 * offsetX  + rotToTrslation.m21 * offsetY + rotToTrslation.m22 * offsetZ;
        
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
    public void updateKeyboard(KeyboardInput keyboardInput)
    {
		this.movePosition(keyboardInput.getDisplInc().x * 0.5f, keyboardInput.getDisplInc().y * 0.5f, keyboardInput.getDisplInc().z *0.5f);
//		System.out.println("Camera x :" + this.getPosition().x + ", y: " + this.getPosition().y + ", z: "+ this.getPosition().z);

    }
}