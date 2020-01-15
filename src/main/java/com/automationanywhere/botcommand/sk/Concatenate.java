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

import static com.automationanywhere.commandsdk.model.AttributeType.SELECT;
import static com.automationanywhere.commandsdk.model.DataType.STRING;

import java.util.Arrays;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
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
@CommandPkg(label = "Concatenate Images", name = "concatenateimage",
        description = "Concatenate Images",
        node_label = "Concatenate Images", icon = "")
public class Concatenate   {


		@Execute
         public void action (@Idx(index = "1", type = AttributeType.FILE)  @Pkg(label = "Image 1" , default_value_type =  DataType.FILE) @NotEmpty String imagefile1,
        		             @Idx(index = "2", type = AttributeType.FILE)  @Pkg(label = "Image 2" , default_value_type =  DataType.FILE) @NotEmpty String imagefile2,
        		             @Idx(index = "3", type = AttributeType.FILE)  @Pkg(label = "Concatenated Image" , default_value_type =  DataType.FILE) @NotEmpty String savefile,
        		    		 @Idx(index = "4", type = SELECT, options = {
        		    	    					@Idx.Option(index = "4.1", pkg = @Pkg(label = "Vertical", value = "VERTICAL")),
        		    							@Idx.Option(index = "4.2", pkg = @Pkg(label = "Horizontal", value = "HORIZONTAL")),
        		    				         	}) @Pkg(label = "Layout", default_value = "VERTICAL", default_value_type = STRING) @NotEmpty String layout) throws Exception
         {    
        	 
			int top, bottom, left, right; 
	        int borderType = Core.BORDER_CONSTANT;
            Scalar colorvalue = new Scalar( 255, 255, 255 );
			
   		  	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
             Mat image1 = Imgcodecs.imread(imagefile1);
             Mat image2 = Imgcodecs.imread(imagefile2);
             
             if (image1.channels() == 1) Imgproc.cvtColor(image1, image1, Imgproc.COLOR_BGR2RGB);
             if (image1.channels() == 1) Imgproc.cvtColor(image2, image2, Imgproc.COLOR_BGR2RGB);
             
             int deltacols = image1.cols()-image2.cols();
             int deltarows = image1.rows()-image2.rows();
             
             left =  Math.abs(deltacols/2);
             right = Math.abs(deltacols/2) + Math.abs(deltacols%2); 
             top = Math.abs(deltarows/2);
             bottom = Math.abs(deltarows/2) + Math.abs(deltarows%2); 
             
  

             Mat dst = new Mat();
             if (layout.equals("VERTICAL")) {
                 if (deltacols > 0) {
                	 Core.copyMakeBorder( image2, image2, 0,0, left, right, borderType, colorvalue);
                 }
                 else
                 {
                	 Core.copyMakeBorder( image1, image1, 0,0, left, right, borderType, colorvalue);
                 }
                 List<Mat> src = Arrays.asList(image1,image2);
            	 Core.vconcat(src,dst);
             }
             else {
                 if (deltarows > 0) {
                	 Core.copyMakeBorder( image2, image2, top,bottom,0,0, borderType, colorvalue);
                 }
                 else
                 {
                  	 Core.copyMakeBorder( image1, image1, top,bottom,0,0, borderType, colorvalue);
                 }
                 List<Mat> src = Arrays.asList(image1,image2);
            	 Core.hconcat(src,dst);
             }
            
             Imgcodecs.imwrite(savefile, dst);
        
         }
}
	
