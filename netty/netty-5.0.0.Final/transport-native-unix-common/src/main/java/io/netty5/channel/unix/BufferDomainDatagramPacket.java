/*
 * Copyright 2022 The Netty Project
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
package io.netty5.channel.unix;

import io.netty5.buffer.api.Buffer;
import io.netty5.channel.BufferAddressedEnvelope;

/**
 * The message container that is used for {@link DomainDatagramChannel} to communicate with the remote peer.
 */
public class BufferDomainDatagramPacket
        extends BufferAddressedEnvelope<DomainSocketAddress, BufferDomainDatagramPacket> {
    /**
     * Create a new instance with the specified packet {@code data}, {@code recipient} address, and {@code sender}
     * address.
     */
    public BufferDomainDatagramPacket(Buffer message, DomainSocketAddress recipient, DomainSocketAddress sender) {
        super(message, recipient, sender);
    }

    /**
     * Create a new instance with the specified packet {@code data} and {@code recipient} address.
     */
    public BufferDomainDatagramPacket(Buffer message, DomainSocketAddress recipient) {
        super(message, recipient);
    }

    @Override
    public BufferDomainDatagramPacket replace(Buffer content) {
        return new BufferDomainDatagramPacket(content, recipient(), sender());
    }

    @Override
    public BufferDomainDatagramPacket touch(Object hint) {
        super.touch(hint);
        return this;
    }
}
