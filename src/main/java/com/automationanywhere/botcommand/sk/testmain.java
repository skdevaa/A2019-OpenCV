package com.automationanywhere.botcommand.sk;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.stream.Collectors;

import org.opencv.calib3d.Calib3d;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.DMatch;
import org.opencv.core.KeyPoint;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.core.TermCriteria;
import org.opencv.features2d.BFMatcher;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.ORB;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.photo.Photo;
import org.opencv.video.Video;

import org.opencv.photo.Photo.*;

public class testmain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		String imagefile = "C:\\Users\\Stefan Karsten\\Documents\\AlleDateien\\Demos\\ID Card\\Pics\\ID__1.jpeg";
		String imagereffile = "C:\\Users\\Stefan Karsten\\Documents\\AlleDateien\\Demos\\ID Card\\Pics\\ID_1_Crop.jpg";
		
		String savefile = "C:\\Users\\Stefan Karsten\\Documents\\AlleDateien\\Demos\\ID Card\\Train\\test.jpg";	
		
		  	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		  	
            Mat image = Imgcodecs.imread(imagefile);
            
            Mat imagebw = Mat.zeros(image.size(), image.type());
            
            HighGui.imshow("New ImageDav", image);
            
            Imgproc.threshold(image, imagebw, 60 ,255,  Imgproc.THRESH_BINARY);
          //  HighGui.imshow("New ImageDav", imagebw);
            
       
           
            Imgproc.cvtColor(imagebw,imagebw,Imgproc.COLOR_BGR2GRAY);

            
           // Imgproc.threshold(imagebw, imagebw, 100,255,  Imgproc.THRESH_BINARY);
            

            double alpha = 1.5;
            double beta = -0.5;
            double gamma = 0;
            
            
            // kernel =2 , Size = 1,1   Blur 3,3
            
            double kernelSize = 1;
			Mat kernel =Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2,2));
			
			
            Mat imageclose =  Mat.zeros(imagebw.size(), imagebw.type());
		//	Imgproc.morphologyEx(imagebw, imageclose, Imgproc.MORPH_CLOSE, kernel);
			Imgproc.morphologyEx(imagebw, imagebw, Imgproc.MORPH_OPEN, kernel);
			
			Mat imagenew = Mat.zeros(imagebw.size(), imagebw.type());
			
           Core.addWeighted(imagebw, alpha, imagenew, beta, gamma,imagenew);
  
            
            Imgproc.GaussianBlur(imagenew,imagenew,new Size(3,3),0); 


			
			
/*
 * 			 Mat hierarchy = new Mat();
 /*
			 List<MatOfPoint> contours = new ArrayList<>();
			 Mat canny = Mat.zeros(imagebw.size(), imagebw.type());
			 Imgproc.Canny( imagesub, canny, 500, 1000, 3 );
			Imgproc.findContours(canny, contours ,hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
			
			double minheight = 20;
			double minwidth = 20;
			double minarea = 100;
			
			List<MatOfPoint> roiRect = new ArrayList<>();
			   Mat drawing= Mat.zeros(imagebw.size(), imagebw.type());
			
			int i = 0;
			for (MatOfPoint contour :contours) {
				
				Rect rect = Imgproc.boundingRect(new MatOfPoint2f(contour.toArray()));
				double rh = rect.height;
				double rw = rect.width;
				
				if (rh > 0) {
					double ratio = rw/rh;
					
					double area = rw*rh;
					if (area > minarea && rh > minheight && rw > minwidth && (ratio > 1  ||  ratio < 0.5)) {
						roiRect.add(contour);
						
					}
					
				}
				Imgproc.drawContours(drawing, contours, i, new Scalar(255,255,255));
				i++;
				
				
				
			}
			
	*/		
		//	   Imgproc.threshold(drawing, imagesub, 20,255,  Imgproc.THRESH_BINARY_INV);
			
			
          //  Imgproc.adaptiveThreshold(imagebw,imagebw,255,Imgproc.ADAPTIVE_THRESH_MEAN_C,Imgproc.THRESH_BINARY,11,0);   	
           //Imgproc.threshold(imageadd, imageadd, 10, 255, Imgproc.THRESH_BINARY);
       //     Imgproc.threshold(imagebw, imagebw, 180, 255, Imgproc.THRESH_BINARY_INV);
      //      Size mgSize = new Size(1,1);
        //    Imgproc.GaussianBlur(imagebw,imagebw,mgSize,0);
 			//int kernel = 0;
		//	Mat element = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT, new Size(kernel  + 1, kernel + 1),   new Point(kernel, kernel));
        //    Imgproc.erode(imagebw, imagebw, element);
            HighGui.imshow("Original Image", image);
    //        HighGui.imshow("Drawing", drawing);

            HighGui.imshow("New ImageBW", imagebw);
            HighGui.imshow("New ImageRes", imagenew);
            Imgcodecs.imwrite(savefile, imagenew);
			HighGui.waitKey(0);
			System.exit(0);
	      
	}
	
    private static byte saturate(double val) {
        int iVal = (int) Math.round(val);
        iVal = iVal > 255 ? 255 : (iVal < 0 ? 0 : iVal);
        return (byte) iVal;
    }
	
	
     
     

}
