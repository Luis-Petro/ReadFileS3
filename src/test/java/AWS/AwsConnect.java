package AWS;

import com.amazonaws.auth.*;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AwsConnect {

    private static AWSCredentials getSessionCredentials() {
        return new BasicSessionCredentials(
                System.getenv("accessKey"),
                System.getenv("secretKey"),
                System.getenv("sessionToken")
        );
    }

    public static String readFileS3(String bucket) throws IOException {

        try {
            AmazonS3 s3 = AmazonS3ClientBuilder
                    .standard()
                    .withRegion(Regions.US_EAST_1)
                    .withCredentials(new AWSStaticCredentialsProvider(getSessionCredentials()))
                    .build();
            String fileName = lastRegister(bucket);
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


    public static ArrayList<String> listFilesInS3(String bucket) {

        ArrayList<String> Content = new ArrayList();
        try {
            AmazonS3 s3 = AmazonS3ClientBuilder
                    .standard()
                    .withRegion(Regions.US_EAST_1)
                    .withCredentials(new AWSStaticCredentialsProvider(getSessionCredentials()))
                    .build();
            ListObjectsV2Result result = s3.listObjectsV2(bucket);
            List<S3ObjectSummary> objects = result.getObjectSummaries();
            for (S3ObjectSummary os : objects) {
                Content.add(os.getKey());
                System.out.println("*******>>" + os.getKey());
            }
            return Content;
        } catch (Exception e) {
            throw e;
        }
    }


    public static String lastRegister(String bucket) {
        String lastRegitesInS3 = "";
        ArrayList<String> Content = listFilesInS3(bucket);
        int lastElement = Content.size() - 1;
        lastRegitesInS3 = Content.get(lastElement).toString();
        System.out.println("=======lastRegister====>>" + lastRegitesInS3);
        return lastRegitesInS3;
    }

}

