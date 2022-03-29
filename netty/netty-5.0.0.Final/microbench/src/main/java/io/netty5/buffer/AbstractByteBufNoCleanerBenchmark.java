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
package io.netty5.buffer;

import io.netty5.microbench.util.AbstractMicrobenchmark;
import org.openjdk.jmh.annotations.Param;

public abstract class AbstractByteBufNoCleanerBenchmark extends AbstractMicrobenchmark {

    public enum ByteBufType {
        UNPOOLED_NO_CLEANER {
            @Override
            ByteBuf newBuffer(int initialCapacity) {
                return new UnpooledUnsafeNoCleanerDirectByteBuf(
                        UnpooledByteBufAllocator.DEFAULT, initialCapacity, Integer.MAX_VALUE);
            }
        },
        UNPOOLED {
            @Override
            ByteBuf newBuffer(int initialCapacity) {
                return new UnpooledUnsafeDirectByteBuf(
                        UnpooledByteBufAllocator.DEFAULT, initialCapacity, Integer.MAX_VALUE);
            }
        };
        abstract ByteBuf newBuffer(int initialCapacity);
    }

    @Param
    public ByteBufType bufferType;
}
