package io.github.aleksavelickovic.service;

import lombok.NonNull;

import java.io.IOException;
import java.util.Map;

import static java.util.Map.entry;

public interface ProcessService {
    public static final Integer NULL = 0;
    public static final Integer READ_GRUB = 1;
    public static final Integer REBOOT = 2;
    public static final Integer SET_GRUB = 3;

    public static final Map<Integer, String> processes = Map.ofEntries(
            entry(1, "awk -F\\' '/menuentry / {print $2}' /boot/grub2/grub.cfg"),
            entry(2, "reboot"),
            entry(3, "grub2-reboot [ARG]")
    );

    public Process execute(@NonNull Integer processName, Boolean root, String... processArgs) throws IOException;
}
