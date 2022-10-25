package AWS;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AwsS3ReadFiles {

    public static String readFileInS3(String bucketName) throws IOException {
        String fileNameLastRegister = AwsS3ListFiles.lastRegisterInBucket(bucketName);
        S3Object s3Object = AwsConnet.s3Connection().getObject(bucketName, fileNameLastRegister);
        S3ObjectInputStream s3is = s3Object.getObjectContent();
        StringBuilder contentObject = new StringBuilder();
        String line;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(s3is));
        while ((line = bufferedReader.readLine()) != null) {
            contentObject.append(line);
        }
        s3is.close();
        return contentObject.toString();
    }
}


