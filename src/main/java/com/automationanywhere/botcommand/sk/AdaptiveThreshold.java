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

import org.opencv.core.Core;
import org.opencv.core.Mat;
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
@CommandPkg(label = "Adpative Threshold Image", name = "adaptiveimage",
        description = "Adaptive Threshold Image",
        node_label = "Adaptive Threshold Image", icon = "")
public class AdaptiveThreshold  {


		@Execute
         public void action (@Idx(index = "1", type = AttributeType.FILE)  @Pkg(label = "Orig. Image" , default_value_type =  DataType.FILE) @NotEmpty String imagefile,
        		                     @Idx(index = "2", type = AttributeType.FILE)  @Pkg(label = "Adpative Threshold Image" , default_value_type =  DataType.FILE) @NotEmpty String savefile,
        		        			@Idx(index = "3", type = AttributeType.CHECKBOX) @Pkg(label = "Apply Blur before Threshold", default_value_type = DataType.BOOLEAN) @NotEmpty Boolean ApplyBlur,
        		        			@Idx(index = "4", type = AttributeType.CHECKBOX) @Pkg(label = "Invert Image", default_value_type = DataType.BOOLEAN) @NotEmpty Boolean InvertImage)
         {    
        	 
        	 
   		  	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
             Mat image = Imgcodecs.imread(imagefile);
             

             int mSize = 3;
             int MaxValue = 255;
           
             // Grayscale
             Imgproc.cvtColor(image,image,Imgproc.COLOR_BGR2GRAY);
    
             if(ApplyBlur.booleanValue()){
                 // Gaussian Blur
                 Size mgSize = new Size(mSize,mSize);
                 Imgproc.GaussianBlur(image,image,mgSize,0);
             }
             
             
            // thresh_mean = cv2.adaptiveThreshold(blurred, pixel_max_value,cv2.ADAPTIVE_THRESH_MEAN_C, cv2.THRESH_BINARY_INV, 11, 4)
             //thresh_gaussian = cv2.adaptiveThreshold(blurred, pixel_max_value,cv2.ADAPTIVE_THRESH_GAUSSIAN_C, cv2.THRESH_BINARY_INV, 29, 3)

             // Thresholding
             if(InvertImage.booleanValue()){
                 Imgproc.adaptiveThreshold(image,image,MaxValue,Imgproc.ADAPTIVE_THRESH_MEAN_C,Imgproc.THRESH_BINARY_INV,11,0);
             }else{
                 Imgproc.adaptiveThreshold(image,image,MaxValue,Imgproc.ADAPTIVE_THRESH_MEAN_C,Imgproc.THRESH_BINARY,11,0);
             }

             Imgcodecs.imwrite(savefile,image);
         }


}
	
