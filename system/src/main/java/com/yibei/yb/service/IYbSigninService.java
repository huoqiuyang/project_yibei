package com.yibei.yb.service;

import com.yibei.yb.domain.YbSignin;
import com.yibei.yb.domain.clientVo.ReportSigninInfoVo;
import com.yibei.yb.domain.vo.YbSigninVo;
import com.yibei.yb.domain.bo.YbSigninBo;
import com.yibei.common.core.mybatisplus.core.IServicePlus;
import com.yibei.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 签到Service接口
 *
 * @author yibei
 * @date 2022-05-18
 */
public interface IYbSigninService extends IServicePlus<YbSignin, YbSigninVo> {
	/**
	 * 查询单个
	 * @return
	 */
	YbSigninVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<YbSigninVo> queryPageList(YbSigninBo bo);

	/**
	 * 查询列表
	 */
	List<YbSigninVo> queryList(YbSigninBo bo);

	/**
	 * 根据新增业务对象插入签到
	 * @param bo 签到新增业务对象
	 * @return
	 */
	Boolean insertByBo(YbSigninBo bo);

	/**
	 * 根据编辑业务对象修改签到
	 * @param bo 签到编辑业务对象
	 * @return
	 */
	Boolean updateByBo(YbSigninBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

	/**
    * 查询关联数据
    * @return
    */
	YbSigninVo queryExtData(YbSigninVo vo);

	/**
    * 查询关联数据
    * @return
    */
	List<YbSigninVo> queryExtData(List<YbSigninVo> list);

	/**
	 * 学习报告-查询打卡信息
	 * @return
	 */
	List<ReportSigninInfoVo> getReportSignin(Long userId,String month);

}
