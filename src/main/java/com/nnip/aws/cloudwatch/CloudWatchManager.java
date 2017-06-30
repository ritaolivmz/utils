package com.nnip.aws.cloudwatch;

import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEvents;
import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEventsClientBuilder;
import com.amazonaws.services.cloudwatchevents.model.*;
import com.nnip.exceptions.CloudWatchManagerException;

public class CloudWatchManager {

    private AmazonCloudWatchEvents cwe;

    private void accessCloudWatch() {
        if (cwe == null) {
            cwe = AmazonCloudWatchEventsClientBuilder.defaultClient();
        }
    }

    /**
     * Method that updates a rules chron expression.
     * Rule will be set to ENABLED status after this update.
     * @param ruleName which contains the chron expression
     * @param chronExpression new chron expression
     * @throws CloudWatchManagerException
     */
    public void updateChronExpression(String ruleName, String chronExpression) throws CloudWatchManagerException{
        try {
            accessCloudWatch();
            PutRuleRequest putRuleRequest = new PutRuleRequest().withName(ruleName);
            putRuleRequest.setScheduleExpression("cron(" + chronExpression + ")");
            cwe.putRule(putRuleRequest);
        } catch (Exception e) {
            throw new CloudWatchManagerException(e.getMessage(), e);
        }
    }
}
