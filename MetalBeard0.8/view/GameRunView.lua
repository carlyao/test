local view = {}
local Fleet = require("view.FleetView")
local FVD = require("view.FVDView")
local bezier = require("util.bezier")
local AmmoView = require("view.AmmoView")
local AmmoBoxView = require("view.AmmoBoxView")
local CAnimationView = require("view.CAnimationView")
local WaveView = require("view.WaveView")

local tempShip, tempFvd, tempAmmo = nil, nil, nil
local explosionAmmo

local function getValue(key1, key2)
  local levelData = Model.levels[Model.level]
  local value = 0
  local range = {}
  local ratio = 0
  for i = 1, #levelData[key1] do
    ratio = ratio + levelData[key1][i]["ratio"]
    local lastRatio = (i == 1 and 0 or levelData[key1][i-1]["ratio"])
    table.insert(range, {tonumber(lastRatio), ratio})
  end
  
  ratio = math.random(0, ratio)
  
  for i = 1, #range do
    if ratio >= range[i][1] and ratio < range[i][2] then
      value = levelData[key1][i][key2]
      break
    end
  end
  
  return value
end

local function getIds(key, count)
  local levelData = Model.levels[Model.level]
  local ids = {}
  for i = 1, count do
    ids[i] = getValue(key, "id")
  end
   
  return ids 
end

local function clearGroup(group)
  if nil == group or nil == group.numChildren then return end
  while group.numChildren > 0 do
    group:remove(1)
  end
end

  local function isPointInView(view, testPoint)
    if nil == view or nil == view.width or nil == view.height or nil == view.x or nil == view.y or not view.isVisible then
      return false
    end
    
    local srcX, srcY = view.x, view.y
    local testX, testY = testPoint[1], testPoint[2]
    local inX, inY = false, false
    if srcX - view.width / 2 < testX and srcX + view.width / 2  > testX then inX = true end
    if srcY - view.height / 2 < testY and srcY + view.height / 2 > testY then inY = true end
    return inX and inY
  end
  
  local function checkCollision(views, testPoint)
    tempShip = nil
    tempFvd = nil
    tempAmmo = nil
    local view = nil
    for i = 1, #views do
      if "ship" == views[i].itemType and isPointInView(views[i].ship, testPoint) then
        view = views[i]
        break
      elseif "wave" ~= views[i].itemType and isPointInView(views[i], testPoint) then
        view = views[i]
        break
      end
    end
    if nil ~= view and "ship" == view.itemType then
      tempShip = view
    elseif nil ~= view and "fvd" == view.itemType then
      tempFvd = view
    elseif nil ~= view and "ammoBox" == view.itemType then
      tempAmmo = view
    end
  end

