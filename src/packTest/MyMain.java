package packTest;


import packWork.*;

import java.util.Scanner;

public class MyMain {
	
    public static TwoClass getTwoValues() {
        return new TwoClass(1, 1);
    }
    
    public static TwoClass getTwoValues(int val1, int val2) {
        return new TwoClass(val1, val2);
    }
	
	public static Scanner scan = new Scanner(System.in);
	//String resourcePath = "C:/Documente/Documente studii/Anul 3/Semestrul 1/AWJ/Proiect/Imagini_Proiect/imagine1.bmp";
    //String resultPath = "C:/Documente/Documente studii/Anul 3/Semestrul 1/AWJ/Proiect/Rezultate_proiect/imagine1.bmp";

    public static void main(String[] args) {
        
    	Buffer b = new Buffer();
    	Producer myProducer1 = new Producer(b);
    	Consumer myConsumer = new Consumer(b);

    	myProducer1.getSource();
    	TwoClass values = getTwoValues(myProducer1.getWidth(), myProducer1.getHeight());
    	int width = values.getValue1();
    	int height = values.getValue2();

    	myConsumer.setWidth(width);
    	myConsumer.setHeight(height);

    	myProducer1.start();
    	myConsumer.start();

    }
}
