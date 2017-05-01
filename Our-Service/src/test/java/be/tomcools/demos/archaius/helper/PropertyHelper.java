package be.tomcools.demos.archaius.helper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import static org.junit.Assert.fail;

/**
 * Created by tomco on 1/05/2017.
 */
public class PropertyHelper {
    public static void setConferenceProperty(String conferenceName) {
        try {
            FileInputStream in = new FileInputStream("target/test-classes/config.properties");
            Properties props = new Properties();
            props.load(in);
            in.close();

            FileOutputStream out = new FileOutputStream("target/test-classes/config.properties");
            props.setProperty("conference", conferenceName);
            props.store(out, null);
            out.close();
        } catch (java.io.IOException e) {
            fail("Something went wrong while changing the test properties:" + e.getMessage());
        }
    }

    public static Properties readConfiguration() {
        FileInputStream in = null;
        try {
            in = new FileInputStream("target/test-classes/config.properties");
            Properties props = new Properties();
            props.load(in);
            in.close();
            return props;
        } catch (java.io.IOException e) {
            fail();
        }
        throw new RuntimeException("Failure!");
    }
}
