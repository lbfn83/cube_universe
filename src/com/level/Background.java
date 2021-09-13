package com.level;

import org.joml.Vector3f;

import com.graphics.Texture;
import com.graphics.VertexArray;

public class Background  {


	private final VertexArray mesh;

	private final Vector3f position;

	// In case of increasing the scale, beware of 'far' argument in projection matrix
	// which far is not big enough to accomodate the size after scaling, whole background wouldn't be displayed
	public static final float scale  = 30f;

	private final Vector3f rotation;

	public Background()
	{   	
		
		position = new Vector3f();
		rotation = new Vector3f();

		float[] positions = new float[]{
				// Front Face
				-1.f, 1.f, 1.f,
				-1.f, -1.f, 1.f,
				1.f, -1.f, 1.f,
				1.f, 1.f, 1.f,

				//Left Face
				-1.f, 1.f, 1.f,
				-1.f, -1.f, 1.f,
				-1.f, -1.f, -1.f,
				-1.f, 1.f, -1.f,

				// Right Face
				1.f, 1.f, 1.f,
				1.f, -1.f, 1.f,
				1.f, -1.f, -1.f,
				1.f, 1.f, -1.f,

				// Back Face
				-1.f, 1.f, -1.f,
				-1.f, -1.f, -1.f,
				1.f, -1.f, -1.f,
				1.f, 1.f, -1.f,

				// Top Face
				-1.f, 1.f, -1.f,
				-1.f, 1.f, 1.f,
				1.f, 1.f, 1.f,
				1.f, 1.f, -1.f,

				// Bottom Face
				-1.f, -1.f, -1.f,
				-1.f, -1.f, 1.f,
				1.f, -1.f, 1.f,
				1.f, -1.f, -1.f,
		};


		// no brainer rule for setting up the index of each rect : {+0, +1, +2} {+3, +0, +2}
		int[] indices = new int[]{
    			0, 1, 2, 3, 0, 2,
    			
    			4, 5, 6, 7, 4, 6,

    			8, 9, 10, 11, 8, 10,

    			12, 13, 14, 15, 12, 14,

    			16, 17, 18, 19, 16, 18,
    			
    			20, 21, 22, 23, 20, 22
		};

		float[] textCoords = new float[]{
				// Front Face
				0.333333f, 0.5f,
				0.333333f, 0.f,
				0.f, 0.f,
				0.f, 0.5f,

				// Left Face
				0.f, 1.f,
				0.f, 0.5f,
				0.333333f, 0.5f,
				0.333333f, 1.f,

				// Right Face
				1.f, 1.f,
				1.f , 0.5f,
				0.666666f, 0.5f,
				0.666666f, 1.f,

				//	Back Face
				0.333333f, 1.f,
				0.333333f, 0.5f,
				0.666666f, 0.5f,
				0.666666f, 1.f,

				//Top Face
				0.333333f, 0.5f,
				0.666666f, 0.5f,
				0.666666f, 0.f,
				0.333333f, 0.f,

				//Bottom Face
				0.666666f, 0.5f,
				0.666666f, 0.f,
				1.f, 0.f,
				1.f, 0.5f,
		};
		Texture texture = new Texture("res/universe.jpg");


		mesh = new VertexArray(positions, indices, textCoords,  texture);
	}


	public Vector3f getRotation() {
		return rotation;
	}

	public float getScale() {
		return scale;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(float x, float y, float z) 
	{

		this.position.x = x;
		this.position.y = y;
		this.position.z = z;
	}


	public VertexArray getMesh() {
		return mesh;
	}


}
