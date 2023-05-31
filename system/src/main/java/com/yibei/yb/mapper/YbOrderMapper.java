package com.yibei.yb.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yibei.yb.domain.YbOrder;
import com.yibei.common.core.mybatisplus.core.BaseMapperPlus;
import com.yibei.yb.domain.clientVo.YbOrderInfoVo;
import org.apache.ibatis.annotations.Param;

/**
 * 会员订单Mapper接口
 *
 * @author yibei
 * @date 2022-04-27
 */
public interface YbOrderMapper extends BaseMapperPlus<YbOrder> {

    /**
     * 用户交易记录列表
     * */
    Page<YbOrderInfoVo> orderRecordList(Page bo, @Param("userId")Long userId);

}
