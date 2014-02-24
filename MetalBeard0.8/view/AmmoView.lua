local ammo = {}

function ammo.new(data, callback)
  local ammoView = display.newImage("assets/img/ammo.png")
  ammoView.width = 20  
  ammoView.height = 20

  local time = 0
  local internal = 0
  local index = 0.01
  local x1, y1
  local x2, y2
  local scale = 1
  function ammoView.update(alpha)
    if index < 1.02 then
        time = time + alpha
      if time > internal then
        time = 0
        internal = 0.02
        x1, y1 = data(index)
        index = index + 0.02
        ammoView.x = x1
        ammoView.y = y1
        scale = scale - 0.001
        ammoView:scale(scale, scale)
      end
    else
      ammoView:removeSelf()
      callback()
    end
  end
  
  return ammoView
end

return ammo