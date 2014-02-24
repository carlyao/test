local json = require("json")

GameManager = {}
local storyboard = require("storyboard")
local currentScene

function GameManager.newScene()
  local scene = storyboard.newScene()
  function scene:update(alpha)
    
  end
  return scene
end

function GameManager.changeScene(scene)
  storyboard.gotoScene(scene, "slideLeft", 800)
  currentScene = storyboard.getScene(scene)
  storyboard.removeAll()
end

function GameManager.currentScene()
  return currentScene
end

function GameManager.loadData()
  local path = system.pathForFile("data.bin", system.DocumentsDirectory)
  local file = io.open(path, "r")
  if file then
    return json.decode(file:read("*a"))
  else 
    return nil
  end
end

function GameManager.saveData()
  local data = Model.record
  for i,v in pairs(data) do
    local sumRecord = Model.sumRecord[tonumber(i)]
    local t_sumRecord  = 0
    if nil ~= v and type(v) == "table" then
      for j,k in pairs(v) do
        t_sumRecord = t_sumRecord + k
      end
      if(sumRecord < t_sumRecord) then
        Model.sumRecord[Model.difficulty] = t_sumRecord
      end
      
    end
  end
  local sumData = Model.sumRecord
  local maxData = Model.maxRecord
  local path = system.pathForFile("data.bin", system.DocumentsDirectory)
  file = io.open(path, "w")
  local datas = {}
  datas[1] = data
  datas[2] = maxData
  datas[3] = sumData
  file:write(json.encode(datas))
  io.close(file)
end