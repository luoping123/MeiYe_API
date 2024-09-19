package com.sct.meiye.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sct.meiye.entity.Video;
import com.sct.meiye.service.VideoService;
import com.sct.meiye.mapper.VideoMapper;
import org.springframework.stereotype.Service;

/**
* @author 15811
* @description 针对表【video】的数据库操作Service实现
* @createDate 2022-04-23 23:18:40
*/
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video>
    implements VideoService{

}




