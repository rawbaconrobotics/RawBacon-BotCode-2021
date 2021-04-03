/*
 * Some of this gamepad logic is based on FTC Team 731's robotics simulator.
 * https://github.com/nicholasday/robotics-simulator
 */

import {
  gamepadConnected,
  gamepadDisconnected,
  sendGamepadState
} from '../actions/gamepad';
import GamepadType from '../enums/GamepadType';

const scale = (value, oldMin, oldMax, newMin, newMax) =>
  newMin + (value - oldMin) * (newMax - newMin) / (oldMax - oldMin);

// based on the corresponding function in the SDK Gamepad
const cleanMotionValues = (value, joystickDeadzone, maxMotionRange) => {
  joystickDeadzone = joystickDeadzone || 0.2;
  maxMotionRange = maxMotionRange || 1.0;

  // apply deadzone
  if (-joystickDeadzone < value && value < joystickDeadzone) return 0;

  // apply trim
  if (value > maxMotionRange) return maxMotionRange;
  if (value < -maxMotionRange) return maxMotionRange;

  // scale values between deadzone and trim to 0 and max range
  if (value > 0) {
    return scale(value, joystickDeadzone, maxMotionRange, 0, maxMotionRange);
  } else {
    return scale(value, -joystickDeadzone, -maxMotionRange, 0, -maxMotionRange);
  }
};

const REST_GAMEPAD_STATE = {
  left_stick_x: 0,
  left_stick_y: 0,
  right_stick_x: 0,
  right_stick_y: 0,
  dpad_up: false,
  dpad_down: false,
  dpad_left: false,
  dpad_right: false,
  a: false,
  b: false,
  x: false,
  y: false,
  guide: false,
  start: false,
  back: false,
  left_bumper: false,
  right_bumper: false,
  left_stick_button: false,
  right_stick_button: false,
  left_trigger: 0,
  right_trigger: 0
};

