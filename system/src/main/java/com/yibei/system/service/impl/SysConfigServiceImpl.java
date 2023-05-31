package com.yibei.system.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yibei.common.annotation.DataSource;
import com.yibei.common.constant.Constants;
import com.yibei.common.constant.UserConstants;
import com.yibei.common.core.mybatisplus.core.ServicePlusImpl;
import com.yibei.common.core.page.TableDataInfo;
import com.yibei.common.enums.DataSourceType;
import com.yibei.common.exception.ServiceException;
import com.yibei.common.utils.PageUtils;
import com.yibei.common.utils.RedisUtils;
import com.yibei.common.utils.StringUtils;
import com.yibei.system.domain.SysConfig;
import com.yibei.system.mapper.SysConfigMapper;
import com.yibei.system.service.ISysConfigService;
import com.yibei.yb.domain.YbStudyConfig;
import com.yibei.yb.service.IYbStudyConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

/**
 * 参数配置 服务层实现
 *
 * @author
 */
@Service
public class SysConfigServiceImpl extends ServicePlusImpl<SysConfigMapper, SysConfig, SysConfig> implements ISysConfigService {

	@Resource
	private IYbStudyConfigService iYbStudyConfigService;

	/**
	 * 项目启动时，初始化参数到缓存
	 */
	@PostConstruct
	public void init() {
		loadingConfigCache();
	}

	@Override
	public TableDataInfo<SysConfig> selectPageConfigList(SysConfig config) {
		Map<String, Object> params = config.getParams();
		LambdaQueryWrapper<SysConfig> lqw = new LambdaQueryWrapper<SysConfig>()
			.like(StringUtils.isNotBlank(config.getConfigName()), SysConfig::getConfigName, config.getConfigName())
			.eq(StringUtils.isNotBlank(config.getConfigType()), SysConfig::getConfigType, config.getConfigType())
			.like(StringUtils.isNotBlank(config.getConfigKey()), SysConfig::getConfigKey, config.getConfigKey())
			.apply(StringUtils.isNotEmpty(params.get("beginTime")),
				"date_format(create_time,'%y%m%d') >= date_format({0},'%y%m%d')",
				params.get("beginTime"))
			.apply(StringUtils.isNotEmpty(params.get("endTime")),
				"date_format(create_time,'%y%m%d') <= date_format({0},'%y%m%d')",
				params.get("endTime"));
		return PageUtils.buildDataInfo(page(PageUtils.buildPage(), lqw));
	}

	/**
	 * 查询参数配置信息
	 *
	 * @param configId 参数配置ID
	 * @return 参数配置信息
	 */
	@Override
	@DataSource(DataSourceType.MASTER)
	public SysConfig selectConfigById(Long configId) {
		return baseMapper.selectById(configId);
	}

	/**
	 * 根据键名查询参数配置信息
	 *
	 * @param configKey 参数key
	 * @return 参数键值
	 */
	@Override
	public String selectConfigByKey(String configKey) {
		String configValue = Convert.toStr(RedisUtils.getCacheObject(getCacheKey(configKey)));
		if (StringUtils.isNotEmpty(configValue)) {
			return configValue;
		}
		SysConfig retConfig = baseMapper.selectOne(new LambdaQueryWrapper<SysConfig>()
			.eq(SysConfig::getConfigKey, configKey));
		if (StringUtils.isNotNull(retConfig)) {
			RedisUtils.setCacheObject(getCacheKey(configKey), retConfig.getConfigValue());
			return retConfig.getConfigValue();
		}
		return StringUtils.EMPTY;
	}

	/**
	 * 获取验证码开关
	 *
	 * @return true开启，false关闭
	 */
	@Override
	public boolean selectCaptchaOnOff() {
		String captchaOnOff = selectConfigByKey("sys.account.captchaOnOff");
		if (StringUtils.isEmpty(captchaOnOff)) {
			return true;
		}
		return Convert.toBool(captchaOnOff);
	}

	/**
	 * 查询参数配置列表
	 *
	 * @param config 参数配置信息
	 * @return 参数配置集合
	 */
	@Override
	public List<SysConfig> selectConfigList(SysConfig config) {
		Map<String, Object> params = config.getParams();
		LambdaQueryWrapper<SysConfig> lqw = new LambdaQueryWrapper<SysConfig>()
			.like(StringUtils.isNotBlank(config.getConfigName()), SysConfig::getConfigName, config.getConfigName())
			.eq(StringUtils.isNotBlank(config.getConfigType()), SysConfig::getConfigType, config.getConfigType())
			.like(StringUtils.isNotBlank(config.getConfigKey()), SysConfig::getConfigKey, config.getConfigKey())
			.apply(StringUtils.isNotEmpty(params.get("beginTime")),
				"date_format(create_time,'%y%m%d') >= date_format({0},'%y%m%d')",
				params.get("beginTime"))
			.apply(StringUtils.isNotEmpty(params.get("endTime")),
				"date_format(create_time,'%y%m%d') <= date_format({0},'%y%m%d')",
				params.get("endTime"));
		return baseMapper.selectList(lqw);
	}

