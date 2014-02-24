local ammoBox = {}

local state = 0
local rotation = 0

local function onCollision(self, event)
  if ( event.phase == "began" ) then
  
  end
  return true
end

function ammoBox.new(id)
  local ammoBox = display.newImage("assets/img/ammobox.png")
  ammoBox.width = 40
  ammoBox.height = 40
  ammoBox.area = ammoBox.height * ammoBox.width
	ammoBox.density = 2
	ammoBox.mass = ammoBox.area * ammoBox.density
  ammoBox.waterY = 0
  ammoBox.id = id
  ammoBox.appearTime = 0
  ammoBox.collision = onCollision  
  ammoBox:addEventListener("collision", ammoBox)
  physics.addBody(ammoBox, "dynamic", {density=2, friction=0.5, bounce=0.5, filter={groupIndex = -1}})
  
  function ammoBox.update(alpha)
    if(ammoBox.appearTime == Model.time) then
      ammoBox.isVisible = true
    end
    
    if (ammoBox.y + (ammoBox.height * 0.5)) >= ammoBox.waterY then
			local submergedPercent = math.floor (100 - (((ammoBox.waterY - ammoBox.y + (ammoBox.height * 0.5)) / ammoBox.height) * 100))
			if submergedPercent > 100 then
				submergedPercent = 100
			end
			
			if submergedPercent > 40 then
        if 0 == state then 
          state = 1
          if 0 == rotation then
            rotation = -1
          elseif -1 == rotation then
            rotation = 2
          elseif 2 == rotation then
            rotation = -2
          elseif -2 == rotation then
            rotation = 2
          end
        end
				local buoyancyForce = (ammoBox.mass * gy)
				ammoBox:applyForce( 0, buoyancyForce * -0.006, ammoBox.x + rotation, ammoBox.y )
				ammoBox.linearDamping = 2
				ammoBox.angularDamping = 2
			else
				ammoBox.linearDamping = 0
				ammoBox.angularDamping = 0
        state = 0
			end 
      ammoBox.x = ammoBox.x + rotation
		end      
  end
  
  return ammoBox
end

return ammoBox