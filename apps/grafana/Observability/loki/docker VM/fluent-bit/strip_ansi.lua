function process(tag, timestamp, record)

    if record["log"] then
        local msg = record["log"]

        -- remove ANSI codes
        msg = string.gsub(msg, "\27%[[0-9;]*[mK]", "")

        -- remove tabs + newlines entirely (normalize log)
        msg = string.gsub(msg, "\\t", " ")
        msg = string.gsub(msg, "\\n", " ")
        msg = string.gsub(msg, "\\r", " ")

        -- collapse multiple spaces
        msg = string.gsub(msg, "%s+", " ")

        record["log"] = msg
    end

    return 1, timestamp, record
end
