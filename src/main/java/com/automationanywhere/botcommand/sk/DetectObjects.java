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



import java.util.PriorityQueue;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;

import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import com.automationanywhere.botcommand.data.impl.NumberValue;
import com.automationanywhere.commandsdk.annotations.BotCommand;
import com.automationanywhere.commandsdk.annotations.CommandPkg;

import com.automationanywhere.commandsdk.annotations.Idx;
import com.automationanywhere.commandsdk.annotations.Pkg;
import com.automationanywhere.commandsdk.annotations.rules.NotEmpty;
import com.automationanywhere.commandsdk.model.AttributeType;
import com.automationanywhere.commandsdk.model.DataType;
import com.automationanywhere.commandsdk.annotations.Execute;

/**
 * @author Stefan Karsten
 *
 */

@BotCommand
@CommandPkg(label = "Detect Objects", name = "detectobjects",
        description = "Detect Objects",
        node_label = "Detect Objects", icon = "",return_type = DataType.NUMBER, return_required = true)
public class DetectObjects  {


		@Execute
         public NumberValue action (@Idx(index = "1", type = AttributeType.FILE)  @Pkg(label = "Orig. Image" , default_value_type =  DataType.FILE) @NotEmpty String imagefile,
        		                     @Idx(index = "2", type = AttributeType.FILE)  @Pkg(label = "Image with Objects" , default_value_type =  DataType.FILE) @NotEmpty String savefile,
        		                     @Idx(index = "3", type = AttributeType.FILE)  @Pkg(label = "Cascade Classifier XML File" , default_value_type =  DataType.FILE) @NotEmpty String xmlfile,
        		                     @Idx(index = "4", type = AttributeType.NUMBER) @Pkg(label = "Number of Top Objects"  , default_value_type = DataType.NUMBER ) Number noofobjects) throws Exception
         {    
        	 
			NumberValue returnvalue = new NumberValue();
			
			int limit = (int) ((noofobjects != null) ? noofobjects.longValue() : 100);
        	 
   		  	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
             Mat image = Imgcodecs.imread(imagefile);

             
             Mat imagebw = Mat.zeros(image.size(), image.type());
             Mat imagenew = Mat.zeros(image.size(), image.type());

             
             imagenew = image.clone();
             Imgproc.cvtColor(image, image, Imgproc.COLOR_BGR2GRAY);
             // Instantiating the CascadeClassifier
             CascadeClassifier classifier = new CascadeClassifier(xmlfile);

             // Detecting the face in the snap
             MatOfRect objectDetections = new MatOfRect();
             classifier.detectMultiScale(image, objectDetections);
             PriorityQueue<Rect> rq = new PriorityQueue<Rect>(5,(r1,r2) -> { 
                 if (r1.area() < r2.area()) 
                     return 1; 
                 else if (r1.area() > r2.area()) 
                     return -1; 
                                 return 0; 
                 } 
             ); 
             
             for (Rect rect : objectDetections.toArray()) {
              	rq.add(rect);
              }

             // Drawing boxes
             Integer objCounter = 0;
     		 for (int i= 0;i<limit && i < objectDetections.toArray().length ;i++) {
             	   objCounter++;
             	   Rect rect = rq.poll();
                Imgproc.rectangle(
                   imagenew,                                               // where to draw the box
                   new Point(rect.x, rect.y),                            // bottom left
                   new Point(rect.x + rect.width, rect.y + rect.height), // top right
                   new Scalar(0, 0, 255),
                   3                                                     // RGB colour
                );
              }
             Imgcodecs.imwrite(savefile, imagenew);

             returnvalue.set(new Double(objCounter));
             return returnvalue;
        
         }
}
	
