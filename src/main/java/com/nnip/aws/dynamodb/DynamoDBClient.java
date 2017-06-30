package com.nnip.aws.dynamodb;

import com.amazonaws.regions.Regions;

public class DynamoDBClient {

    private String proxyHost;
    private int proxyPort;
    private String proxyUserName;
    private String proxyPassword;
    private String accessKeyId;
    private String secretAccessKey;
    private Regions region;
    private String tablePrefix;
    private Long maxReadCapacity;
    private Long maxWriteCapacity;
    private Long burstReadCapacity;
    private Long burstWriteCapacity;
    private Long normalReadCapacity;
    private Long normalWriteCapacity;

    public DynamoDBClient(String proxyHost, int proxyPort, String proxyUserName, String proxyPassword,
                          String accessKeyId, String secretAccessKey, Regions region,
                          String tablePrefix, Long maxReadCapacity, Long maxWriteCapacity,
                          Long burstReadCapacity, Long burstWriteCapacity, Long normalReadCapacity,
                          Long normalWriteCapacity) {
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
        this.proxyUserName = proxyUserName;
        this.proxyPassword = proxyPassword;
        this.accessKeyId = accessKeyId;
        this.secretAccessKey = secretAccessKey;
        this.region = region;
        this.tablePrefix = tablePrefix;
        this.maxReadCapacity = maxReadCapacity;
        this.maxWriteCapacity = maxWriteCapacity;
        this.burstReadCapacity = burstReadCapacity;
        this.burstWriteCapacity = burstWriteCapacity;
        this.normalReadCapacity = normalReadCapacity;
        this.normalWriteCapacity = normalWriteCapacity;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getProxyUserName() {
        return proxyUserName;
    }

    public void setProxyUserName(String proxyUserName) {
        this.proxyUserName = proxyUserName;
    }

    public String getProxyPassword() {
        return proxyPassword;
    }

    public void setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getSecretAccessKey() {
        return secretAccessKey;
    }

    public void setSecretAccessKey(String secretAccessKey) {
        this.secretAccessKey = secretAccessKey;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public Long getMaxReadCapacity() {
        return maxReadCapacity;
    }

    public void setMaxReadCapacity(Long maxReadCapacity) {
        this.maxReadCapacity = maxReadCapacity;
    }

    public Long getMaxWriteCapacity() {
        return maxWriteCapacity;
    }

    public void setMaxWriteCapacity(Long maxWriteCapacity) {
        this.maxWriteCapacity = maxWriteCapacity;
    }

    public Long getBurstReadCapacity() {
        return burstReadCapacity;
    }

    public void setBurstReadCapacity(Long burstReadCapacity) {
        this.burstReadCapacity = burstReadCapacity;
    }

    public Long getBurstWriteCapacity() {
        return burstWriteCapacity;
    }

    public void setBurstWriteCapacity(Long burstWriteCapacity) {
        this.burstWriteCapacity = burstWriteCapacity;
    }

    public Long getNormalReadCapacity() {
        return normalReadCapacity;
    }

    public void setNormalReadCapacity(Long normalReadCapacity) {
        this.normalReadCapacity = normalReadCapacity;
    }

    public Long getNormalWriteCapacity() {
        return normalWriteCapacity;
    }

    public void setNormalWriteCapacity(Long normalWriteCapacity) {
        this.normalWriteCapacity = normalWriteCapacity;
    }

    public Regions getRegion() {
        return region;
    }

    public void setRegion(Regions region) {
        this.region = region;
    }
}
