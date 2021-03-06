/*
 * Copyright 2012 The Netty Project
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
package io.netty5.util.internal.logging;


import org.apache.commons.logging.LogFactory;

/**
 * Logger factory which creates an
 * <a href="https://commons.apache.org/logging/">Apache Commons Logging</a>
 * logger.
 *
 * @deprecated Please use {@link Log4J2LoggerFactory} or {@link Log4JLoggerFactory} or
 * {@link Slf4JLoggerFactory}.
 */
@Deprecated
public class CommonsLoggerFactory extends InternalLoggerFactory {

    public static final InternalLoggerFactory INSTANCE = new CommonsLoggerFactory();

    /**
     * @deprecated Use {@link #INSTANCE} instead.
     */
    @Deprecated
    public CommonsLoggerFactory() {
    }

    @Override
    public InternalLogger newInstance(String name) {
        return new CommonsLogger(LogFactory.getLog(name), name);
    }
}
