package com.object;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
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

	
	private CubeItem collisionCube;
	
    private List<CubeItem> cubeItems;

	public List<CubeItem> getCubeItems() {
		return cubeItems;
	}

	public void setCubeItems(List<CubeItem> cubeItems) {
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
    	cube_1.updateRandomMove(true);
    	cube_1.setScale(15f);
    	
    	CubeItem cube_2 = new CubeItem(mesh2);
    	cube_2.setPosition(20f, 15f, -20f);
    	cube_2.updateRandomMove(true);
    	cube_2.setScale(15f);
    	
    	CubeItem cube_3 = new CubeItem(mesh1);
    	cube_3.setPosition(1f, 10f, 5f);
    	cube_3.updateRandomMove(true);
    	cube_3.setScale(6f);
    	
    	CubeItem cube_4 = new CubeItem(mesh1);
    	cube_4.setPosition(10f, -13f, -9f);
    	cube_4.updateRandomMove(true);
    	
    	CubeItem cube_5 = new CubeItem(mesh1);
    	cube_5.setPosition(-3f, -1.3f, 19f);
    	cube_5.updateRandomMove(true);
    	
    	CubeItem cube_6 = new CubeItem(mesh1);
    	cube_6.setPosition(-1f, 0f, -9f);
    	cube_6.updateRandomMove(true);
    
    	cubeItems = new ArrayList<CubeItem>();
    	cubeItems.add(cube_1);
    	cubeItems.add(cube_2);
//    	cubeItems.add(cube_3);
//    	cubeItems.add(cube_4);
//    	cubeItems.add(cube_5);
//    	cubeItems.add(cube_6);
//    			{ cube_1, cube_2, cube_3, cube_4, cube_5, cube_6};
    	float distance;
//    	for(int i=0; i<6 ; i++)
//    	{
//    		for(int j=i+1 ; j < 6; j++)
//    		{
//    			distance = cubeItems[i].getPosition().distance(cubeItems[j].getPosition());
//    			if(distance < cubeItems[i].getScale())
//    			{
//    				System.out.println("collide : " + i + " and " +j);
//    			}
//    		}
//    	}
    	
    }
    
    //To extract coordinates of each image in a single texture and each texture contains multiple images 
    private float[] calcTextCoords( int numCols, int numRows, Texture texture) {
    	
    	List<Float> positions = new ArrayList<>();
    	List<Float> textCoords = new ArrayList<>();
    	
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
    
   

    public void update(KeyboardInput keyboardInput) throws CloneNotSupportedException {
    	
    	//    	if(cubeItems.length > 1)
    	//    	{
    	//    		System.out.println("The # of cubes are " + cubeItems.length);
    	//    	}
    	collisionCheck();
    	
    	for (CubeItem each_cube : cubeItems) {
// before there is small diff between position and destination where new destination should be calculated
    		// make sure cube must first move all the way to the destination.
    		// The new destination coordinates is configured one more iteration of update after distance detection
    		if(each_cube.updateRandomMove(false))
    		{
    			Vector3f velo = each_cube.getVelocity();

    			Vector3f itemPos = each_cube.getPosition();

    			itemPos.x = each_cube.getPosition().x;
    			itemPos.y = each_cube.getPosition().y;
    			itemPos.z = each_cube.getPosition().z;

    			float posx = itemPos.x + velo.x *(CubeItem.moveUpdateSensitivity);
    			float posy = itemPos.y + velo.y *(CubeItem.moveUpdateSensitivity);
    			float posz = itemPos.z + velo.z *(CubeItem.moveUpdateSensitivity);

    			each_cube.setPosition(posx, posy, posz);


    			/*debugging block*/
    			if(each_cube.getCubeID() == 0)
    			{
    				//				if( !previousDispInc.equals(displInc))
    				//					System.out.println("disp : " + displInc);
    				//				previousDispInc = new Vector3f(displInc);
    				//				System.out.println( "Position : " + each_cube.getPosition());
    				//System.out.println( "Dest : " + each_cube.getDestination());
    			}
    			/***************d*d*****/



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

    		}
    	}
    	
    	
    	
    	
    	
    }



    /*
     * collision resolution
     * 0) Collision check and if two cubes are collided, 
     * 1) move colliding cubes out of collided position ( new variable required : how far this cube should be moved to be out of collision area ) 
     * 2) Use Newton's law to determine velocity vector of each cube after collision
     * 
     * */

	private void collisionCheck() {

		//Nothing to collide since there is only single object
		if(cubeItems.size() == 1)
		{
			return;
		}
		// The collision resolution algorithm here is based on the code I found below link
		// https://github.com/Studiofreya/code-samples/blob/master/opengl/collisiondetect/collisiondetect.cpp
		// https://studiofreya.com/3d-math-and-physics/simple-sphere-sphere-collision-detection-and-collision-response/
		for(int i = 0; i < cubeItems.size(); i++)
		{
			for(int j = i+1; j < cubeItems.size(); j++)
			{
				float collisionCalc = intersect(cubeItems.get(i), cubeItems.get(j));
				if( collisionCalc > 0)
				{
					Vector3f posVec = new Vector3f();
					Vector3f posVecNegate = new Vector3f();
					
					Vector3f v1x = new Vector3f();  
					Vector3f v1y = new Vector3f();
					Vector3f v2x = new Vector3f();  
					Vector3f v2y = new Vector3f();
					Vector3f aVel = new Vector3f();
					Vector3f bVel = new Vector3f();

					CubeItem a = cubeItems.get(i);
					CubeItem b = cubeItems.get(j);
					
					
					/* From where the collision occurs, derive another two axis based vector system : v1x, v1y from cube a / v2x, v2y from cube b
					*/
					
					Vector3f aPos = a.getPosition();
					Vector3f bPos = b.getPosition();
					
					// Cube A perspective, derive two vectors 
					aPos.sub(bPos, posVec);

					posVec.normalize();
					
					Vector3f aCurrVel = a.getVelocity();
					
				    posVec.mul(posVec.dot(aCurrVel), v1x) ;
				    
				    //v1y = v1(a->vel) - v1x( x * x.dot(v1));
				    aCurrVel.sub(v1x, v1y);

				    
				    
				   // Cube B perspective, derive two vectors 
				    posVec.negate(posVecNegate);
				    
				    Vector3f bCurrVel = b.getVelocity();
					
				    posVecNegate.mul(posVecNegate.dot(bCurrVel), v2x) ;
				    
				    bCurrVel.sub(v2x, v2y);

				    
				    // Scale is treated as mass of specific object 
				    //Vector3( v1x*(m1-m2)/(m1+m2) + v2x*(2*m2)/(m1+m2) + v1y )
				    Vector3f temp = new Vector3f();
				    v1x.mul((a.getScale()-b.getScale())/(a.getScale()+b.getScale()), temp);
				    aVel.add(temp);
				    v2x.mul((2*b.getScale())/(a.getScale()+b.getScale()), temp);
				    aVel.add(temp);
				    aVel.add(v1y);
				    
				    //Vector3( v1x*(2*m1)/(m1+m2) + v2x*(m2-m1)/(m1+m2) + v2y )
				    v1x.mul((2*a.getScale())/(a.getScale()+b.getScale()), temp);
				    bVel.add(temp);
				    v2x.mul((b.getScale()-a.getScale())/(a.getScale()+b.getScale()), temp);
				    bVel.add(temp);
				    bVel.add(v2y);
				    
				    //move colliding cubes out of collided position 
				    posVec.mul(collisionCalc/2);
				    a.setPosition(aPos.x + posVec.x, aPos.y + posVec.y, aPos.z + posVec.z);
				    
				    posVecNegate.mul(collisionCalc/2);
				    b.setPosition(bPos.x + posVecNegate.x, bPos.y + posVecNegate.y, bPos.z + posVecNegate.z);
				    
				    a.setVelocityCollision(aVel);
				    b.setVelocityCollision(bVel);

				}
			}
		}
	}
    		


    
    
    
    private float intersect(CubeItem cube_a, CubeItem cube_b)
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

    	// To simplify the collision detection logic, the algorithm treats the cube as sphere looking object
    	// however, the root of 2 ( which is supposed to be the exact weight for the radius calculation when it comes to sphere)
    	// seems to make the collision event is detected a bit early considering the objects are cubes
    	// so change the value to 1.2f
    	
    	float collisionCalc = (cube_b.getScale()*1.2f/2 + cube_a.getScale()*1.2f/2)  - distance;
    	return collisionCalc  ;
    }

}

