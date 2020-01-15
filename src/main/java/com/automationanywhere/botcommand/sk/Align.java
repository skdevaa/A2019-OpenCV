/*
 * Copyright (c) 2019 Automation Anywhere.
 * All rights reserved.
 *
 * This software is the proprietary information of Automation Anywhere.
 * You shall use it only in accordance with the terms of the license agreement
 * you entered into with Automation Anywhere.
 */
/**
 * 
 */
package com.automationanywhere.botcommand.sk;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.opencv.calib3d.Calib3d;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.DMatch;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.ORB;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import com.automationanywhere.commandsdk.annotations.BotCommand;
import com.automationanywhere.commandsdk.annotations.CommandPkg;
import com.automationanywhere.commandsdk.annotations.Execute;
import com.automationanywhere.commandsdk.annotations.Idx;
import com.automationanywhere.commandsdk.annotations.Pkg;
import com.automationanywhere.commandsdk.annotations.rules.NotEmpty;
import com.automationanywhere.commandsdk.model.AttributeType;
import com.automationanywhere.commandsdk.model.DataType;

/**
 * @author Stefan Karsten
 *
 */

@BotCommand
@CommandPkg(label = "Align Images", name = "alignimage2",
        description = "Align Images",
        node_label = " Align Image", icon = "")
public class Align  {


