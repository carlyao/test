require("util.CSVParser")
require("util.StringUtil")

-----------------------------------
--
-- covert csv data to lua data
--
-----------------------------------

local function buildPath(filename)
  return system.pathForFile(filename, system.ResourceDirectory)
end

function parseFleet(file)
  local fp = assert(io.open(buildPath(file)))
  local line = fp:read()
  local headers=ParseCSVLine(line,",")
  line = fp:read()
  local fleets = {}
  while nil ~= line do 
    local myfields={}
    local cols=ParseCSVLine(line,",")
    for i,v in pairs(headers) do                            
      myfields[v]=cols[i] or ""
    end
    table.insert(fleets, myfields)
    line = fp:read()
  end
  return fleets
end

function parseFVD(file)
  local fp = assert(io.open(buildPath(file)))
  local line = fp:read()
  local headers=ParseCSVLine(line,",")
  line = fp:read()
  local fvds = {}
  while nil ~= line do 
    local myfields={}
    local cols=ParseCSVLine(line,",")
    for i,v in pairs(headers) do                            
      myfields[v]=cols[i] or ""
    end
    table.insert(fvds, myfields)
    line = fp:read()
  end
  return fvds
end

function parseDifficulty(file)
  local fp = assert(io.open(buildPath(file)))
  local line = fp:read()
  local headers=ParseCSVLine(line,",")
  line = fp:read()
  local difficulty = {}
  while nil ~= line do 
    local myfields={}
    local cols=ParseCSVLine(line,",")
    for i,v in pairs(headers) do                            
      myfields[v]=cols[i] or ""
    end
    if "Metal Beard" == myfields["game"] then
      table.insert(difficulty, myfields)
    end
    line = fp:read()
  end
  return difficulty
end

function parseAmmo(file)
  local fp = assert(io.open(buildPath(file)))
  local line = fp:read()
  local headers=ParseCSVLine(line,",")
  line = fp:read()
  local ammos = {}
  while nil ~= line do 
    local myfields={}
    local cols=ParseCSVLine(line,",")
    for i,v in pairs(headers) do                            
      myfields[v]=cols[i] or ""
    end
    table.insert(ammos, myfields)
    line = fp:read()
  end
  return ammos
end

function parseLeveConfig(file)
  local fp = assert(io.open(buildPath(file)))
  local line = fp:read()
  local headers=ParseCSVLine(line,",")
  line = fp:read()
  local levels = {}
  
  local function comp(a, b)
    return a.ratio < b.ratio
  end
  
  while nil ~= line do 
    local myfields={}
    local cols=ParseCSVLine(line,",")
    for i,v in pairs(headers) do                            
      myfields[v]=cols[i] or ""
    end
    
    local ids = SplitString(myfields["fvd_id"], "|")
    myfields["fvd_id"] = {}
    for i, v in ipairs(ids) do
      local id_ratio = SplitString(v, ":")
      table.insert(myfields["fvd_id"], {id=id_ratio[1], ratio=id_ratio[2]})
    end
    table.sort(myfields["fvd_id"], comp)
    
    local counts = SplitString(myfields["fvd_count"], "|")
    myfields["fvd_count"] = {}
    for i, v in ipairs(counts) do
      local count_ratio = SplitString(v, ":")
      table.insert(myfields["fvd_count"], {count=count_ratio[1], ratio=count_ratio[2]})
    end
    table.sort(myfields["fvd_count"], comp)
    
    ids = SplitString(myfields["ship_id"], "|")
    myfields["ship_id"] = {}
    for i, v in ipairs(ids) do
      local id_ratio = SplitString(v, ":")
      table.insert(myfields["ship_id"], {id=id_ratio[1], ratio=id_ratio[2]})
    end
    table.sort(myfields["ship_id"], comp)
    
    ids = SplitString(myfields["ammo_id"], "|")
    myfields["ammo_id"] = {}
    for i, v in ipairs(ids) do
      local id_ratio = SplitString(v, ":")
      table.insert(myfields["ammo_id"], {id=id_ratio[1], ratio=id_ratio[2]})
    end
    table.sort(myfields["ammo_id"], comp)
    
    counts = SplitString(myfields["ammo_count"], "|")
    myfields["ammo_count"] = {}
    for i, v in ipairs(counts) do
      local count_ratio = SplitString(v, ":")
      table.insert(myfields["ammo_count"], {count=count_ratio[1], ratio=count_ratio[2]})
    end
    table.sort(myfields["ammo_count"], comp)
    
    counts = SplitString(myfields["ship_count"], "|")
    myfields["ship_count"] = {}
    for i, v in ipairs(counts) do
      local count_ratio = SplitString(v, ":")
      table.insert(myfields["ship_count"], {count=count_ratio[1], ratio=count_ratio[2]})
    end
    table.sort(myfields["ship_count"], comp)
    
    counts = SplitString(myfields["wave_count"], "|")
    myfields["wave_count"] = {}
    for i, v in ipairs(counts) do
      local count_ratio = SplitString(v, ":")
      table.insert(myfields["wave_count"], {count=count_ratio[1], ratio=count_ratio[2]})
    end
    table.sort(myfields["wave_count"], comp)
    
    table.insert(levels, myfields)
    line = fp:read()
  end
  return levels
end