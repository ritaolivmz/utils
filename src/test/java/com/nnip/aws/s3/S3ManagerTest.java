package com.nnip.aws.s3;

import com.nnip.exceptions.S3ManagerException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.net.URL;

@RunWith(MockitoJUnitRunner.class)
public class S3ManagerTest {

    @Mock
    S3Manager s3Manager;
    @Mock
    S3Client s3Client;

    private String proxyHost;
    private String proxyUserName;
    private int proxyPort;
    private String proxyPassword;
    private String accessKey;
    private String secretKey;
    private String bucketName;

    @Before
    public void setup() {
        s3Manager = new S3Manager();

        this.proxyHost = "10.132.124.167";
        this.proxyUserName = "VK72EJ";
        this.proxyPort = 8080;
        this.proxyPassword = "";
        this.accessKey = "AKIAJBGHCE5ASQ";
        this.secretKey = "qxorTGOGCvl/8";
        this.bucketName = "rita.hello-lambda";

        s3Client = new S3Client(proxyHost, proxyUserName, proxyPort, proxyPassword, accessKey, secretKey);
    }

    @Ignore
    @Test
    public void testPushResource() throws S3ManagerException {
        s3Manager.pushFile(s3Client, bucketName, retrieveFileToPush());
    }

    private File retrieveFileToPush() {
        URL url = Thread.currentThread().getContextClassLoader().getResource("S3File.txt");
        return new File(url.getPath());
    }

    @Test(expected=S3ManagerException.class)
    public void testInvalidS3Client() throws S3ManagerException {
        s3Manager.pushFile(s3Client, bucketName, retrieveFileToPush());
    }

}
