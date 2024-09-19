package com.sct.meiye.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * @TableName video
 */
@TableName(value ="video")
@Data
@Accessors(chain = true)
public class Video implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 每个视频独有id，必须为字符串类型
     */
    private String videoId;

    /**
     * 视频拥有者名称
     */
    private String username;

    /**
     * 头像地址
     */
    private String href;

    /**
     * 第一行标题
     */
    private String title;

    /**
     * 第二行内容
     */
    private String msg;

    /**
     * 初始状态标志（不改）
     */
    private String state;

    /**
     * 是否是自己喜欢，0不喜欢，1喜欢。默认不喜欢(添加属性映射为like)
     */
    private Boolean isLike;

    /**
     * 喜欢数量
     */
    private Integer likeN;

    /**
     * 评论数
     */
    private Integer smsN;

    /**
     * 视频链接
     */
    private String src;

    /**
     * 播放视频的数量
     */
    private Integer playnumber;

    /**
     * 评论
     */
    private String pinlun;

    /**
     * 播放（默认这个即可）
     */
    private Boolean playing;

    /**
     * 是否显示封面（默认这个即可）
     */
    private Boolean isshowimage;

    /**
     * 是否显示进度条（默认这个即可）
     */
    private Boolean isshowprogressbartime;

    /**
     * 是否播放音频（默认这个即可）
     */
    private Boolean isplay;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}