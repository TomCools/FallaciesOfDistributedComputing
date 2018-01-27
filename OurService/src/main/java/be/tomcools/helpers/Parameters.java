package be.tomcools.helpers;

public class Parameters {
    private String delay;
    private String failChance;

    public Parameters() {
    }

    public String getDelay() {
        if (delay == null) {
            return "0";
        }
        return delay;
    }

    public String getFailChance() {
        if (failChance == null) {
            return "0";
        }
        return failChance;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public void setFailChance(String failChance) {
        this.failChance = failChance;
    }

    public String toPathParamString() {
        return "?delay=" + getDelay() + "&failChance=" + getFailChance();
    }
}
