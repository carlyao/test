local component = {}
function component.new(level, score)
  local levelTxt, scoreTxt
  local group = display.newGroup()
  local bg = display.newImage("assets/img/level_bg.png")
  local lock_bg = display.newImage("assets/img/level_lock_bg.png")
  local lock = display.newImage("assets/img/Lock.png")
  group:insert(bg)
  group:insert(lock_bg)
  
  levelTxt = display.newText(tostring(level), 0, 0, native.systemFontBold, 25)
  levelTxt.x = group.width / 2
  levelTxt.y = group.height / 2 - 10
  levelTxt:setTextColor(0)
  group:insert(levelTxt)
  
  scoreTxt = display.newText(tostring(score), 0, 0, native.systemFontBold, 25)
  scoreTxt.x = group.width / 2
  scoreTxt.y = group.height - 20
  scoreTxt:setTextColor(0, 255, 0)
  group:insert(scoreTxt)

  group:insert(lock)
   
  local function onTap(event)
    Runtime:dispatchEvent({name = "selectLevel", levelId = levelTxt.text})
  end
   
  function group.lock(locked)
    if locked then
      lock.isVisible = true
      lock_bg.isVisible = true
      bg.isVisible = false
      group:removeEventListener("tap", onTap)
    else
      lock.isVisible = false
      lock_bg.isVisible = false
      bg.isVisible = true
      group:removeEventListener("tap", onTap)
      group:addEventListener("tap", onTap)
    end
  end
 
  function group.update(score)
    if score then
      scoreTxt.isVisible = true
      scoreTxt.text = tostring(score)
      group.lock(false)
    else
      group.lock(true)
      scoreTxt.isVisible = false
    end
  end
    
  return group
end

return component