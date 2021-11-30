package com.jite.flow.job;

/**
 * @author Lux Sun
 * @date 2021/10/18
 */
public class DefaultModule extends AbstractModule {

    private Integer second;

    public Integer getSecond() {
        return second;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return "DefaultModule{" +
                "second=" + second +
                ", id='" + id + '\'' +
                '}';
    }
}