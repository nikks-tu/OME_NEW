package com.techuva.ome_new.post_parameters;

public class VersionCheckInfoPostParameters {
    public VersionCheckInfoPostParameters(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String appVersion;
}
