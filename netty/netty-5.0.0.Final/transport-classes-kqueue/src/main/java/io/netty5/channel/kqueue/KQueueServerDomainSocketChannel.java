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
package io.netty5.channel.kqueue;

import io.netty5.channel.Channel;
import io.netty5.channel.EventLoop;
import io.netty5.channel.EventLoopGroup;
import io.netty5.channel.unix.DomainSocketAddress;
import io.netty5.channel.unix.ServerDomainSocketChannel;
import io.netty5.util.internal.UnstableApi;
import io.netty5.util.internal.logging.InternalLogger;
import io.netty5.util.internal.logging.InternalLoggerFactory;

import java.io.File;
import java.net.SocketAddress;

import static io.netty5.channel.kqueue.BsdSocket.newSocketDomain;

@UnstableApi
public final class KQueueServerDomainSocketChannel extends AbstractKQueueServerChannel
                                                  implements ServerDomainSocketChannel {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(
            KQueueServerDomainSocketChannel.class);

    private final KQueueServerChannelConfig config = new KQueueServerChannelConfig(this);
    private volatile DomainSocketAddress local;

    public KQueueServerDomainSocketChannel(EventLoop eventLoop, EventLoopGroup childEventLoopGroup) {
        super(eventLoop, childEventLoopGroup, newSocketDomain(), false);
    }

    public KQueueServerDomainSocketChannel(EventLoop eventLoop, EventLoopGroup childEventLoopGroup, int fd) {
        this(eventLoop, childEventLoopGroup, new BsdSocket(fd), false);
    }

    KQueueServerDomainSocketChannel(EventLoop eventLoop, EventLoopGroup childEventLoopGroup,
                                    BsdSocket socket, boolean active) {
        super(eventLoop, childEventLoopGroup, socket, active);
    }

    @Override
    protected Channel newChildChannel(int fd, byte[] addr, int offset, int len) throws Exception {
        return new KQueueDomainSocketChannel(this, childEventLoopGroup().next(), new BsdSocket(fd));
    }

    @Override
    protected DomainSocketAddress localAddress0() {
        return local;
    }

    @Override
    protected void doBind(SocketAddress localAddress) throws Exception {
        socket.bind(localAddress);
        socket.listen(config.getBacklog());
        local = (DomainSocketAddress) localAddress;
        active = true;
    }

    @Override
    protected void doClose() throws Exception {
        try {
            super.doClose();
        } finally {
            DomainSocketAddress local = this.local;
            if (local != null) {
                // Delete the socket file if possible.
                File socketFile = new File(local.path());
                boolean success = socketFile.delete();
                if (!success && logger.isDebugEnabled()) {
                    logger.debug("Failed to delete a domain socket file: {}", local.path());
                }
            }
        }
    }

    @Override
    public KQueueServerChannelConfig config() {
        return config;
    }

    @Override
    public DomainSocketAddress remoteAddress() {
        return (DomainSocketAddress) super.remoteAddress();
    }

    @Override
    public DomainSocketAddress localAddress() {
        return (DomainSocketAddress) super.localAddress();
    }
}
