package AWS;

import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import java.util.ArrayList;
import java.util.List;

public class AwsS3ListFiles {

    public static ArrayList<String> listAllObjectsIn(String bucketName) {
        ArrayList<String> objectsInBucket = new ArrayList<>();
        ListObjectsV2Result result = AwsConnet.s3Connection().listObjectsV2(bucketName);
        List<S3ObjectSummary> objects = result.getObjectSummaries();
        for (S3ObjectSummary objectSummary : objects) {
            objectsInBucket.add(objectSummary.getKey());
            System.out.println("*******>>" + objectSummary.getKey());
        }
        return objectsInBucket;
    }

    public static String lastRegisterInBucket(String bucketName) {
        int positionLastElement = listAllObjectsIn(bucketName).size() - 1;
        return listAllObjectsIn(bucketName).get(positionLastElement);
    }
}
