package com.nnip.aws.dynamodb;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import com.nnip.exceptions.DynamoDBManagerException;

import java.util.ArrayList;
import java.util.List;

public class DynamoDBManager {

    private AmazonDynamoDBClient amazonDynamoDBClient;
    private DynamoDBMapper mapper;

    /**
     * Creates a new table in DynamoDB.
     * @param dynamoDBClient
     * @param clazz DynamoDBTable class
     * @throws DynamoDBManagerException
     */
    public void createDynamoDbTable(DynamoDBClient dynamoDBClient, Class clazz) throws DynamoDBManagerException {

        try {
            verifyIfDynamoDBClientExists(dynamoDBClient);

            CreateTableRequest createTableRequest = getMapper(dynamoDBClient).generateCreateTableRequest(clazz);

            String tableName = createTableRequest.getTableName();

            verifyTableNameIsUnique(tableName);

            ProvisionedThroughput provisionedThroughput = setProvisionedThroughput(
                    dynamoDBClient.getNormalReadCapacity(), dynamoDBClient.getNormalWriteCapacity());

            createTableRequest.setProvisionedThroughput(provisionedThroughput);

            DynamoDB dynamoDB = new DynamoDB(amazonDynamoDBClient);
            Table table = dynamoDB.createTable(createTableRequest);
            waitForTable(table);
        }
        catch (Exception e) {
            throw new DynamoDBManagerException(e.getMessage(), e);
        }
    }

    /**
     * Increase read and write capacity of a specific table.
     * @param dynamoDBClient
     * @param clazz table class
     * @throws DynamoDBManagerException
     */
    public void increaseReadAndWriteCapacity(DynamoDBClient dynamoDBClient, Class clazz) throws DynamoDBManagerException {
        try {
            verifyIfDynamoDBClientExists(dynamoDBClient);

            DynamoDB dynamoDB = new DynamoDB(amazonDynamoDBClient);
            Table table = dynamoDB.getTable(determineTableName(dynamoDBClient, clazz));
            UpdateTableRequest tableRequest = buildUpdateTableRequest(table,
                    dynamoDBClient.getBurstReadCapacity(), dynamoDBClient.getBurstWriteCapacity());
            amazonDynamoDBClient.updateTable(tableRequest);
            waitForTable(table);
        } catch (Exception e) {
            throw new DynamoDBManagerException(e.getMessage(), e);
        }
    }

    /**
     * Restore normal read and write capacities
     * @param dynamoDBClient
     * @param clazz
     */
    public void restoreCapacity(DynamoDBClient dynamoDBClient, Class clazz) throws DynamoDBManagerException {
        try {
            verifyIfDynamoDBClientExists(dynamoDBClient);

            DynamoDB dynamoDB = new DynamoDB(amazonDynamoDBClient);
            Table table = dynamoDB.getTable(determineTableName(dynamoDBClient, clazz));
            UpdateTableRequest tableRequest = buildUpdateTableRequest(table,
                    dynamoDBClient.getNormalReadCapacity(), dynamoDBClient.getNormalWriteCapacity());
            amazonDynamoDBClient.updateTable(tableRequest);
            waitForTable(table);
        } catch (Exception e) {
            throw new DynamoDBManagerException(e.getMessage(), e);
        }
    }

    /**
     * Saves record into dynamoDB table.
     * @param dynamoDBClient
     * @param record
     */
    public void saveRecord(DynamoDBClient dynamoDBClient, Object record) throws DynamoDBManagerException {
        try {
            verifyIfDynamoDBClientExists(dynamoDBClient);
            getMapper(dynamoDBClient).save(record);
        }
        catch (Exception e) {
            throw new DynamoDBManagerException(e.getMessage(), e);
        }
    }

    /**
     * Deletes record from dynamoDB table.
     * @param dynamoDBClient
     * @param record
     * @throws DynamoDBManagerException
     */
    public void deleteRecord(DynamoDBClient dynamoDBClient, Object record) throws DynamoDBManagerException {
        try {
            verifyIfDynamoDBClientExists(dynamoDBClient);
            getMapper(dynamoDBClient).delete(record);
        }
        catch (Exception e) {
            throw new DynamoDBManagerException(e.getMessage(), e);
        }
    }

    /**
     * Update records
     * @param dynamoDBClient
     * @param records
     * @throws DynamoDBManagerException
     */
    public void updateRecords(DynamoDBClient dynamoDBClient, List<? extends Object> records) throws DynamoDBManagerException {
        try {
            verifyIfDynamoDBClientExists(dynamoDBClient);
            getMapper(dynamoDBClient).batchSave(records);
        }
        catch (Exception e) {
            throw new DynamoDBManagerException(e.getMessage(), e);
        }
    }

