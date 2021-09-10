package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.mybatis.dao.ZjLzehFileMapper;
import com.apih5.mybatis.pojo.ZjLzehFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjLzehVideoMapper;
import com.apih5.mybatis.pojo.ZjLzehVideo;
import com.apih5.service.ZjLzehVideoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjLzehVideoService")
public class ZjLzehVideoServiceImpl implements ZjLzehVideoService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjLzehVideoMapper zjLzehVideoMapper;

    @Autowired(required = true)
    private ZjLzehFileMapper zjLzehFileMapper;

    @Override
    public ResponseEntity getZjLzehVideoListByCondition(ZjLzehVideo zjLzehVideo) {
        if (zjLzehVideo == null) {
            zjLzehVideo = new ZjLzehVideo();
        }
        // 分页查询
        PageHelper.startPage(zjLzehVideo.getPage(),zjLzehVideo.getLimit());
        // 获取数据
        List<ZjLzehVideo> zjLzehVideoList = zjLzehVideoMapper.selectByZjLzehVideoList(zjLzehVideo);

        for (ZjLzehVideo zjLzehVideo2 : zjLzehVideoList) {
            ZjLzehFile file = new ZjLzehFile();
            file.setOtherId(zjLzehVideo2.getVideoId());
            List<ZjLzehFile> files = zjLzehFileMapper.selectByZjLzehFileList(file);
            zjLzehVideo2.setFileList(files);
        }


        // 得到分页信息
        PageInfo<ZjLzehVideo> p = new PageInfo<>(zjLzehVideoList);

        return repEntity.okList(zjLzehVideoList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjLzehVideoDetails(ZjLzehVideo zjLzehVideo) {
        if (zjLzehVideo == null) {
            zjLzehVideo = new ZjLzehVideo();
        }
        // 获取数据
        ZjLzehVideo dbZjLzehVideo = zjLzehVideoMapper.selectByPrimaryKey(zjLzehVideo.getVideoId());
        // 数据存在
        if (dbZjLzehVideo != null) {
            return repEntity.ok(dbZjLzehVideo);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjLzehVideo(ZjLzehVideo zjLzehVideo) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjLzehVideo.setVideoId(UuidUtil.generate());
        zjLzehVideo.setCreateUserInfo(userKey, realName);

        if(zjLzehVideo.getLargeScreenShow()==null){
            zjLzehVideo.setLargeScreenShow(0);
        }
        int flag = zjLzehVideoMapper.insert(zjLzehVideo);

        if(zjLzehVideo.getLargeScreenShow()!=null&&zjLzehVideo.getLargeScreenShow()==1){
            this.updateZjLzehVideoValue(zjLzehVideo);
        }

        if(zjLzehVideo.getFileList() != null && zjLzehVideo.getFileList().size() >0) {
            for (ZjLzehFile file : zjLzehVideo.getFileList()) {
                file.setUid(UuidUtil.generate());
                file.setOtherId(zjLzehVideo.getVideoId());
                file.setCreateUserInfo(userKey, realName);
                zjLzehFileMapper.insert(file);
            }
        }

        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjLzehVideo);
        }
    }

    @Override
    public ResponseEntity updateZjLzehVideo(ZjLzehVideo zjLzehVideo) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjLzehVideo dbzjLzehVideo = zjLzehVideoMapper.selectByPrimaryKey(zjLzehVideo.getVideoId());
        if (dbzjLzehVideo != null && StrUtil.isNotEmpty(dbzjLzehVideo.getVideoId())) {
           // 排序
           dbzjLzehVideo.setOrderFlag(zjLzehVideo.getOrderFlag());
           // 大屏展示
           dbzjLzehVideo.setLargeScreenShow(zjLzehVideo.getLargeScreenShow());
           // 共通
           dbzjLzehVideo.setModifyUserInfo(userKey, realName);
           flag = zjLzehVideoMapper.updateByPrimaryKey(dbzjLzehVideo);
        }


        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjLzehVideo);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjLzehVideo(List<ZjLzehVideo> zjLzehVideoList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjLzehVideoList != null && zjLzehVideoList.size() > 0) {
           ZjLzehVideo zjLzehVideo = new ZjLzehVideo();
           zjLzehVideo.setModifyUserInfo(userKey, realName);
           flag = zjLzehVideoMapper.batchDeleteUpdateZjLzehVideo(zjLzehVideoList, zjLzehVideo);
        }

        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjLzehVideoList);
        }
    }

    @Override
    public ResponseEntity getZjLzehVideo(ZjLzehVideo zjLzehVideo) {
        if (zjLzehVideo == null) {
            zjLzehVideo = new ZjLzehVideo();
        }
        // 获取数据
        ZjLzehVideo zjLzehVideo2 = zjLzehVideoMapper.selectByZjLzehVideo(zjLzehVideo);
        if(zjLzehVideo2!=null) {
            ZjLzehFile file = new ZjLzehFile();
            file.setOtherId(zjLzehVideo2.getVideoId());
            List<ZjLzehFile> files = zjLzehFileMapper.selectByZjLzehFileList(file);
            zjLzehVideo2.setFileList(files);
            return repEntity.ok(zjLzehVideo2);
        }else {
            return repEntity.layerMessage("no", "没有设定大屏展示");
        }
    }

    @Override
    public ResponseEntity updateZjLzehVideoValue(ZjLzehVideo zjLzehVideo) {
        if (zjLzehVideo == null) {
            return repEntity.layerMessage("no", "无数据");
        }
        int flag = 0;
        //先全变成0
        zjLzehVideoMapper.updateZjLzehVideoValue0();

        if(zjLzehVideo.getLargeScreenShow()!=0){
            //根据id变1
            flag = zjLzehVideoMapper.updateZjLzehVideoValue1(zjLzehVideo);
        }else {
            flag = 1;
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjLzehVideo);
        }
    }
}
