package com.object;

import java.util.Random;

import org.joml.Vector3f;

import com.graphics.VertexArray;


import org.joml.Vector3f;

public class CubeItem {
	private static int totalNumber = 0;
	
	private boolean collided = false;
	
	private final float randomMin = -Background.scale*0.95f;
	
	private final float randomMax = Background.scale*0.95f;
	
	private Random r;

	private final float step = 1f;
	
	private int cubeID;
	
    private final VertexArray mesh;// mesh가 왜 필요하지?어짜피 같은 VAO VBO에 다른 좌표만 넣어서 반복해서 넣어줄건데
    
    private Vector3f pivot_position;
    
    private Vector3f position;
    
    private Vector3f destination;
    
    private Vector3f displInc;

	private float scale = 4f;

    private Vector3f rotation;

    public CubeItem(VertexArray va) {
    	
    	cubeID = (totalNumber++);
        this.mesh = va;
        pivot_position = new Vector3f();
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
//    	if(destination.x > randomMax )
//    	{
//    		destination.x = randomMax;
//    	}
    	
    	
    	this.destination = destination;
    }
    
    public Vector3f getPosition() {
    	
        return position;
    }
    
    public void setPosition(float x, float y, float z) {
    	//every time set the position, its unit vector for movement should be calculated
    	Vector3f temp = new Vector3f(destination);
		displInc = temp.sub(position);
		displInc.normalize();
		
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
    
	public Vector3f getDisplInc() {
		return displInc;
	}

	public void updateRandomMove() {
		// TODO 이걸 항상 해줄께 아니자나
		if(position.distance(destination) < step)
		{
			destination = new Vector3f(randomNumGen(), randomNumGen(), randomNumGen());

//			System.out.println(displInc);
		}
		
	}

	public boolean getCollided() {
		// TODO Auto-generated method stub
		return collided;
	}

	public void setCollided(boolean b) {
		// TODO Auto-generated method stub
		collided = b;
		
	}
	
}
