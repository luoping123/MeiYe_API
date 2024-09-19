package com.sct.meiye.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sct.meiye.entity.Beautician;
import com.sct.meiye.entity.dto.BeauticianVo;
import com.sct.meiye.service.BeauticianService;
import com.sct.meiye.mapper.BeauticianMapper;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 15811
* @description 针对表【beautician】的数据库操作Service实现
* @createDate 2022-04-20 20:23:27
*/
@Service
public class BeauticianServiceImpl extends ServiceImpl<BeauticianMapper, Beautician>
    implements BeauticianService{

    @Autowired
    private BeauticianMapper beauticianMapper;

    public List<BeauticianVo> getBeauticianListByTime(String beauticianTime){
        return beauticianMapper.getBeauticianListByTime(beauticianTime);
    }

}




