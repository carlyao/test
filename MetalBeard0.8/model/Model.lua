Model = {ROUND_TIME = 30, MAX_ROUND = 15}
Model.firstPlay = false
Model.score = 0
Model.roundScore = 0
Model.roundDestroyCount = 0
Model.ammo = 0
Model.ammoDamage = 10
Model.difficulty = 1
Model.round = 1 -- each difficulty has 15 rounds
Model.level = 1 -- all rounds make level, it will be 1...15...30...45
Model.time = Model.ROUND_TIME
Model.record = {["1"] = {0},["2"] = {0}, ["3"] = {0}}
Model.maxRecord = {["1"] = {0},["2"] = {0}, ["3"] = {0}}
Model.sumRecord = {0,0,0}

------------------------------------------------
--
-- read config data from csv files
--
------------------------------------------------
Model.levels = {}
Model.fleets = {}
Model.ammos = {}
Model.difficulties = {}
Model.fvds = {}

-- reset data to round 1 in current difficulty
function Model.reset()
  Model.round = 1
  Model.score = 0
  Model.roundScore = 0
  Model.time = Model.ROUND_TIME
  Model.level = (Model.difficulty - 1) * Model.MAX_ROUND + 1
  Model.ammo = Model.levels[(Model.difficulty - 1) * Model.MAX_ROUND + 1].ammo
end

function Model.resetTime()
  Model.time = Model.ROUND_TIME
end

function Model.nextLevel()
  Model.roundScore = 0
  Model.roundDestroyCount = 0
  Model.time = Model.ROUND_TIME
  Model.round = Model.round + 1
  Model.level = Model.level + 1
end