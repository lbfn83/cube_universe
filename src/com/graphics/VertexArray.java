package com.graphics;

import static org.lwjgl.opengl.GL30.*;

import com.utils.BufferUtils;


public class VertexArray {
	
	private int vao, vbo, ibo, tbo;
	private int count;
    private final Texture texture;
    
	public VertexArray(int count, Texture texture) {
		this.count = count;
		vao = glGenVertexArrays();
		this.texture = texture;
	}
	
	public VertexArray(float[] vertices, int[] indices, float[] textureCoordinates, Texture texture)
	{
		
		this.texture = texture;
		
		count = indices.length;
		
		vao = glGenVertexArrays();
		glBindVertexArray(vao);
		
		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(vertices), GL_STATIC_DRAW);
		glVertexAttribPointer(Shader.VERTEX_ATTRIB, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(Shader.VERTEX_ATTRIB);
		
		tbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, tbo);
		glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(textureCoordinates), GL_STATIC_DRAW);
		glVertexAttribPointer(Shader.TCOORD_ATTRIB, 2, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(Shader.TCOORD_ATTRIB);
		
		ibo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createIntBuffer(indices),  GL_STATIC_DRAW);

		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}
	
	public void bind() {
		texture.bind();
		glBindVertexArray(vao);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
	}
	
	public void unbind() {
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
		texture.unbind();
	}
	
	public void draw() {
		glDrawElements( GL_TRIANGLES, count, GL_UNSIGNED_INT,0);
	}
	
	public void render()
	{
		bind();
		draw();
	}
}
