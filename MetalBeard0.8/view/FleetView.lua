local fleet = {}

local state = 0
local rotation = -4

local function onCollision(self, event)
  if ( event.phase == "began" ) then
  
  end
  return true
end

function fleet.new(id, intensity)
  local group = display.newGroup()
  local ship = display.newImage("assets/img/ship"..id..".png")
  ship.density = 2
  ship.area = ship.height * ship.width
	ship.mass = ship.area * ship.density
  ship.speed = intensity
  rotation = intensity * 2
  group.waterY = 0
  group.id = id
  ship.collision = onCollision  
  ship:addEventListener("collision", ship)
  physics.addBody(ship, "dynamic", {density=2.0, friction=0.5, filter={groupIndex = -1}})
  group:insert(ship)
  group.ship = ship
  
  local bar1 = display.newImage("assets/img/hp1.png")
  group:insert(bar1)
  
  local bar2 = display.newImage("assets/img/hp2.png")
  group:insert(bar2)
  
  function group.resetHp(hp, total)
    bar2.width = 60 * hp / total
  end
  
  local speed = 1
  local rotationValue = intensity * 2
  function group.update(alpha)
    if (ship.y + (ship.height * 0.5)) >= group.waterY then
			local submergedPercent = math.floor (100 - (((group.waterY - ship.y + (ship.height * 0.5)) / ship.height) * 100))
			if submergedPercent > 100 then
				submergedPercent = 100
			end
		
			if submergedPercent > 40 then
        if 0 == state then 
          state = 1
          if rotationValue == rotation then
            rotation = -rotationValue
          elseif -rotationValue == rotation then
            rotation = rotationValue
          end
        end
				local buoyancyForce = (ship.mass * gy)
				ship:applyForce( 0, buoyancyForce * -0.007, ship.x + rotation, ship.y )
				ship.linearDamping = 4
				ship.angularDamping = 5
        ship.rotation = 0
			else
				ship.linearDamping = 0
				ship.angularDamping = 0
        state = 0
			end  
      ship.x = ship.x + speed
      if ship.x > ship.maxX then
        speed = -ship.speed
      elseif ship.x < ship.minX then
        speed = ship.speed
      end
      bar1.x = ship.x
      bar1.y = ship.y - ship.height / 2
      bar2.x = ship.x - (60 -  bar2.width) / 2
      bar2.y = bar1.y
		end      
  end
  return group
end

return fleet