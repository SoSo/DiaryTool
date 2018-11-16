package me.soso.config.database;

import com.avos.avoscloud.AVOSCloud;

public class LeanCloud implements Database {

    private String appID;

    private String appKey;

    private String masterKey;

    public LeanCloud(String appID, String appKey, String masterKey) {
        this.appID = appID;
        this.appKey = appKey;
        this.masterKey = masterKey;
    }

    @Override
    public boolean connect() {
        AVOSCloud.initialize(appID,appKey,masterKey);
        return true;
    }
}
