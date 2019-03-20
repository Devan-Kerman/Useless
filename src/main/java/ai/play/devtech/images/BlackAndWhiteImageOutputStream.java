package ai.play.devtech.images;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class BlackAndWhiteImageOutputStream extends OutputStream {
	private ByteArrayOutputStream baos;

	public BlackAndWhiteImageOutputStream(ByteArrayOutputStream o) {
		baos = o;
	}
	
	@Override
	public void write(int b) throws IOException {
		baos.write(b);
	}
	
	private static final int BLACK = Color.BLACK.getRGB();
	private static final int WHITE = Color.WHITE.getRGB();
	public BufferedImage getImg() {
		int size = (int) Math.ceil(Math.sqrt((baos.size()*8)));
		BufferedImage bi = new BufferedImage(size, size, BufferedImage.BITMASK);
		int index = 0;
		for(byte b : baos.toByteArray()) {
			bi.setRGB(index/size, index%size, (b&1)==0?BLACK:WHITE); index++;
			bi.setRGB(index/size, index%size, (b&2)==0?BLACK:WHITE); index++;
			bi.setRGB(index/size, index%size, (b&4)==0?BLACK:WHITE); index++;
			bi.setRGB(index/size, index%size, (b&8)==0?BLACK:WHITE); index++;
			bi.setRGB(index/size, index%size, (b&16)==0?BLACK:WHITE); index++;
			bi.setRGB(index/size, index%size, (b&32)==0?BLACK:WHITE); index++;
			bi.setRGB(index/size, index%size, (b&64)==0?BLACK:WHITE); index++;
			bi.setRGB(index/size, index%size, (b&128)==0?BLACK:WHITE); index++;
		}
		return bi;
	}
}
