package be.tomcools.demos.archaius;

import com.netflix.config.DynamicPropertyFactory;
import com.netflix.config.DynamicStringProperty;
import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class A_PropertyReading {
    @Test
    public void dynamicPropertiesUsesDefaultWhenPropertyNotPresent() {
        DynamicStringProperty property = DynamicPropertyFactory.getInstance()
                .getStringProperty("talk", "Unknown");

        String propertyValue = property.get();

        assertThat(propertyValue).isEqualTo("Unknown");
    }

    @Test
    public void dynamicPropertiesUsesDefinedSystemProperty() {
        System.setProperty("talk", "Anticipating the Fallacies!");

        DynamicStringProperty property = DynamicPropertyFactory.getInstance()
                .getStringProperty("talk", "Unknown");

        String propertyValue = property.get();

        assertThat(propertyValue).isEqualTo("Anticipating the Fallacies!");
    }

    @Test
    public void dynamicPropertiesUsesDefinedPropertyInConfigFile() throws InterruptedException {
        DynamicStringProperty property = DynamicPropertyFactory.getInstance()
                .getStringProperty("conference", "Unknown");

        String propertyValue = property.getValue();

        assertThat(propertyValue).isEqualTo("Devoxxx");
    }
}
