package io.github.aleksavelickovic.service.impl;

import io.github.aleksavelickovic.service.ProcessService;
import lombok.NonNull;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.UnixDomainSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class ProcessServiceDaemonImpl implements ProcessService {
    @Override
    public Process execute(@NonNull Integer processName, Boolean root, String... processArgs) throws IOException {
        UnixDomainSocketAddress address = UnixDomainSocketAddress.of("/run/dualbootd.sock");

        SocketChannel socket = SocketChannel.open(address);

        String json = "{" +
                "\"action:\" \"reboot\"" +
                "\"entry:\" \"Windows Boot Manager\"" +
                "}";

        BufferedWriter writer =
                new BufferedWriter(
                        Channels.newWriter(socket, StandardCharsets.UTF_8));

        writer.write(json);

        writer.newLine();

        writer.flush();
        return null;
    }

    @Override
    public Process execute(@NonNull Integer processName, Boolean root) throws IOException {
        return null;
    }

    private ProcessServiceDaemonImpl() {

    }

    private static class ProcessServiceDaemonImplSingletonHelper {
        private static final ProcessServiceDaemonImpl PROCESS_SERVICE_INSTANCE = new ProcessServiceDaemonImpl();
    }

    public static ProcessServiceDaemonImpl getInstance() {
        return ProcessServiceDaemonImplSingletonHelper.PROCESS_SERVICE_INSTANCE;
    }
}
