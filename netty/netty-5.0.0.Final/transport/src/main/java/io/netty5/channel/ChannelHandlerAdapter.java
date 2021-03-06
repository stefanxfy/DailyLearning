/*
 * Copyright 2013 The Netty Project
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

package io.netty5.channel;

import io.netty5.util.concurrent.FastThreadLocal;

import java.lang.reflect.AnnotatedType;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Skeleton implementation of a {@link ChannelHandler}.
 */
public abstract class ChannelHandlerAdapter implements ChannelHandler {

    private static final int HANDLER_SHARABLE_CACHE_INITIAL_CAPACITY = 4;
    private static final FastThreadLocal<Map<Class<?>, Boolean>> CACHE = new FastThreadLocal<>() {
        @Override
        protected Map<Class<?>, Boolean> initialValue() {
            // Start with small capacity to keep memory overhead as low as possible.
            return new WeakHashMap<>(HANDLER_SHARABLE_CACHE_INITIAL_CAPACITY);
        }
    };

    // Not using volatile because it's used only for a sanity check.
    boolean added;

    /**
     * Throws {@link IllegalStateException} if {@link ChannelHandlerAdapter#isSharable()} returns {@code true}
     */
    protected void ensureNotSharable() {
        if (isSharable()) {
            throw new IllegalStateException("ChannelHandler " + getClass().getName() + " is not allowed to be shared");
        }
    }

    /**
     * Return {@code true} if the implementation is {@link Sharable} and so can be added
     * to different {@link ChannelPipeline}s.
     */
    public boolean isSharable() {
        /**
         * Cache the result of {@link Sharable} annotation detection to workaround a condition. We use a
         * {@link ThreadLocal} and {@link WeakHashMap} to eliminate the volatile write/reads. Using different
         * {@link WeakHashMap} instances per {@link Thread} is good enough for us and the number of
         * {@link Thread}s are quite limited anyway.
         *
         * See <a href="https://github.com/netty/netty/issues/2289">#2289</a>.
         */
        Class<?> clazz = getClass();
        Map<Class<?>, Boolean> cache = CACHE.get();
        Boolean sharable = cache.get(clazz);
        if (sharable == null) {
            sharable = clazz.isAnnotationPresent(Sharable.class);
            if (!sharable) {
                AnnotatedType annotatedType = clazz.getAnnotatedSuperclass();
                sharable = annotatedType.isAnnotationPresent(Sharable.class);
            }
            cache.put(clazz, sharable);
        }
        return sharable;
    }
}
