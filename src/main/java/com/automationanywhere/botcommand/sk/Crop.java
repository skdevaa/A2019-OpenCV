/*
C:\Users\Stefan Karsten\Documents\AlleDateien\Demos\Packages\IBM Watson * Copyright (c) 2019 Automation Anywhere.
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



import static com.automationanywhere.commandsdk.model.AttributeType.TEXT;
import static com.automationanywhere.commandsdk.model.AttributeType.NUMBER;

import static com.automationanywhere.commandsdk.model.DataType.STRING;

import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import com.automationanywhere.commandsdk.annotations.BotCommand;
import com.automationanywhere.commandsdk.annotations.CommandPkg;

import com.automationanywhere.commandsdk.annotations.Idx;
import com.automationanywhere.commandsdk.annotations.Pkg;
import com.automationanywhere.commandsdk.annotations.rules.NotEmpty;
import com.automationanywhere.commandsdk.model.AttributeType;
import com.automationanywhere.commandsdk.model.DataType;


import com.automationanywhere.commandsdk.annotations.ConditionTest;
import com.automationanywhere.commandsdk.annotations.Execute;
import com.automationanywhere.commandsdk.annotations.BotCommand.CommandType;
/**
 * @author Stefan Karsten
 *
 */


@BotCommand
@CommandPkg(label = "Crop Image", name = "cropimage",
        description = "Crop Image",
        node_label = " Crop Image", icon = "")
public class Crop  {


		@Execute
         public void action (@Idx(index = "1", type = AttributeType.FILE)  @Pkg(label = "Orig. Image" , default_value_type =  DataType.FILE) @NotEmpty String imagefile,
        		                     @Idx(index = "2", type = AttributeType.FILE)  @Pkg(label = "Sharpen Image" , default_value_type =  DataType.FILE) @NotEmpty String savefile,
        		                     @Idx(index = "3", type = NUMBER) @Pkg(label = "Threshold"  , default_value_type = DataType.NUMBER ) @NotEmpty Number threshold ) throws Exception
         {    
        	 
        	 
   		  	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
             Mat image = Imgcodecs.imread(imagefile);

             
             Mat imagebw = Mat.zeros(image.size(), image.type());
             Mat imagenew = Mat.zeros(image.size(), image.type());
             Imgproc.cvtColor(image, imagebw, Imgproc.COLOR_BGR2GRAY);

             Imgproc.threshold(imagebw, imagebw, threshold.doubleValue(),255, Imgproc.THRESH_BINARY_INV);

             Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(11,11));
             Imgproc.morphologyEx(imagebw, imagebw, Imgproc.MORPH_CLOSE, kernel); 


              MatOfPoint maxContour = null ;
              Mat hierarchy = new Mat();
              double maxVal = 0;
              List<MatOfPoint> contours = new ArrayList<>();
             Imgproc.findContours(imagebw, contours,hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
              for (int contourIdx = 0; contourIdx < contours.size(); contourIdx++)
              {
                 double contourArea = Imgproc.contourArea(contours.get(contourIdx));
     		     if (maxVal < contourArea)
                 {
                     maxVal = contourArea;
                     maxContour = contours.get(contourIdx);
                 }
             }

             Rect boundingrect = Imgproc.boundingRect(new MatOfPoint(maxContour.toArray()));
             imagenew = image.submat(boundingrect);

             Imgcodecs.imwrite(savefile, imagenew);
        
         }
}
	
