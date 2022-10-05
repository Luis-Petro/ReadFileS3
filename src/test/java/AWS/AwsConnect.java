package AWS;

import com.amazonaws.auth.*;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AwsConnect {
    private static AWSCredentials getSessionCredentials() {
        /*return new BasicSessionCredentials(
                System.getenv("accessKey"),
                System.getenv("secretKey"),
                System.getenv("sessionToken")
        );*/
        return new BasicAWSCredentials(
                System.getenv("clientid"),
                System.getenv("secretid")
        );
    }

    public static boolean checkIfFileExists(String bucket, String fileName) {
        boolean existsJson = false;
        try {
            AmazonS3 s3 = AmazonS3ClientBuilder
                    .standard()
                    .withRegion(Regions.US_EAST_1)
                    .withCredentials(new AWSStaticCredentialsProvider(getSessionCredentials()))
                    .build();
            existsJson = doesItExists(s3, bucket, fileName);
        } catch (Exception e) {
            throw e;
        }
        return existsJson;
    }

    public static boolean doesItExists(AmazonS3 s3, String bucket, String fileName) {
        boolean doesItExists = false;
        try {
            doesItExists = s3.doesObjectExist(bucket, fileName); //s3.doesBucketExistV2(bucket);
        } catch (Exception error) {
            boolean isUploadedFile = false;
            throw new IllegalStateException(error);
        }
        return doesItExists;
    }


    public static String readFileS3(String bucket, String fileName) throws IOException {

        try {
            AmazonS3 s3 = AmazonS3ClientBuilder
                    .standard()
                    .withRegion(Regions.US_EAST_1)
                    .withCredentials(new AWSStaticCredentialsProvider(getSessionCredentials()))
                    .build();
            S3Object o = s3.getObject(bucket, fileName);
            S3ObjectInputStream s3is = o.getObjectContent();
            StringBuilder sb = new StringBuilder();
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(s3is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            s3is.close();
            String textjson = sb.toString();
            return textjson;

        } catch (Exception e) {
            throw e;
        }
    }


    public static ArrayList<String> filesInS3(String bucket) {
        String list = "";
        ArrayList<String> Content = new ArrayList<String>();
        try {
            AmazonS3 s3 = AmazonS3ClientBuilder
                    .standard()
                    .withRegion(Regions.US_EAST_1)
                    .withCredentials(new AWSStaticCredentialsProvider(getSessionCredentials()))
                    .build();
            ListObjectsV2Result result = s3.listObjectsV2(bucket);
            List<S3ObjectSummary> objects = result.getObjectSummaries();
            //list = objects.toString();
            for (S3ObjectSummary os : objects) {
                list = list + ", " + os.getKey();
                Content.add(os.getKey());
                System.out.println("********" + os.getKey());
            }
            return Content;
        } catch (Exception e) {
            throw e;
        }
    }

    public static String ShowContent(String bucket) {
        String list = "";
        ArrayList<String> Content = new ArrayList<String>();
        ArrayList<Integer> Content2 = new ArrayList<Integer>();
       Content = filesInS3(bucket);

        for (int i=0;i<Content.size();i++) {
            list = list + ", " + Content.get(i);
            System.out.println("--->"+Content.get(i));
        }

        for(int j=0;j< Content.size();j++){
            if(ClasificationYears(Content.get(j)) != 0) {
                Content2.add(ClasificationYears(Content.get(j)));
                System.out.println("_-_-" + ClasificationYears(Content.get(j)));
            }
        }
      //Integer UltYear = Integer.parseInt(Collections.max(Content2).toString());
     return list;
    }

    public static  Integer ClasificationHour(String Daily, String Month){
        Integer IntDay = 0;
        String StringDay = "";
        if(Daily.contains(Month) && Daily.length()>30 && Daily.length() < 35  ){
            StringDay = Daily.substring(31,Daily.length()-1);
            StringDay = StringDay.replaceAll("[^0-9]", "");
            IntDay = Integer.parseInt(StringDay);
        }
        return IntDay;
    }
//
    public static  Integer ClasificationDay(String Daily, String Month){
        Integer IntDay = 0;
        String StringDay = "";
        if(Daily.contains(Month) && Daily.length()>22 && Daily.length() < 27  ){
            StringDay = Daily.substring(20,Daily.length()-1);
            StringDay = StringDay.replaceAll("[^0-9]", "");
            IntDay = Integer.parseInt(StringDay);
            if(StringDay == ""){
                IntDay = -1;
            }
        }
        return IntDay;
    }

    public static Integer ClasificationMonth(String Months, Integer Year){
        Integer IntMonth = 0;
        String  StringMonth= "";
        if(Months.length()>10 && Months.length() < 21 && Months.contains(Year.toString())){
            StringMonth = Months.substring(10, Months.length()-1);
            StringMonth = StringMonth.replaceAll("[^0-9]", "");
            IntMonth = Integer.parseInt(StringMonth);
        }
        return IntMonth;
    }

    public  static Integer ClasificationYears(String Data){
        Integer YearInt = 0;
        String YearString = "";
            if(Data.length() < 12){
               YearString = Data.replaceAll("[^0-9]", "");
               YearInt = Integer.parseInt(YearString);
            }
          return  YearInt;
        }
    }






