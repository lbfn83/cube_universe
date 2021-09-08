package com.utils;

import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;

import com.graphics.Window;
import com.object.Camera;

import static org.lwjgl.glfw.GLFW.*;

public class MouseInput {

    private final Vector3f displInc;
	
    //Mouse cursor position vector
    private final Vector2f displVec;

    private final Vector2d previousPos;

    private final Vector2d currentPos;

    private boolean inWindow = false;


	private boolean leftButtonPressed = false;

    private boolean rightButtonPressed = false;

    
    public MouseInput() {
        previousPos = new Vector2d(-1, -1);
        currentPos = new Vector2d(0, 0);
        displVec = new Vector2f();
        displInc = new Vector3f();
    }

    // Mouse event callback function registering	
    public void init(Window window) {
    	/**Parameters
			[in]	window	The window that received the event.
			[in]	xpos	The new x-coordinate, in screen coordinates, of the cursor.
			[in]	ypos	The new y-coordinate, in screen coordinates, of the cursor.
    	 */
        glfwSetCursorPosCallback(window.getWindowHandle(), (windowHandle, xpos, ypos) -> {
            currentPos.x = xpos;
            currentPos.y = ypos;
        });
        /**Parameters
			[in]	window	The window that received the event.
			[in]	entered	GL_TRUE if the cursor entered the window's client area, or GL_FALSE if it left it.
		*/
        glfwSetCursorEnterCallback(window.getWindowHandle(), (windowHandle, entered) -> {
            inWindow = entered;
        });
        /**
         * Parameters
			[in]	window	The window that received the event.
			[in]	button	The mouse button that was pressed or released.
			[in]	action	One of GLFW_PRESS or GLFW_RELEASE.
			[in]	mods	Bit field describing which modifier keys were held down.
         * */
        glfwSetMouseButtonCallback(window.getWindowHandle(), (windowHandle, button, action, mode) -> {
            leftButtonPressed = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS;
            rightButtonPressed = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS;
        });
    }


    public void input(Window window) {

    	// Keyboard Input process, will be reflected to the movement of camera	
    	displInc.x = 0;
    	displInc.y = 0;
    	displInc.z = 0;
    	
    	if (window.isKeyPressed(GLFW_KEY_Z)) {
    		displInc.y = 1;
    	} else if (window.isKeyPressed(GLFW_KEY_X)) {
    		displInc.y = -1;
    	} else if (window.isKeyPressed(GLFW_KEY_A)) {
    		displInc.x = -1;
    	} else if (window.isKeyPressed(GLFW_KEY_D)) {
    		displInc.x = 1;
    	} 
    	else if (window.isKeyPressed(GLFW_KEY_W)) {
    		displInc.z = -1;
    	} else if (window.isKeyPressed(GLFW_KEY_S)) {
    		displInc.z = 1;
    	} 

    	// Mouse (left click drag) Input process, 
    	// Calculate the delta of previous and current coordinates in terms of mouse cursors
        displVec.x = 0;
        displVec.y = 0;
        
        if (previousPos.x > 0 && previousPos.y > 0 && inWindow) {
            double deltax = currentPos.x - previousPos.x;
            double deltay = currentPos.y - previousPos.y;
            
            // Drag horizontal to X axis -> expectation is y axis rotation
            // Y axis is vice versa
            boolean rotateX = deltax != 0;
            boolean rotateY = deltay != 0;
            
            if (rotateX) {
                displVec.y = (float) deltax;
            }
            if (rotateY) {
                displVec.x = (float) deltay;
            }
        }
        previousPos.x = currentPos.x;
        previousPos.y = currentPos.y;
    }

    public boolean isLeftButtonPressed() {
        return leftButtonPressed;
    }

    public boolean isRightButtonPressed() {
        return rightButtonPressed;
    }
    public Vector3f getDisplInc() {
    	return displInc;
    }
    
    public Vector2f getDisplVec() {
    	return displVec;
    }
}
