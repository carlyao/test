local HUD = require("view.HUD")
local GameView = require("view.GameRunView")
local scene = GameManager.newScene()
local hud, gameView

local oldY = 0
local oldX = 0
local function onTouch(event)
  if event.phase == "began" then
    oldX, oldY = event.xStart, event.yStart
    gameView.drawPath()
    gameView.draw = true
	end
	if event.phase =="moved" then	
    if gameView.draw then
      --newY = event.y
      newX, newY = event.x, event.y
      gameView.canno.rotation = gameView.canno.rotation + (newX - oldX) / display.contentCenterX * 90
      if -90 > gameView.canno.rotation then 
        gameView.canno.rotation = -90 
      end
      if 90 < gameView.canno.rotation then 
        gameView.canno.rotation = 90 
      end
      
      gameView.canno.angle = gameView.canno.angle - (newY - oldY)
      if gameView.canno.maxAngle < gameView.canno.angle then gameView.canno.angle = gameView.canno.maxAngle end
      if gameView.canno.minAngle > gameView.canno.angle then gameView.canno.angle = gameView.canno.minAngle end
      local frame = math.modf((gameView.canno.angle - gameView.canno.minAngle) / 20 + 1)
      gameView.canno:setFrame(frame)
      
      local i = -gameView.canno.rotation * math.pi / 180
      local cX, cY = gameView.canno.x, gameView.canno.y
      local sX, sY = gameView.canno.x, gameView.canno.y - gameView.canno.height / 2 - (frame - 1) * 3
      gameView.cross.x = (sX - cX) * math.cos(i) + (sY - cY) * math.sin(i) + cX
      gameView.cross.y = -(sX - cX) * math.sin(i) + (sY - cY) * math.cos(i) + cY
      
      oldX, oldY = newX, newY
      
      gameView.drawPath()
    end
	end	
	if event.phase == "ended" or event.phase == "cancelled" then
    gameView.draw = false
    gameView.clearPath()
	end
end

local function onFire(event)
  if tonumber(Model.ammo) > 0 then
    gameView.fire()
  end
end

-- Start from round 1 of current difficulty
local function onRestart(event)
  Model.reset()
  gameView.reset()
end

local function onTimeOver(event)
  
  local data = Model.levels[Model.level]
  Model.record[tostring(Model.difficulty)][Model.round] = Model.roundScore
  local maxRecord = Model.maxRecord[tostring(Model.difficulty)][Model.round]
  if Model.round <= Model.MAX_ROUND then
    local dataValue = tonumber(data["score"])
    if Model.roundDestroyCount >= dataValue then
      if(nil == maxRecord or Model.roundScore > maxRecord  ) then
        Model.maxRecord[tostring(Model.difficulty)][Model.round] = Model.roundScore
      end
      Model.nextLevel()
      gameView.nextLevel()
      Model.record[tostring(Model.difficulty)][Model.round] = Model.roundScore
      GameManager.saveData()
    else
      GameManager.changeScene("scene.GameOver")
    end
  else
    GameManager.changeScene("scene.GameOver")
  end
end

function scene:update(alpha)
  if hud then 
    hud.update(alpha)
  end
  
  if gameView then
    gameView.update(alpha)
  end
end

function scene:createScene( event )
  gameView = GameView.new()
  local group = self.view
  hud = HUD.new()
  group:insert(gameView)
  group:insert(hud)
end

function scene:enterScene( event )
  local group = self.view
  Runtime:addEventListener("touch", onTouch)
  Runtime:addEventListener("fire", onFire)
  Runtime:addEventListener("reSet", onRestart)
  Runtime:addEventListener("timeOver", onTimeOver)
end

function scene:exitScene(event)
  Runtime:removeEventListener("touch", onTouch)
  Runtime:removeEventListener("fire", onFire)
  Runtime:removeEventListener("reSet", onRestart)
  Runtime:removeEventListener("timeOver", onTimeOver)
end

function scene:destroyScene(event)
  local group = self.view
  hud:removeSelf()
  gameView:removeSelf()
  hud = nil
  gameView = nil
end

scene:addEventListener("createScene", scene)
scene:addEventListener("enterScene", scene)
scene:addEventListener("exitScene", scene)
scene:addEventListener("destroyScene", scene)

return scene