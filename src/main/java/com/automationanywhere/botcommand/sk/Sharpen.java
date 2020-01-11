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

import org.opencv.core.Core;
import org.opencv.core.Mat;
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
@CommandPkg(label = "Sharpen Image", name = "sharpenimage",
        description = "Sharpen Image",
        node_label = " Sharpen Image", icon = "")
public class Sharpen  {


		@Execute
         public void action (@Idx(index = "1", type = AttributeType.FILE)  @Pkg(label = "Orig. Image" , default_value_type =  DataType.FILE) @NotEmpty String imagefile,
        		                     @Idx(index = "2", type = AttributeType.FILE)  @Pkg(label = "Sharpen Image" , default_value_type =  DataType.FILE) @NotEmpty String savefile,
        		                     @Idx(index = "3", type = NUMBER) @Pkg(label = "Gaussian Blur Sigma"  , default_value_type = DataType.NUMBER ) @NotEmpty Number sigma,
        		                     @Idx(index = "4", type = NUMBER) @Pkg(label = "Weighted Alpha"  , default_value_type = DataType.NUMBER ) @NotEmpty Number alpha,
        		                     @Idx(index = "5", type = NUMBER) @Pkg(label = "Weighted Beta"  , default_value_type = DataType.NUMBER ) @NotEmpty Number beta,
        		                     @Idx(index = "6", type = NUMBER) @Pkg(label = "Weighted Gamma"  , default_value_type = DataType.NUMBER ) @NotEmpty Number gamma) throws Exception
         {    
        	 
        	 
   		  	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
             Mat image = Imgcodecs.imread(imagefile);

             
             Mat imagebw = Mat.zeros(image.size(), image.type());
             Mat imagenew = Mat.zeros(image.size(), image.type());
     		Imgproc.cvtColor(image, imagebw, Imgproc. COLOR_BGR2GRAY);
             Imgproc.GaussianBlur(imagebw, imagenew, new Size(0, 0), sigma.doubleValue());
             Core.addWeighted(imagebw, alpha.doubleValue(), imagenew, beta.doubleValue(), gamma.doubleValue(),imagenew);
             Imgcodecs.imwrite(savefile, imagenew);
        
         }
}
	
