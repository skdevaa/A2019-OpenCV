package com.automationanywhere.botcommand.sk;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class OpenCVUtils {
	
	
	public static  List<MatOfPoint> maxContours(Mat orig,double threshold,int count) 
	{
		Mat croptemp = Mat.zeros(orig.size(), orig.type());
	   Imgproc.threshold(orig, croptemp, threshold,255, Imgproc.THRESH_BINARY_INV);
       Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(11,11));
       Imgproc.morphologyEx(croptemp, croptemp, Imgproc.MORPH_CLOSE, kernel); 

        Mat hierarchy = new Mat();
        List<MatOfPoint> contours = new ArrayList<>();
       Imgproc.findContours(croptemp, contours,hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
       System.out.println("Count "+contours.size());
       contours.sort((c1,c2) -> Double.compare(Imgproc.contourArea(c2),Imgproc.contourArea(c1)));
       return contours.subList(0, count);
       	
	}
	
	
	public static double ratio(Mat image) {
		return image.size().height/image.size().width;
	}
	
    public static byte saturate(double val) {
        int iVal = (int) Math.round(val);
        iVal = iVal > 255 ? 255 : (iVal < 0 ? 0 : iVal);
        return (byte) iVal;
    }
	
	
	
	public static Mat imagerotate(Mat orig,int angle) 
	{
		

		
		int h  = orig.height();
		int w = orig.width();
		
		int cx = w/2;
		int cy = h/2;
	
		 Mat M = Imgproc.getRotationMatrix2D(new Point(cx,cy), -angle, 1);
		 
		 double absCos = Math.abs(M.get(0, 0)[0]);
		 double absSin = Math.abs(M.get(0, 1)[0]);
		 
		 double  boundW = (double)h * absSin + (double)w * absCos;
	     double  boundH = (double)h * absCos + (double)w * absSin;
	     
	     double[] data1 = {M.get(0, 2)[0] + (boundW / 2) - (double)cx}; 
	     M.put(0, 2,data1);
	     
	     double[] data2  = {M.get(1, 2)[0] + (boundH / 2) - (double)cy}; 
	     M.put(1, 2,data2);
	     
		 Mat dst = new Mat();
	     
		 Imgproc.warpAffine(orig, dst,M,new Size(boundW, boundH), Imgproc.INTER_CUBIC, Core.BORDER_CONSTANT,new Scalar(256,256,256));	  
		 return dst;
	}

}
