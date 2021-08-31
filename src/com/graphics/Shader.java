package com.graphics;

import static org.lwjgl.opengl.GL20.*;

import java.util.HashMap;
import java.util.Map;


import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryStack;

import com.utils.ShaderUtils;



public class Shader {
	public static final int VERTEX_ATTRIB = 0;
	public static final int TCOORD_ATTRIB = 1;
	
	public static Shader cubeshader;
	
	private boolean enabled = false;
	
	private final int progID;
	
	private Map<String, Integer> locationCache = new HashMap<String, Integer>();
	
	private Shader(String vertex, String fragment) {
		progID = ShaderUtils.load(vertex, fragment);
	}
	
	public static void loadAll()
	{
		cubeshader = new Shader("shader/vertex.vs" , "shader/fragment.fs");
	}
	//Uniform 변수의 location을 알려주는 거다. uniform 변수의 값 자체는 get할 이유는 없다.. set 만 있을 뿐
	public int getUniform(String name)
	{
		if(locationCache.containsKey(name))
			return locationCache.get(name);
		
		int result = glGetUniformLocation(progID, name);
		if ( result == -1)
		{
			System.err.println("Could not find uniform variable '" + name + "'!");
			
		}else
		{
			locationCache.put(name, result);
		}
		return result;
	}
	
	public void setUniform1i(String name, int value)
	{
		if(!enabled)
			enable();
		glUniform1i(getUniform(name), value);
	}
	public void setUniform1f(String name, float value)
	{
		if(!enabled)
			enable();
		glUniform1f(getUniform(name), value);
	}
	public void setUniform2f(String name, float x, float y)
	{
		if(!enabled)
			enable();
		glUniform2f(getUniform(name), x, y);
	}
	public void setUniform3f(String name, Vector3f vector)
	{
		if(!enabled)
			enable();
		glUniform3f(getUniform(name), vector.x, vector.y, vector.z);
	}
	public void setUniform4f(String name, Vector4f vector)
	{
		if(!enabled)
			enable();
		glUniform4f(getUniform(name), vector.x, vector.y, vector.z, vector.w);
	}
	public void setUniform4fv(String name, Matrix4f matrix)
	{
		if(!enabled)
			enable();
		
		try(MemoryStack stack = MemoryStack.stackPush();){
	    	glUniformMatrix4fv(getUniform(name), false, matrix.get(stack.mallocFloat(16)));}
	}
	
	
	public void enable()
	{
		glUseProgram(progID);
		enabled = true;
	}
	public void disable()
	{
		glUseProgram(0);
		enabled = false;
	}
}
