package io.github.aleksavelickovic.service.impl;

import io.github.aleksavelickovic.service.ProcessService;
import lombok.NonNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;

public class ProcessServiceImpl implements ProcessService {
    public static final String ROOT_COMMAND = "pkexec";

    @Override
    public Process execute(@NonNull Integer processName, @NonNull Boolean root, String... processArgs) throws IOException {
        return executeHelper(processName, root, processArgs);
    }

    @Override
    public Process execute(@NonNull Integer processName, @NonNull Boolean root) throws IOException {
        return executeHelper(processName, root, null);
    }

    private Process executeHelper(@NonNull Integer processName, @NonNull Boolean root, String... processArgs) throws IOException {
        ProcessBuilder pb = null;
        String processString = processes.get(processName);
        if (root) {
            processString = ROOT_COMMAND + " " + processString;
        }

        if (processArgs == null || processArgs.length == 0) {
            pb = new ProcessBuilder("bash", "-c", processString); // TODO hardcoded bash -c command
        } else {
            pb = new ProcessBuilder("bash", "-c", processString.replace("[ARG]", processArgs[0])); // TODO implement multiple args support
        }

        Process process = pb.start();

        return process;
    }
}
