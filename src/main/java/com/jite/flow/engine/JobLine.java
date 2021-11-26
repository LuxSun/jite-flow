package com.jite.flow.engine;

import com.jite.flow.constant.Const;

/**
 * @author Lux Sun
 * @date 2021/10/11
 */
public class JobLine {

    private String fromId;

    private String toId;

    public JobLine(String fromId, String toId) {
        this.fromId = fromId;
        this.toId = toId;
    }

    public String getFromId() {
        return fromId;
    }

    public String getToId() {
        return toId;
    }

    public String getFromToId() {
        return getFromId() + Const.JobLine.Mark.FROM_TO + getToId();
    }

    public String getToFromId() {
        return getToId() + Const.JobLine.Mark.FROM_TO + getFromId();
    }
}