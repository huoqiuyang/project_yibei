package com.yibei.yb.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yibei.system.domain.clientBo.PageBo;
import com.yibei.yb.domain.YbUserBookshelf;
import com.yibei.common.core.mybatisplus.core.BaseMapperPlus;
import com.yibei.yb.domain.clientVo.YbUserBookshelfInfoVo;
import org.apache.ibatis.annotations.Param;

/**
 * 用户书架Mapper接口
 *
 * @author yibei
 * @date 2022-05-06
 */
public interface YbUserBookshelfMapper extends BaseMapperPlus<YbUserBookshelf> {

    /**
     * 书架列表信息
     * */
    Page<YbUserBookshelfInfoVo> getListInfo(Page page, @Param("userId")Long userId);

}
