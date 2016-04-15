import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.OutputStream;
import java.io.FileOutputStream;


public class DisplayEnergy extends Component {
	
	public static double work(String path, int time) {
		try {
			BufferedImage image = ImageIO.read(new File(path));
			return marchThroughImage(image) * time;
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		
		return 0.0;
	}

	public static double getPower(int rgb) {
		double power = 0.0;

		// int alpha = (rgb >> 24) & 0xff;
		int red = (rgb >> 16) & 0xff;
		int green = (rgb >> 8) & 0xff;
		int blue = (rgb) & 0xff;
		//System.out.print("argb: " + ", " + red + ", " + green + ", " + blue);
		if(red+green+blue == 0){
			power = 118.91;
		} else {
			power = 0.0624*red + 0.1512*green + 0.2614*blue + 106.7342;
		}

		return power;
	}

	public static double marchThroughImage(BufferedImage image) {
		int w = image.getWidth();
		int h = image.getHeight();
		//System.out.println("width, height: " + w + ", " + h);
		double sum_poewr = 0.0;
		int num = 0;

		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				int pixel_rgb = image.getRGB(j, i);
				sum_poewr += getPower(pixel_rgb);
				num++;
			}
		}

		return sum_poewr/num;
	}
}
