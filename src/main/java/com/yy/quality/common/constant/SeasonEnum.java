package com.yy.quality.common.constant;

/**
 * @author ycr
 * @date 2022/6/8 21:56
 */
public enum SeasonEnum {
    /**
     * 春天
     */
    SPRING(1, "spring"),

    /**
     * 夏天
     */
    SUMMER(2, "summer"),

    /**
     * 秋天
     */
    AUTUMN(3, "autumn"),

    /**
     * 冬天
     */
    WINTER(4, "winter");

    private final Integer number;

    private final String season;


    SeasonEnum(Integer number, String season) {
        this.number = number;
        this.season = season;
    }

    public Integer getNumber() {
        return number;
    }

    public String getSeason() {
        return season;
    }

    public static SeasonEnum getInstance(String season) {
        if (season == null || "".equals(season)) {
            return null;
        }
        for (SeasonEnum value : SeasonEnum.values()) {
            if (value.getSeason().equals(season)) {
                return value;
            }
        }
        return null;
    }
}
