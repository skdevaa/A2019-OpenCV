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
import org.opencv.photo.Photo;

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
@CommandPkg(label = "Advanced Denoise Image", name = "advanceddenoiseimage",
        description = "Advanced Image",
        node_label = "Advanced Denoise Image", icon = "")
public class DenoiseAdvanced  {


		@Execute
         public void action (@Idx(index = "1", type = AttributeType.FILE)  @Pkg(label = "Orig. Image" , default_value_type =  DataType.FILE) @NotEmpty String imagefile,
        		                     @Idx(index = "2", type = AttributeType.FILE)  @Pkg(label = "Denoised Image" , default_value_type =  DataType.FILE) @NotEmpty String savefile,
        		                     @Idx(index = "3", type = NUMBER) @Pkg(label = " Min. Threshold"  , default_value_type = DataType.NUMBER ) @NotEmpty Number threshold,
           		                     @Idx(index = "4", type = NUMBER) @Pkg(label = " Kernel Size"  , default_value_type = DataType.NUMBER ) @NotEmpty Number kernel,
           		                     @Idx(index = "5", type = NUMBER) @Pkg(label = " Blur Size"  , default_value_type = DataType.NUMBER ) @NotEmpty Number blur ) throws Exception
         {    

			
            
            // Good initial values : kernel =2 , Size = 1,1   Blur 3,3

            double alpha = 1.5;
            double beta = -0.5;
            double gamma = 0;
            
        	 
   		  	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            Mat image = Imgcodecs.imread(imagefile);
            
            Mat imagebw = Mat.zeros(image.size(), image.type());
            Imgproc.threshold(image, imagebw, threshold.intValue(),255,  Imgproc.THRESH_BINARY);
            Imgproc.cvtColor(image,imagebw,Imgproc.COLOR_BGR2GRAY);

            
            int kernelSize = kernel.intValue();
			Mat kernelMat =Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(kernelSize,kernelSize));
            Mat imageclose =  Mat.zeros(imagebw.size(), imagebw.type());
			Imgproc.morphologyEx(imagebw, imagebw, Imgproc.MORPH_OPEN, kernelMat);
			
			Mat imagenew = Mat.zeros(imagebw.size(), imagebw.type());
			Core.addWeighted(imagebw, alpha, imagenew, beta, gamma,imagenew);
            
		     int blurSize = blur.intValue();
            Imgproc.GaussianBlur(imagenew,imagenew,new Size(blurSize,blurSize),0); 

            Imgcodecs.imwrite(savefile, imagenew);

        
         }
}
	