const extractGamepadState = (gamepad) => {
  const type = GamepadType.getFromGamepad(gamepad);
  if (!GamepadType.isSupported(type)) {
    throw new Error('Unable to extract state from unsupported gamepad.');
  }
  switch (type) {
  case GamepadType.LOGITECH_DUAL_ACTION:
    return {
      left_stick_x: cleanMotionValues(-gamepad.axes[1]),
      left_stick_y: cleanMotionValues(gamepad.axes[2]),
      right_stick_x: cleanMotionValues(-gamepad.axes[3]),
      right_stick_y: cleanMotionValues(gamepad.axes[4]),
      dpad_up: gamepad.buttons[12].pressed,
      dpad_down: gamepad.buttons[13].pressed,
      dpad_left: gamepad.buttons[14].pressed,
      dpad_right: gamepad.buttons[15].pressed,
      a: gamepad.buttons[1].pressed,
      b: gamepad.buttons[2].pressed,
      x: gamepad.buttons[0].pressed,
      y: gamepad.buttons[3].pressed,
      guide: false,
      start: gamepad.buttons[9].pressed,
      back: gamepad.buttons[8].pressed,
      left_bumper: gamepad.buttons[4].pressed,
      right_bumper: gamepad.buttons[5].pressed,
      left_stick_button: gamepad.buttons[10].pressed,
      right_stick_button: gamepad.buttons[11].pressed,
      left_trigger: gamepad.buttons[6].value,
      right_trigger: gamepad.buttons[7].value
    };
  case GamepadType.XBOX_360:
    return {
      left_stick_x: cleanMotionValues(gamepad.axes[0]),
      left_stick_y: cleanMotionValues(gamepad.axes[1]),
      right_stick_x: cleanMotionValues(gamepad.axes[2]),
      right_stick_y: cleanMotionValues(gamepad.axes[3]),
      dpad_up: gamepad.buttons[12].pressed,
      dpad_down: gamepad.buttons[13].pressed,
      dpad_left: gamepad.buttons[14].pressed,
      dpad_right: gamepad.buttons[15].pressed,
      a: gamepad.buttons[0].pressed,
      b: gamepad.buttons[1].pressed,
      x: gamepad.buttons[2].pressed,
      y: gamepad.buttons[3].pressed,
      guide: false,
      start: gamepad.buttons[9].pressed,
      back: gamepad.buttons[8].pressed,
      left_bumper: gamepad.buttons[4].pressed,
      right_bumper: gamepad.buttons[5].pressed,
      left_stick_button: gamepad.buttons[10].pressed,
      right_stick_button: gamepad.buttons[11].pressed,
      // the trigger range is [-1, 1] although it starts at 0.0 for some reason
      //left_trigger: gamepad.axes[6] === 0.0 ? 0.0 : (gamepad.axes[6] + 1) / 2,
      //right_trigger: gamepad.axes[7] === 0.0 ? 0.0 : (gamepad.axes[7] + 1) / 2
      left_trigger: gamepad.buttons[6].value,
      right_trigger: gamepad.buttons[7].value
    };
  case GamepadType.SWITCH_PRO:
    return {
      left_stick_x: cleanMotionValues(gamepad.axes[0]),
      left_stick_y: cleanMotionValues(-gamepad.axes[1]),
      right_stick_x: cleanMotionValues(gamepad.axes[2]),
      right_stick_y: cleanMotionValues(-gamepad.axes[3]),
      dpad_up: gamepad.buttons[12].pressed,
      dpad_down: gamepad.buttons[13].pressed,
      dpad_left: gamepad.buttons[14].pressed,
      dpad_right: gamepad.buttons[15].pressed,
      a: gamepad.buttons[1].pressed,
      b: gamepad.buttons[0].pressed,
      x: gamepad.buttons[3].pressed,
      y: gamepad.buttons[2].pressed,
      guide: false,
      start: gamepad.buttons[9].pressed,
      back: gamepad.buttons[8].pressed,
      left_bumper: gamepad.buttons[4].pressed,
      right_bumper: gamepad.buttons[5].pressed,
      left_stick_button: gamepad.buttons[10].pressed,
      right_stick_button: gamepad.buttons[11].pressed,
      left_trigger: gamepad.buttons[6].value,
      right_trigger: gamepad.buttons[7].value
    };
  case GamepadType.WII_REMOTE:
    return {
      left_stick_x: cleanMotionValues(gamepad.axes[0]),
      left_stick_y: cleanMotionValues(-gamepad.axes[1]),
      right_stick_x: cleanMotionValues(gamepad.axes[2]),
      right_stick_y: cleanMotionValues(-gamepad.axes[3]),
      dpad_up: false,
      dpad_down: false,
      dpad_left: false,
      dpad_right: false,
      a: gamepad.buttons[6].pressed,
      b: gamepad.buttons[5].pressed,
      x: gamepad.buttons[0].pressed,
      y: gamepad.buttons[1].pressed,
      guide: false,
      start: gamepad.buttons[3].pressed,
      back: gamepad.buttons[4].pressed,
      left_bumper: false,
      right_bumper: false,
      left_stick_button: false,
      right_stick_button: false,
      left_trigger: false,
      right_trigger: false
    };


  case GamepadType.L_JOYCON:
    return {
      left_stick_y: +(gamepad.buttons[12]) - +(gamepad.buttons[13]),
      left_stick_x: +(gamepad.buttons[14]) - +(gamepad.buttons[15]),
      right_stick_y: +(gamepad.buttons[0]) - +(gamepad.buttons[2]),
      right_stick_x: +(gamepad.buttons[1]) - +(gamepad.buttons[3]),
      dpad_up: null,
      dpad_down: null,
      dpad_left: null,
      dpad_right: null,
      a: gamepad.buttons[6].pressed,
      b: gamepad.buttons[10].pressed,
      x: null,
      y: null,
      guide: false,
      start: gamepad.buttons[8].pressed,
      back: null,
      left_bumper: gamepad.buttons[3].pressed,
      right_bumper: gamepad.buttons[4].pressed,
      left_stick_button: null,
      right_stick_button: null,
      left_trigger: gamepad.buttons[3].value,
      right_trigger: gamepad.buttons[4].value,
    };
  case GamepadType.PS4:
    return {
      left_stick_x: cleanMotionValues(gamepad.axes[0]),
      left_stick_y: cleanMotionValues(-gamepad.axes[1]),
      right_stick_x: cleanMotionValues(gamepad.axes[2]),
      right_stick_y: cleanMotionValues(-gamepad.axes[3]),
      dpad_up: gamepad.buttons[12].pressed,
      dpad_down: gamepad.buttons[13].pressed,
      dpad_left: gamepad.buttons[14].pressed,
      dpad_right: gamepad.buttons[15].pressed,
      a: gamepad.buttons[0].pressed, //X
      b: gamepad.buttons[1].pressed, //O
      x: gamepad.buttons[2].pressed, //Square
      y: gamepad.buttons[3].pressed, //Triangle
      guide: false,
      start: gamepad.buttons[9].pressed,
      back: gamepad.buttons[8].pressed,
      left_bumper: gamepad.buttons[4].pressed,
      right_bumper: gamepad.buttons[5].pressed,
      left_stick_button: gamepad.buttons[10].pressed,
      right_stick_button: gamepad.buttons[11].pressed,
      left_trigger: gamepad.buttons[6].value,
      right_trigger: gamepad.buttons[7].value,
    };
  default:
    throw new Error(`Unable to handle support gamepad of type ${type}`);
  }
};

