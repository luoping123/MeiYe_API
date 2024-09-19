package com.sct.meiye.service;

import com.sct.meiye.entity.Beautician;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sct.meiye.entity.dto.BeauticianVo;

import java.util.List;

/**
* @author 15811
* @description 针对表【beautician】的数据库操作Service
* @createDate 2022-04-20 20:23:27
*/
public interface BeauticianService extends IService<Beautician> {

    List<BeauticianVo> getBeauticianListByTime(String beauticianTime);
}
