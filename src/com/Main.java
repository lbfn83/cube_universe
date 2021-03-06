package com;


import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.graphics.MVPMatrix;
import com.graphics.Shader;
import com.graphics.Window;
import com.level.Background;
import com.level.Camera;
import com.level.Cube;
import com.level.CubeItem;
import com.utils.KeyboardInput;
import com.utils.MouseInput;
import com.utils.Timer;



public class Main implements Runnable {
	
	
	private Camera camera;
	private Cube cubegroup;
	private Background bg;

	private Window window;
	private MVPMatrix mvpMatrix;
	
	private KeyboardInput keyboardInput;
	private MouseInput mouseInput;
	private Timer timer;
	
	
	private int width = 1280;
	private int height = 720;

	private Thread thread;
	
    public static final int TARGET_UPS = 90;
    
	public void start() throws InterruptedException {
		
		thread = new Thread(this, "3D Cube");
		thread.start();
	}
	
	public void run() {
		init();
		
		runningLoop();
		
		glfwTerminate();
	}

	private void init() {

		window = new Window("Cube Universe", width, height);
		window.init();

		camera = new Camera(new Vector3f(0f, 0f, 0f));
		cubegroup = new Cube();
		
		bg = new Background();
		bg.setPosition(-0f, -0f, -0f);

		
		mvpMatrix = new MVPMatrix();
		
		Shader.loadAll();
		window.setClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glActiveTexture(GL_TEXTURE1);

		timer= new Timer();
		timer.init();
		
		keyboardInput = new KeyboardInput();
		mouseInput = new MouseInput();
		mouseInput.init(window);
		
	}
	
	protected void runningLoop() {
		float elapsedTime;
		float accumulator = 0f;
		float interval = 1f / TARGET_UPS;

		while ( !window.windowShouldClose()) {
			elapsedTime = timer.getElapsedTime();
			accumulator += elapsedTime;

			//System.out.println("time: " + accumulator);
			keyboardInput.input(window);
			
			//Get the delta of mouse cursor movement
			mouseInput.input();

			//Camera's movement is much smoother when it is not regulated by timer
			camera.updateMouse(mouseInput);
			
			while (accumulator >= interval) {
				accumulator -= interval;
				camera.updateKeyboard(keyboardInput);

				try {
					cubegroup.update(keyboardInput);
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			}
			render();
		}
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
		
		Shader.bgshader.enable();
		Shader.cubeshader.enable();

        Matrix4f projectionMatrix = mvpMatrix.getProjectionMatrix( window.getWidth(), window.getHeight());
        
        Matrix4f viewMatrix = mvpMatrix.getViewMatrix(camera);
        
        /*When the background should be fixed regardless of camera movement, below will disable translation movement*/
        //        viewMatrix.m30(0);
        //        viewMatrix.m31(0);
        //        viewMatrix.m32(0);
        
        Matrix4f modelMatrix = mvpMatrix.getModelMatrix(
        		bg.getPosition(),
        		bg.getRotation(),
        		bg.getScale());

        Shader.bgshader.setUniform4fv("projectionMatrix", projectionMatrix);
       
        Shader.bgshader.setUniform1i("texture_sampler", 1);
        
        Shader.bgshader.setUniform4fv("viewMatrix", viewMatrix);
        
        Shader.bgshader.setUniform4fv("modelMatrix", modelMatrix);
        
        bg.getMesh().render();
        
        Shader.cubeshader.setUniform4fv("projectionMatrix", projectionMatrix);
        
        Shader.cubeshader.setUniform1i("texture_sampler", 1);
        
        Shader.cubeshader.setUniform4fv("viewMatrix", viewMatrix);
        
     // Render each gameItem
        for (CubeItem element : cubegroup.getCubeItems()) {
            // Set world matrix for this item
            modelMatrix = mvpMatrix.getModelMatrix(
            		element.getPosition(),
            		element.getRotation(),
            		element.getScale());
            Shader.cubeshader.setUniform4fv("modelMatrix", modelMatrix);

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
