package be.tomcools.demos.archaius;

import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static be.tomcools.demos.archaius.helper.PropertyHelper.setConferenceProperty;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class B_DynamicProperties {

    static {
        System.setProperty("archaius.fixedDelayPollingScheduler.initialDelayMills", "50"); //DEFAULT IS 30000
        System.setProperty("archaius.fixedDelayPollingScheduler.delayMills", "500"); //DEFAULT IS 60000
    }

    private DynamicStringProperty property;

    @Before
    @After
    public void setup() {
        property = DynamicPropertyFactory.getInstance()
                .getStringProperty("conference", "Unknown");
        setConferenceProperty("javasi");
    }

    @Test
    public void dynamicPropertiesUseLatestVerionOfProperty() throws InterruptedException {
        Thread.sleep(2000);
        setConferenceProperty("JavaSi2017");
        Thread.sleep(2000);

        assertThat(property.getValue()).isEqualTo("JavaSi2017");
    }

    @Test
    public void listenerForDynamicProperties() throws InterruptedException {
        property.addCallback(() -> {
            System.out.println("Doing assertion");
        });

        //RELOADING
        Thread.sleep(2000);
        setConferenceProperty("JavaSi2017");
        Thread.sleep(2000);
    }


}
