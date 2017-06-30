package com.nnip.aws.dynamodb;

import com.amazonaws.regions.Regions;
import com.nnip.exceptions.DynamoDBManagerException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class DynamoDBManagerTest {

    @Mock
    DynamoDBManager dynamoDBManager;
    @Mock
    DynamoDBClient dynamoDBClient;
    @Mock
    TestTable testTable;

    private String proxyHost, proxyUserName, proxyPassword, accessKeyId, secretAccessKey, tablePrefix;
    private int proxyPort;
    private Regions region;
    private Long maxReadCapacity, maxWriteCapacity, burstReadCapacity, burstWriteCapacity,
    normalReadCapacity, normalWriteCapacity;

    @Before
    public void setup() {
        dynamoDBManager = new DynamoDBManager();
        testTable = new TestTable();

        this.proxyHost = "10.132.124.167";
        this.proxyUserName = "VK72EJ";
        this.proxyPort = 8080;
        this.proxyPassword = "";
        this.accessKeyId = "AKI7Q87";
        this.secretAccessKey = "qxor8TGOl/8";
        this.tablePrefix = "rita_";
        this.region = Regions.EU_WEST_1;
        this.maxReadCapacity = new Long(50);
        this.maxWriteCapacity = new Long(10);
        this.burstReadCapacity = new Long(50);
        this.burstWriteCapacity = new Long(100);
        this.normalReadCapacity = new Long(10);
        this.normalWriteCapacity = new Long(10);

        dynamoDBClient = new DynamoDBClient(this.proxyHost, this.proxyPort, this.proxyUserName,
                this.proxyPassword, this.accessKeyId, this.secretAccessKey, this.region,
                this.tablePrefix, this.maxReadCapacity, this.maxWriteCapacity, this.burstReadCapacity,
                this.burstWriteCapacity, this.normalReadCapacity, this.normalWriteCapacity);
    }

    @Ignore
    @Test
    public void testCreateDynamoDbTable() throws DynamoDBManagerException {
        dynamoDBManager.createDynamoDbTable(dynamoDBClient, TestTable.class);
    }

    @Ignore
    @Test(expected=DynamoDBManagerException.class)
    public void testCreationExistingTable() throws DynamoDBManagerException {
        dynamoDBManager.createDynamoDbTable(dynamoDBClient, TestTable.class);
    }

    @Ignore
    @Test(expected=DynamoDBManagerException.class)
    public void testInvalidDynamoDBClient() throws DynamoDBManagerException {
        dynamoDBClient.setProxyPort(8000);
        dynamoDBManager.createDynamoDbTable(dynamoDBClient, TestTable.class);
        dynamoDBClient.setProxyPort(8080);
    }

    @Ignore
    @Test
    public void testIncreaseReadAndWriteCapacity() throws DynamoDBManagerException {
        dynamoDBManager.increaseReadAndWriteCapacity(dynamoDBClient, TestTable.class);
    }

    @Ignore
    @Test
    public void testRestoreCapacity() throws DynamoDBManagerException {
        dynamoDBManager.restoreCapacity(dynamoDBClient, TestTable.class);
    }

    @Ignore
    @Test
    public void testSaveRecord() throws DynamoDBManagerException {
        testTable.setIsin("DE0387392");
        testTable.setLanguages("PT");
        dynamoDBManager.saveRecord(dynamoDBClient, testTable);
    }

    @Ignore
    @Test(expected=DynamoDBManagerException.class)
    public void testInvalidClientSaveRecord() throws DynamoDBManagerException {
        testTable.setIsin("HU9484833");
        testTable.setLanguages("EN");
        dynamoDBClient.setProxyPort(8000);
        dynamoDBManager.saveRecord(dynamoDBClient, testTable);
        dynamoDBClient.setProxyPort(8080);
    }

    @Ignore
    @Test
    public void testDeleteRecord() throws DynamoDBManagerException {
        testTable.setIsin("DE0387392");
        testTable.setLanguages("PT");
        dynamoDBManager.deleteRecord(dynamoDBClient, testTable);
    }

    @Ignore
    @Test
    public void testUpdateRecords() throws DynamoDBManagerException {
        TestTable testTable1 = new TestTable();
        testTable1.setIsin("DE0387392");
        testTable1.setLanguages("PT");

        testTable.setIsin("LU2837822");
        testTable.setLanguages("DE");

        List<TestTable> testTableList = new ArrayList();
        testTableList.add(testTable1);
        testTableList.add(testTable);

        dynamoDBManager.updateRecords(dynamoDBClient, testTableList);
    }

    @Ignore
    @Test
    public void testUpdateRecord() throws DynamoDBManagerException {
        testTable.setIsin("LU2837822");
        testTable.setLanguages("CH");

        dynamoDBManager.updateRecord(dynamoDBClient, testTable);
    }

    @Ignore
    @Test
    public void testUpdateNonExistingRecord() throws DynamoDBManagerException {
        testTable.setIsin("LU28378222112");
        testTable.setLanguages("CH");

        dynamoDBManager.updateRecord(dynamoDBClient, testTable);
    }

}
