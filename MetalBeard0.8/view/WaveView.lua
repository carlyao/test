local CAnimationView = require("view.CAnimationView")

local wave = {}

function wave.new(speed, num)
  local random_delay = math.random() * 1500 + speed
  local group = display.newGroup()
  local waveAni = CAnimationView.new("wave.png", 480, 660, num, 1, random_delay)
  waveAni.y = 10
  waveAni:scale(1, math.random() + 1)
  group:insert(waveAni)
  group.waveAni = waveAni
  local frame = math.floor(math.random() * num)
  waveAni:setFrame(frame + 1)
  waveAni:play()

  function group.update(alpha)
  end
  
  return group
end

return wave