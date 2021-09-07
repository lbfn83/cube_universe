package com.utils;

import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector3i;

import com.graphics.Window;

import static org.lwjgl.glfw.GLFW.*;

public class MouseInput {

    private final Vector3i displInc;

	
    private final Vector2d previousPos;

    private final Vector2d currentPos;

    private final Vector2f displVec;

    private boolean inWindow = false;

    public Vector3i getDisplInc() {
		return displInc;
	}

	private boolean leftButtonPressed = false;

    private boolean rightButtonPressed = false;

    public MouseInput() {
        previousPos = new Vector2d(-1, -1);
        currentPos = new Vector2d(0, 0);
        displVec = new Vector2f();
        
        displInc = new Vector3i();
    }

    public void init(Window window) {
        glfwSetCursorPosCallback(window.getWindowHandle(), (windowHandle, xpos, ypos) -> {
            currentPos.x = xpos;
            currentPos.y = ypos;
        });
        glfwSetCursorEnterCallback(window.getWindowHandle(), (windowHandle, entered) -> {
            inWindow = entered;
        });
        glfwSetMouseButtonCallback(window.getWindowHandle(), (windowHandle, button, action, mode) -> {
            leftButtonPressed = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS;
            rightButtonPressed = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS;
        });
    }

    public Vector2f getDisplVec() {
        return displVec;
    }

    public void input(Window window) {
    	displInc.x =0 ;
    	displInc.y = 0;
    	displInc.z = 0;
      if (window.isKeyPressed(GLFW_KEY_UP)) {
    	  displInc.y = 1;
  } else if (window.isKeyPressed(GLFW_KEY_DOWN)) {
	  displInc.y = -1;
  } else if (window.isKeyPressed(GLFW_KEY_LEFT)) {
	  displInc.x = -1;
  } else if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
	  displInc.x = 1;
  } 
  //should be q e
  else if (window.isKeyPressed(GLFW_KEY_Q)) {
	  displInc.z = -1;
  } else if (window.isKeyPressed(GLFW_KEY_E)) {
	  displInc.z = 1;
  } 
    	
    	
    	
        displVec.x = 0;
        displVec.y = 0;
        if (previousPos.x > 0 && previousPos.y > 0 && inWindow) {
            double deltax = currentPos.x - previousPos.x;
            double deltay = currentPos.y - previousPos.y;
            
            //이 변수 이름 때문에 헷갈리지 마라.. 
            // rotateX는 사실 rotateY임. 
            // x 축으로 드래그 무브하면 실상 y 축 회전을 바라는 것이기 때문이지. 
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
}
