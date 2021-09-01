package com.level;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;
import com.graphics.Window;
import com.utils.BufferUtils;
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

	private Texture texture; 
	
	
    public Cube() {
    	// Create the Mesh
    	float[] positions = new float[]{
    			// V0
    			-0.5f, 0.5f, 0.5f,
    			// V1
    			-0.5f, -0.5f, 0.5f,
    			// V2
    			0.5f, -0.5f, 0.5f,
    			// V3
    			0.5f, 0.5f, 0.5f,
//    			
    			
    			
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

    			
//    			
     			// V1
    			-0.5f, -0.5f, 0.5f,
    			// V5
    			-0.5f, -0.5f, -0.5f,
    			// V6
    			0.5f, -0.5f, -0.5f,
    			// V2
    			0.5f, -0.5f, 0.5f,
//
    			
    			
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
    	
    	//TODO 6개의 texture를 면마다 잡아서 여기서 하나의 cube를 만들ㅇ저구
    	texture = new Texture("res/3d_cube.jpg");
    	float[] textCoords = calcTextCoords(2, 3);

    	
    	VertexArray mesh = new VertexArray(positions, indices, textCoords,  texture);
    	
    	CubeItem cube_1 = new CubeItem(mesh);
    	cube_1.setPosition(3f, 2f, -20f);
    	
    	CubeItem cube_2 = new CubeItem(mesh);
    	cube_2.setPosition(3f, 5f, -20f);
    	
    	CubeItem cube_3 = new CubeItem(mesh);
    	cube_3.setPosition(1f, 0f, -5f);
    	
    	CubeItem cube_4 = new CubeItem(mesh);
    	cube_4.setPosition(1f, -1.3f, -9f);
    	
    	CubeItem cube_5 = new CubeItem(mesh);
    	cube_5.setPosition(-3f, -1.3f, -6f);
    	
    	CubeItem cube_6 = new CubeItem(mesh);
    	cube_6.setPosition(-1f, 0f, -9f);
    	
    	cubeItems = new CubeItem[] { cube_1, cube_2, cube_3, cube_4, cube_5, cube_6 };
    }
    
    private float[] calcTextCoords( int numCols, int numRows) {

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
    
    public void init(Window window) throws Exception {
    }

    public void input(Window window) {
        displyInc = 0;
        displxInc = 0;
        displzInc = 0;
        scaleInc = 0;
        
        rotationX = 0;
        rotationY = 0;
        rotationZ = 0;
        
        if (window.isKeyPressed(GLFW_KEY_UP)) {
            displyInc = 1;
        } else if (window.isKeyPressed(GLFW_KEY_DOWN)) {
            displyInc = -1;
        } else if (window.isKeyPressed(GLFW_KEY_LEFT)) {
            displxInc = -1;
        } else if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
            displxInc = 1;
        } 
        //should be q e
        else if (window.isKeyPressed(GLFW_KEY_Q)) {
            displzInc = -1;
        } else if (window.isKeyPressed(GLFW_KEY_E)) {
            displzInc = 1;
        } 
        
        else if (window.isKeyPressed(GLFW_KEY_Z)) {
            scaleInc = -1;
        } else if (window.isKeyPressed(GLFW_KEY_X)) {
            scaleInc = 1;
        } 
        //should be w s
        else if (window.isKeyPressed(GLFW_KEY_S)) {
        	rotationX = 1;
        }else if (window.isKeyPressed(GLFW_KEY_W)) {
        	rotationX = -1;
        }
        // should be a d
        else if (window.isKeyPressed(GLFW_KEY_D)) {
        	rotationY = 1;
        }else if (window.isKeyPressed(GLFW_KEY_A)) {
        	rotationY = -1;
        }
        
    }

    public void update() {
//    	if(cubeItems.length > 1)
//    	{
//    		System.out.println("The # of gameitems are " + cubeItems.length);
//    	}
        for (CubeItem each_cube : cubeItems) {
            // Update position
            Vector3f itemPos = each_cube.getPosition();
            float posx = itemPos.x + displxInc * 0.05f;
            float posy = itemPos.y + displyInc * 0.05f;
            float posz = itemPos.z + displzInc * 0.05f;
            each_cube.setPosition(posx, posy, posz);
            
            // Update scale
            float scale = each_cube.getScale();
            scale += scaleInc * 0.05f;
            if ( scale < 0 ) {
                scale = 0;
            }
            each_cube.setScale(scale);
            
            // Update rotation angle
            float rotation_x = each_cube.getRotation().x +  1.5f * rotationX; //
            float rotation_y = each_cube.getRotation().y +  1.5f * rotationY; //1.5f *
            
            if ( rotation_x > 360 ) {
            	rotation_x = 0;
            }
            if ( rotation_y > 360 ) {
            	rotation_y = 0;
            }
            each_cube.setRotation(rotation_x, rotation_y, 0);        
            System.out.println("x :" + each_cube.getPosition().x + ", y: " + each_cube.getPosition().y + ", z: "+ each_cube.getPosition().z);
        }
    }

}

