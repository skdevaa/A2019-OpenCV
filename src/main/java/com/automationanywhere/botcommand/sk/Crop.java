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




import static com.automationanywhere.commandsdk.model.AttributeType.NUMBER;



import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Size;

import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

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
@CommandPkg(label = "Crop Image", name = "cropimage",
        description = "Crop Image",
        node_label = "Crop Image", icon = "")
public class Crop  {


		@Execute
         public void action (@Idx(index = "1", type = AttributeType.FILE)  @Pkg(label = "Orig. Image" , default_value_type =  DataType.FILE) @NotEmpty String imagefile,
        		                     @Idx(index = "2", type = AttributeType.FILE)  @Pkg(label = "Cropped Image 1" , default_value_type =  DataType.FILE) @NotEmpty String savefile1,
        		                     @Idx(index = "3", type = AttributeType.FILE)  @Pkg(label = "Cropped Image 2" , default_value_type =  DataType.FILE)  String savefile2,
        		                     @Idx(index = "4", type = AttributeType.FILE)  @Pkg(label = "Cropped Image 3" , default_value_type =  DataType.FILE)  String savefile3,
        		                     @Idx(index = "5", type = NUMBER) @Pkg(label = "Threshold"  , default_value_type = DataType.NUMBER ) @NotEmpty Number threshold ) throws Exception
         {    
        	 
   		  	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
             Mat image = Imgcodecs.imread(imagefile);
             
             int count = 1;

             if (savefile2 != null) count++;
             if (savefile3 != null) count++;
             
             Mat imagebw = Mat.zeros(image.size(), image.type());
             Mat imagenew1 = new Mat();
             Mat imagenew2 = new Mat();
             Mat imagenew3 = new Mat();
             Imgproc.cvtColor(image, imagebw, Imgproc.COLOR_BGR2GRAY);

             List<MatOfPoint> contours  = OpenCVUtils.maxContours(imagebw,threshold.intValue(),count) ;
             Rect boundingrect1 = Imgproc.boundingRect(new MatOfPoint(contours.get(0).toArray()));
             imagenew1 = image.submat(boundingrect1);
             Imgcodecs.imwrite(savefile1, imagenew1);
             
             if (savefile2 != null && contours.size()>=2)  { 
            	 Rect boundingrect2 = Imgproc.boundingRect(new MatOfPoint(contours.get(1).toArray()));
                 imagenew2 = image.submat(boundingrect2);
                 Imgcodecs.imwrite(savefile2, imagenew2);
             }
             
             if (savefile3 != null && contours.size()>=3)  { 
            	 Rect boundingrect3 = Imgproc.boundingRect(new MatOfPoint(contours.get(2).toArray()));
                 imagenew3 = image.submat(boundingrect3);
                 Imgcodecs.imwrite(savefile3, imagenew3);
             }

        
         }
}
	
