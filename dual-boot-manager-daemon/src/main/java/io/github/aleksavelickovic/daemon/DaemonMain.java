package io.github.aleksavelickovic.daemon;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermissions;


public class DaemonMain {

    static void main(String[] args) throws IOException {
        System.out.println("DBM Daemon started!");

        Path socketPath = Path.of("/run/dualbootd.sock");

        Files.deleteIfExists(socketPath);

        UnixDomainSocketAddress adress = UnixDomainSocketAddress.of(socketPath);

        ServerSocketChannel server = ServerSocketChannel.open(StandardProtocolFamily.UNIX);


        server.bind(adress);

        Files.setPosixFilePermissions(
                socketPath,
                PosixFilePermissions.fromString("rw-rw----")
        );

        while (true) {

            SocketChannel client = server.accept();

            handleClient(client);

        }
    }

    private static void handleClient(SocketChannel client) throws IOException {
        BufferedReader reader =
                new BufferedReader(
                        Channels.newReader(client, StandardCharsets.UTF_8));

        String request = reader.readLine();
        System.out.println("Pristigao request: " + request);
    }
}
