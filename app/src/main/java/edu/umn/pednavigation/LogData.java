/*
 * LogData.java
 * Log data class. Write log to data file.
 * Created on Wednesday, December 21, 2011, 10:34:24 AM
 */

/*
 * PI: Chen-Fu Liao
 * University of Minnesota
 * Department of Civil Engineering
 * Minnesota Traffic Observatory (MTO)
 * 500 Pillsbury Drive SE
 * Minneapolis, MN 55455
 */

package edu.umn.pednavigation;

import android.app.Application;
import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/* Class to log the information to file */
public class LogData extends Application {

	// Write2Log flag - added by Chen-Fu, 4/21/12
	private boolean Log2File_FLAG = true ;	// true - write log to file, false - no logging
	public static final String PED_DIRPATH = "c://smartphone";

	private String getCurrentTime() {
		final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return sdf.format(cal.getTime());
	}

	// Chen-Fu 4/10/12
	private String getCurrentDate() {
		final String DATE_FORMAT = "yyyy_MM_dd";

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return sdf.format(cal.getTime());
	}
	
	public void WriteDataLogtoFile(String str) {
		if (Log2File_FLAG==true) {
			File root = Environment.getExternalStorageDirectory();
		//	File file = new File(root.getAbsolutePath() + PED_DIRPATH);
			File file = new File(PED_DIRPATH);
			File sDatafile = new File("C:\\Users\\Kaushiki\\maps\\logg.txt");
		
		//	if (root.canWrite())
			{
				BufferedWriter out = null;
				try {
				//	System.out.println("logging: "+str);
					//File sDatafile = new File(file, "Pedistrian_Log_" + getCurrentDate() + ".txt");
					
					FileWriter sDatawriter = new FileWriter("c:\\Users\\Kaushiki\\maps\\logg.txt", true);
					
					out = new BufferedWriter(sDatawriter, 8 * 1024);
					out.write(getCurrentTime() +  " " +	str + "\n");
//					System.out.println(str);
					out.close();
					out = null;
				} catch (IOException e) {
				} catch (Exception e) {
				} finally {
					try {
						if (out != null)
							out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}	// if Log2File_FLAG
	}	// WriteDataLogtoFile method

}	// LogData Class