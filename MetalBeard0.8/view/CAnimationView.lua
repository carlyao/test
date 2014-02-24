local animation = {}

function animation.new(image, width, height, num, direction, delay_time)
  local aniWidth, aniUnitWidth = width, width
  local aniHeight, aniUnitHeight = height, height
  if 0 == direction then
    aniUnitWidth = aniWidth / num
  elseif 1 == direction then
    aniUnitHeight = aniHeight / num
  end
  local seaOptions = 
  {
    width = aniUnitWidth,
    height = aniUnitHeight,
    numFrames = num,
    sheetContentWidth = aniWidth,
    sheetContentHeight = aniHeight
  }
  local aniImage = graphics.newImageSheet("assets/img/"..image, seaOptions)
  local aniData = 
  {
    name = "move",
    start = 1,
    time = delay_time,
    count = num
  }
  local animationView = display.newSprite(aniImage, aniData)
  animationView.y = display.contentHeight - animationView.height / 2
  animationView.x = display.contentCenterX
  return animationView
end

return animation