function view.new()
  local group = display.newGroup()
  local items = {}
  local targetShip, targetFvd, targetAmmo = nil, nil, nil 
  local ammo, curve = nil, nil
  
  local sky = display.newImage("assets/img/sky.png")
  group:insert(sky)

  local sea = display.newImage("assets/img/sea.png")
  sea.y = 231.5
  group:insert(sea)
  
  local itemGroup = display.newGroup()
  group:insert(itemGroup)
  
  local explosionAmmo = CAnimationView.new("bomb_2.png", 1440, 144, 9, 0, 300)
  group:insert(explosionAmmo)
  explosionAmmo.isVisible = false
  
  local function setItemsLayer()
    local function sortItem(item1, item2)
      local layer1 = item1.y
      local layer2 = item2.y
      if "ship" == item1.itemType then
        layer1 = item1.ship.y
      elseif "ship" == item2.itemType then
        layer2 = item2.ship.y
      end
      return layer1 < layer2
    end
      
    table.sort( items, sortItem )
    for i, k in pairs(items) do
      itemGroup:insert(k)
    end
  end
  
  local function createShips()      
    local shipCount = getValue("ship_count", "count")
    local shipIds = getIds("ship_id", shipCount)
    for i = 1, #shipIds do
      local ship = Fleet.new(shipIds[i], 1)
      local shipx = math.random() * (display.contentWidth - 80) + 40
      local shipy = math.random() * 105 + 120
      local xScale = shipx > display.contentCenterX and display.contentCenterX / shipx or shipx / display.contentCenterX
      local yScale = shipy / 225
      local scale = xScale / 4 + yScale * 3 / 4
      ship.itemType = "ship"
      ship.hp = tonumber(Model.fleets[tonumber(shipIds[i])].hp)
      ship.ship:scale(scale, scale)
      ship.ship.x, ship.ship.y = shipx, shipy
      ship.ship.maxX = ship.ship.x + 15
      ship.ship.minX = ship.ship.x - 15
      ship.waterY = ship.ship.y + ship.ship.height / 2
      table.insert(items, ship)
    end
  end
  createShips()
    
  local function createFvds()    
    local fvdCount = getValue("fvd_count", "count")
    local fvdIds = getIds("fvd_id", fvdCount)
    for i = 1, #fvdIds do
      local fvd = FVD.new(fvdIds[i])
      local fvdx = math.random() * (display.contentWidth - 80) + 40
      local fvdy = math.random() * 105 + 120
      local xScale = fvdx > display.contentCenterX and display.contentCenterX / fvdx or fvdx / display.contentCenterX
      local yScale = fvdy / 225
      local scale = xScale / 4 + yScale * 3 / 4
      fvd:scale(scale, scale)
      fvd.x, fvd.y = fvdx, fvdy
      fvd.waterY = fvd.y + fvd.height / 2
      fvd.itemType = "fvd"
      fvd.appearTime = math.floor(math.random() * (Model.ROUND_TIME - 10) ) + 5
      fvd.isVisible = false
      table.insert(items, fvd)
    end
  end
  createFvds()
  
  local function createAmmoBox()
    local ammoBoxCount = getValue("ammo_count", "count")
    local ammoBoxIds = getIds("ammo_id", ammoBoxCount)
    for i = 1, #ammoBoxIds do
      local ammoBox = AmmoBoxView.new(ammoBoxIds[i])
      local ammoBoxx = math.random() * (display.contentWidth - 80) + 40
      local ammoBoxy = math.random() * 105 + 120
      local xAmmoBoxScale = ammoBoxx > display.contentCenterX and display.contentCenterX / ammoBoxx or ammoBoxx / display.contentCenterX
      local yAmmoBoxScale = ammoBoxy / 225
      local scale = xAmmoBoxScale / 4 + yAmmoBoxScale * 3 / 4
      ammoBox.itemType = "ammoBox"
      ammoBox:scale(scale, scale)
      ammoBox.x, ammoBox.y = ammoBoxx, ammoBoxy
      ammoBox.waterY = ammoBox.y + ammoBox.height / 2
      ammoBox.appearTime = math.floor(math.random() * (Model.ROUND_TIME - 10) ) + 5
      ammoBox.isVisible = false
      table.insert(items, ammoBox)
    end
  end
  createAmmoBox()
 
  local function createWave()
    local stepy = -15
    for i=1,9,1 do
      local randomWaveAni = WaveView.new(500, 15)
      randomWaveAni.itemType = "wave"
      randomWaveAni.y = sky.height + stepy
      table.insert(items, randomWaveAni)
      stepy = stepy + 19
    end
  end

  createWave()
 
  setItemsLayer()
  
  local cannoP = display.newImage("assets/img/canno platform.png")
  cannoP.x = display.contentCenterX
  cannoP.y = display.contentHeight - 120
  group:insert(cannoP)
  
  local options = 
  {
    width = 116,
    height = 158,
    numFrames = 8,
    sheetContentWidth = 928,
    sheetContentHeight = 158
  }
  local imageSheet = graphics.newImageSheet("assets/img/canno.png", options)
  local sequenceData = 
  {
    name = "move",
    start = 1,
    count = 8
  }
  local canno = display.newSprite(imageSheet, sequenceData)
  canno.x = display.contentCenterX
  canno.y = display.contentHeight + 50
  canno.angle = 30
  canno.maxAngle = 170
  canno.minAngle = 30
  group:insert(canno)
  group.canno = canno
  
  local cross = display.newImage("assets/img/crosshair.png")
  cross.x = canno.x
  cross.y = canno.y - canno.height / 4
  cross:scale(0.12, 0.12)
  group:insert(cross)
  group.cross = cross
  
  group.r = canno.y - cross.y -- the length between cross and canno
  
  local target = display.newImage("assets/img/xxx.png")
  target.isVisible = false
  group:insert(target)
  
  local lineGroup = display.newGroup()
  local destX, destY = 0, 0
  local targetX, targetY = 0, 0
  function group.drawPath()
    group.clearPath()
    local destLen = 5 + math.abs(canno.rotation) + (canno.angle - canno.minAngle)
    local radius = math.rad(canno.rotation)
    local srcX, srcY = cross.x, cross.y
    destX = math.sin(radius) * destLen + srcX
    destY = srcY - math.cos(radius) * destLen
    curve = bezier:curve({srcX, (srcX + destX) / 2, destX},{srcY, (srcY + destY) / 5, destY})
    local x1, y1 = curve(0.01)
    for i = 0.02, 1, 0.04 do
      local x1, y1 = curve(i)
      local x2, y2 = curve(i + 0.02)
      local line = display.newLine(x1, y1, x2, y2)
      line.width = 2
      line:setColor(255, 102, 102, 255)
      lineGroup:insert(line)
    end
    group:insert(lineGroup)
    checkCollision(items, {destX, destY})
    if tempShip or tempFvd or tempAmmo then
      target.x, target.y = destX, destY
      target.isVisible = true
    else
      target.isVisible = false
    end
  end
  
  function group.clearPath()
    target.isVisible = false
    clearGroup(lineGroup)
  end
  
  local function explosionAmmoCallBack( event )
    if "loop" == event.phase then
      explosionAmmo:removeEventListener( "sprite", explosionAmmoCallBack )
      explosionAmmo.isVisible = false
    end
  end
  
  local function onExplosion()
    checkCollision(items, {targetX, targetY})
    explosionAmmo.x = targetX
    explosionAmmo.y = targetY
    targetShip, targetFvd, targetAmmo = tempShip, tempFvd, tempAmmo
    if targetShip or targetFvd or targetAmmo then
      local data, score
      local destroy = false
      if nil ~= targetShip then
        targetShip.hp = targetShip.hp - 4
        if targetShip.hp <= 0 then
          data = Model.fleets[tonumber(targetShip.id)]
          score = tonumber(data.score)
          table.remove(items, table.indexOf(items, targetShip))
          targetShip:removeSelf()
          destroy = true
        else
          targetShip.resetHp(targetShip.hp, 10)
        end
      elseif nil ~= targetFvd then
        data = Model.fvds[tonumber(targetFvd.id)]
        score = data.score
        table.remove(items, table.indexOf(items, targetFvd))
        targetFvd:removeSelf()
        destroy = true
      elseif nil ~= targetAmmo then
        data = Model.ammos[tonumber(targetAmmo.id)]
        score = 0
        Model.ammo = Model.ammo + tonumber(data.amount)
        if Model.ammo > tonumber(Model.levels[(Model.difficulty - 1) * Model.MAX_ROUND + 1].ammo) then
          Model.ammo = Model.levels[(Model.difficulty - 1) * Model.MAX_ROUND + 1].ammo
        end
        table.remove(items, table.indexOf(items, targetAmmo))
        targetAmmo:removeSelf()
        destroy = true
      else 
    end
      targetShip = nil
      targetFvd = nil
      targetAmmo = nil
      if destroy == true then
        Model.score = Model.score + score
        Model.roundScore = Model.roundScore + score
        Model.roundDestroyCount = Model.roundDestroyCount + 1
        explosionAmmo.isVisible = true
        explosionAmmo:setFrame(1)
        explosionAmmo:play()
        explosionAmmo:addEventListener( "sprite", explosionAmmoCallBack )
      end
    end
  end
  
  function group.fire()
    if curve and (nil == ammo or nil == ammo.y)then
      ammo = AmmoView.new(curve, onExplosion)
      Model.ammo = Model.ammo - 1
      group:insert(ammo)
      targetX = destX
      targetY = destY
    end
  end
  
  group.draw = false
  function group.update(alpha)   
    if ammo and ammo.y then
      ammo.update(alpha)
    end
    
    for i = 1, #items do
      local child = items[i]
      if child and child.y then
        child.update(alpha)
      end
    end
  end
  
  function group.nextLevel()
    items = {}
    clearGroup(itemGroup)
    createShips()
    createFvds()
    createAmmoBox()
    createWave()
    setItemsLayer()
    
  end
  
  -- re-assign ships and fvds
  function group.reset()
    items = {}
    canno:setFrame(1)
    canno.rotation = 0
    cross.x = canno.x
    cross.y = canno.y - canno.height / 2 - 10
    clearGroup(itemGroup)
    createShips()
    createFvds()
    createAmmoBox()
    createWave()
    setItemsLayer()
    
  end
  
  return group
end

return view