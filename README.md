# Dual Boot Manager

Dual Boot Manager is a small desktop utility for Linux systems that helps you reboot directly into a selected operating system entry without waiting for the GRUB menu at startup.

The app reads available GRUB menu entries from the local system, shows them in a simple Swing interface, and then triggers a one-time GRUB boot entry change followed by a reboot.

## What it does

- Reads boot entries from `/boot/grub2/grub.cfg`
- Displays the detected entries in a dropdown menu
- Lets you select the OS you want to boot into next
- Runs `grub2-reboot` through `pkexec` to set the next boot target
- Reboots the machine after the selection is applied

## How it works

The application follows this flow:

1. It executes a privileged command to extract `menuentry` values from GRUB configuration.
2. It populates the GUI dropdown with the discovered entries.
3. When you click `Reboot`, it runs:
   - `pkexec grub2-reboot "<selected entry>"`
   - `reboot`

Because of this, the app is meant for systems where:

- GRUB is used as the bootloader
- `grub2-reboot` is available
- `pkexec` is configured and can request administrator privileges

## Requirements

- Linux system with GRUB2
- Java 25 or a compatible runtime/toolchain for this project configuration
- Maven 3.x
- `pkexec`
- `grub2-reboot`

## Build

Package the application with Maven:

```bash
mvn clean package
```

The build uses:

- `maven-jar-plugin` to set the main class
- `maven-shade-plugin` to produce a shaded JAR

## Run

After packaging, run the generated JAR from the `target` directory.

Example:

```bash
java -jar target/dual-boot-manager-1.0-SNAPSHOT.jar
```

## Notes and limitations

- The application currently reads entries from `/boot/grub2/grub.cfg`, so it is tailored to systems that use that path.
- It assumes the boot entry name shown in GRUB can be passed directly back to `grub2-reboot`.
- The app performs privileged operations, so it may prompt for administrator authentication.
- If your system does not use GRUB2, this project will not work as intended.

## Project structure

- `src/main/java/io/github/aleksavelickovic/Main.java` - application entry point and GRUB entry discovery
- `src/main/java/io/github/aleksavelickovic/ui/MainWindow.java` - Swing UI and reboot action
- `src/main/java/io/github/aleksavelickovic/ui/MainWindow.form` - IntelliJ GUI Designer form

## Technology stack

- Java
- Swing
- Maven
- IntelliJ GUI Designer runtime (`forms_rt`)
- Lombok

## Security warning

This tool executes system-level reboot and bootloader commands. Use it only on a machine you control and only if you understand the impact of changing the next boot target.

## License

No license file is currently included in the repository.
