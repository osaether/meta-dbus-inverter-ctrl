SUMMARY = "Provides dbus control of inverters on Venus"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ce8464bf9333255588d9f64d05730589"

inherit ve_package
inherit daemontools
inherit python-compile

SRC_URI = "gitsm://github.com/osaether/dbus-inverter-ctrl.git;protocol=https;tag=v${PV}"

S = "${WORKDIR}/git"

RDEPENDS_${PN} = " \
	python3-core \
"

DAEMONTOOLS_SERVICE_DIR = "${bindir}/service"
DAEMONTOOLS_RUN = "softlimit -d 100000000 -s 1000000 -a 100000000 ${bindir}/dbus_inverter_ctrl.py"

do_install () {
	install -d ${D}${bindir}
	cp -r ${S}/* ${D}${bindir}
}

pkg_postinst_${PN} () {
	if test "x$D" = "x"; then
		pushd /opt/victronenergy/gui/qml
		cp PageMain.qml PageMain.qml.save
		cp PageDigitalInput.qml PageDigitalInput.qml.save
		cp MbItemDigitalInput.qml MbItemDigitalInput.qml.save
		svc -d /service/gui
		patch -p1 PageMain.qml /opt/victronenergy/dbus-inverter-ctrl/diffs/PageMain.diff
		patch -p1 PageDigitalInput.qml /opt/victronenergy/dbus-inverter-ctrl/diffs/PageDigitalInput.diff
		patch -p1 MbItemDigitalInput.qml /opt/victronenergy/dbus-inverter-ctrl/diffs/MbItemDigitalInput.diff
		svc -u /service/gui
		popd
		pushd /opt/victronenergy/dbus-digitalinputs
		cp dbus_digitalinputs.py dbus_digitalinputs.py.save
		svc -d /service/dbus-digitalinputs
		patch -p1 dbus_digitalinputs.py /opt/victronenergy/dbus-inverter-ctrl/diffs/dbus_digitalinputs.diff
		chmod +x dbus_digitalinputs.py
		svc -u /service/dbus-digitalinputs
		popd
	fi
}

pkg_postrm_${PN} () {
	if test "x$D" = "x"; then
		pushd /opt/victronenergy/gui/qml
		svc -d /service/gui
		if [ -e "PageMain.qml.save" ]; then
			mv -f PageMain.qml.save PageMain.qml
		fi
		if [ -e "PageDigitalInput.qml.save" ]; then
			mv -f PageDigitalInput.qml.save PageDigitalInput.qml
		fi
		if [ -e "MbItemDigitalInput.qml.save" ]; then
			mv -f MbItemDigitalInput.qml.save MbItemDigitalInput.qml
		fi
		svc -u /service/gui
		popd
		pushd /opt/victronenergy/dbus-digitalinputs
		svc -d /service/dbus-digitalinputs
		if [ -e "dbus_digitalinputs.py.save" ]; then
			mv -f dbus_digitalinputs.py.save dbus_digitalinputs.py
			chmod +x dbus_digitalinputs.py
		fi
		svc -u /service/dbus-digitalinputs
		popd
	fi
}