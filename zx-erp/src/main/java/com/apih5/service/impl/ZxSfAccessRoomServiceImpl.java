package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSfAccessRoomMapper;
import com.apih5.mybatis.pojo.ZxSfAccessRoom;
import com.apih5.service.ZxSfAccessRoomService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSfAccessRoomService")
public class ZxSfAccessRoomServiceImpl implements ZxSfAccessRoomService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSfAccessRoomMapper zxSfAccessRoomMapper;

    @Override
    public ResponseEntity getZxSfAccessRoomListByCondition(ZxSfAccessRoom zxSfAccessRoom) {
        if (zxSfAccessRoom == null) {
            zxSfAccessRoom = new ZxSfAccessRoom();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxSfAccessRoom.setCompanyId("");
        	zxSfAccessRoom.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxSfAccessRoom.setCompanyId(zxSfAccessRoom.getProjectId());
        	zxSfAccessRoom.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxSfAccessRoom.setProjectId(zxSfAccessRoom.getProjectId());
        }
        // 分页查询
        PageHelper.startPage(zxSfAccessRoom.getPage(),zxSfAccessRoom.getLimit());
        // 获取数据
        List<ZxSfAccessRoom> zxSfAccessRoomList = zxSfAccessRoomMapper.selectByZxSfAccessRoomList(zxSfAccessRoom);
        // 得到分页信息
        PageInfo<ZxSfAccessRoom> p = new PageInfo<>(zxSfAccessRoomList);

        return repEntity.okList(zxSfAccessRoomList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfAccessRoomDetail(ZxSfAccessRoom zxSfAccessRoom) {
        if (zxSfAccessRoom == null) {
            zxSfAccessRoom = new ZxSfAccessRoom();
        }
        // 获取数据
        ZxSfAccessRoom dbZxSfAccessRoom = zxSfAccessRoomMapper.selectByPrimaryKey(zxSfAccessRoom.getZxSfAccessRoomId());
        // 数据存在
        if (dbZxSfAccessRoom != null) {
            return repEntity.ok(dbZxSfAccessRoom);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSfAccessRoom(ZxSfAccessRoom zxSfAccessRoom) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSfAccessRoom.setZxSfAccessRoomId(UuidUtil.generate());
        zxSfAccessRoom.setCreateUserInfo(userKey, realName);
        int flag = zxSfAccessRoomMapper.insert(zxSfAccessRoom);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSfAccessRoom);
        }
    }

    @Override
    public ResponseEntity updateZxSfAccessRoom(ZxSfAccessRoom zxSfAccessRoom) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfAccessRoom dbZxSfAccessRoom = zxSfAccessRoomMapper.selectByPrimaryKey(zxSfAccessRoom.getZxSfAccessRoomId());
        if (dbZxSfAccessRoom != null && StrUtil.isNotEmpty(dbZxSfAccessRoom.getZxSfAccessRoomId())) {
           // 考核项目
           dbZxSfAccessRoom.setExamName(zxSfAccessRoom.getExamName());
           // 考核内容
           dbZxSfAccessRoom.setExamContext(zxSfAccessRoom.getExamContext());
           // 满分
           dbZxSfAccessRoom.setAllScore(zxSfAccessRoom.getAllScore());
           // 备注
           dbZxSfAccessRoom.setNotes(zxSfAccessRoom.getNotes());
           // 编辑时间
           dbZxSfAccessRoom.setEditTime(zxSfAccessRoom.getEditTime());
           // 排序
           dbZxSfAccessRoom.setOrderNo(zxSfAccessRoom.getOrderNo());
           // 所属机构（项目ID）
           dbZxSfAccessRoom.setProjectId(zxSfAccessRoom.getProjectId());
           // 项目名称
           dbZxSfAccessRoom.setProjectName(zxSfAccessRoom.getProjectName());
           // 所属公司ID
           dbZxSfAccessRoom.setCompanyId(zxSfAccessRoom.getCompanyId());
           // 所属公司Name
           dbZxSfAccessRoom.setCompanyName(zxSfAccessRoom.getCompanyName());
           // 备注
           dbZxSfAccessRoom.setRemarks(zxSfAccessRoom.getRemarks());
           // 排序
           dbZxSfAccessRoom.setSort(zxSfAccessRoom.getSort());
           // 共通
           dbZxSfAccessRoom.setModifyUserInfo(userKey, realName);
           flag = zxSfAccessRoomMapper.updateByPrimaryKey(dbZxSfAccessRoom);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSfAccessRoom);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSfAccessRoom(List<ZxSfAccessRoom> zxSfAccessRoomList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSfAccessRoomList != null && zxSfAccessRoomList.size() > 0) {
           ZxSfAccessRoom zxSfAccessRoom = new ZxSfAccessRoom();
           zxSfAccessRoom.setModifyUserInfo(userKey, realName);
           flag = zxSfAccessRoomMapper.batchDeleteUpdateZxSfAccessRoom(zxSfAccessRoomList, zxSfAccessRoom);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSfAccessRoomList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @Override
    public List<ZxSfAccessRoom> getZxSfAccessRoomInit() {

        // 获取数据
        List<ZxSfAccessRoom> zxSfAccessRoomList = zxSfAccessRoomMapper.selectZxSfAccessRoomInit();


        return zxSfAccessRoomList;
    }
}
