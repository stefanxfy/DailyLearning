/*
 * Copyright 2019 The Netty Project
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
package io.netty5.resolver.dns;

import io.netty5.channel.AddressedEnvelope;
import io.netty5.channel.Channel;
import io.netty5.handler.codec.dns.DefaultDnsQuery;
import io.netty5.handler.codec.dns.DnsQuery;
import io.netty5.handler.codec.dns.DnsQuestion;
import io.netty5.handler.codec.dns.DnsRecord;
import io.netty5.handler.codec.dns.DnsResponse;
import io.netty5.util.concurrent.Promise;

import java.net.InetSocketAddress;

final class TcpDnsQueryContext extends DnsQueryContext {

    private final Channel channel;

    TcpDnsQueryContext(DnsNameResolver parent, Channel channel, InetSocketAddress nameServerAddr, DnsQuestion question,
                       DnsRecord[] additionals, Promise<AddressedEnvelope<DnsResponse, InetSocketAddress>> promise) {
        super(parent, nameServerAddr, question, additionals, promise);
        this.channel = channel;
    }

    @Override
    protected DnsQuery newQuery(int id) {
        return new DefaultDnsQuery(id);
    }

    @Override
    protected Channel channel() {
        return channel;
    }

    @Override
    protected String protocol() {
        return "TCP";
    }
}
