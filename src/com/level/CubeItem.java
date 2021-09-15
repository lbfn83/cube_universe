package com.level;

import java.util.Random;

import org.joml.Vector3f;

import com.graphics.VertexArray;


public class CubeItem {
	
	private static int totalNumber = 0;

	public static final float moveUpdateSensitivity = 0.5f;

	private boolean destUpdate = false;
	
	private Random r;

	private final float step = 1f;
	
	private int cubeID;
	
    private final VertexArray mesh;
    
    private Vector3f position;
    
    private Vector3f destination;
    
    private Vector3f velocity;
    
	private float scale = 10f;

	private float randomMin = -Background.scale + (scale/org.joml.Math.sqrt(2));
	
	private float randomMax = Background.scale - (scale/org.joml.Math.sqrt(2));

    private Vector3f rotation;
    
    

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
    	
    	this.destination = destination;

    	//every time set up a new destination, its unit velocity vector is also be calculated
    	calcVelocity();
    }
    // This method is only called when destination is newly generated
    private void calcVelocity()
    {
    	Vector3f temp = new Vector3f(destination);
    	velocity = temp.sub(position);
    	velocity.normalize();
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
    // randomMin and Max is dependent on scale value
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
    
	public Vector3f getVelocity() {
		return velocity;
	}

	public boolean updateRandomMove(boolean initStage) {
		if(initStage)
		{
			this.setDestination(new Vector3f(randomNumGen(), randomNumGen(), randomNumGen()));
		}
		
		// 1. alternative to step variable : (Vector3f.length(velocity.x, velocity.y, velocity.z) 
		// 2. Cube is considered to be sphere for simplification
		// 3. when the cube is close to its destination, make sure it iterates in the update loop one more time  
		// and moves all the way up to destination before generating new destination
		else if(( destination.distance(position) <= step ) 
				|| (position.distance(new Vector3f(0f,0f,0f)) > (2 * Background.scale) /org.joml.Math.sqrt(2)))
		{
			destUpdate = true;
			//			System.out.println("Cube ID : "+ this.cubeID + "Velocity leng: " + velocity);
		}
		// setPosition is done in below logic, so returning false to skip the another upcoming setPosition in Cube.update() method
		if(destUpdate)
		{
			destUpdate = false;
			//			System.out.println("Cube ID : "+ this.cubeID + "dest: " + destination + " position: " + position);
			setPosition(destination.x, destination.y, destination.z);
			this.setDestination(new Vector3f(randomNumGen(), randomNumGen(), randomNumGen()));
			return false;
		}
		return true;
	}

	
	private float randomNumGen()
	{
		return randomMin + r.nextFloat() * (randomMax - randomMin);
		
	}
	
	public void setVelocityCollision(Vector3f velocity) {
		
		this.velocity = velocity;
		
		float xWeight, yWeight, zWeight, minWeight;
		// Derive Destination based on the velocity vector calculated from collision
		// calculate the minimum steps to take among three axis until it hits the wall of background ( predicted projection of this object) 
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
	}
}
