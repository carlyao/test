local scene = GameManager.newScene()

function scene:createScene(event)
  local group = self.view
  
  local bg = display.newImage("assets/img/bg.jpg")
  bg.x = display.contentCenterX
  bg.y = display.contentCenterY
  bg.rotation = 90
  group:insert(bg)
  
  local text1 = display.newText("Touch to play", 0, 0, native.systemFont, 24)
  text1:setTextColor(255)
  text1.x, text1.y = display.contentCenterX, display.contentCenterY
  text1.isVisible = false
  group:insert(text1)
end

function scene:enterScene( event )
  local group = self.view
  local datas = GameManager.loadData()
  if datas then
    Model.record = datas[1]
    Model.maxRecord = datas[2]
    Model.sumRecord = datas[3]
  else
    Model.firstPlay = true
  end
  local path = "assets/csv/"
  Model.fleets = parseFleet(path.."fleets.csv")
  Model.fvds = parseFVD(path.."fvd.csv")
  Model.difficulties = parseDifficulty(path.."difficulty.csv")
  Model.ammos = parseAmmo(path.."ammo.csv")
  Model.levels = parseLeveConfig(path.."level.csv")
  GameManager.changeScene("scene.LevelSelection")
end

function scene:exitScene( event )
  local group = self.view
end

function scene:destroyScene( event )
  local group = self.view
end

scene:addEventListener("createScene", scene)
scene:addEventListener("enterScene", scene)
scene:addEventListener("exitScene", scene)
scene:addEventListener("destroyScene", scene)

return scene