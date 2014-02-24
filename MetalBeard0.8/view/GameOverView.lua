local widget = require("widget")

local view = {}

function view.new()
  local group = display.newGroup()
  
  local bg = display.newImage("assets/img/Level Cleared.png")
  bg.width = display.contentWidth
  bg.height = display.contentHeight
  bg.x = display.contentCenterX
  bg.y = display.contentCenterY
  group:insert(bg)
  
  local scoreTxt = display.newText(tostring(Model.score), 0, 0, native.systemFontBold, 40)
  scoreTxt.x = group.width / 2
  scoreTxt.y = group.height / 2
  group:insert(scoreTxt)
  
  local function onReplayHandler(event)
    Runtime:dispatchEvent({name="restart"})
  end

  local function onMenuHandler(event)
    GameManager.changeScene("scene.LevelSelection")
  end
  
  local replayBtn = widget.newButton
  {
    top = display.contentHeight - 90,
    width = 73,
    height = 75,
    defaultFile = "assets/img/Reload btn.png",
    overFile = "assets/img/Reload btn.png",
    onRelease = onReplayHandler
  }
  replayBtn.x = display.contentCenterX - replayBtn.width
  group:insert(replayBtn)
  
  local menuBtn = widget.newButton
  {
    top = display.contentHeight - 90,
    width = 73,
    height = 75,
    defaultFile = "assets/img/Menu btn.png",
    overFile = "assets/img/Menu btn.png",
    onRelease = onMenuHandler
  }
  menuBtn.x = display.contentCenterX + menuBtn.width
  group:insert(menuBtn)
  
  return group
end

return view