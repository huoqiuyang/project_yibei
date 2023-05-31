package com.yibei.system.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yibei.system.domain.User;
import com.yibei.common.core.mybatisplus.core.BaseMapperPlus;
import com.yibei.system.domain.bo.UserBo;
import com.yibei.system.domain.vo.UserVo;
import org.apache.ibatis.annotations.Param;

/**
 * 用户Mapper接口
 *
 * @author yibei
 * @date 2022-04-26
 */
public interface UserMapper extends BaseMapperPlus<User> {

    /**
     * 获取用户信息列表
     * */
    Page<UserVo> getList(Page bo,@Param("keyword")String keyword,@Param("vipStatus")Integer vipStatus, @Param("time")String time);

}
