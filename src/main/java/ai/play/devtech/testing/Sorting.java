package ai.play.devtech.testing;

import java.util.Arrays;
import java.util.SplittableRandom;

public class Sorting {
	
	public static final SplittableRandom rand = new SplittableRandom();
	public static void main(String[] args) {
		//Main.main(args);
		System.out.println("Generating...");
		Integer[] gen = gen(10_000_000);
		System.out.println("Sorting...");
		Copier<Integer> copier = new Copier<>();
		for(int x = 0; x < 100; x++)
			copier.copy(gen);
		long nan = System.nanoTime();
		
		Integer[] _new =  new Integer[gen.length];//copier.copy(gen);
		//System.arraycopy(gen, 0, _new, 0, gen.length);
		
		System.out.printf("Copied in %dms.\n", (System.nanoTime()-nan)/1000000);
		if(gen.length < 1_001) {
			System.out.println(Arrays.toString(gen));
			System.out.println(Arrays.toString(_new));
		}
	}
	
	public static final Integer[] gen(int len) {
		Integer[] array = new Integer[len];
		for(int x = 0; x < len; x++)
			array[x] = rand.nextInt(0, len);
		return array;
	}
}
