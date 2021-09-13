package com.object;

import java.util.Random;

import org.joml.Vector3f;
import org.joml.Vector3fc;

import com.graphics.VertexArray;


import org.joml.Vector3f;

public class CubeItem {
	private static int totalNumber = 0;

	public static final float moveUpdateSensitivity = 0.5f;

	private boolean destUpdate = false;
	
	private Random r;

	private final float step = 1f;
	
	private int cubeID;
	
    private final VertexArray mesh;// mesh가 왜 필요하지?어짜피 같은 VAO VBO에 다른 좌표만 넣어서 반복해서 넣어줄건데
    
    private Vector3f position;
    
    private Vector3f destination;
    
    private Vector3f velocity;

    
	private float scale = 10f;

	// half of cube's size should be taken into account when determine the destionation of it
	// but where this should be calculated? after scale is set!
	private float randomMin = -Background.scale + (scale/2 );//org.joml.Math.sqrt(2)
	
	private float randomMax = Background.scale - (scale/2 );

    private Vector3f rotation;
    
    public void setVelocityCollision(Vector3f velocity) {
    	this.velocity = velocity;
    	
    	float xWeight, yWeight, zWeight, minWeight;
    	
    	// To calculate how far the cubeitem is from the wall of background in terms of each axis
    	// Then calculate the minimum value among these so that the destination coordinates can be set up somewhere inside the cube
    	if(velocity.x < 0)
    	{
    		xWeight = (randomMin - position.x)/velocity.x;    		
    	}else
    	{
    		xWeight = (randomMax - position.x)/velocity.x; 
    	}
    	
    	if(velocity.y < 0)
    	{
    		yWeight = (randomMin - position.y)/velocity.y;    		
    	}else
    	{
    		yWeight = (randomMax - position.y)/velocity.y; 
    	}
    	
    	if(velocity.z < 0)
    	{
    		zWeight = (randomMin - position.z)/velocity.z;    		
    	}else
    	{
    		zWeight = (randomMax - position.z)/velocity.z; 
    	}
    	
    	minWeight = Math.min(Math.min(xWeight, yWeight), zWeight); 
    	
    	
    	destination.x = position.x + velocity.x * minWeight ;
    	destination.y = position.y + velocity.y * minWeight;
    	destination.z = position.z + velocity.z * minWeight;
    	
//    	if( (Math.abs(destination.x) > 39.3934)||(Math.abs(destination.y) > 39.3934) || (Math.abs(destination.z) > 39.3934)   )
//    	{
//    	System.out.println("abnormal");
//    	}
//    	System.out.println("Cube ID : "+ this.cubeID + "destination after collision: " +destination);
//    
    }
    

    public CubeItem(VertexArray va) {
    	
    	cubeID = (totalNumber++);
        this.mesh = va;
        position = new Vector3f();
        rotation = new Vector3f();
        r= new Random();
        destination = new Vector3f(randomNumGen(), randomNumGen(), randomNumGen());

    }

    public int getCubeID() {
    	return cubeID;
    }
    
    public Vector3f getDestination() {
    	return destination;
    }
    
    public void setDestination(Vector3f destination) {
    	//every time set the position, its unit vector for movement should be calculated
    	//This method either invoked by RandomMovement where cube almost reaches its dest
    	// or when collision happened
    	
    	//이거 굉장히 에러 프론이네.. 
    	//dest 를 먼저 업데이트 한 후에 . velocity를 해줘야 하는데...
    	this.destination = destination;

    	calcVelocity();
    }
    
    public Vector3f getPosition() {
    	
        return position;
    }
    // Only when you set position, direction vector is calculated... 
    // does it sound reasonable to you?
    
    private void calcVelocity()
    {
    	Vector3f temp = new Vector3f(destination);
		velocity = temp.sub(position);
		velocity.normalize();
    }
    
    public void setPosition(float x, float y, float z) {
    	//every time set the position, its unit vector for movement shouldn't be calculated
    	
//    	calcvelocity();
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
    	randomMin = -Background.scale + (scale/2 * org.joml.Math.sqrt(2));
    	randomMax = Background.scale - (scale/2 * org.joml.Math.sqrt(2));
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
		if ( z > 360 ) {
			z = 0;
		}
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }
    
    public VertexArray getMesh() {
        return mesh;
    }
    
    private float randomNumGen()
	{
		return randomMin + r.nextFloat() * (randomMax - randomMin);

	}
    
	public Vector3f getVelocity() {
		return velocity;
	}

	public boolean updateRandomMove(boolean initStage) {
		//position.distance(destination)
		// if you use distance... step 안에 들면.. 
		//+ step
		if(initStage)
		{
			this.setDestination(new Vector3f(randomNumGen(), randomNumGen(), randomNumGen()));
		}
		else if(( destination.distance(position) <= (Vector3f.length(velocity.x* moveUpdateSensitivity, velocity.y* moveUpdateSensitivity, velocity.z* moveUpdateSensitivity) )) 
				|| (position.distance(new Vector3f(0f,0f,0f)) > 2 * (Background.scale) /1.4 ))
		{
			destUpdate = true;
			System.out.println("Cube ID : "+ this.cubeID + "Velocity leng: " + velocity);
		}
		
		if(destUpdate)
		{
			destUpdate = false;
			System.out.println("Cube ID : "+ this.cubeID + "dest: " + destination + " position: " + position);
			setPosition(destination.x, destination.y, destination.z);
			this.setDestination(new Vector3f(randomNumGen(), randomNumGen(), randomNumGen()));
			return false;
		}
		return true;
	}

	
}
