package com.yibei.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yibei.common.core.domain.entity.SysDictData;
import com.yibei.common.core.mybatisplus.core.BaseMapperPlus;

import java.util.List;

/**
 * 字典表 数据层
 *
 * @author
 */
public interface SysDictDataMapper extends BaseMapperPlus<SysDictData> {

	default List<SysDictData> selectDictDataByType(String dictType) {
		return selectList(
			new LambdaQueryWrapper<SysDictData>()
				.eq(SysDictData::getStatus, "0")
				.eq(SysDictData::getDictType, dictType)
				.orderByAsc(SysDictData::getDictSort));
	}

	default List<SysDictData> selectDictDataAll() {
		return selectList(
				new LambdaQueryWrapper<SysDictData>()
						.eq(SysDictData::getStatus, "0")
						.orderByAsc(SysDictData::getDictSort));
	}
}
