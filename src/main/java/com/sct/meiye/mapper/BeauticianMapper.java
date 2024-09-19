package com.sct.meiye.mapper;

import com.sct.meiye.entity.Beautician;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sct.meiye.entity.dto.BeauticianVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 15811
* @description 针对表【beautician】的数据库操作Mapper
* @createDate 2022-04-20 20:23:26
* @Entity com.sct.meiye.entity.Beautician
*/
public interface BeauticianMapper extends BaseMapper<Beautician> {

    @Select("select b.*,bt.* " +
            "from beautician b " +
            "inner join beautician_time bt " +
            "on b.id=bt.beautician_id " +
            "where bt.beautician_time=#{beauticianTime} " +
            "and number >0")
    List<BeauticianVo> getBeauticianListByTime(String beauticianTime);
}




