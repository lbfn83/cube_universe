package com.level;

import org.joml.Vector3f;

import com.graphics.VertexArray;


import org.joml.Vector3f;

public class CubeItem {

	private static int totalNumber = 0;
	
	public int getCubeID() {
		return cubeID;
	}


	private int cubeID;
	
    private final VertexArray mesh;// mesh가 왜 필요하지?어짜피 같은 VAO VBO에 다른 좌표만 넣어서 반복해서 넣어줄건데
    
    private final Vector3f position;
    
    private float scale;

    private final Vector3f rotation;

    public CubeItem(VertexArray va) {
    	cubeID = (totalNumber++);
        this.mesh = va;
        position = new Vector3f();
        scale = 1;
        rotation = new Vector3f();
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
    	
    	boolean overTheLimit = false;
    	if ( x > 4f )
    	{
    		overTheLimit = true;
    		x = 4f;
    	}
    	if ( x < -4f )
    	{
    		overTheLimit = true;
    		x = -4f;
    	}
    	if ( y > 1.8f )
    	{
    		overTheLimit = true;
    		y = 1.8f;
    	}
    	if ( y < -1.8 )
    	{
    		overTheLimit = true;
    		y = -1.8f;
    	}
    	if ( z > -5f )
    	{
    		overTheLimit = true;
    		z = -5f;
    	}
    	if ( z < -15f )
    	{
    		overTheLimit = true;
    		z = -15f;
    	}
    	
    	if(overTheLimit)
    		System.out.println("Cube Item " + cubeID + "'s coordination is over the limit so it is adjusted");
    	
    	
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
		if ( x > 360 ) {
			x = 0;
		}
		if ( y > 360 ) {
			y = 0;
		}
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }
    
    public VertexArray getMesh() {
        return mesh;
    }

	public void randomMove() {
		// TODO Auto-generated method stub
		
	}
	
}
