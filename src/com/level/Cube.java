package com.level;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector3f;
import com.graphics.Window;
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
    			// V4
    			-0.5f, 0.5f, -0.5f,
    			// V5
    			0.5f, 0.5f, -0.5f,
    			// V6
    			-0.5f, -0.5f, -0.5f,
    			// V7
    			0.5f, -0.5f, -0.5f,
    			
    			// For text coords in top face
    			// V8: V4 repeated
    			-0.5f, 0.5f, -0.5f,
    			// V9: V5 repeated
    			0.5f, 0.5f, -0.5f,
    			// V10: V0 repeated
    			-0.5f, 0.5f, 0.5f,
    			// V11: V3 repeated
    			0.5f, 0.5f, 0.5f,
    			
    			// For text coords in right face
    			// V12: V3 repeated
    			0.5f, 0.5f, 0.5f,
    			// V13: V2 repeated
    			0.5f, -0.5f, 0.5f,
    			
    			// For text coords in left face
    			// V14: V0 repeated
    			-0.5f, 0.5f, 0.5f,
    			// V15: V1 repeated
    			-0.5f, -0.5f, 0.5f,
    			
    			// For text coords in bottom face
    			// V16: V6 repeated
    			-0.5f, -0.5f, -0.5f,
    			// V17: V7 repeated
    			0.5f, -0.5f, -0.5f,
    			// V18: V1 repeated
    			-0.5f, -0.5f, 0.5f,
    			// V19: V2 repeated
    			0.5f, -0.5f, 0.5f,
    	};
    	float[] textCoords = new float[]{
    			0.5f, 0.5f,
    			0.0f, 0.5f,
    			0.0f, 0.0f,
    			0.5f, 0.0f,
    			
    			0.0f, 0.0f,
    			0.5f, 0.0f,
    			0.0f, 0.5f,
    			0.5f, 0.5f,
    			
    			// For text coords in top face
    			0.0f, 0.5f,
    			0.5f, 0.5f,
    			0.0f, 1.0f,
    			0.5f, 1.0f,
    			
    			// For text coords in right face
    			0f, 0.0f,
    			0f, 0.5f,
    			
    			// For text coords in left face
    			0.5f, 0.0f,
    			0.5f, 0.5f,
    			
    			// For text coords in bottom face
    			0.5f, 0.0f,
    			1.0f, 0.0f,
    			0.5f, 0.5f,
    			1.0f, 0.5f,
    	};
    	int[] indices = new int[]{
    			// Front face
    			0, 1, 3, 3, 1, 2,
    			// Top Face
    			8, 10, 11, 9, 8, 11,
    			// Right face
    			12, 13, 7, 5, 12, 7,
    			// Left face
    			14, 15, 6, 4, 14, 6,
    			// Bottom face
    			16, 18, 19, 17, 16, 19,
    			// Back face
    			4, 6, 7, 5, 4, 7,
    	};
    	// 이건 그래픽 파일을 넣어줘서 해주면 되는거고
    	// texture는 여기 또 왜 넣어주는거지?
    	//render 시에 bind unbind를 하긴 해야하지만.
    	//오 이건 내가 쓴 vertex array 보다는 ... 
    	
    	//TODO 6개의 texture를 면마다 잡아서 여기서 하나의 cube를 만들ㅇ저구
    	texture = new Texture("res/grassblock.png");
    	
    	VertexArray mesh = new VertexArray(positions, indices, textCoords,  texture);
    	
    	CubeItem cube_1 = new CubeItem(mesh);
    	cube_1.setPosition(0, 0, -2);
    	
    	CubeItem cube_2 = new CubeItem(mesh);
    	cube_2.setPosition(3, 5, -2);
    	
    	cubeItems = new CubeItem[] { cube_1, cube_2 };
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
    	if(cubeItems.length > 1)
    	{
    		System.out.println("The # of gameitems are " + cubeItems.length);
    	}
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
        }
    }

}

