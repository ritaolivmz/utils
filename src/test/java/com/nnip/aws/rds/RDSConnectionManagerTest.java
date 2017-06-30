package com.nnip.aws.rds;
import com.nnip.exceptions.RDSConnectionManagerException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RDSConnectionManagerTest {

    @Mock
    RDSConnectionManager rdsConnectionManager;

    private String dbNameAurora;
    private String userNameAurora;
    private String passwordAurora;
    private String hostnameAurora;
    private String portAurora;

    @Before
    public void setup() {
        rdsConnectionManager = new RDSConnectionManager();
        userNameAurora = "master";
    }

    @Ignore
    @Test
    public void testGetRemoteConnectionMariaDB() throws RDSConnectionManagerException {
        setMariaDBProperties();
        rdsConnectionManager.getRemoteConnection(dbNameAurora, userNameAurora, passwordAurora, hostnameAurora, portAurora);
    }

    @Ignore
    @Test
    public void testGetRemoteConnectionAuroraDB() throws RDSConnectionManagerException {
        setAuroraDBProperties();
        rdsConnectionManager.getRemoteConnection(dbNameAurora, userNameAurora, passwordAurora, hostnameAurora, portAurora);
    }

    private void setAuroraDBProperties() {
        dbNameAurora = "testaurora";
        passwordAurora = "masteraurora";
        hostnameAurora = "testauroradb.cluster-crfcz1hswmj0.eu-west-1.rds.amazonaws.com";
        portAurora = "8050";
    }

    private void setMariaDBProperties() {
        dbNameAurora = "testmaria";
        passwordAurora = "mastermaria";
        hostnameAurora = "testmariadb.crfcz1hswmj0.eu-west-1.rds.amazonaws.com";
        portAurora = "8060";
    }

}
