This repository contains a recipe for building 
[bus-inverter-ctrl](https://github.com/osaether/dbus-inverter-ctrl)

## Building

To build bus-inverter-ctrl follow these steps:

1. Clone and initialize the Venus repo
   git clone git@github.com:victronenergy/venus.git
   sudo git make prereq
   make fetch

2. Copy the .bb file in this repository to subfolder of the Venus source. 
   E.g source/meta-victronenergy/meta-venus/recipes-ve

3. Start the building environment for your hardware. For Venus GX that is
   Beaglebone:
   make beaglebone-bb

4. Build the project:
   bitbake dbus-inverter-ctrl
