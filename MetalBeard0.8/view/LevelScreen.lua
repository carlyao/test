local levelScreen = {}
local LevelComponent = require("view.LevelComponent")
function levelScreen.new(record)
  local group = display.newGroup()
  local count = 1
  for i = 1, 3 do
    for j = 1, 5 do
      local score = record and record[count] or 0
      local comp = LevelComponent.new(count, score)
      comp.x = (j - 1) * comp.width + 6
      comp.y = (i - 1) * comp.height - 5 * i
      group:insert(comp)
      count = count + 1
    end
  end
  
  function group.update(record)
    for i = 1, group.numChildren do
      local child = group[i]
      child.update(record[i])
    end
  end
  
  return group
end

return levelScreen