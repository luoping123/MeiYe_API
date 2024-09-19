package com.sct.meiye;

import com.sct.meiye.entity.Goods;
import com.sct.meiye.entity.GoodsCate;
import com.sct.meiye.entity.Video;
import com.sct.meiye.service.GoodsCateService;
import com.sct.meiye.service.GoodsService;
import com.sct.meiye.service.VideoService;
import com.sct.meiye.util.RandomIdUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class VideoTest {

    @Autowired
    private VideoService videoService;

    /**
     * 加入初始数据
     */
    @Test
    public void insertVideo(){
        Random random = new Random();
        List<Video> videoList=new ArrayList<>();
        for(int i=1;i<=20;i++){//制造数据
            Integer r = random.nextInt(900000000) + 100000000;
            Video video=new Video();
            video.setVideoId(r+"");//id
            video.setUsername("智慧美业");//视频拥有者名称
            //头像
            video.setHref("http://cn.shichengtai.xyz/beautician/beautician3.png");
            video.setTitle("美业分享视频"+i);//第一行标题
            video.setMsg("每日美容视频分享，店内有免费体验项目，欢迎大家体验~");//第二行内容
            video.setLikeN(i*3);//喜欢数量
            video.setSmsN(i*2);//评论数
            video.setSrc("http://cn.shichengtai.xyz/video/video"+ i +".mp4");//视频链接
            video.setPlaynumber(i*10);//播放量
            //加入列表
            videoList.add(video);
        }
        //批量保存
        videoService.saveBatch(videoList);
    }

}
