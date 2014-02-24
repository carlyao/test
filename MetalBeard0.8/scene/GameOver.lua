local gameOverView = require("view.GameOverView").new()
local scene = GameManager.newScene()

function scene:createScene( event )
  local group = self.view
  gameOverView.x = (display.contentWidth - gameOverView.width) / 2
  gameOverView.y = (display.contentHeight - gameOverView.height) / 2
  group:insert(gameOverView)
end

local function onRestart(event)
  Model.reset()
  GameManager.changeScene("scene.GameRunning")
end

function scene:enterScene( event )
  local group = self.view
  Runtime:addEventListener("restart", onRestart)
end

function scene:exitScene( event )
  local group = self.view
  Runtime:removeEventListener("restart", onRestart)
end

function scene:destroyScene( event )
  local group = self.view
end

scene:addEventListener("createScene", scene)
scene:addEventListener("enterScene", scene)
scene:addEventListener("exitScene", scene)
scene:addEventListener("destroyScene", scene)

return scene