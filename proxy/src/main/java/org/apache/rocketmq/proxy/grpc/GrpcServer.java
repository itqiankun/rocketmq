

package org.apache.rocketmq.proxy.grpc;

import java.util.concurrent.TimeUnit;
import org.apache.rocketmq.common.constant.LoggerName;
import org.apache.rocketmq.logging.InternalLogger;
import org.apache.rocketmq.logging.InternalLoggerFactory;
import org.apache.rocketmq.proxy.common.StartAndShutdown;

public class GrpcServer implements StartAndShutdown {
    private static final InternalLogger log = InternalLoggerFactory.getLogger(LoggerName.PROXY_LOGGER_NAME);

    private final io.grpc.Server server;

    protected GrpcServer(io.grpc.Server server) {
        this.server = server;
    }

    public void start() throws Exception {
        this.server.start();
        log.info("grpc server start successfully.");
    }

    public void shutdown() {
        try {
            this.server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
            log.info("grpc server shutdown successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}