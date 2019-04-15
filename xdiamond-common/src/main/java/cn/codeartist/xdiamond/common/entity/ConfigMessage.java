package cn.codeartist.xdiamond.common.entity;

import lombok.Data;

import java.util.List;

/**
 * @author 艾江南
 * @date 2019/4/15
 */
@Data
public class ConfigMessage {
    private Project project;
    private String profile;
    private List<Config> configs;
}
