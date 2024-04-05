package packWork;

import java.awt.image.BufferedImage;

import packTest.*;

public class Consumer extends Thread {
	
    //Thread myThread;
    private Buffer buffer;

    private int number = -1;
    private boolean available = false;
    
    private BufferedImage image;
    private int width, height;

    @Override
    public void run() {
        int[] rgb;
        BufferedImage imageCopy = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        long time = System.currentTimeMillis();
        for (int i = 0; i < height; i++)
        {
                try {
                    rgb = buffer.get();
                    if(i % 10 == 0)
                    	System.out.println("Linia Consumer: " + i);
                    for (int j = 0; j < width; j++) {
                    imageCopy.setRGB(j, i, rgb[j]);}
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
        System.out.println(System.currentTimeMillis() - time);
        time = System.currentTimeMillis();
       	Prewitt myFilter = new Prewitt();
       	myFilter.setWidth(width);
    	myFilter.setHeight(height);
    	myFilter.setImage(imageCopy);
    	myFilter.filterImage2();
    	System.out.println(System.currentTimeMillis() - time);
    	time = System.currentTimeMillis();
    	myFilter.getSource();
    	System.out.println(System.currentTimeMillis() - time);
    }

    public synchronized int get() {
        while (!available) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        available = false;
        notifyAll();
        return number;
    }
    

    public Consumer(Buffer b) {
        System.out.println("Consumer Constructor with parameters");
        this.buffer = b;
    }

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
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
}
