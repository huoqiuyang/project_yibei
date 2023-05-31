package com.yibei.system.service.impl;

import com.yibei.common.core.domain.entity.SysDictData;
import com.yibei.common.core.mybatisplus.core.ServicePlusImpl;
import com.yibei.common.core.page.TableDataInfo;
import com.yibei.common.utils.DictUtils;
import com.yibei.common.utils.PageUtils;
import com.yibei.common.utils.StringUtils;
import com.yibei.system.mapper.SysDictDataMapper;
import com.yibei.system.service.ISysDictDataService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字典 业务层处理
 *
 * @author
 */
@Service
public class SysDictDataServiceImpl extends ServicePlusImpl<SysDictDataMapper, SysDictData, SysDictData> implements ISysDictDataService {

	@Override
	@Cacheable(cacheNames="sysCache",key="'dictDataListAll'")
	public List<SysDictData> listAll() {
		return list();
	}

	@Override
	public TableDataInfo<SysDictData> selectPageDictDataList(SysDictData dictData) {
		LambdaQueryWrapper<SysDictData> lqw = new LambdaQueryWrapper<SysDictData>()
			.eq(StringUtils.isNotBlank(dictData.getDictType()), SysDictData::getDictType, dictData.getDictType())
			.like(StringUtils.isNotBlank(dictData.getDictLabel()), SysDictData::getDictLabel, dictData.getDictLabel())
			.eq(StringUtils.isNotBlank(dictData.getStatus()), SysDictData::getStatus, dictData.getStatus())
			.orderByAsc(SysDictData::getDictSort);
		return PageUtils.buildDataInfo(page(PageUtils.buildPage(), lqw));
	}

	/**
	 * 根据条件分页查询字典数据
	 *
	 * @param dictData 字典数据信息
	 * @return 字典数据集合信息
	 */
	@Override
	public List<SysDictData> selectDictDataList(SysDictData dictData) {
		return list(new LambdaQueryWrapper<SysDictData>()
			.eq(StringUtils.isNotBlank(dictData.getDictType()), SysDictData::getDictType, dictData.getDictType())
			.like(StringUtils.isNotBlank(dictData.getDictLabel()), SysDictData::getDictLabel, dictData.getDictLabel())
			.eq(StringUtils.isNotBlank(dictData.getStatus()), SysDictData::getStatus, dictData.getStatus())
			.orderByAsc(SysDictData::getDictSort));
	}

	/**
	 * 根据字典类型和字典键值查询字典数据信息
	 *
	 * @param dictType  字典类型
	 * @param dictValue 字典键值
	 * @return 字典标签
	 */
	@Override
	public String selectDictLabel(String dictType, String dictValue) {
		return getOne(new LambdaQueryWrapper<SysDictData>()
			.select(SysDictData::getDictLabel)
			.eq(SysDictData::getDictType, dictType)
			.eq(SysDictData::getDictValue, dictValue))
			.getDictLabel();
	}

	/**
	 * 根据字典数据ID查询信息
	 *
	 * @param dictCode 字典数据ID
	 * @return 字典数据
	 */
	@Override
	public SysDictData selectDictDataById(Long dictCode) {
		return getById(dictCode);
	}

	/**
	 * 批量删除字典数据信息
	 *
	 * @param dictCodes 需要删除的字典数据ID
	 * @return 结果
	 */
	@Override
	public void deleteDictDataByIds(Long[] dictCodes) {
		for (Long dictCode : dictCodes) {
			SysDictData data = selectDictDataById(dictCode);
            removeById(dictCode);
			List<SysDictData> dictDatas = baseMapper.selectDictDataByType(data.getDictType());
			DictUtils.setDictCache(data.getDictType(), dictDatas);
		}
	}

	/**
	 * 新增保存字典数据信息
	 *
	 * @param data 字典数据信息
	 * @return 结果
	 */
	@Override
	public int insertDictData(SysDictData data) {
		int row = baseMapper.insert(data);
		if (row > 0) {
			List<SysDictData> dictDatas = baseMapper.selectDictDataByType(data.getDictType());
			DictUtils.setDictCache(data.getDictType(), dictDatas);
		}
		return row;
	}

	/**
	 * 修改保存字典数据信息
	 *
	 * @param data 字典数据信息
	 * @return 结果
	 */
	@Override
	public int updateDictData(SysDictData data) {
		int row = baseMapper.updateById(data);
		if (row > 0) {
			List<SysDictData> dictDatas = baseMapper.selectDictDataByType(data.getDictType());
			DictUtils.setDictCache(data.getDictType(), dictDatas);
		}
		return row;
	}
}
