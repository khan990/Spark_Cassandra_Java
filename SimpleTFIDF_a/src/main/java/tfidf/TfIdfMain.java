/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tfidf;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author Mubin Shrestha
 */
public class TfIdfMain {
    
    /**
     * Main method
     * @param args
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void main(String args[]) throws FileNotFoundException, IOException
    {
    	System.out.println("Started...");
    	Date startTime = new Date();
        DocumentParser dp = new DocumentParser();
        dp.parseFiles("/home/khan990/Documents/input150/input");
        System.out.println("Log: Calculating TFIDF...");
        dp.tfIdfCalculator(); //calculates tfidf
        System.out.println("Log: Calculating Cosine Similarities...");
        dp.getCosineSimilarity(); //calculated cosine similarity
        Date endTime = new Date();
        System.out.println("Start Time: " + startTime.toString());
		System.out.println("End Time: " + endTime.toString());
		System.out.println("Time Difference: "
				+ (endTime.getTime() - startTime.getTime()) + " milli seconds");
        System.out.println("Ended...");
    }
}
