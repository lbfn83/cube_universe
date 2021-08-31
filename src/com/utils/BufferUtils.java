package com.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

public class BufferUtils {
	private BufferUtils() {
		
	}
	
	public static ByteBuffer createByteBuffer(byte[] array)
	{
		ByteBuffer result = ByteBuffer.allocateDirect(array.length).order(ByteOrder.nativeOrder());
		//flip: get ready for read . position go to 0
		result.put(array).flip();
		return result;
	}
	
	public static FloatBuffer createFloatBuffer(float[] array)
	{
		//float is 4 bytes
		FloatBuffer result = ByteBuffer.allocateDirect(array.length << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
		//flip: get ready for read . position go to 0
		result.put(array).flip();
		return result;
	}
	
	public static IntBuffer createIntBuffer(int[] array)
	{
		//float is 4 bytes 
		IntBuffer result = ByteBuffer.allocateDirect(array.length << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
		//flip: get ready for read . position go to 0
		result.put(array).flip();
		return result;
	}
	
    public static float[] listToArray(List<Float> list) {
        int size = list != null ? list.size() : 0;
        float[] floatArr = new float[size];
        for (int i = 0; i < size; i++) {
            floatArr[i] = list.get(i);
        }
        return floatArr;
    }
}