let gamepad1Index = -1;
let gamepad2Index = -1;

var leftShift = false;
var rightShift = false;
var leftCtrl = false;
var rightCtrl = false;
var pressedKeys = {};
window.onkeyup = function(e) { pressedKeys[e.keyCode] = false; if(e.keyCode===16){
  if(e.code === "ShiftLeft"){
    leftShift = false;
  }
  else if(e.code === "ShiftRight"){
    rightShift = false;
  }
}else if(e.keyCode===17){
    if(e.code === "ControlLeft"){
      leftCtrl = false;
    }
    else if(e.code === "ControlRight"){
      rightCtrl = false;
    }
} }
window.onkeydown = function(e) { pressedKeys[e.keyCode] = true; if(e.keyCode===16){
  if(e.code === "ShiftLeft"){
    leftShift = true;
  }
  else if(e.code === "ShiftRight"){
    rightShift = true;
  }
}else if(e.keyCode===17){
    if(e.code === "ControlLeft"){
      leftCtrl = true;
    }
    else if(e.code === "ControlRight"){
      rightCtrl = true;
    }
}}

let keyboardPlayer = true;

let leftYaxis = function(){
  if(pressedKeys["87"]){
    return 1;
  }
  else if(pressedKeys["83"]){
    return -1;
  }
  else{
    return 0;
  }
}
let leftXaxis = function(){
  if(pressedKeys["68"]){
    return 1;
  }
  else if(pressedKeys["65"]){
    return -1;
  }
  else{
    return 0;
  }
}
let rightYaxis = function(){
  if(pressedKeys["80"]){
    return 1;
  }
  else if(pressedKeys["186"]){
    return -1;
  }
  else{
    return 0;
  }
}
let rightXAxis = function(){
  if(pressedKeys["222"]){
    return 1;
  }
  else if(pressedKeys["76"]){
    return -1;
  }
  else{
    return 0;
  }
}
let gamepad1Rested = false;
let gamepad2Rested = false;

