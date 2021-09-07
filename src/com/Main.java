package com;


import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.glfw.Callbacks.*;
import java.util.Date;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import com.utils.MouseInput;

import com.graphics.Shader;
import com.graphics.Window;
import com.object.Background;
import com.object.Camera;
import com.object.Cube;
import com.object.CubeItem;
import com.utils.Timer;
import com.graphics.MVPMatrix;



public class Main implements Runnable {
	
	private Window window;
	private MVPMatrix mvpMatrix;
	private Camera camera;
	
	private int width = 1280;
	private int height = 720;

	private Thread thread;
	
	private Cube cubeobj;
	private Background bg;
	
    public static final int TARGET_UPS = 90;
    private final Timer timer= new Timer();
    
    private final MouseInput mouseInput = new MouseInput();
    
	public void start() throws InterruptedException {
		
		thread = new Thread(this, "3D Cube");
		thread.start();
	}
	// game Engine part
	private void init() {
		//Window creation
		window = new Window("3D Cube with pic", width, height);
		window.init();
		
		cubeobj = new Cube();
		camera = new Camera(new Vector3f(0f, 0f, 0f));
		
//		camera.setPosition(0.65f, 1.15f, 4.34f);
		
		bg = new Background();
		bg.setPosition(-0f, -0f, -0f);
		
		
		mvpMatrix = new MVPMatrix();
		Shader.loadAll();
		window.setClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glActiveTexture(GL_TEXTURE1);

		timer.init();
		mouseInput.init(window);
		
	}
	
	protected void runningLoop() {
		float elapsedTime;
		float accumulator = 0f;
		float interval = 1f / TARGET_UPS;

		while ( !window.windowShouldClose()) {
			elapsedTime = timer.getElapsedTime();
			accumulator += elapsedTime;

			cubeobj.input(window);
			//Get the delta of mouse cursor movement
			mouseInput.input(window);

			camera.update(mouseInput);
			
			while (accumulator >= interval) {
				accumulator -= interval;
				cubeobj.update(mouseInput);
			}

			render();

			//	            if ( !window.isvSync() ) {
			//	                sync();
			//	            }
		}
	}
	 
	
	public void run() {
		init();
		
		runningLoop();

		glfwTerminate();
		
	}
	

	private void update()
	{
		
		
	}
	
	private void render()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		if (window.isResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }
		
	
	
		int i = glGetError();
		if ( i != GL_NO_ERROR)
		{
			System.out.println("LWJGL Error Code :" + i);
		}
		
		
//		
		Shader.bgshader.enable();
		
		Shader.cubeshader.enable();

		
        //Update projection matrix
        Matrix4f projectionMatrix = mvpMatrix.getProjectionMatrix( window.getWidth(), window.getHeight());
        
        Matrix4f viewMatrix = mvpMatrix.getViewMatrix(camera);
//        viewMatrix.setLookAt( new Vector3f(0,0,5),  new Vector3f(0f,0f,0f), new Vector3f(0f,-1f,0f));
        Matrix4f viewMatrixForCube = new Matrix4f(viewMatrix);
        
//        viewMatrix.m30(0);
//        viewMatrix.m31(0);
//        viewMatrix.m32(0);
        
        Shader.bgshader.setUniform4fv("projectionMatrix", projectionMatrix);
       
        Shader.bgshader.setUniform1i("texture_sampler", 1);
        
        Shader.bgshader.setUniform4fv("viewMatrix", viewMatrix);
        
        Matrix4f modelMatrix = mvpMatrix.getModelMatrix(
        		bg.getPosition(),
        		bg.getRotation(),
        		bg.getScale());
        Shader.bgshader.setUniform4fv("modelMatrix", modelMatrix);
        
        bg.getMesh().render();
        
        Shader.cubeshader.setUniform4fv("projectionMatrix", projectionMatrix);
        
        Shader.cubeshader.setUniform1i("texture_sampler", 1);
        
        Shader.cubeshader.setUniform4fv("viewMatrix", viewMatrixForCube);
        
     // Render each gameItem
        for (CubeItem element : cubeobj.getCubeItems()) {
            // Set world matrix for this item
            modelMatrix = mvpMatrix.getModelMatrix(
            		element.getPosition(),
            		element.getRotation(),
            		element.getScale());
            Shader.cubeshader.setUniform4fv("modelMatrix", modelMatrix);
            // Render the mes for this game item
            element.getMesh().render();
            element.getMesh().unbind();
        }
        
        bg.getMesh().unbind();

        Shader.bgshader.disable();
		
        Shader.cubeshader.disable();
        
        window.render();
	}
	public static void main(String[] args) {
		// 'new' => instantiate Main class implicitly
		try {
			new Main().start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