    /**
     * Update one record
     * @param dynamoDBClient
     * @param record
     * @throws DynamoDBManagerException
     */
    public void updateRecord(DynamoDBClient dynamoDBClient, Object record) throws DynamoDBManagerException {
        try {
            verifyIfDynamoDBClientExists(dynamoDBClient);
            getMapper(dynamoDBClient).save(record);
        }
        catch (Exception e) {
            throw new DynamoDBManagerException(e.getMessage(), e);
        }
    }

    private ProvisionedThroughput setProvisionedThroughput(Long readCapacity, Long writeCapacity) {
        ProvisionedThroughput provisionedThroughput = new ProvisionedThroughput();
        provisionedThroughput.setReadCapacityUnits(readCapacity);
        provisionedThroughput.setWriteCapacityUnits(writeCapacity);
        return provisionedThroughput;
    }

    private UpdateTableRequest buildUpdateTableRequest(Table table, Long readCapacity, Long writeCapacity) throws Exception {
        TableDescription description = table.describe();
        ProvisionedThroughput provisionedThroughput = setProvisionedThroughput(readCapacity, writeCapacity);
        UpdateTableRequest tableRequest = new UpdateTableRequest()
                .withTableName(table.getTableName())
                .withProvisionedThroughput(provisionedThroughput);

        if (description.getGlobalSecondaryIndexes() != null) {
            List<GlobalSecondaryIndexUpdate> secondaryIndexUpdates = new ArrayList<>();
            description.getGlobalSecondaryIndexes().forEach(index -> {
                String indexName = index.getIndexName();
                UpdateGlobalSecondaryIndexAction updateGlobalSecondaryIndexAction = new UpdateGlobalSecondaryIndexAction()
                        .withProvisionedThroughput(provisionedThroughput)
                        .withIndexName(indexName);

                secondaryIndexUpdates.add(new GlobalSecondaryIndexUpdate()
                        .withUpdate(updateGlobalSecondaryIndexAction));
            });
            tableRequest = tableRequest.withGlobalSecondaryIndexUpdates(secondaryIndexUpdates);
        }
        return tableRequest;
    }

    private String determineTableName(DynamoDBClient dynamoDBClient, Class clazz) {
        CreateTableRequest createTableRequest = getMapper(dynamoDBClient).generateCreateTableRequest(clazz);
        return createTableRequest.getTableName();
    }

    private void verifyTableNameIsUnique(String tableName) throws DynamoDBManagerException {
        ListTablesResult listTablesResult = null;
        try {
            listTablesResult = amazonDynamoDBClient.listTables();
        }
        catch (Exception e) {
            throw new DynamoDBManagerException(e);
        }

        if (listTablesResult.getTableNames().contains(tableName)) {
            throw new DynamoDBManagerException("Table name already exists.");
        }
    }

    private void buildDynamoDbClient(DynamoDBClient dynamoDBClient) {
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        clientConfiguration.setProxyHost(dynamoDBClient.getProxyHost());
        clientConfiguration.setProxyUsername(dynamoDBClient.getProxyUserName());
        clientConfiguration.setProxyPort(dynamoDBClient.getProxyPort());
        clientConfiguration.setProxyPassword(dynamoDBClient.getProxyPassword());

        BasicAWSCredentials awsCreds = new BasicAWSCredentials(dynamoDBClient.getAccessKeyId(),
                dynamoDBClient.getSecretAccessKey());

        amazonDynamoDBClient = new AmazonDynamoDBClient(awsCreds, clientConfiguration);
        amazonDynamoDBClient.setRegion(Region.getRegion(dynamoDBClient.getRegion()));
    }

    private void verifyIfDynamoDBClientExists(DynamoDBClient dynamoDBClient) {
        if (amazonDynamoDBClient == null) {
            buildDynamoDbClient(dynamoDBClient);
        }
    }

    private DynamoDBMapper getMapper(DynamoDBClient dynamoDBClient) {
        if (mapper == null) {
            DynamoDBMapperConfig mapperConfig = (new DynamoDBMapperConfig.Builder())
                    .withSaveBehavior(DynamoDBMapperConfig.SaveBehavior.CLOBBER)
                    .withTableNameOverride(new DynamoDBMapperConfig.TableNameOverride(null)
                            .withTableNamePrefix(dynamoDBClient.getTablePrefix())).build();
            mapper = new DynamoDBMapper(amazonDynamoDBClient, mapperConfig);
        }
        return mapper;
    }

    private void waitForTable(Table table) throws DynamoDBManagerException {
        try {
            table.waitForActive();
        } catch (InterruptedException e) {
            throw new DynamoDBManagerException(e.getMessage(), e);
        }
    }

}
