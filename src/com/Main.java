package com;


import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.glfw.Callbacks.*;
import java.util.Date;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import com.graphics.Shader;
import com.graphics.Window;
import com.level.Cube;
import com.level.CubeItem;
import com.utils.Timer;

import com.graphics.MVPMatrix;



public class Main implements Runnable {
	
	private Window window;
	private MVPMatrix mvpMatrix;
	
	private int width = 1280;
	private int height = 720;

	private Thread thread;
	
	private Cube cubeobj;
	
    public static final int TARGET_UPS = 30;
    private final Timer timer= new Timer();
    
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
		mvpMatrix = new MVPMatrix();
		Shader.loadAll();
		window.setClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glActiveTexture(GL_TEXTURE1);
		timer.init();
//		Shader.cubeshader.setUniform1f("texture_sampler", 1);
//		Shader.cubeshader.setUniform4fv("worldMatrix", null);
//		Shader.cubeshader.setUniform4fv("projectionMatrix", null);
		
	}
	
	protected void runningLoop() {
		float elapsedTime;
		float accumulator = 0f;
		float interval = 1f / TARGET_UPS;

		while ( !window.windowShouldClose()) {
			elapsedTime = timer.getElapsedTime();
			accumulator += elapsedTime;

			cubeobj.input(window);

			while (accumulator >= interval) {
				update();
				accumulator -= interval;
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
		cubeobj.update();
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
		Shader.cubeshader.enable();

        //Update projection matrix
        Matrix4f projectionMatrix = mvpMatrix.getProjectionMatrix( window.getWidth(), window.getHeight());
        Shader.cubeshader.setUniform4fv("projectionMatrix", projectionMatrix);
        Shader.cubeshader.setUniform1i("texture_sampler", 1);
        
     // Render each gameItem
        for (CubeItem element : cubeobj.getCubeItems()) {
            // Set world matrix for this item
            Matrix4f worldMatrix = mvpMatrix.getWorldMatrix(
            		element.getPosition(),
            		element.getRotation(),
            		element.getScale());
            Shader.cubeshader.setUniform4fv("worldMatrix", worldMatrix);
            // Render the mes for this game item
            element.getMesh().render();
            element.getMesh().unbind();
        }
        
        
        
		
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
