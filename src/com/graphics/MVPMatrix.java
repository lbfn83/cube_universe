package com.graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class MVPMatrix {

	private static final float FOV = (float) Math.toRadians(60.0f);
	// The vertical Field of View, in radians: the amount of "zoom". Think "camera lens". Usually between 90° (extra wide) and 30° (quite zoomed in)
	private static final float Z_NEAR = 0.1f;

	private static final float Z_FAR = 100.f;

	private final Matrix4f projectionMatrix;
	private final Matrix4f worldMatrix;

	public MVPMatrix() {
		worldMatrix = new Matrix4f();
		projectionMatrix = new Matrix4f();
	}
	public final Matrix4f getProjectionMatrix(float width, float height) {
		return projectionMatrix.setPerspective(FOV, width/height, Z_NEAR, Z_FAR);
	}
	public final Matrix4f getProjectionMatrix(float fov, float width, float height, float zNear, float zFar) {
		return projectionMatrix.setPerspective(fov, width/height, zNear, zFar);
	}

	public final Matrix4f getWorldMatrix(Vector3f offset, Vector3f rotation, float scale) {
		return worldMatrix.translation(offset).rotateX((float)Math.toRadians(rotation.x)).rotateY((float)Math.toRadians(rotation.y))
				.rotateZ((float)Math.toRadians(rotation.z)).scale(scale);
	}

}
