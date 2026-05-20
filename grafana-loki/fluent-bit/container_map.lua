function map_container_name(tag, timestamp, record)

    local id = record["container_id"]
    if not id then
        return 1, timestamp, record
    end

    local path = "/var/lib/docker/containers/" .. id .. "/config.v2.json"

    local file = io.open(path, "r")
    if not file then
        return 1, timestamp, record
    end

    local content = file:read("*a")
    file:close()

    -- match: "Name": "/nms"
    local name = string.match(content, '"Name"%s*:%s*"([^"]+)"')

    if name then
        record["container_name"] = string.gsub(name, "^/", "")
    else
        record["container_name"] = "unknown"
    end

    return 1, timestamp, record
end
