/*
 * Copyright 2017 The Netty Project
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

import io.netty5.handler.codec.dns.DnsQuestion;

import static java.util.Objects.requireNonNull;

/**
 * Combines two {@link DnsQueryLifecycleObserverFactory} into a single {@link DnsQueryLifecycleObserverFactory}.
 */
public final class BiDnsQueryLifecycleObserverFactory implements DnsQueryLifecycleObserverFactory {
    private final DnsQueryLifecycleObserverFactory a;
    private final DnsQueryLifecycleObserverFactory b;

    /**
     * Create a new instance.
     * @param a The {@link DnsQueryLifecycleObserverFactory} that will receive events first.
     * @param b The {@link DnsQueryLifecycleObserverFactory} that will receive events second.
     */
    public BiDnsQueryLifecycleObserverFactory(DnsQueryLifecycleObserverFactory a, DnsQueryLifecycleObserverFactory b) {
        this.a = requireNonNull(a, "a");
        this.b = requireNonNull(b, "b");
    }

    @Override
    public DnsQueryLifecycleObserver newDnsQueryLifecycleObserver(DnsQuestion question) {
        return new BiDnsQueryLifecycleObserver(a.newDnsQueryLifecycleObserver(question),
                                               b.newDnsQueryLifecycleObserver(question));
    }
}
