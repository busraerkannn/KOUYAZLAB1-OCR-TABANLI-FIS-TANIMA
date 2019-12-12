package Proje;


import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import static org.opencv.imgproc.Imgproc.COLOR_BGR2GRAY;
import static org.opencv.imgproc.Imgproc.cvtColor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;


/**
 * Recognize text from image
 * 
 * 
 *
 */
public class OpenCV {

	private Random rng=new Random(12345);
	
	// Source path content images
	static String SRC_PATH = "C:\\Users\\gozde\\eclipse-workspace\\Proje\\";
	static String TESS_DATA = "C:\\Program Files (x86)\\Tesseract-OCR\\tessdata";
	
	static Tesseract tesseract = new Tesseract();
	
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		tesseract.setDatapath(TESS_DATA);
	}
		
	
	String extractTextFromImage(Mat inputMat) {
		String result = "";
	
		Mat gray = new Mat();
		// Convert to gray scale
		cvtColor(inputMat, gray, COLOR_BGR2GRAY);
		imwrite(SRC_PATH + "gray.png", gray);
		
		
		Mat gaussian = new Mat();
		Imgproc.GaussianBlur(gray, gaussian, new Size(5,5),0);
		imwrite(SRC_PATH + "gaussian.png", gaussian);
		
		Mat dst = new Mat();
		Imgproc.adaptiveThreshold(gaussian, dst,255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,Imgproc.THRESH_BINARY, 75, 12); //11 12 //91 12
		imwrite(SRC_PATH + "adaptive.png", dst);
		
		Mat threshold = new Mat();
		Imgproc.threshold(dst, threshold, 0, 255, Imgproc.THRESH_OTSU + Imgproc.THRESH_BINARY);
		imwrite(SRC_PATH + "threshold.png", threshold);
		
		Mat erode = new Mat();
		Imgproc.erode(threshold, erode, Imgproc.getStructuringElement(Imgproc.MORPH_ERODE, new Size(9,9)));
		imwrite(SRC_PATH + "erode.png", erode);
		
		Mat dilate = new Mat();
		Imgproc.dilate(erode, dilate, Imgproc.getStructuringElement(Imgproc.MORPH_DILATE, new Size(7,7)));
		imwrite(SRC_PATH + "dilate.png", dilate);


		/*Mat gray = new Mat();
		// Convert to gray scale
		cvtColor(inputMat, gray, COLOR_BGR2GRAY);
		imwrite(SRC_PATH + "gray.png", gray);
		
		Mat dst = new Mat();
		//Imgproc.adaptiveThreshold(gray, dst,125, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C,Imgproc.THRESH_BINARY, 11, 12); //11 12 //91 12
		Imgproc.adaptiveThreshold(gray, dst, 125, Imgproc.ADAPTIVE_THRESH_MEAN_C,Imgproc.THRESH_BINARY, 81, 21); //11 12 - 91 12 - 51 12 - 51 17

		imwrite(SRC_PATH + "adaptive.png", dst);*/
		
		
		try {
			tesseract.setLanguage("tur");
			//tesseract.setPageSegMode(1);
			// Recognize text with OCR
			result = tesseract.doOCR(new File(SRC_PATH + "adaptive.png"));
			
		} catch (TesseractException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	
	
	public static void main(String[] args)throws IOException {
		
		
				
		Arayuz a1=new Arayuz();
		try {
			a1.setVisible(true);
			Thread.sleep(10000);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		long start = System.currentTimeMillis();
		//System.out.println(a1.img);

		//Mat origin = imread(SRC_PATH + "Fis10.png");
		
		Mat origin = imread(a1.img);
				
		String result = new OpenCV().extractTextFromImage(origin);
		
		File file = new File("text.txt");
		
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
			
			writer.write(result);

		} catch (IOException e) {
			
		    System.out.println("Unable to read file " +file.toString());
		}
		
		File file2 = new File("kontrol.txt");
		BufferedReader reader = null;
        reader = new BufferedReader(new FileReader(file2));
        
        File file3 = new File("kontrol2.txt");
		BufferedReader reader2 = null;
        reader2 = new BufferedReader(new FileReader(file3));
        
        
        String satir=null;
        satir = reader.readLine();
		ArrayList<String> kontrol=new ArrayList<String>();
		ArrayList<String> kontrol2=new ArrayList<String>();
		
		kontrol.add("TEŞEKKÜRLER"); kontrol.add("TESEKKURLER"); kontrol.add("TESEKKÖRLER"); kontrol.add("TEŞEKKÖRLER"); kontrol.add("TESEKKORLER");kontrol.add("TEŞEKKORLER");
		kontrol.add("İYİ GÜNLER"); kontrol.add("IYI GUNLER"); kontrol.add("İYİ GUNLER");  kontrol.add("IYI GÜNLER"); kontrol.add("İYİ GÖNLER"); kontrol.add("İYİ GONLER"); 
		kontrol.add("TARİH");  kontrol.add("TARIH"); kontrol.add("ARİH");  kontrol.add("ARIH");  kontrol.add("TARİK");kontrol.add("TARIK"); kontrol.add("ARİK");  kontrol.add("ARIK");  
		kontrol.add("FİŞ"); kontrol.add("FİŞNO"); kontrol.add("FIŞ"); kontrol.add("FIŞNO"); kontrol.add("FIS"); kontrol.add("FISNO"); kontrol.add("FİS"); kontrol.add("FİSNO"); kontrol.add("FİSŞ"); 
		kontrol.add("İŞ"); kontrol.add("İŞNO"); kontrol.add("IŞ"); kontrol.add("IŞNO"); kontrol.add("IS"); kontrol.add("ISNO");kontrol.add("İS");  kontrol.add("İSNO");
		kontrol.add("0"); kontrol.add("1"); kontrol.add("2"); kontrol.add("3"); kontrol.add("4"); kontrol.add("5"); kontrol.add("6"); kontrol.add("7"); kontrol.add("8"); kontrol.add("9"); 

		kontrol2.add("&"); kontrol2.add("%");  
		kontrol2.add("408"); kontrol2.add("418"); kontrol2.add("508"); kontrol2.add("518"); kontrol2.add("008"); kontrol2.add("018"); kontrol2.add("108"); kontrol2.add("118"); kontrol2.add("08"); kontrol2.add("18"); kontrol2.add("4108"); kontrol2.add("4118"); 
		kontrol2.add("*"); kontrol2.add("$"); kontrol2.add("1"); kontrol2.add("#"); kontrol2.add("8"); kontrol2.add("t"); kontrol2.add("4");
		kontrol2.add("%08");kontrol2.add("&08");kontrol2.add("5118");kontrol2.add("5008");kontrol2.add("708");kontrol2.add("718");kontrol2.add("%18");kontrol2.add("&18");
		
		
		File file4 = new File("text.txt");
		Scanner dosya = null;
		dosya = new Scanner(file4);
		Locale dosya2= null;
		//dosya2 = new Scanner(file4);

		String isletmeAdi=null;
		boolean bitir=true;
		boolean bitir2=true;
		
		while(dosya.hasNextLine() && bitir) {
			isletmeAdi=dosya.nextLine();
			if(isletmeAdi.contains(":") || isletmeAdi.contains(";") || isletmeAdi.contains("“") || isletmeAdi.contains("|"))
			{
				isletmeAdi=dosya.nextLine();
			}
					
			for(int j=0;j<kontrol.size();j++)
			{
				if(isletmeAdi.equalsIgnoreCase(kontrol.get(j)))
				{
					isletmeAdi=dosya.nextLine();
					bitir=false;
					break;
				}
				else
				{
					bitir=false;
					//str=dosya.next();
					break;
				}
			}
			
		}
		a1.veri.add(isletmeAdi);
		String tarih=null;
		bitir=true;
		while(dosya.hasNext() && bitir) {
			tarih=dosya.next();			
			for(int j=0;j<kontrol.size();j++ )
			{
				if(tarih.equalsIgnoreCase(kontrol.get(j)))
				{
					tarih=dosya.next();
					tarih=dosya.next();
					
					if(tarih.equals("—"))
					{
						tarih=dosya.next();
						tarih=dosya.next();						
					}
					if(tarih.equals(":"))
					{
						tarih=dosya.next();
					}
					bitir=false;
				}
			}
		}
		bitir=true;
		a1.veri.add(tarih);
		
		String fisNo=null;
		while(dosya.hasNext() && bitir) {
			fisNo=dosya.next();
			for(int j=0;j<kontrol.size();j++ )
			{
				if(fisNo.equals(kontrol.get(j)))
				{
					fisNo=dosya.next();
					fisNo=dosya.next();
					fisNo=dosya.next();						

					if(fisNo.equals("—"))
					{
						fisNo=dosya.next();
						fisNo=dosya.next();						
					}
					
					bitir=false;
				}
			}
		}
		
		a1.veri.add(fisNo);
		
		bitir=true;
		boolean bitir3=true;
		boolean bitir4=false;
		boolean bitir5=true;
		boolean bitir6=true;
		boolean bitir7=true;
		boolean bitir8=true;

		String urun=null;
		String tumUrun=null;
		String kdv=null;
		String fiyat=null;
		String tumFiyat=null;

		
		while(dosya.hasNext() && bitir)
		{
			urun=dosya.next();
			if(urun.contains("KDV") || urun.contains("kdv") || urun.contains("KODV"))
			{
				bitir = false;
			}
			String str=urun;
			
			for (int j = 0; j < kontrol.size(); j++) {
				
				if((urun.contains(kontrol.get(j)) || urun.contains("|") || urun.contains("—") || urun.contains(".") || urun.contains("'")) && bitir  )
				{
					urun=dosya.next();
					if(urun.contains("X") || urun.contains("KG") || urun.contains("K"))
					{
						urun=dosya.nextLine();
						bitir4=true;
						break;
					}
					
				}
				
			}
			if(bitir4==false && bitir)
			{
				tumUrun=str;
			}
			while(dosya.hasNext() && bitir3 && bitir)
			{
				urun=dosya.next();
				for (int j = 0; j<kontrol2.size(); j++) {
					if(urun.equals(kontrol2.get(j)) || urun.equals("*|"))
					{

						
						if(urun.contains("%08") || urun.contains("&08") || urun.contains("408") || urun.contains("508") || urun.contains("008") || urun.contains("108") || urun.contains("08") || urun.contains("708") || urun.contains("4108") || urun.contains("5008") )
						{
							kdv="08";
						}
						else if(urun.contains("%18") || urun.contains("&18") || urun.contains("418") || urun.contains("518") || urun.contains("018") || urun.contains("118") || urun.contains("18") ||  urun.contains("718") || urun.contains("4118") || urun.contains("5118"))
						{
							kdv="18";
						}
					
						
						
						
						bitir3=false;
						break;
					}
				}
				if(bitir3==false)
				{
					fiyat=dosya.next();
					if(fiyat.equals("—"))
					{
						fiyat=dosya.next();

					}
					
					for (int j = 0; j < kontrol2.size(); j++) {

						if((fiyat.charAt(0)==kontrol2.get(j).charAt(0)) && (fiyat.length()>2) && bitir7)
						{
							tumFiyat=fiyat.substring(1,fiyat.length());
							bitir7=false;
							break;
							
						}
					}
						if(fiyat.length()<=2)
						{
							tumFiyat=fiyat.substring(1);
							if(tumFiyat.equals(")"))
							{
								tumFiyat="1";
							}
						}
						if(bitir7)
						{
							fiyat=dosya.next();
							if(fiyat.length()==2)
							{
								tumFiyat=tumFiyat+","+fiyat;
								bitir5=false;
								break;
							}
						}
						
						
					
					
				}
				if(bitir4)
				{
					tumUrun=urun;
					bitir4=false;
				}
				else if(bitir3)
				{
					tumUrun=tumUrun+" "+urun;
				}
			}
			if(bitir==true)
			{
				a1.urun.add(tumUrun);
				a1.kdv.add(kdv);
				a1.fiyat.add(tumFiyat);
			}
			
			
			bitir3=true;
			bitir4=false;
			bitir5=true;
			bitir6=true;
			bitir7=true;
		}
		
		String fis,toplamFiyat=null;
		while(dosya.hasNext() && (bitir5 || bitir8))
		{
			fis=dosya.next();
			if(fis.contains("TOPLAM") || fis.contains("toplam"))
			{
				fis=dosya.next();
				if(fis.equals("—"))
				{
					fis=dosya.next();
				}
				for (int j = 0; j < kontrol2.size(); j++) {

					if((fis.charAt(0)==kontrol2.get(j).charAt(0)) && (fis.length()>2) && bitir8)
					{
						toplamFiyat=fis.substring(1,fis.length());
						bitir8=false;
						break;
						
					}
				}
				if(fis.length()<=2)
				{
					toplamFiyat=fis.substring(1);
					if(toplamFiyat.equals(")"))
					{
						toplamFiyat="1";
					}
				}
				if(bitir8)
				{
					fis=dosya.next();
					if(fis.length()==2)
					{
						toplamFiyat=toplamFiyat+","+fis;
						bitir5=false;
						break;
					}
				}
			}
		}
		a1.veri.add(toplamFiyat);
		
				
		
	}
	
}