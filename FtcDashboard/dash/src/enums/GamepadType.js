/*
The gamepad mappings have been tested with the following devices:
  - 46d-c216-Logitech Dual Action
  - 46d-c21d-Xbox 360 Wired Controller
*/

const GamepadType = {
  LOGITECH_DUAL_ACTION: 'LOGITECH_DUAL_ACTION',
  XBOX_360: 'XBOX_360',
  SWITCH_PRO: 'SWITCH_PRO',
  UNKNOWN: 'UNKNOWN'
};

export default Object.freeze({
  ...GamepadType,

  getFromGamepad: (gamepad) => {
    if (gamepad.id.search('Logitech Dual Action') !== -1) {
      return GamepadType.LOGITECH_DUAL_ACTION;
    } else if (gamepad.id.search('Xbox 360') !== -1) {
      return GamepadType.XBOX_360;
    } else if (gamepad.id.search('xinput') !== -1) {
      return GamepadType.SWITCH_PRO;
    } else {
      return GamepadType.UNKNOWN;
    }
  },

  getJoystickDeadzone: (gamepadType) => {
    switch (gamepadType) {
    case GamepadType.LOGITECH_DUAL_ACTION:
      return 0.06;
    case GamepadType.XBOX_360:
      return 0.15;
    case GamepadType.SWITCH_PRO:
      return 0.15;
    default:
      return 0.2;
    }
  },

  isSupported: (gamepadType) => 
    gamepadType !== GamepadType.UNKNOWN
});
