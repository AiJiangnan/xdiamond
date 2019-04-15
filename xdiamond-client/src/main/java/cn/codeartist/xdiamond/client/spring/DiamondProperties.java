package cn.codeartist.xdiamond.client.spring;

import lombok.Data;

/**
 * 客户端配置
 *
 * @author 艾江南
 * @date 2019/4/15
 */
@Data
public class DiamondProperties {

    /**
     * 服务器地址
     */
    private String host = "127.0.0.1";
    /**
     * 服务器端口
     */
    private Integer port = 5678;
    /**
     * 重连时间间隔
     */
    private Integer retryIntervalSeconds = 5;
    /**
     * 最大重连次数
     */
    private Integer maxRetryTimes = Integer.MAX_VALUE;
    /**
     * 项目：groupId
     */
    private String groupId;
    /**
     * 项目：artifactId
     */
    private String artifactId;
    /**
     * 项目：version
     */
    private String version;
    /**
     * 项目：profile
     */
    private String profile;

    public String getProjectInfo() {
        return groupId + "|" + artifactId + "|" + version + "|" + profile;
    }
}
