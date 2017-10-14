package be.tomcools.demos.archaius;

import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import com.netflix.config.validation.PropertyChangeValidator;
import com.netflix.config.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;

import static be.tomcools.demos.archaius.helper.PropertyHelper.setConferenceProperty;
import static org.assertj.core.api.Java6Assertions.assertThat;

public class C_PropertyValidation {

    static {
        System.setProperty("archaius.fixedDelayPollingScheduler.initialDelayMills", "50"); //DEFAULT IS 30000
        System.setProperty("archaius.fixedDelayPollingScheduler.delayMills", "500"); //DEFAULT IS 60000
    }

    private DynamicStringProperty property;

    @Before
    public void init() {
        property = DynamicPropertyFactory.getInstance()
                .getStringProperty("conference", "Unknown");
        setConferenceProperty("javasi");
    }

    @Test
    public void validateProperty() throws InterruptedException {

        property.addValidator(new PropertyChangeValidator() {
            @Override
            public void validate(String s) throws ValidationException {
                System.out.println("Running validation for value: " + s);
            }
        });

        Thread.sleep(1000);
        setConferenceProperty("Devoxxx");
        Thread.sleep(1000);

        assertThat(property.getValue()).isEqualTo("Devoxxx");
    }
}
