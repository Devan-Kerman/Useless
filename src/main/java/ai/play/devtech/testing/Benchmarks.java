package ai.play.devtech.testing;

import java.util.Arrays;
import java.util.Collections;
import java.util.SplittableRandom;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(value = Scope.Thread)
public class Benchmarks {
	
	
	public Integer[] array;
	public static final SplittableRandom rand = new SplittableRandom();
	
	@Setup(Level.Invocation)
	public void setup() {
		array = new Integer[100];
		for(int x = 0; x < array.length; x++)
			array[x] = rand.nextInt(0, 1000);
	}
	
	@Benchmark
	public void collection() {
		Collections.sort(Arrays.asList(array));
	}
	
	@Benchmark
	public void loop() {
		Integer t;
		for(int u = 0; u < array.length; u++) {
			int o = getInsertLoop(array, 0, u, array[u])+1;
			t = array[u];
			if(u > o)
				System.arraycopy(array, o, array, o+1, u-o);
			else if(u < o)
				System.arraycopy(array, u, array, u, o-u);
			array[o] = t;
		}
	}
	
	@Benchmark
	public void recursion() {
		Integer t;
		for(int u = 0; u < array.length; u++) {
			int o = getInsertRecu(array, 0, u, array[u])+1;
			t = array[u];
			if(u > o)
				System.arraycopy(array, o, array, o+1, u-o);
			else if(u < o)
				System.arraycopy(array, u, array, u, o-u);
			array[o] = t;
		}
	}
	
	@Benchmark
	public void arraySort() {
		Arrays.sort(array);
	}
	
	@Benchmark
	public void parralelSort() {
		Arrays.parallelSort(array);
	}
	
	public static <T extends Comparable<T>> int getInsertLoop(T[] data, int min, int max, T element) {
        while(true) {
            int mid = (min + max) / 2;
            int comp = element.compareTo(data[mid]);
            if(comp > 0)
                min = mid;
            if(comp == 0 || max-min==1)
                return mid;
            if(comp < 0)
                max = mid;
        }
    }
	
	public static <T extends Comparable<T>> int getInsertRecu(T[] data, int min, int max, T element) {
		int mid = (min + max) / 2;
		int comp = element.compareTo(data[mid]);
		return (max - min == 1 || comp == 0) ? mid
				: getInsertRecu(data, comp > 0 ? mid : min, comp < 0 ? mid : max, element);
	}
}
