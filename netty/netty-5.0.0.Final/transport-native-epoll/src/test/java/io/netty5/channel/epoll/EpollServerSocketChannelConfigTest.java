/*
 * Copyright 2016 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty5.channel.epoll;

import io.netty5.bootstrap.ServerBootstrap;
import io.netty5.channel.ChannelHandler;
import io.netty5.channel.ChannelOption;
import io.netty5.channel.EventLoopGroup;
import io.netty5.channel.MultithreadEventLoopGroup;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EpollServerSocketChannelConfigTest {

    private static EventLoopGroup group;
    private static EpollServerSocketChannel ch;

    @BeforeAll
    public static void before() throws Exception {
        group = new MultithreadEventLoopGroup(1, EpollHandler.newFactory());
        ServerBootstrap bootstrap = new ServerBootstrap();
        ch = (EpollServerSocketChannel) bootstrap.group(group)
                .channel(EpollServerSocketChannel.class)
                .childHandler(new ChannelHandler() { })
                .bind(new InetSocketAddress(0)).get();
    }

    @AfterAll
    public static void after() {
        try {
            ch.close().syncUninterruptibly();
        } finally {
            group.shutdownGracefully();
        }
    }

    @Test
    public void testTcpDeferAccept() {
        ch.config().setTcpDeferAccept(0);
        assertEquals(0, ch.config().getTcpDeferAccept());
        ch.config().setTcpDeferAccept(10);
        // The returned value may be bigger then what we set.
        // See https://www.spinics.net/lists/netdev/msg117330.html
        assertTrue(10 <= ch.config().getTcpDeferAccept());
    }

    @Test
    public void testReusePort() {
        ch.config().setReusePort(false);
        assertFalse(ch.config().isReusePort());
        ch.config().setReusePort(true);
        assertTrue(ch.config().isReusePort());
    }

    @Test
    public void testFreeBind() {
        ch.config().setFreeBind(false);
        assertFalse(ch.config().isFreeBind());
        ch.config().setFreeBind(true);
        assertTrue(ch.config().isFreeBind());
    }

    @Test
    public void getGetOptions() {
        Map<ChannelOption<?>, Object> map = ch.config().getOptions();
        assertFalse(map.isEmpty());
    }
}
