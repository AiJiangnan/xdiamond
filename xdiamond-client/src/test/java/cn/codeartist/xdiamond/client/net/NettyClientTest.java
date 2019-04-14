package cn.codeartist.xdiamond.client.net;

import org.junit.Test;

public class NettyClientTest {

    @Test
    public void connect() {
        try {
            new NettyClient().connect(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}