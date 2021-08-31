package com.utils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import com.utils.FileUtils;

public class ShaderUtils {
	private ShaderUtils( ) {
		
	}
	public static int load(String vertPath, String fragPath)
	{
		String vert = FileUtils.loadAsString(vertPath);
		String frag = FileUtils.loadAsString(fragPath);
		return create(vert, frag);
	}
	
	public static int create(String vert, String frag) {
		int program = glCreateProgram();
		int vertID = glCreateShader(GL_VERTEX_SHADER);
		int fragID = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(vertID, vert);
		glShaderSource(fragID, frag);
		
		glCompileShader(vertID);
		if(glGetShaderi(vertID, GL_COMPILE_STATUS) == GL_FALSE)
		{
			System.err.println("Failed to compile vertex shader!");
			System.err.println(glGetShaderInfoLog(vertID));
			return -1;
		}
		glCompileShader(fragID);
		if(glGetShaderi(fragID, GL_COMPILE_STATUS) == GL_FALSE)
		{
			System.err.println("Failed to compile vertex shader!");
			System.err.println(glGetShaderInfoLog(fragID));
			return -1;
		}
		glAttachShader(program, fragID);
		glAttachShader(program, vertID);
		glLinkProgram(program);
		glValidateProgram(program);
		return program;
		
	}
}