	/**
	 * 新增参数配置
	 *
	 * @param config 参数配置信息
	 * @return 结果
	 */
	@Override
	public int insertConfig(SysConfig config) {
		int row = baseMapper.insert(config);
		if (row > 0) {
			RedisUtils.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
		}
		return row;
	}

	/**
	 * 修改参数配置
	 *
	 * @param config 参数配置信息
	 * @return 结果
	 */
	@Override
	public int updateConfig(SysConfig config) {
		int row = baseMapper.updateById(config);
		if (row > 0) {
			RedisUtils.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
		}
		return row;
	}

	/**
	 * 批量删除参数信息
	 *
	 * @param configIds 需要删除的参数ID
	 * @return 结果
	 */
	@Override
	public void deleteConfigByIds(Long[] configIds) {
		for (Long configId : configIds) {
			SysConfig config = selectConfigById(configId);
			if (StringUtils.equals(UserConstants.YES, config.getConfigType())) {
				throw new ServiceException(String.format("内置参数【%1$s】不能删除 ", config.getConfigKey()));
			}
			RedisUtils.deleteObject(getCacheKey(config.getConfigKey()));
		}
		baseMapper.deleteBatchIds(Arrays.asList(configIds));
	}

	/**
	 * 加载参数缓存数据
	 */
	@Override
	public void loadingConfigCache() {
		List<SysConfig> configsList = selectConfigList(new SysConfig());
		for (SysConfig config : configsList) {
			RedisUtils.setCacheObject(getCacheKey(config.getConfigKey()), config.getConfigValue());
		}
	}

	/**
	 * 清空参数缓存数据
	 */
	@Override
	public void clearConfigCache() {
		Collection<String> keys = RedisUtils.keys(Constants.SYS_CONFIG_KEY + "*");
		RedisUtils.deleteObject(keys);
	}

	/**
	 * 重置参数缓存数据
	 */
	@Override
	public void resetConfigCache() {
		clearConfigCache();
		loadingConfigCache();
	}

	/**
	 * 校验参数键名是否唯一
	 *
	 * @param config 参数配置信息
	 * @return 结果
	 */
	@Override
	public String checkConfigKeyUnique(SysConfig config) {
		Long configId = StringUtils.isNull(config.getConfigId()) ? -1L : config.getConfigId();
		SysConfig info = baseMapper.selectOne(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getConfigKey, config.getConfigKey()));
		if (StringUtils.isNotNull(info) && info.getConfigId().longValue() != configId.longValue()) {
			return UserConstants.NOT_UNIQUE;
		}
		return UserConstants.UNIQUE;
	}

	@Override
	public Map<String, Integer> getStudyConfig(Long userId, Long teachingMaterialId) {

		//后台配置-复习倍率
		Integer ratio = Integer.valueOf(getOne(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getConfigKey,"study_review_ratio").last("LIMIT 1")).getConfigValue());
		//每天学习数
		Integer studyNum = 0;

		//优先获取用户设置
		YbStudyConfig studyConfig = iYbStudyConfigService.getOne(new LambdaQueryWrapper<YbStudyConfig>()
				.eq(YbStudyConfig::getTeachingMaterialId,teachingMaterialId)
				.eq(YbStudyConfig::getUserId,userId)
				.last("LIMIT 1"));
		if(studyConfig!=null){
			//用户在当前教材配置-每天学习数
			studyNum = studyConfig.getQuantity().intValue();
		}else{
			//后台配置-每天学习数
			studyNum = Integer.valueOf(getOne(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getConfigKey,"study_entry_num").last("LIMIT 1")).getConfigValue());
		}

		//每天复习数
		Integer review = studyNum.intValue() * ratio;

		Map<String, Integer> map = new HashMap<>(2);
		map.put("studyNum",studyNum);
		map.put("review",review);

		return map;
	}

	/**
	 * 设置cache key
	 *
	 * @param configKey 参数键
	 * @return 缓存键key
	 */
	private String getCacheKey(String configKey) {
		return Constants.SYS_CONFIG_KEY + configKey;
	}
}
