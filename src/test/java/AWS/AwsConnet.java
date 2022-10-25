package AWS;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public  class  AwsConnet {

    public static  AWSCredentials getSessionCredentials() {
        return new BasicAWSCredentials(
                System.getenv("clientid"),
                System.getenv("secretid")
        ); /*
        return new BasicSessionCredentials(
                System.getenv("accessKey"),
                System.getenv("secretKey"),
               System.getenv("sessionToken")
        );  */
    }
    public static  AmazonS3 s3Connection(){
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.US_EAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(getSessionCredentials()))
                .build();
    }
}
