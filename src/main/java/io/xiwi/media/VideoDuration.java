package io.xiwi.media;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;

/**
 * @author <a href="mailto:simling82@gmail.com">Simling</a>
 * @version v1.0 on 2018/5/31
 */


public class VideoDuration {

    public void getVideoDuration(String path) {
        // get all files in specified "path"
        File[] files = new File(path).listFiles();

        Encoder encoder = new Encoder();
        MultimediaInfo multimediaInfo;

        long totalTime = 0L;
        long duration = 0L;

        for (int i = 0; i < files.length; i++) {
            System.out.println("file: "+files[i]);
            // here, the format of video can be changed, JAVE upports dozens of formats
            if (!files[i].isDirectory() && files[i].toString().endsWith(".avi")) {
                try {
                    multimediaInfo = encoder.getInfo(files[i]);
                    duration = multimediaInfo.getDuration();
                    totalTime += duration;
                } catch (EncoderException e) {
                    e.printStackTrace();
                }
            }
        }

        // long --> hh:mm: calculate the time manually
        System.out.print(totalTime/(3600*1000) + ":" + totalTime%(3600*1000)/(60*1000) + ":" + totalTime%(3600*1000)%(60*1000)/1000);
        System.out.println("==>Manually");

        // set a default TimeZone before using Date, Calendar and SimpleDateFormat
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+00:00")); // January 1, 1970, 00:00:00 GMT(can be found in Date.class)

        // long --> hh:mm:ss by means of java.util.Date
        Date date = new Date(totalTime);
        System.out.print(date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds());
        System.out.println("==>By Date");

        // long --> hh:mm:ss by means of java.util.Calendar, Date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        System.out.print(calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND));
        System.out.println("==>By Calendar");

        // long --> hh:mm:ss by means of java.text.SimpleDateFormat, java.util.Date
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        System.out.print(simpleDateFormat.format(date));
        System.out.println("==>By SimpleDateFormat");
    }
    public static void main(String[] args) {
//        if(args == null || args.length == 0){
//            System.out.println("Usage: File path is empty!");
//            return;
//        }
        String filePath = "/Users/Simling/Downloads/baidu/";//args[0]; //"E:\\BaiduYunDownload\\MySQL";

        VideoDuration videoDuration = new VideoDuration();
        videoDuration.getVideoDuration(filePath);
    }
}
