package com.fresco.parse;

import java.nio.ByteBuffer;

public interface IProcessor<T> {

	public void acumular(SplitFile sf);

	public boolean processLine(ByteBuffer bb, long size);

	public T combinar(T otro);
	
}
