package com.graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.level.Camera;

public class MVPMatrix {

	// The vertical Field of View, in radians: the amount of "zoom". 
	// Think "camera lens". Usually between 90° (extra wide) and 30° (quite zoomed in)
	private static final float FOV = (float) Math.toRadians(60.0f);
	
	private static final float Z_NEAR = 0.1f;

	private static final float Z_FAR = 500.f;

	private final Matrix4f projectionMatrix;
	private final Matrix4f modelMatrix;
    private final Matrix4f viewMatrix;

	public MVPMatrix() {
		modelMatrix = new Matrix4f();
		projectionMatrix = new Matrix4f();
		viewMatrix = new Matrix4f();
	}
	public final Matrix4f getProjectionMatrix(float width, float height) {
		return projectionMatrix.setPerspective(FOV, width/height, Z_NEAR, Z_FAR);
	}
	public final Matrix4f getProjectionMatrix(float fov, float width, float height, float zNear, float zFar) {
		return projectionMatrix.setPerspective(fov, width/height, zNear, zFar);
	}

	public final Matrix4f getModelMatrix(Vector3f offset, Vector3f rotation, float scale) {
		return modelMatrix.translation(offset).rotateX((float)Math.toRadians(rotation.x)).rotateY((float)Math.toRadians(rotation.y))
				.rotateZ((float)Math.toRadians(rotation.z)).scale(scale);
	}
	// Yaw and Pitch rotation are already applied to the camera input in Camera.movePosition() method 
	public Matrix4f getViewMatrix(Camera camera) {
		Vector3f cameraPos = camera.getPosition();
		Vector3f rotation = camera.getRotation();

		viewMatrix.identity();
		// First do the rotation so camera rotates over its position
		viewMatrix.rotate((float)Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
		.rotate((float)Math.toRadians(rotation.y), new Vector3f(0, 1, 0));
		// Then do the translation
		viewMatrix.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
		return viewMatrix;
	}

}
