/**
 *  Virtual Dimmer
 *
 *  Copyright 2015 Michael Struck
 *  Version 1.1.0 1/3/16
 *
 *  Version 1.0.0 - Initial release
 *  Version 1.1.0 - Updated the interface to better match SmartThings dimmers (thanks to @BoruGee)
 *
 *  Uses code from SmartThings
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */

metadata {
    definition (name: "Virtual Switch", namespace: "pmckinnon", author: "SmartThings") {
        capability "Switch"
        capability "Refresh"

        attribute "enable", "string"

        command "enable"
        command "disable"
    }

    // simulator metadata
    simulator {
    }

    // UI tile definitions
    tiles(scale: 2) {
        multiAttributeTile(name: "switch", type: "lighting", width: 6, height: 4, canChangeIcon: true, canChangeBackground: true) {
            tileAttribute("device.switch", key: "PRIMARY_CONTROL") {
                attributeState "off", label: '${name}', action: "switch.on", icon: "st.switches.light.off", backgroundColor: "#ffffff", nextState: "turningOn"
                attributeState "on", label: '${name}', action: "switch.off", icon: "st.switches.light.on", backgroundColor: "#79b821", nextState: "turningOff"
                attributeState "turningOff", label: '${name}', action: "switch.on", icon: "st.switches.light.off", backgroundColor: "#ffffff", nextState: "turningOn"
                attributeState "turningOn", label: '${name}', action: "switch.off", icon: "st.switches.light.on", backgroundColor: "#79b821", nextState: "turningOff"
            }
        }

        standardTile("refresh", "device.switch", inactiveLabel: false, decoration: "flat", width: 2, height: 2) {
            state "default", label:"", action:"refresh.refresh", icon:"st.secondary.refresh"
        }
        standardTile("enable", "device.enable", width: 2, height: 2, canChangeIcon: false, inactiveLabel: true, canChangeBackground: false) {
            state "enabled", label: '${name}', action:"disable", backgroundColor: '#B6F7BD', icon:"st.Lighting.light11"
            state "disabled", label: '${name}', action:"enable", backgroundColor: '#F46F7D', icon:"st.Lighting.light13"
        }
        main "switch"
        details(["switch","lValue","refresh","enable"])

    }
}

def installed() {
    enable();
}

def updated() {
    enable();
}

def enable() {
  sendEvent(name: 'enable', value: 'enabled')
}

def disable() {
  sendEvent(name: 'enable', value: 'disabled')
}

def parse(String description) {
}

def isEnabled() {
  device.currentValue('enable') == 'enabled'
}

def on() {
    if(isEnabled()) {
      sendEvent(name: "switch", value: "on")
      log.info "Virtual Switch On"
    }
}

def off() {
    if(isEnabled()) {
      sendEvent(name: "switch", value: "off")
      log.info "Virtual Switch Off"
    }
}

def refresh() {
    log.info "refresh"
}
