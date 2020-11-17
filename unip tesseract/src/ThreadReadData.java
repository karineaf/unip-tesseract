

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;



public class ThreadReadData extends Thread {
	
	
	String caminho = new String(System.getProperty("user.dir"));

	
	public TransparentFrame windowReference;

    public void run(){
        try {
            while(true){
                if(this.windowReference.isShowing()){
                    Robot robot;
                    try {
                        //captura a tela
                        robot = new Robot();
                        BufferedImage screenShot = robot.createScreenCapture(
                            new Rectangle(windowReference.getLocationOnScreen().x, windowReference.getLocationOnScreen().y,
                                        windowReference.getSize().width, windowReference.getSize().height));
                        Graphics2D graphics = screenShot.createGraphics();

                        //salva a captura
                        ImageIO.write(screenShot, "png", new File(caminho + "\\OCR\\image.png"));

                        String caminho = new String(System.getProperty("user.dir"));
                		Tesseract tesseract = new Tesseract(); 
                		try { 

                			tesseract.setDatapath(caminho + "\\tessdata"); 

                			// the path of your tess data folder 
                			// inside the extracted file 
                			String text 
                				= tesseract.doOCR(new File(caminho + "\\OCR\\image.png")); 

                			// path of your image file 
                			System.out.print(text); 
                		} 
                		catch (TesseractException e) { 
                			e.printStackTrace(); 
                		} 
                        //String everything = this.readFile(caminho + "\\OCR\\outTeresseract.txt");
                        //System.out.println("OCR: " + everything);
                        
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                sleep(1000);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String readFile(String file) {
        String everything = "";
        BufferedReader br;
        try{
            br = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null){
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
            br.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return everything;
    }
}
