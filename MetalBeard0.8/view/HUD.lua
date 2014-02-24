local widget = require("widget")

local hud = {}

local function onFireHandler(event)
  Runtime:dispatchEvent({name="fire"})
end

local function onReplayHandler(event)
  local function onOk()
    Runtime:dispatchEvent({name="reSet"})
  end
  Alert.popup("Do you want to start back from round 1?", onOk)
end

function hud.new()
  local group = display.newGroup()
  local scoreTxt = display.newText("SCORE: "..tostring(Model.score), 10, 10, native.systemFontBold, 20)
  scoreTxt:setTextColor(255, 0, 255)
  group:insert(scoreTxt)
  
  local fireBtn = widget.newButton
  {
    left = display.contentWidth - 66,
    top = display.contentHeight - 66,
    width = 56,
    height = 56,
    defaultFile = "assets/img/Play btn1.png",
    overFile = "assets/img/Play btn1.png",
    onRelease = onFireHandler
  }
  group:insert(fireBtn)
  
  local replayBtn = widget.newButton
  {
    left = display.contentWidth - 40,
    top = 10,
    width = 30,
    height = 30,
    defaultFile = "assets/img/Reload btn.png",
    overFile = "assets/img/Reload btn.png",
    onRelease = onReplayHandler
  }
  group:insert(replayBtn)
  
  local ammoGroup = display.newGroup()
  local function fillAmmo(count)
    local ammoCount = ammoGroup.numChildren
    for i = ammoCount + 1, count do
      local ammo = display.newImage("assets/img/Music btn.png")
      ammo.width = 25
      ammo.height = 25
      ammo.x = (i - 1) * 32
      ammo.y = 25
      ammoGroup:insert(ammo)
    end
  end
  fillAmmo(5)
  ammoGroup.x = display.contentCenterX - ammoGroup.width / 2
  
  group:insert(ammoGroup)
  local ammoExtraCount = display.newText(group, "+"..(Model.ammo - 5), ammoGroup.x + ammoGroup.width + 5, 10, native.systemFontBold, 25)
  
  local timeTxt = display.newText(group, tostring(Model.time), 10, display.contentHeight - 60, native.systemFontBold, 35)
  timeTxt:setTextColor(255, 0, 0)
  
  local function reduceAmmo(count)
    if count == ammoGroup.numChildren then return end
    for i = ammoGroup.numChildren, count + 1, -1 do
      ammoGroup:remove(i)
    end
  end

  local elapse = 1
  function group.update(alpha)
    scoreTxt.text = "SCORE: "..tostring(Model.score)
    scoreTxt.x = scoreTxt.width / 2 + 10
    local extra = Model.ammo - 5
    if extra >= 0 then
      ammoExtraCount.text = "+"..(extra)
      fillAmmo(5)
      if 0 == extra then ammoExtraCount.isVisible = false end
    elseif tonumber(Model.ammo) > ammoGroup.numChildren then
      fillAmmo(tonumber(Model.ammo))
    else
      ammoExtraCount.isVisible = false
      reduceAmmo(Model.ammo)
    end
    
    elapse = elapse - alpha
    if (0 >= elapse) then
      elapse = 1
      Model.time = Model.time - 1
      timeTxt.text = Model.time
      if 0 == Model.time then
        Model.resetTime()
        timeTxt.text = Model.time
        Runtime:dispatchEvent({name = "timeOver"})
      end
    end
  end

  return group
end

return hud