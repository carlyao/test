local fvd = {}

local state = 0
local rotation = 0

local function onCollision(self, event)
  if ( event.phase == "began" ) then
  
  end
  return true
end

function fvd.new(id)
  local fvd = display.newImage("assets/img/crate.png")
  fvd.width = 40
  fvd.height = 40
  fvd.area = fvd.height * fvd.width
	fvd.density = 2
	fvd.mass = fvd.area * fvd.density
  fvd.waterY = 0
  fvd.id = id
  fvd.appearTime = 0
  fvd.collision = onCollision  
  fvd:addEventListener("collision", fvd)
  physics.addBody(fvd, "dynamic", {density=2, friction=0.5, filter={groupIndex = -1}})
  
  function fvd.update(alpha)
    
    if(fvd.appearTime == Model.time) then
      fvd.isVisible = true
      --print("do=================",fvd.id)
    end
    
    if (fvd.y + (fvd.height * 0.5)) >= fvd.waterY then
			local submergedPercent = math.floor (100 - (((fvd.waterY - fvd.y + (fvd.height * 0.5)) / fvd.height) * 100))
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
				local buoyancyForce = (fvd.mass * gy)
				fvd:applyForce( 0, buoyancyForce * -0.006, fvd.x + rotation, fvd.y )
				fvd.linearDamping = 4
				fvd.angularDamping = 5
			else
				fvd.linearDamping = 0
				fvd.angularDamping = 0
        state = 0
			end     
      fvd.x = fvd.x + rotation
		end      
  end
  
  return fvd
end

return fvd