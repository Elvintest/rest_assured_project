
package ru.atc.fgislk.shared.testcomponents.back.health;

public class Components {

    private DiskSpace diskSpace;
    private LivenessState livenessState;
    private Ping ping;
    private ReadinessState readinessState;

    public DiskSpace getDiskSpace() {
        return diskSpace;
    }

    public LivenessState getLivenessState() {
        return livenessState;
    }

    public Ping getPing() {
        return ping;
    }

    public ReadinessState getReadinessState() {
        return readinessState;
    }
}
