package packWork;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import java.awt.image.*;
import packTest.Buffer;
import packTest.MyMain;

public class Prewitt implements MyInterface{
	
	private int height, width; 
	private BufferedImage image;
	ImageOutputStream imgOut = null;
	
	public void filterImage()
	{
		if(image!=null){
		setHeight(image.getHeight());
		setWidth(image.getWidth());
		
		for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int randomColor = image.getRGB(j, i);
                int grayscaleValue = calculateGrayscale(randomColor);
                //Color grayscaleColor = new Color(grayscaleValue, grayscaleValue, grayscaleValue);
                image.setRGB(j, i, (grayscaleValue << 16) | (grayscaleValue << 8) | grayscaleValue);
            }
        }
		
		BufferedImage copy = getImage();
		
		for(int i = 0; i < height; i++)
            for(int j = 0; j < width; j++)
            {
            	int gx, gy;
            	if(i == 0)
            	{
            		if(j == 0)
            		{
            			gx = - image.getRGB(j + 1, i) - image.getRGB(j + 1, i + 1);
            			gy = - image.getRGB(j, i + 1) - image.getRGB(j + 1, i + 1);
            		}
            		else if(j == width - 1)
            		{
            			gx = image.getRGB(j - 1, i) + image.getRGB(j - 1, i + 1);
            			gy = - image.getRGB(j - 1, i + 1) - image.getRGB(j, i + 1);
            		}
            		else
            		{
            			gx = image.getRGB(j - 1, i) + image.getRGB(j - 1, i + 1) - image.getRGB(j + 1, i) - image.getRGB(j + 1, i + 1);
            			gy = - image.getRGB(j - 1, i + 1) - image.getRGB(j, i + 1) - image.getRGB(j + 1, i + 1);
            		}
            	}
            	else if(i == height - 1)
            	{
            		if(j == 0)
            		{
            			gx = - image.getRGB(j + 1, i - 1) - image.getRGB(j + 1, i);
            			gy = image.getRGB(j, i - 1) + image.getRGB(j + 1, i - 1);
            		}
            		else if(j == width - 1)
            		{
            			gx = image.getRGB(j - 1, i - 1) + image.getRGB(j - 1, i);
            			gy = image.getRGB(j - 1, i - 1) + image.getRGB(j, i - 1);
            		}
            		else
            		{
            			gx = image.getRGB(j - 1, i - 1) + image.getRGB(j - 1, i) - image.getRGB(j + 1, i - 1) - image.getRGB(j + 1, i);
            			gy = image.getRGB(j - 1, i - 1) + image.getRGB(j, i - 1) + image.getRGB(j + 1, i - 1);
            		}
            	}
            	else
            	{
            		if(j == 0)
            		{
            			gx = - image.getRGB(j + 1, i - 1) - image.getRGB(j + 1, i) - image.getRGB(j + 1, i + 1);
            			gy = image.getRGB(j, i - 1) + image.getRGB(j + 1, i - 1) - image.getRGB(j, i + 1) - image.getRGB(j + 1, i + 1);
            		}
            		else if(j == width - 1)
            		{
            			gx = image.getRGB(j - 1, i - 1) + image.getRGB(j - 1, i) + image.getRGB(j - 1, i + 1);
            			gy = image.getRGB(j - 1, i - 1) + image.getRGB(j, i - 1) - image.getRGB(j - 1, i + 1) - image.getRGB(j, i + 1);
            		}
            		else
            		{
            			gx = image.getRGB(j - 1, i - 1) + image.getRGB(j - 1, i) + image.getRGB(j - 1, i + 1) - image.getRGB(j + 1, i - 1) - image.getRGB(j + 1, i) - image.getRGB(j + 1, i + 1);
            			gy = image.getRGB(j - 1, i - 1) + image.getRGB(j, i - 1) + image.getRGB(j + 1, i - 1) - image.getRGB(j - 1, i + 1) - image.getRGB(j, i + 1) - image.getRGB(j + 1, i + 1);
            		}
            	}
            	int g = (int) Math.sqrt(gx * gx + gy * gy);
            	if(g < 0) g = 0;
            	else if(g > 255) g = 255;
            	copy.setRGB(j, i, (g << 16) | (g << 8) | g);
            }
		setImage(copy);
		}
		else
		{
			System.out.println("Imagine null");
		}
	}
	
	
	{
		System.out.println("Afisare");
	}
	
	private Buffer buffer;
	
	private ImageOutputStream out;
	public Prewitt(ImageOutputStream out) {
		this.out = out;
	}
	
	
	public Prewitt() {
		// TODO Auto-generated constructor stub
	}


	@Override
	public void getSource() 
	{
		try{
		String resourcePath = "C:" + File.separator + "Documente" + File.separator + "Documente studii" + File.separator + "Anul 3" + File.separator + "Semestrul 1" + File.separator + "AWJ" + File.separator + "Proiect" + File.separator + "Rezultate_proiect" + File.separator;
		System.out.println("Introduceti numele imaginii rezultate!");
	    String denumireBMP = MyMain.scan.nextLine();
	    String filePath = resourcePath + denumireBMP + ".bmp";
	    
	    //C:/Documente/Documente studii/Anul 3/Semestrul 1/AWJ/Proiect/Rezultate_proiect/imagine1.bmp
	    //filePath = denumireBMP;
	    imgOut = new FileImageOutputStream(new File(filePath));
        ImageIO.write((RenderedImage)getImage(), "bmp", imgOut);
		}
		catch (IOException e) {
            e.printStackTrace();
		}
		finally {
         try {
             // Close the ImageOutputStream to release resources
             if (imgOut != null) {
            	 imgOut.close();
             }
         } catch (IOException e) {
             e.printStackTrace(); // Handle the exception appropriately
         }
     }
	}
	
	private static int calculateGrayscale(int rgb) {
		int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        return (int) (0.33 * red + 0.33 * green + 0.34 * blue);
    }
	
	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public void filterImage2() {
	    if (image != null) {
	        setHeight(image.getHeight());
	        setWidth(image.getWidth());

	        BufferedImage copy = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

	        // Apply Prewitt filters separately for horizontal and vertical gradients
	        for (int i = 1; i < height - 1; i++) {
	            for (int j = 1; j < width - 1; j++) {
	                int gx = applyPrewittHorizontal(j, i);
	                int gy = applyPrewittVertical(j, i);

	                int g = (int) Math.sqrt(gx * gx + gy * gy);

	                // Ensure g is within valid range
	                g = Math.min(255, Math.max(0, g));

	                copy.setRGB(j, i, (g << 16) | (g << 8) | g);
	            }
	        }

	        setImage(copy);
	    } else {
	        System.out.println("Image is null");
	    }
	}

	private int applyPrewittHorizontal(int x, int y) {
	    int[][] horizontalKernel = {{-1, 0, 1}, {-1, 0, 1}, {-1, 0, 1}};
	    return applyKernel(x, y, horizontalKernel);
	}

	private int applyPrewittVertical(int x, int y) {
	    int[][] verticalKernel = {{-1, -1, -1}, {0, 0, 0}, {1, 1, 1}};
	    return applyKernel(x, y, verticalKernel);
	}

	private int applyKernel(int x, int y, int[][] kernel) {
	    int result = 0;
	    for (int i = -1; i <= 1; i++) {
	        for (int j = -1; j <= 1; j++) {
	            int grayscale = getGrayscale(image.getRGB(x + j, y + i));
	            result += grayscale * kernel[i + 1][j + 1];
	        }
	    }
	    return result;
	}

	// The getGrayscale method remains the same as in the previous response

	private int getGrayscale(int rgb) {
	    int red = (rgb >> 16) & 0xFF;
	    int green = (rgb >> 8) & 0xFF;
	    int blue = rgb & 0xFF;
	    return (int) (0.33 * red + 0.33 * green + 0.34 * blue);
	}

}
