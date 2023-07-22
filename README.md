This repository contains a recipe for building 
[dbus-inverter-ctrl](https://github.com/osaether/dbus-inverter-ctrl)

# Building

To build dbus-inverter-ctrl follow these steps:

1. Clone and initialize the [venus](https://github.com/victronenergy/venus) repo

   ```
   git clone git@github.com:victronenergy/venus.git
   cd venus
   sudo make prereq
   make fetch
   ```
2. Copy the .bb file in this repository to a subfolder of the Venus source. 
   E.g source/meta-victronenergy/meta-venus/recipes-ve

3. Start the building environment for your hardware. For [Venus GX](https://www.victronenergy.com/panel-systems-remote-monitoring/venus-gx) that is
   Beaglebone:
   ```
   make beaglebone-bb
   ```

4. Build the project:
   ```
   bitbake dbus-inverter-ctrl
   ```
