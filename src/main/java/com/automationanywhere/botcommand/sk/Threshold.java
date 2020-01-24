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
public class Threshold  {


		@Execute
         public void action (@Idx(index = "1", type = AttributeType.FILE)  @Pkg(label = "Orig. Image" , default_value_type =  DataType.FILE) @NotEmpty String imagefile,
        		                     @Idx(index = "2", type = AttributeType.FILE)  @Pkg(label = "Threshold Image" , default_value_type =  DataType.FILE) @NotEmpty String savefile,
        		                     @Idx(index = "3", type = AttributeType.FILE)  @Pkg(label = "Min. Threshold" , default_value_type =  DataType.FILE) @NotEmpty Number minThreshold,
        		                     @Idx(index = "4", type = AttributeType.FILE)  @Pkg(label = "Max. Threshold" , default_value_type =  DataType.FILE) @NotEmpty Number maxThreshold,
        		        			@Idx(index = "5", type = AttributeType.CHECKBOX) @Pkg(label = "Apply Blur before Threshold", default_value_type = DataType.BOOLEAN) @NotEmpty Boolean ApplyBlur,
        		        			@Idx(index = "6", type = AttributeType.CHECKBOX) @Pkg(label = "Invert Image", default_value_type = DataType.BOOLEAN) @NotEmpty Boolean InvertImage)
         {    
        	 
        	 
   		  	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
             Mat image = Imgcodecs.imread(imagefile);
             

             int mSize = 3;

             // Grayscale
             Imgproc.cvtColor(image,image,Imgproc.COLOR_BGR2GRAY);
    

             // Thresholding
             if(InvertImage.booleanValue()){
            	 Imgproc.threshold(image, image, minThreshold.intValue(),maxThreshold.intValue(), Imgproc.THRESH_BINARY_INV);
             }else{
            	 Imgproc.threshold(image, image, minThreshold.intValue(),maxThreshold.intValue(),  Imgproc.THRESH_BINARY);
             }
             
             if(ApplyBlur.booleanValue()){
                 // Gaussian Blur
                 Size mgSize = new Size(mSize,mSize);
                 Imgproc.GaussianBlur(image,image,mgSize,0);
             }
             

             Imgcodecs.imwrite(savefile,image);
         }


}
	