const gamepadMiddleware = store => {
  function updateGamepads() {
    //const gamepads = navigator.getGamepads();
    const gamepads = [];
    if ((gamepads.length === 999999) && !(pressedKeys[81] === true)) {
      if(!keyboardPlayer){
      setTimeout(updateGamepads, 500);
      }
      return;
    }

    let gamepadState = REST_GAMEPAD_STATE;
    let gamepad1State = null;
    let gamepad2State = null;

    // check for Start-A/Start-B
    for (let gamepad of navigator.getGamepads()) {
      if(pressedKeys["81"]){
        keyboardPlayer = true;
      }
      else if ((gamepad === null || !gamepad.connected) && !keyboardPlayer) {
        continue;
      }
      let gamepadType;
      if (gamepad !== null){
        gamepadType = GamepadType.getFromGamepad(gamepad);
        if (!GamepadType.isSupported(gamepadType)) {
          continue;
        }
      }

  
      if(!keyboardPlayer){
      gamepadState = extractGamepadState(gamepad);
    } 

      // update gamepad 1 & 2 associations
      if (pressedKeys["82"]) {
        if(!keyboardPlayer){
        gamepad1Index = gamepad.index;
        }
        else{
        gamepad1Index = 1;  
        }
        store.dispatch(gamepadConnected(1));
  
        if ((gamepad2Index === gamepad1Index) && (gamepad1Index !== -1)) {
          gamepad2State = null;

          store.dispatch(gamepadDisconnected(2));

          gamepad2Index = -2;
        }
      } else if ((gamepadState.start && gamepadState.b) | ((pressedKeys["85"]))) {
        //    } else if ((gamepadState.start && gamepadState.b) | ((pressedKeys["81"]) && (pressedKeys["83"]))) {

        if(!keyboardPlayer){
          gamepad2Index = gamepad.index;
          }
          else{
          gamepad2Index = 1;  
          }  
        store.dispatch(gamepadConnected(2));
        if ((gamepad1Index === gamepad2Index) && (gamepad1Index !== -1)) {
          gamepad1State = null;
          store.dispatch(gamepadDisconnected(1));

          gamepad1Index = -2;
        }
      } 
      // actually dispatch motion events
      if (((gamepad1Index !== -2) && (gamepad1Index !== -1)) && !keyboardPlayer) {
        gamepad1State = extractGamepadState(gamepads[gamepad1Index], 1);
      } else if(keyboardPlayer && (gamepad1Index === 1)){
        let PAD1_KEYBOARD_STATE = {
          left_stick_x: 0.6 * leftXaxis(),
          left_stick_y: 0.6 * -leftYaxis(),
          right_stick_x: 0.6 * rightXAxis(),
          right_stick_y: 0.6 * -rightYaxis(),
          dpad_up: pressedKeys["38"],
          dpad_down: pressedKeys["40"],
          dpad_left: pressedKeys["37"],
          dpad_right: pressedKeys["39"],
          a: pressedKeys["77"],
          b: pressedKeys["188"],
          x: pressedKeys["190"],
          y: pressedKeys["191"],
          guide: false,
          start: pressedKeys["221"],
          back: pressedKeys["220"],
          left_bumper: leftShift,
          right_bumper: rightShift,
          left_stick_button: pressedKeys["-1"],
          right_stick_button: pressedKeys["-1"],
          left_trigger: +(pressedKeys["20"]),
          right_trigger: +(pressedKeys["13"])
        };
        gamepad1State = PAD1_KEYBOARD_STATE;
      } 
      //else {
      //  gamepad1State = REST_GAMEPAD_STATE;
      //}
      
      if (((gamepad2Index !== -2) && (gamepad2Index !== -1)) && !keyboardPlayer) {
        gamepad2State = extractGamepadState(gamepads[gamepad2Index], 2);
      } else if((keyboardPlayer) && (gamepad2Index === 1)){
        let PAD2_KEYBOARD_STATE = {
          left_stick_x: 0.6 * leftXaxis(),
          left_stick_y: 0.6 * -leftYaxis(),
          right_stick_x: 0.6 * rightXAxis(),
          right_stick_y: 0.6 * -rightYaxis(),
          dpad_up: pressedKeys["38"],
          dpad_down: pressedKeys["40"],
          dpad_left: pressedKeys["37"],
          dpad_right: pressedKeys["39"],
          a: pressedKeys["77"],
          b: pressedKeys["188"],
          x: pressedKeys["190"],
          y: pressedKeys["191"],
          guide: false,
          start: pressedKeys["221"],
          back: pressedKeys["220"],
          left_bumper: leftShift,
          right_bumper: rightShift,
          left_stick_button: pressedKeys["-1"],
          right_stick_button: pressedKeys["-1"],
          left_trigger: +(pressedKeys["20"]),
          right_trigger: +(pressedKeys["13"])
        };
        gamepad2State = PAD2_KEYBOARD_STATE;
      }
      //}else{
      //  gamepad2State = REST_GAMEPAD_STATE;
      //}      
      if((gamepad2Index !== -1) | (gamepad1Index !== -1)){

      store.dispatch(sendGamepadState(gamepad1State, gamepad2State));
      }
    }
     
    requestAnimationFrame(updateGamepads);
  }

  window.addEventListener('gamepaddisconnected', ({ gamepad }) => {
    if (gamepad1Index === gamepad.index) {
      store.dispatch(gamepadDisconnected(gamepad1Index));
      
      gamepad1Index = -2;
    } else if (gamepad2Index === gamepad.index) {
      store.dispatch(gamepadDisconnected(gamepad2Index));

      gamepad2Index = -2;
    }
  });

  updateGamepads();

  return next => action => next(action);
};


export default gamepadMiddleware;