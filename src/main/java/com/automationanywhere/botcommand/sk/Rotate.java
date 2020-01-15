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
import org.opencv.core.Point;
import org.opencv.core.Scalar;
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
 * @param <imagerotate>
 *
 */

@BotCommand
@CommandPkg(label = "Rotate Image", name = "rotateimage",
        description = "Rotate Image",
        node_label = "Rotate Image", icon = "")
public class Rotate<imagerotate>  {


		@Execute
         public void action (@Idx(index = "1", type = AttributeType.FILE)  @Pkg(label = "Orig. Image" , default_value_type =  DataType.FILE) @NotEmpty String imagefile,
        		                     @Idx(index = "2", type = AttributeType.FILE)  @Pkg(label = "Rotated Image" , default_value_type =  DataType.FILE) @NotEmpty String savefile,
        		                     @Idx(index = "3", type = NUMBER) @Pkg(label = "Angle (Degree)"  , default_value_type = DataType.NUMBER ) @NotEmpty Number angle) throws Exception
         {    
        	 
        	 
   		  	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
             Mat image = Imgcodecs.imread(imagefile);

             Mat imagerot = OpenCVUtils.imagerotate(image,angle.intValue());

             Imgcodecs.imwrite(savefile,  imagerot );
             
        
         }
}
	
