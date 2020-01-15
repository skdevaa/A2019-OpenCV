package com.automationanywhere.botcommand.sk;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
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
		
		
		String imagefile = "C:\\Users\\Stefan Karsten\\Documents\\AlleDateien\\Demos\\ID Card\\Pics\\New_ID.jpg";
		String imagereffile = "C:\\Users\\Stefan Karsten\\Documents\\AlleDateien\\Demos\\ID Card\\Pics\\ID_1_Crop.jpg";
		
		String savefile = "C:\\Users\\Stefan Karsten\\Documents\\AlleDateien\\Demos\\ID Card\\Train\\nonoise.jpg";	
		
		  	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            Mat image = Imgcodecs.imread(imagefile);

            Mat newImage = Mat.zeros(image.size(), image.type());
            double alpha = 3.0; /*< Simple contrast control */
            int beta = 0;       /*< Simple brightness control */

            byte[] imageData = new byte[(int) (image.total()*image.channels())];
            image.get(0, 0, imageData);
            byte[] newImageData = new byte[(int) (newImage.total()*newImage.channels())];
            for (int y = 0; y < image.rows(); y++) {
                for (int x = 0; x < image.cols(); x++) {
                    for (int c = 0; c < image.channels(); c++) {
                        double pixelValue = imageData[(y * image.cols() + x) * image.channels() + c];
                        pixelValue = pixelValue < 0 ? pixelValue + 256 : pixelValue;
                        newImageData[(y * image.cols() + x) * image.channels() + c]
                                = saturate(alpha * pixelValue + beta);
                    }
                }
            }
            newImage.put(0, 0, newImageData);
            HighGui.imshow("Original Image", image);
            HighGui.imshow("New Image", newImage);


			HighGui.waitKey(0);
			System.exit(0);
	      
	}
	
    private static byte saturate(double val) {
        int iVal = (int) Math.round(val);
        iVal = iVal > 255 ? 255 : (iVal < 0 ? 0 : iVal);
        return (byte) iVal;
    }
	
	
     
     

}