		@Execute
         public void action (@Idx(index = "1", type = AttributeType.FILE)  @Pkg(label = "Orig. Image" , default_value_type =  DataType.FILE) @NotEmpty String imagefile,
        		                     @Idx(index = "2", type = AttributeType.FILE)  @Pkg(label = "Reference Image" , default_value_type =  DataType.FILE) @NotEmpty String reffile,
        		                     @Idx(index = "3", type = AttributeType.FILE)  @Pkg(label = "Aligned Image" , default_value_type =  DataType.FILE) @NotEmpty String savefile) throws Exception
         {    
        	 
        	 
   		  		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

   	            Mat image = Imgcodecs.imread(imagefile);
   	            Mat imageref = Imgcodecs.imread(reffile);
   	            double ratioref = OpenCVUtils.ratio(imageref);

   	            Mat imagebw = Mat.zeros(image.size(), image.type());
   	            Mat imagebwref = Mat.zeros(imageref.size(), imageref.type());
   	      		Imgproc.cvtColor(imageref, imagebwref, Imgproc.COLOR_BGR2GRAY);
   	 
   	       		int newwidth = (int)((double)image.width()*ratioref);
   	    		int newheight = (int)((double)image.height()*ratioref);
   	    		Imgproc.resize(image, imagebw, new Size(newwidth,newheight));
   	    		Imgproc.cvtColor(imagebw, imagebw, Imgproc.COLOR_BGR2GRAY);
   	    		
   	    		
   	    		
   	    		//Some preprocessing to align the image 
	    		List<MatOfPoint>  maxcontours = OpenCVUtils.maxContours(imagebw,240,1) ;
   	    		MatOfPoint2f  maxcontour2f  = new MatOfPoint2f();
	    		maxcontours.get(0).convertTo(maxcontour2f, CvType.CV_32F);
	    		RotatedRect mrect = Imgproc.minAreaRect(maxcontour2f);
	    		
	    		int angle =(int) Math.abs(mrect.angle);
	    		
	    		Mat imagerot1 = OpenCVUtils.imagerotate(image,angle) ;
	    		Mat imagerot2 = OpenCVUtils.imagerotate(imagerot1,-90) ;
	    		
	    		
	    		//Check which height/width ratio fits better to the one of the reference image
   	            double ratio1 = OpenCVUtils.ratio(imagerot1);
   	            double ratio2 = OpenCVUtils.ratio(imagerot2);

	    		
	    		double ration1 = ratio1-ratioref;
	    		double ration2 = ratio2-ratioref;
	    		
	    		imagebw = (ration1 < ration2)  ? imagerot1 : imagerot2 ;
	    		
	    		Mat imagerot = imagebw;

	    	/*	
	    		//Now find identical keypoints and align image to reference image
   	    		ORB orb_detector = ORB.create(5000);

   	    		Mat descriptorsref = new Mat();
   	    		MatOfKeyPoint keypointsref = new MatOfKeyPoint();
   	    		orb_detector.detectAndCompute(imagebwref, new Mat(),keypointsref, descriptorsref);

   	    		Mat descriptors = new Mat();
   	    		MatOfKeyPoint keypoints = new MatOfKeyPoint();
   	    		orb_detector.detectAndCompute(imagebw, new Mat(),keypoints, descriptors);
   	    		
   	    		  
   	    		//match 2 images' descriptors
   	    	    MatOfDMatch matches = new MatOfDMatch();
   	    	    DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);
   				List<MatOfDMatch> matchLists = new ArrayList<MatOfDMatch>();
   				matcher.knnMatch(descriptors, descriptorsref,matchLists,3);
   				
   				 // distance test
   			    LinkedList<DMatch> good_matches = new LinkedList<DMatch>();
   			    for (Iterator<MatOfDMatch> iterator = matchLists.iterator(); iterator.hasNext();) {
   			        MatOfDMatch matOfDMatch = (MatOfDMatch) iterator.next();
   			        if (matOfDMatch.toArray()[0].distance / matOfDMatch.toArray()[1].distance < 0.9) {
   			            good_matches.add(matOfDMatch.toArray()[0]);
   			        }
   			    }
   			    
   			    // get keypoint coordinates of good matches to find homography and remove outliers using ransac
   			    List<Point> points = new ArrayList<Point>();
   			    List<Point> pointsref = new ArrayList<Point>();
   			    for(int i = 0; i<good_matches.size(); i++){
   			        points.add(keypoints.toList().get(good_matches.get(i).queryIdx).pt);
   			        pointsref.add(keypointsref.toList().get(good_matches.get(i).trainIdx).pt);
   			    }

   			    Mat outputMask = new Mat();
   			    MatOfPoint2f pointsMat = new MatOfPoint2f();
   			    pointsMat.fromList(points);
   			    MatOfPoint2f pointsrefMat = new MatOfPoint2f();
   			    pointsrefMat.fromList(pointsref);
   			  

   	    	    Mat H = Calib3d.findHomography( pointsMat, pointsrefMat, Calib3d.RANSAC, 1, outputMask, 1000, 0.995);
   	    	    
   	    	    
   	    	    // outputMask contains zeros and ones indicating which matches are filtered
   	    	    LinkedList<DMatch> better_matches = new LinkedList<DMatch>();
   	    	    for (int i = 0; i < good_matches.size(); i++) {
   	    	        if (outputMask.get(i, 0)[0] != 0.0) {
   	    	            better_matches.add(good_matches.get(i));
   	    	        }
   	    	    }
   		

   	    	    Mat outputImg = new Mat();
   	    	    // this will draw all matches, works fine
   	    	    MatOfDMatch better_matches_mat = new MatOfDMatch();
   	    	    better_matches_mat.fromList(better_matches);
   	    	   // Features2d.drawMatches(imagebw, keypoints, imagebwref, keypointsref, better_matches_mat, outputImg);
   	    	    
   	    	    
   	    	    //create triangles for estimateAffine2D 
   	            Point[] srcTri = new Point[3];
   	            srcTri[0] = keypoints.toList().get(better_matches.get(0).queryIdx).pt;
   	            srcTri[1] =  keypoints.toList().get(better_matches.get(1).queryIdx).pt;
   	            srcTri[2] =  keypoints.toList().get(better_matches.get(2).queryIdx).pt;
   	            Imgproc.line( imagebw,srcTri[0], srcTri[1],new Scalar( 200, 150, 200 ));
   	            Imgproc.line( imagebw,srcTri[1], srcTri[2],new Scalar( 200, 150, 200 ));
   	            Imgproc.line( imagebw,srcTri[2], srcTri[0],new Scalar(  200, 150, 200 ));
   	            Point[] dstTri = new Point[3];
   	            dstTri[0] = keypointsref.toList().get(better_matches.get(0).trainIdx).pt;
   	            dstTri[1] = keypointsref.toList().get(better_matches.get(1).trainIdx).pt;
   	            dstTri[2] = keypointsref.toList().get(better_matches.get(2).trainIdx).pt;
   	            Imgproc.line( imagebwref,dstTri[0], dstTri[1],new Scalar( 200, 150, 200  ));
   	            Imgproc.line( imagebwref,dstTri[1], dstTri[2],new Scalar(  200, 150, 200 ));
   	            Imgproc.line( imagebwref,dstTri[2], dstTri[0],new Scalar(  200, 150, 200  ));
   	            
   	            Mat warpMat = Calib3d.estimateAffine2D(new MatOfPoint2f(srcTri), new MatOfPoint2f(dstTri));
   	            double angle1 =  Math.atan2(-warpMat.get(0, 1)[0], warpMat.get(0, 0)[0])*180.0/Math.PI;
   	            double angle2 =  Math.atan2(warpMat.get(1, 0)[0], warpMat.get(1, 1)[0])*180.0/Math.PI;
   	   
   	            imagerot1 =  OpenCVUtils.imagerotate(image.clone(),(int)angle1);
   	            imagerot2 =  OpenCVUtils.imagerotate(image.clone(),(int)angle2);
   	            

   	            // Test which height/width ratio is closer to the one of the referenceimage
   	            ratio1 = OpenCVUtils.ratio(imagerot1)-ratioref;
   	            ratio2 = OpenCVUtils.ratio(imagerot2)-ratioref;

   	            
   	            Mat imagerot = (ratio1<ratio2) ? imagerot1 : imagerot2 ;
   
   	            */
   	            Imgcodecs.imwrite(savefile,  imagerot );
             
        
         }
		
	
}
	
