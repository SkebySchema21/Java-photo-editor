package packWork;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import packTest.*;


public class Producer extends Thread implements MyInterface{
	
	private int width, height, rgb;
	private String filePath;
	private BufferedImage image;
	
	static {
		System.out.println("Incepe consumul imaginii");
	}
	
	@Override
	public void getSource() 
	{
		try{
		String resourcePath = "C:/Documente/Documente studii/Anul 3/Semestrul 1/AWJ/Proiect/Imagini_Proiect/";
		System.out.println("Introduceti numele imagine pentru procesare!");
	    String denumireBMP = MyMain.scan.nextLine();
	    filePath = resourcePath + denumireBMP + ".bmp";
	    //System.out.println(filePath);
	    
	    //C:/Documente/Documente studii/Anul 3/Semestrul 1/AWJ/Proiect/Imagini_Proiect/imagine1.bmp
	    //filePath = denumireBMP;
	    FileInputStream fileInputStream = new FileInputStream(filePath);
        setImage(ImageIO.read(new File(filePath)));

        setWidth(this.image.getWidth());
        setHeight(this.image.getHeight());

        /*
        fileInputStream.close();
        this.image = ImageIO.read(file);
	    */
		}
		catch (IOException e) {
            e.printStackTrace();
		}
	}
	
	public BufferedImage getImage() {
		return image;
	}


	public void setImage(BufferedImage image) {
		this.image = image;
	}

	//private int number = -1;
	private boolean available = false;
	private Buffer buffer;
	

	public void run() {
		int width = getWidth();
        int height = getHeight();
		
        for (int i = 0; i < height; i++)
        {
        	int[] var = new int[width];
        	if(i % 10 == 0)
        	System.out.println("Linia Producer: " + i);
            for (int j = 0; j < width; j++){
                var[j] = this.image.getRGB(j, i);
            }
		try {
			buffer.put(var);
			} catch (Exception e) {
				e.printStackTrace ();
		}
		
		try {
			sleep((int)(Math.random () * 10));
			} catch (InterruptedException e) {System.out.println(e);}
      }
	}


	public synchronized void put(int number) {
		while (available) {
			try {
				wait();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.rgb = number;
		available = true;
		notifyAll ();
	}
	
	public Producer(Buffer b) {
		this.buffer = b;
		System.out.println("Producer Constructor no parameters");
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getRgb() {
		return rgb;
	}

	public void setRgb(int rgb) {
		this.rgb = rgb;
	}
	
}
