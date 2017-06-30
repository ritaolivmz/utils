package com.nnip.aws.cloudwatch;

import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEvents;
import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEventsClientBuilder;
import com.nnip.exceptions.CloudWatchManagerException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CloudWatchManagerTest {

    @Mock
    CloudWatchManager cloudWatchManager;

    private AmazonCloudWatchEvents cwe;
    private String ruleName, chronExpression;

    @Before
    public void setup() {
        cwe = AmazonCloudWatchEventsClientBuilder.defaultClient();
        ruleName = "hello-lambda-rule";
        chronExpression = "0/33 * * * ? *";
        cloudWatchManager = new CloudWatchManager();
    }

    @Ignore
    @Test
    public void testUpdateChronExpression() throws CloudWatchManagerException {
        cloudWatchManager.updateChronExpression(ruleName, chronExpression);
    }

}
