package cn.codeartist.xdiamond.client.spring;

import lombok.Data;

/**
 * @author 艾江南
 * @date 2019/4/15
 */
@Data
public class DiamondProperties {

    private String host = "127.0.0.1";
    private Integer port = 5678;
    private Integer retryIntervalSeconds = 5;
    private Integer maxRetryTimes = 100;

    private String groupId;
    private String artifactId;
    private String version;
    private String profile;

    public String getProjectInfo() {
        return groupId + "|" + artifactId + "|" + version + "|" + profile;
    }
}
