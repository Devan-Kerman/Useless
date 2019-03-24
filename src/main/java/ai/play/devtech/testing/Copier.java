package ai.play.devtech.testing;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Copier<T> {
	private static final int CORES = Runtime.getRuntime().availableProcessors()*4;
	private static final int COREL = CORES-1;
	private final ExecutorService SERVICE = Executors.newFixedThreadPool(CORES);
	
	@SuppressWarnings("unchecked")
	public T[] copy(T[] arr) {
		T[] _new = (T[]) Array.newInstance(arr.getClass().getComponentType(), arr.length);
		int len = arr.length/CORES;
		for(int x = 0; x < COREL; x++) {
			final int y = x;
			SERVICE.submit(() -> System.arraycopy(arr, y*len, _new, y*len, len));
		}
		SERVICE.execute(() -> System.arraycopy(arr, COREL*len, _new, COREL*len, arr.length-COREL*len));
		while(true)
			if(Arrays.stream(_new).noneMatch(Objects::isNull))
				return _new;
	}
}
