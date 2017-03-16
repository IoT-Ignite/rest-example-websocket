package com.ardic.training.model;

public class SubscribeMessage {
    private String deviceId;
    private String nodeId;
    private String sensorId;
    private String subscribeId;
    private String version;
    private String token;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getSubscribeId() {
        return subscribeId;
    }

    public void setSubscribeId(String subscribeId) {
        this.subscribeId = subscribeId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "SubscribeMessage{" +
                "deviceId='" + deviceId + '\'' +
                ", nodeId='" + nodeId + '\'' +
                ", sensorId='" + sensorId + '\'' +
                ", subscribeId='" + subscribeId + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
