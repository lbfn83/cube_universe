package com.object;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.graphics.Window;
import com.utils.BufferUtils;
import com.utils.MouseInput;
import com.graphics.Texture;
import com.graphics.VertexArray;


public class Cube {

	
    private int displxInc = 0;

    private int displyInc = 0;

    private int displzInc = 0;

    private int scaleInc = 0;
    
    private CubeItem[] cubeItems;

	public CubeItem[] getCubeItems() {
		return cubeItems;
	}

	public void setCubeItems(CubeItem[] cubeItems) {
		this.cubeItems = cubeItems;
	}

	private int rotationX;

	private int rotationZ;

	private int rotationY;

	
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
    	cube_1.setPosition(3f, -0.2f, -20f);
    	
    	CubeItem cube_2 = new CubeItem(mesh2);
    	cube_2.setPosition(39.5f, 39.5f, -20f);
    	
    	CubeItem cube_3 = new CubeItem(mesh1);
    	cube_3.setPosition(1f, 0f, -5f);
    	
    	CubeItem cube_4 = new CubeItem(mesh1);
    	cube_4.setPosition(1f, -1.3f, -9f);
    	
    	CubeItem cube_5 = new CubeItem(mesh1);
    	cube_5.setPosition(-3f, -1.3f, -6f);
    	
    	CubeItem cube_6 = new CubeItem(mesh1);
    	cube_6.setPosition(-1f, 0f, -9f);
    	
    	cubeItems = new CubeItem[] { cube_1, cube_2, cube_3, cube_4, cube_5, cube_6 };
    	
    	
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
    
    // Cube's manual movement is restricted to rotational move 
    // translation move is only enabled in Camera class which is acutally processed in MouseInput class
    
    public void input(Window window) {
    	//        displyInc = 0;
    	//        displxInc = 0;
    	//        displzInc = 0;
    	scaleInc = 0;

    	rotationX = 0;
    	rotationY = 0;
    	rotationZ = 0;

    	if (window.isKeyPressed(GLFW_KEY_DOWN)) {
    		rotationX = 1;
    	}else if (window.isKeyPressed(GLFW_KEY_UP)) {
    		rotationX = -1;
    	}
    	// should be a d
    	else if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
    		rotationY = 1;
    	}else if (window.isKeyPressed(GLFW_KEY_LEFT)) {
    		rotationY = -1;
    	}

    	
    	//        else if (window.isKeyPressed(GLFW_KEY_Z)) {
    	//            scaleInc = -1;
    	//        } else if (window.isKeyPressed(GLFW_KEY_X)) {
    	//            scaleInc = 1;
    	//        } 
    	//        //should be w s
    	//        else if (window.isKeyPressed(GLFW_KEY_S)) {
    	//        	rotationX = 1;
    	//        }else if (window.isKeyPressed(GLFW_KEY_W)) {
    	//        	rotationX = -1;
    	//        }
    	//        // should be a d
    	//        else if (window.isKeyPressed(GLFW_KEY_D)) {
    	//        	rotationY = 1;
    	//        }else if (window.isKeyPressed(GLFW_KEY_A)) {
    	//        	rotationY = -1;
    	//        }
    	//        
    	//        else if (window.isKeyPressed(GLFW_KEY_R)) {
    	//        	rotationZ = 1;
    	//        }else if (window.isKeyPressed(GLFW_KEY_F)) {
    	//        	rotationZ = -1;
    	//        }
        
    }

    
    
    public void update() {
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
    			Vector3f displInc = each_cube.getDisplInc();
    			
    			// Update position
    			//itemPos should be deep-copied, but seems like joml lib doens't have implementation of clone
    		
    			 
    			Vector3f itemPos = each_cube.getPosition();
    			
    			itemPos.x = each_cube.getPosition().x;
    			itemPos.y = each_cube.getPosition().y;
    			itemPos.z = each_cube.getPosition().z;
    			
    			float posx = itemPos.x + displInc.x *0.05f;
    			float posy = itemPos.y + displInc.y *0.05f;
    			float posz = itemPos.z + displInc.z *0.05f;

    			each_cube.setPosition(posx, posy, posz);

//    			if(collisionCheck(each_cube))
//    			{
//    				//if collision, rollback
//    				each_cube.setPosition(itemPos.x, itemPos.y, itemPos.z);
//    			}
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
    			float rotation_x = each_cube.getRotation().x +  1.5f * rotationX; //
    			float rotation_y = each_cube.getRotation().y +  1.5f * rotationY; //1.5f *
    			float rotation_z = each_cube.getRotation().z +  1.5f * rotationZ;

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
    	
    	return distance < (float)(Math.sqrt(2));
    }

}

