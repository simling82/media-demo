package io.xiwi.media;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.MultimediaInfo;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

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
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");
        int rowindex = 0;
        for (int i = 0; i < files.length; i++) {

            // here, the format of video can be changed, JAVE upports dozens of formats
            if (!files[i].isDirectory()) {
                try {
                    multimediaInfo = encoder.getInfo(files[i]);
                    long duration = multimediaInfo.getDuration();
                    totalTime = duration;
                    String time = DurationFormatUtils.formatDuration(totalTime, "HH:mm:ss");
                    System.out.println(files[i].getName() + "\t" + time);

                    Row row = sheet.createRow(rowindex);
                    Cell cell = row.createCell(0);
                    cell.setCellValue(files[i].getName());
                    Cell cell1 = row.createCell(1);
                    cell1.setCellValue(time);
                    rowindex++;
                } catch (Exception e) {
                    System.out.println(files[i].getName()+" is not video file.");
                }
            }
        }

        try {
            workbook.write(new FileOutputStream(path+"/output.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void print(String filename, long totalTime){

        System.out.println(filename+"\t"+ DurationFormatUtils.formatDuration(totalTime, "HH:mm:ss"));
//         long --> hh:mm: calculate the time manually
//        System.out.print(totalTime/(3600*1000) + ":" + totalTime%(3600*1000)/(60*1000) + ":" + totalTime%(3600*1000)%(60*1000)/1000);
//        System.out.println("==>Manually");
//
//        // set a default TimeZone before using Date, Calendar and SimpleDateFormat
//        TimeZone.setDefault(TimeZone.getTimeZone("GMT+00:00")); // January 1, 1970, 00:00:00 GMT(can be found in Date.class)
//
//        // long --> hh:mm:ss by means of java.util.Date
//        Date date = new Date(totalTime);
//        System.out.print(date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds());
//        System.out.println("==>By Date");
//
//        // long --> hh:mm:ss by means of java.util.Calendar, Date
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        System.out.print(calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND));
//        System.out.println("==>By Calendar");

        // long --> hh:mm:ss by means of java.text.SimpleDateFormat, java.util.Date
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
//        System.out.println(file.getName()+"\t"+simpleDateFormat.format(date));
//        System.out.println("==>By SimpleDateFormat");
    }
    public static void main(String[] args) {
        if(args == null || args.length == 0){
            System.out.println("Usage: File path is empty!");
            return;
        }
        String filePath = args[0]; //"C:/BaiduNetdiskDownload/";;

        VideoDuration videoDuration = new VideoDuration();
        videoDuration.getVideoDuration(filePath);
    }
}
