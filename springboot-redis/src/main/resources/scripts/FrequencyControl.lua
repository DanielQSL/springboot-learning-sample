--
-- 频率控制脚本
--
-- 参数：key，过期时间（秒），允许的最大次数，发送时间（时间戳：秒）
-- 返回：0：未超出频率；1：超出频率
-- 注意：缓存一周数据，会出现某个时间段断档
--

local limitLen = redis.call('LLEN', KEYS[1])
if limitLen < tonumber(ARGV[2]) then
    redis.call('LPUSH', KEYS[1], tonumber(ARGV[3]))
    if limitLen == 0 then
        redis.call("EXPIRE", KEYS[1], 604800)
    end
else
    local times = redis.call('LINDEX', KEYS[1], -1)
    if tonumber(ARGV[3]) - times <= tonumber(ARGV[1]) then
        return 1
    else
        redis.call('LPUSH', KEYS[1], tonumber(ARGV[3]))
        redis.call('LTRIM', KEYS[1], 0, tonumber(ARGV[2])-1)
    end
end
return 0