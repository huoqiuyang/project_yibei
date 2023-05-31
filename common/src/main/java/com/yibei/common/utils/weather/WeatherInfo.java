package com.yibei.common.utils.weather;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WeatherInfo {

    @ApiModelProperty("时间")
    private String date;

    @ApiModelProperty("城市名")
    private String cityname;

    @ApiModelProperty("天气")
    private String weather;

    @ApiModelProperty("天气图标地址")
    private String weatherImgUrl;

    @ApiModelProperty("气温")
    private String temperature;

    @ApiModelProperty("pm2.5")
    private String airquality;

}
