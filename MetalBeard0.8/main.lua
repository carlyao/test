--
-- Project: MetalBeard
-- Description: 
--
-- Version: 1.0
-- Managed with http://CoronaProjectManager.com
--
-- Copyright 2013 . All Rights Reserved.
-- 

require("mobdebug").start()
require("util.GameManager")
require("util.ResourceParser")
require("model.Model")
require("view.WarningAlert")
local physics = require("physics")
physics.start()
gx, gy = 0,10
physics.setGravity(0, gy)

console = display.newText("", 10, 10, display.contentWidth - 10, display.contentHeight - 10, native.systemFontBold, 20)
console:setTextColor(0, 0, 255)

function log(str)
  console.text = console.text.."\n"..str
end

display.setStatusBar(display.HiddenStatusBar)
local lastTime = 0;
local function update(event)
  local delta = event.time - lastTime
  lastTime = event.time
  local scene = GameManager.currentScene()
  if scene then
    scene:update(delta / 1000)
  end
end

local function onSystemEvent( event )
    if "applicationSuspend" == event.type then
      print()
    elseif "applicationExit" == event.type then
      GameManager.saveData()
    end
end

GameManager.changeScene("scene.SplashScreen")
Runtime:addEventListener("enterFrame", update)
Runtime:addEventListener("system", onSystemEvent )