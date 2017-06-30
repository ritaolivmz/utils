package com.nnip.aws.s3;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.nnip.exceptions.S3ManagerException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class S3Manager {

    private AmazonS3 awsS3Client;

    private void buildS3Client(S3Client s3Client) throws S3ManagerException {
        try {
            ClientConfiguration clientConfiguration = new ClientConfiguration();
            clientConfiguration.setProxyHost(s3Client.getProxyHost());
            clientConfiguration.setProxyUsername(s3Client.getProxyUserName());
            clientConfiguration.setProxyPort(s3Client.getProxyPort());
            clientConfiguration.setProxyPassword(s3Client.getProxyPassword());
            awsS3Client = new AmazonS3Client(new BasicAWSCredentials(s3Client.getAccessKey(), s3Client.getSecretKey()),clientConfiguration);
        }
        catch(Exception e) {
            throw new S3ManagerException(e.getMessage(), e);
        }
    }


    /**
     * Push file into an S3 bucket.
     * @param s3Client which expects to have set: proxyHost, proxyUserName, proxyPort and proxyPassword
     * @param bucketName destination bucket name
     * @param resource file to be pushed
     * @throws S3ManagerException
     */
    public void pushFile(S3Client s3Client, String bucketName, File resource) throws S3ManagerException {
        verifyIfS3ClientExists(s3Client);

        pushFile(bucketName, resource, resource.getName());
    }

    /**
     * Push file into a direction in an S3 bucket.
     * @param s3Client which expects to have set: proxyHost, proxyUserName, proxyPort and proxyPassword
     * @param bucketName destination bucket name
     * @param resource file to be pushed
     * @param directory directory inside bucket
     * @param apiName
     * @throws S3ManagerException
     */
    public void pushFileWithDirectory(S3Client s3Client, String bucketName, File resource,
                                         String directory, String apiName) throws S3ManagerException {
        verifyIfS3ClientExists(s3Client);

        pushFile(bucketName, resource, directory + apiName);
    }

    private void pushFile(String bucketName, File resource, String key) throws S3ManagerException {
        try {
            FileInputStream inputStream = new FileInputStream(resource);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setSSEAlgorithm("AES256");
            metadata.setContentLength(resource.length());
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, metadata);
            awsS3Client.putObject(putObjectRequest);
        }
        catch(Exception e) {
            throw new S3ManagerException(e.getMessage(), e);
        }
    }

    private void verifyIfS3ClientExists(S3Client s3Client) throws S3ManagerException {
        if (awsS3Client == null) {
            buildS3Client(s3Client);
        }
    }
}
