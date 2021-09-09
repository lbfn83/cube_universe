package com.object;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix3dc;
import org.joml.Matrix3fc;
import org.joml.Matrix3x2fc;
import org.joml.Matrix4dc;
import org.joml.Matrix4fc;
import org.joml.Matrix4x3fc;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector2f;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.joml.Vector3i;

import com.graphics.Window;
import com.utils.BufferUtils;
import com.utils.KeyboardInput;
import com.utils.MouseInput;
import com.graphics.Texture;
import com.graphics.VertexArray;


public class Cube {

    
    private CubeItem[] cubeItems;

	public CubeItem[] getCubeItems() {
		return cubeItems;
	}

	public void setCubeItems(CubeItem[] cubeItems) {
		this.cubeItems = cubeItems;
	}


	
    public Cube() {
    	float[] positions = new float[]{
    			// V0
    			-0.5f, 0.5f, 0.5f,
    			// V1
    			-0.5f, -0.5f, 0.5f,
    			// V2
    			0.5f, -0.5f, 0.5f,
    			// V3
    			0.5f, 0.5f, 0.5f,
    			
    			
    			// V4
    			-0.5f, 0.5f, -0.5f, 
    			// V5
    			-0.5f, -0.5f, -0.5f,
    			// V1
    			-0.5f, -0.5f, 0.5f,
    			// V0
    			-0.5f, 0.5f, 0.5f,
    			
    			
    			// V3
    			0.5f, 0.5f, 0.5f,
    			// V2
    			0.5f, -0.5f, 0.5f,
    			// V6
    			0.5f, -0.5f, -0.5f,
    			// V7
    			0.5f, 0.5f, -0.5f,
    			
    			
    			// V7
    			0.5f, 0.5f, -0.5f,
    			// V6
    			0.5f, -0.5f, -0.5f,
    			// V5
    			-0.5f, -0.5f, -0.5f,
    			// V4
    			-0.5f, 0.5f, -0.5f,

    			
     			// V1
    			-0.5f, -0.5f, 0.5f,
    			// V5
    			-0.5f, -0.5f, -0.5f,
    			// V6
    			0.5f, -0.5f, -0.5f,
    			// V2
    			0.5f, -0.5f, 0.5f,

    			
    			// V7
    			0.5f, 0.5f, -0.5f,
    			// V3
    			0.5f, 0.5f, 0.5f,
    			// V0
    			-0.5f, 0.5f, 0.5f,
    			// V4
    			-0.5f, 0.5f, -0.5f, 
    			
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
    	
    	//TODO Mesh for each cube should be created as each cube will contain different set of pictures
    	Texture texture1 = new Texture("res/3d_cube.jpg");
    	float[] textCoords1 = calcTextCoords(2, 3, texture1);
    	
    	Texture texture2 = new Texture("res/skybox.png");
    	float[] textCoords2 = calcTextCoords(3, 2, texture2);
    	
    	VertexArray mesh1 = new VertexArray(positions, indices, textCoords1,  texture1);
    	VertexArray mesh2 = new VertexArray(positions, indices, textCoords2,  texture2);
    	
    	CubeItem cube_1 = new CubeItem(mesh1);
    	cube_1.setPosition(-10f, -0.2f, -20f);
    	
    	CubeItem cube_2 = new CubeItem(mesh2);
    	cube_2.setPosition(20f, 15f, -20f);
    	
    	CubeItem cube_3 = new CubeItem(mesh1);
    	cube_3.setPosition(1f, 10f, 5f);
    	
    	CubeItem cube_4 = new CubeItem(mesh1);
    	cube_4.setPosition(10f, -13f, -9f);
    	
    	CubeItem cube_5 = new CubeItem(mesh1);
    	cube_5.setPosition(-3f, -1.3f, 19f);
    	
    	CubeItem cube_6 = new CubeItem(mesh1);
    	cube_6.setPosition(-1f, 0f, -9f);
    	
    
    	cubeItems = new CubeItem[] { cube_1, cube_2, cube_3, cube_4, cube_5, cube_6 };
    
    	float distance;
    	for(int i=0; i<6 ; i++)
    	{
    		for(int j=i+1 ; j < 6; j++)
    		{
    			distance = cubeItems[i].getPosition().distance(cubeItems[j].getPosition());
    			if(distance < cubeItems[i].getScale())
    			{
    				System.out.println("collide : " + i + " and " +j);
    			}
    		}
    	}
    	
    }
    
    //To extract coordinates of each image in a single texture and each texture contains multiple images 
    private float[] calcTextCoords( int numCols, int numRows, Texture texture) {

    	List<Float> positions = new ArrayList<>();
    	List<Float> textCoords = new ArrayList<>();
    	List<Integer> indices   = new ArrayList<>();

    	float imageWidth = (float)texture.getWidth();
    	float imageHeight = (float)texture.getHeight();
    	
    	float tileWidth = imageWidth / (float)numCols;
    	float tileHeight = imageHeight/ (float)numRows;

    	for(int i=0; i<numCols; i++) {
    		for(int j=0; j<numRows; j++ )
    		{

    			// Left Top vertex
    			textCoords.add((float)i*tileWidth/ imageWidth );
    			textCoords.add((float)j*tileHeight / imageHeight);

    			// Left Bottom vertex
    			textCoords.add((float)i*tileWidth / imageWidth );
    			textCoords.add((float)(j+1)*tileHeight / imageHeight);

    			// Right Bottom vertex
    			textCoords.add((float)(i+1)*tileWidth / imageWidth );
    			textCoords.add((float)(j+1)*tileHeight / imageHeight);

    			// Right Top vertex
    			textCoords.add((float)(i+1)*tileWidth / imageWidth );
    			textCoords.add((float)j*tileHeight / imageHeight);
    		}
    	}
    	return BufferUtils.listToArray(textCoords);
    }
    
    
    public void update(KeyboardInput keyboardInput) {
//    	if(cubeItems.length > 1)
//    	{
//    		System.out.println("The # of cubes are " + cubeItems.length);
//    	}
    	/*if there is any defined Keyboard input, move the cube according to that*/
    	
    	boolean manualInput = true;
    	if(manualInput)
    	{
    		
    		
    		
    		for (CubeItem each_cube : cubeItems) {
    			each_cube.updateRandomMove();
    			
    			// Update position
    			//itemPos should be deep-copied, but seems like joml lib doens't have implementation of clone
    			Vector3f displInc = each_cube.getDisplInc();
    			Vector3f itemPos = each_cube.getPosition();
    			
    			itemPos.x = each_cube.getPosition().x;
    			itemPos.y = each_cube.getPosition().y;
    			itemPos.z = each_cube.getPosition().z;

    			float posx = itemPos.x + displInc.x *(0.05f);
    			float posy = itemPos.y + displInc.y *(0.05f);
    			float posz = itemPos.z + displInc.z *(0.05f);
    			
    			each_cube.setPosition(posx, posy, posz);

    			if(collisionCheck(each_cube))
    			{	
    				//if Collision is just detected process
    				// otherwise direction vector was set up correctly in previous iteration
    				// so stick to it
    				if(!each_cube.getCollided())
    				{
    					each_cube.setCollided(true);
    					Vector3f dest = each_cube.getDestination();
    					//if collision occurs, simply negate
    					each_cube.setDestination(dest.negate());
    					//dispInc will also be updated
    					each_cube.setPosition(itemPos.x, itemPos.y, itemPos.z);
    					displInc = each_cube.getDisplInc();
    					posx = itemPos.x + displInc.x *(0.2f);
    	    			posy = itemPos.y + displInc.y *(0.2f);
    	    			posz = itemPos.z + displInc.z *(0.2f);
    				}
    			}else
    			{
    				if(each_cube.getCollided())
    				{
    					each_cube.setCollided(false);
    				}
    			}
    			
    			
    			
    			/*
    			// Update scale : should I keep this as movement of Z is doing the same function
    			float scale = each_cube.getScale();
    			scale += scaleInc * 0.05f;
    			if ( scale < 0 ) {
    				scale = 0;
    			}
    			each_cube.setScale(scale);
    			 */
    			// Update rotation angle
    			
    			float rotation_x = each_cube.getRotation().x +  1.5f * keyboardInput.getRotationRate().x; //
    			float rotation_y = each_cube.getRotation().y +  1.5f * keyboardInput.getRotationRate().y; //1.5f *
    			float rotation_z = each_cube.getRotation().z +  1.5f * keyboardInput.getRotationRate().z;

    			each_cube.setRotation(rotation_x, rotation_y, rotation_z);   			     
    			//    			System.out.println("x :" + each_cube.getPosition().x + ", y: " + each_cube.getPosition().y + ", z: "+ each_cube.getPosition().z);
    		}
    	}
    	/*No keyboard input then, each cube takes the random movement*/
    	else
    	{
    		for (CubeItem each_cube : cubeItems) {
    			each_cube.updateRandomMove();


    		}
    	}
    }


	private boolean collisionCheck(CubeItem cubeItem) {
    	
    	boolean collide = false;
    	
    	for(CubeItem each_cube : cubeItems)
    	{
    		if(cubeItem.getCubeID() != each_cube.getCubeID())
    		{
    			collide = intersect(each_cube, cubeItem);
    			if(collide)
    				break;
    		}
    	}
    	return collide;
    }
    
    
    
    private boolean intersect(CubeItem cube_a, CubeItem cube_b)
    {
    	float x1, x2;
    	float y1, y2;
    	float z1, z2;
    	
    	x1 = cube_a.getPosition().x;
    	x2 = cube_b.getPosition().x;
    	y1 = cube_a.getPosition().y;
    	y2 = cube_b.getPosition().y;
    	z1 = cube_a.getPosition().z;
    	z2 = cube_b.getPosition().z;

    	float distance =  (float)Math.sqrt((x1 - x2) * (x1 - x2) + 
    			(y1 - y2) * (y1 - y2) +
    			(z1 - z2) * (z1 - z2));
    	
    	return distance <( cube_b.getScale());
    }

}

