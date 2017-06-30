package com.nnip.aws.s3;

public class S3Client {

    private String proxyHost;
    private String proxyUserName;
    private int proxyPort;
    private String proxyPassword;
    private String accessKey;
    private String secretKey;

    public S3Client(String proxyHost, String proxyUserName, int proxyPort, String proxyPassword,
                    String accessKey, String secretKey) {
        this.proxyHost = proxyHost;
        this.proxyUserName = proxyUserName;
        this.proxyPort = proxyPort;
        this.proxyPassword = proxyPassword;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public String getProxyUserName() {
        return proxyUserName;
    }

    public void setProxyUserName(String proxyUserName) {
        this.proxyUserName = proxyUserName;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getProxyPassword() {
        return proxyPassword;
    }

    public void setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
