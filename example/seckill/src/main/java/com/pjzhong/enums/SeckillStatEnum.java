package com.pjzhong.enums;

import lombok.Getter;

@Getter
public enum  SeckillStatEnum {
    SUCCESS(1, "秒杀成功"),
    END(0, "秒杀结束"),
    REPEAT_KILL(-1, "重复秒杀"),
    INNER_ERROR(-2, "系统异常"),
    ;

    private int state;
    private String stateInfo;

    SeckillStatEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    private static SeckillStatEnum stateOf(int index) {
        for(SeckillStatEnum state: values()) {
            if(state.getState() == index) {
                return state;
            }
        }

        return null;
    }
}
