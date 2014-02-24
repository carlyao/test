local widget = require("widget")
local scene = GameManager.newScene()
local LevelScreen = require("view.LevelScreen")
local levelScreen = nil

local function onBtnReleaseHandler(event)
  local id = event.target._id
  if ("easy" == id) then
    Model.difficulty = 1
  elseif ("medium" == id) then
    Model.difficulty = 2
  elseif ("hard" == id) then
    Model.difficulty = 3
  end
  levelScreen.update(Model.record[tostring(Model.difficulty)])
end

local function onLevelSelectHandler(event)
  Model.reset()
  Model.level = tonumber(event.levelId) + (Model.difficulty - 1) * Model.MAX_ROUND
  GameManager.changeScene("scene.GameRunning")
end

function scene:createScene( event )
  local group = self.view
  local bg = display.newImage("assets/img/level selection bg.png")
  bg.x = display.contentCenterX
  bg.y = display.contentCenterY
  group:insert(bg)
  local tabButtons = 
  {
    {
      id = "easy",
      width = 150,
      height = 50,
      defaultFile = "assets/img/Easy.png",
      overFile = "assets/img/Easy.png",
      labelYOffset = -10,
      labelColor =
      {
          default = { 125, 125, 125 },
          over = { 255, 255, 255 },
      },
      font = native.systemFontBold,
      size = 25,
      selected = true,
      onPress = onBtnReleaseHandler
    },
    
    {
      id = "medium",
      width = 150,
      height = 50,
      defaultFile = "assets/img/Medium.png",
      overFile = "assets/img/Medium.png",
      labelYOffset = -10,
      labelColor =
      {
          default = { 125, 125, 125 },
          over = { 255, 255, 255 },
      },
      font = native.systemFontBold,
      size = 25,
      selected = false,
      onPress = onBtnReleaseHandler
    },
    
    {
      id = "hard",
      width = 150,
      height = 50,
      defaultFile = "assets/img/Hard.png",
      overFile = "assets/img/Hard.png",
      labelYOffset = -10,
      labelColor =
      {
          default = { 125, 125, 125 },
          over = { 255, 255, 255 },
      },
      font = native.systemFontBold,
      size = 25,
      selected = false,
      onPress = onBtnReleaseHandler
    }
  }
  
  local tabBar = widget.newTabBar
  {
    width = display.contentWidth,
    backgroundFile = "assets/img/tabbar.png",
    tabSelectedLeftFile = "assets/img/tabBar_tabSelectedLeft.png",
    tabSelectedRightFile = "assets/img/tabBar_tabSelectedRight.png",
    tabSelectedMiddleFile = "assets/img/tabBar_tabSelectedMiddle.png",
    tabSelectedFrameWidth = 20,
    tabSelectedFrameHeight = 52,
    buttons = tabButtons
  }
  group:insert(tabBar)
  
  levelScreen = LevelScreen.new(Model.record[tostring(Model.difficulty)])
  levelScreen.x = 15
  levelScreen.y = tabBar.height + 5
  levelScreen.update(Model.record[tostring(Model.difficulty)])
  group:insert(levelScreen)
end

function scene:enterScene( event )
  local group = self.view
  Runtime:addEventListener("selectLevel", onLevelSelectHandler)
end

function scene:exitScene( event )
  local group = self.view
  Runtime:removeEventListener("selectLevel", onLevelSelectHandler)
end

function scene:destroyScene( event )
  local group = self.view
end

scene:addEventListener("createScene", scene)
scene:addEventListener("enterScene", scene)
scene:addEventListener("exitScene", scene)
scene:addEventListener("destroyScene", scene)

return scene