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
@CommandPkg(label = "Contrast Brightness Image", name = "contrastimage",
        description = "Contrast Brightness Image",
        node_label = "Contrast Brightness Image", icon = "")
public class Brightness_Contrast  {


		@Execute
         public void action (@Idx(index = "1", type = AttributeType.FILE)  @Pkg(label = "Orig. Image" , default_value_type =  DataType.FILE) @NotEmpty String imagefile,
        		                     @Idx(index = "2", type = AttributeType.FILE)  @Pkg(label = "Contrast Image" , default_value_type =  DataType.FILE) @NotEmpty String savefile,
        		                     @Idx(index = "3", type = NUMBER) @Pkg(label = "Contrast"  , default_value_type = DataType.NUMBER ) @NotEmpty Number contrast,
        		                     @Idx(index = "4", type = NUMBER) @Pkg(label = "Brightness"  , default_value_type = DataType.NUMBER ) @NotEmpty Number brightness) throws Exception
         {    
        	 
        	 
   		  	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
             Mat image = Imgcodecs.imread(imagefile);

             
             Mat imagenew = Mat.zeros(image.size(), image.type());
             Mat newImage = Mat.zeros(image.size(), image.type());
             double alpha = contrast.doubleValue();
             int beta = brightness.intValue()  ;   

             byte[] imageData = new byte[(int) (image.total()*image.channels())];
             image.get(0, 0, imageData);
             byte[] newImageData = new byte[(int) (newImage.total()*newImage.channels())];
             for (int y = 0; y < image.rows(); y++) {
                 for (int x = 0; x < image.cols(); x++) {
                     for (int c = 0; c < image.channels(); c++) {
                         double pixelValue = imageData[(y * image.cols() + x) * image.channels() + c];
                         pixelValue = pixelValue < 0 ? pixelValue + 256 : pixelValue;
                         newImageData[(y * image.cols() + x) * image.channels() + c]
                                 = OpenCVUtils.saturate(alpha * pixelValue + beta);
                     }
                 }
             }
             
             imagenew.put(0, 0, newImageData);
             Imgcodecs.imwrite(savefile, imagenew);
        
         }
}
	
