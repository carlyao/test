local widget = require("widget")

Alert = {}
local group = display.newGroup()
group.isVisible = false

local bg = display.newImage("assets/img/Warning.png")
group:insert(bg)

local message = display.newText("", 0, 0, 250, 150, native.systemFontBold, 25)
message:setTextColor(255, 0, 0)
message.x = group.width / 2
message.y = group.height / 2
group:insert(message)

local ok = nil
local cancel = nil

local function onCancelBtn(event)
  group.isVisible = false
  if nil ~= cancel then cancel() end
end

local cancelBtn = widget.newButton
{
  width = 73,
  height = 75, 
  defaultFile = "assets/img/Warning_x_bth.png",
  overFile = "assets/img/Warning_x_bth.png",
  onRelease= onCancelBtn
}
group:insert(cancelBtn)
cancelBtn.x = group.width - cancelBtn.width / 2 + 10
cancelBtn.y = cancelBtn.height / 2 - 10

local function onOKBtn(event)
  group.isVisible = false
  if nil ~= ok then ok() end
end

local okBtn = widget.newButton
{
  width = 73,
  height = 75, 
  defaultFile = "assets/img/Accept btn.png",
  overFile = "assets/img/Accept btn.png",
  onRelease= onOKBtn
}
group:insert(okBtn)
okBtn.x = group.width / 2
okBtn.y = group.height - okBtn.height / 2

function Alert.popup(msg, okHandler, cancelHandler)
  message.text = msg
  group.x = display.contentCenterX - group.width / 2
  group.y = display.contentCenterY - group.height / 2
  group.isVisible = true
  ok = okHandler
  cancel = cancelHandler
end