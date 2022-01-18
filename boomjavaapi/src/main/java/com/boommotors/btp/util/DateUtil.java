/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boommotors.btp.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author rjanumpally
 */
@Component
public class DateUtil {
    
    @Autowired 
    DateDAO dateRepository;

    private static final long TIME_DURATION = 24 * 60 * 60 * 1000; // 24 hours
    
    public java.sql.Timestamp convertStringToJavaSQLDateTime(String inputDate) {
        java.sql.Timestamp convertedDate = null;
        String chngdDate = "";
        //System.out.println("input date is "+inputDate);
        if (inputDate != null) {
            try {
                //yyyymmdd HH:mm:ss
                SimpleDateFormat sdfIn = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
                java.util.Date parsedDt = new java.util.Date();
                try {
                    parsedDt = sdfIn.parse(inputDate);
                } catch (ParseException ex) {
                    Logger.getLogger(DateUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
                sdfIn.applyPattern("yyyy-MM-dd HH:mm:ss");
                chngdDate = sdfIn.format(parsedDt);

                //System.out.println("Changed DateTime: " + chngdDate);
                SimpleDateFormat sdfOut = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                java.util.Date date = sdfOut.parse(chngdDate);

                //System.out.println("Util DateTime: " + date);
                convertedDate = new java.sql.Timestamp(date.getTime());
                //System.out.println("SQL DateTime: " + convertedDate);
            } catch (ParseException ex) {
                Logger.getLogger(DateUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //System.out.println("chngdDate is ** " + chngdDate);
        return convertedDate;
    }

    public Timestamp getTimestamp() {
        DateFormat df = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        DateFormat df1 = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("IST"));
        df1.setTimeZone(TimeZone.getTimeZone("IST"));
        Timestamp date = null;
        try {
            date = new Timestamp(df1.parse(df.format(new java.util.Date())).getTime());
        } catch (ParseException e) {
        }
        return date;
    }

    
    public Timestamp getTimestamptoSendSMS() {
       DateDTO dateNow = dateRepository.retrieveISTTimeFromDB();
       System.out.println("dateNow : " + dateNow.getTimezone());
       
       return dateNow.getTimezone();
    }
    
    public Date convertJsonInputDateToSqlDate(String inputDate) {
        java.sql.Date convertedDate = null;
        String chngdDate = "";
        try {
            java.util.Date parsedDt = new java.util.Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("IST"));

            parsedDt = sdf.parse(inputDate);

            sdf.applyPattern("yyyy-MM-dd");
            chngdDate = sdf.format(parsedDt);
            //System.out.println("Changed Date: " + chngdDate);

            SimpleDateFormat sdfOut = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
            sdfOut.setTimeZone(TimeZone.getTimeZone("IST"));
            java.util.Date date = sdfOut.parse(chngdDate);

            //System.out.println("Util Date: " + date);
            convertedDate = new java.sql.Date(date.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(DateUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("convertedDate: " + convertedDate);
        return convertedDate;
    }

    public Date getTodayDate() {
        SimpleDateFormat formatterOut = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
        Calendar cal = Calendar.getInstance();
        String todayDateString = dateFormat.format(cal.getTime());
        //System.out.println(todayDateString); //2014/08/06 16:00:22
        Date todayDate = parseDate(formatStringDate(todayDateString), formatterOut);
        //System.out.println("todayDate:" + todayDate); //2014/08/06 16:00:22
        return todayDate;
    }

    public String formatStringDate(String sDate) {
        SimpleDateFormat formatterIn = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatterOut = new SimpleDateFormat("yyyy-MM-dd");

        String newformat = parseDate(sDate, formatterIn, formatterOut);

        return (newformat);
    }

    private String parseDate(String sDate, SimpleDateFormat formatterIn, SimpleDateFormat formatterOut) {
        String newformat = "";
        try {
            java.util.Date oDate = formatterIn.parse(sDate); //parse the string to a date
            newformat = formatterOut.format(oDate); //create new String format
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (newformat);
    }

    private java.sql.Date parseDate(String sDate, SimpleDateFormat formatter) {
        java.sql.Date sqlDate = null;

        try {
            java.util.Date oDate = formatter.parse(sDate); //parse the string to a date
            long time = oDate.getTime(); //get the time
            sqlDate = new java.sql.Date(time); //set the sql date
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return (sqlDate);
    }
    
    
     public Timestamp getYesterdaysTimestamp() {
        DateFormat df = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        DateFormat df1 = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("IST"));
        df1.setTimeZone(TimeZone.getTimeZone("IST"));
        Timestamp date = null;
      
        try { 
            Long now = (df1.parse(df.format(new java.util.Date())).getTime()) - TIME_DURATION ;
            date = new Timestamp(now);
        } catch (ParseException e) {
        }
        return date;
    }
}
