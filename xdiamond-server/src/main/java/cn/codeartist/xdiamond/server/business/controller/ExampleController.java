package cn.codeartist.xdiamond.server.business.controller;

import cn.codeartist.xdiamond.server.business.service.netty.NettyServerService;
import cn.codeartist.xdiamond.server.entity.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 艾江南
 * @date 2019/4/13
 */
@RestController
@RequestMapping("example")
public class ExampleController {

    @Autowired
    private NettyServerService nettyServerService;

    @GetMapping
    public RestResponse test(String msg) {
        nettyServerService.sendMessage(msg);
        return RestResponse.ok();
    }
}
