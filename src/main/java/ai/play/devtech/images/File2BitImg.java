package ai.play.devtech.images;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import javax.imageio.ImageIO;

public class File2BitImg {
	interface ERun<T> {
		T run() throws Exception;
	}
	
	public static void main(String[] args) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		getFiles("C:\\Users\\devan\\OneDrive\\Documents\\Github\\PoliticsAndJava")
			.stream()
			.map(f -> (ERun<FileInputStream>) () -> new FileInputStream(f))
			.map(File2BitImg::run)
			.forEach(f -> run(() -> {
				byte[] arr = new byte[1024];
				int red = 0;
				while((red = f.read(arr)) != -1)
					baos.write(arr, 0, red);
				return null;
			}));
		System.out.printf("Size: %20d bytes\n", baos.size());
		BlackAndWhiteImageOutputStream bawios = new BlackAndWhiteImageOutputStream(baos);
		long ms = System.currentTimeMillis();
		baos.writeTo(bawios);
		System.out.printf("Wrote to image in %8dms\n", System.currentTimeMillis()-ms);
		ms = System.currentTimeMillis();
		BufferedImage bi = bawios.getImg();
		System.out.printf("Rendered image in %8dms\n", System.currentTimeMillis()-ms);
		ms = System.currentTimeMillis();
		ImageIO.write(bi, "png", new File("test.png"));
		System.out.printf("Wrote image to file in %5dms\n", System.currentTimeMillis()-ms);
	}
	
	public static List<File> getFiles(String start) {
		List<File> files = new LinkedList<>();
		forFile(new File(start), files::add);
		return files;
	}
	
	public static void forFile(File iter, Consumer<File> cons) {
		if(iter.isDirectory())
			for(File f : iter.listFiles())
				forFile(f, cons);
		else
			cons.accept(iter);
	}
	
	public static <T> T run(ERun<T> e) {
		try {
			return e.run();
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}
	}
}

