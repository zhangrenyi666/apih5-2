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
import com.apih5.mybatis.dao.ZxSfJuAccessRoomMapper;
import com.apih5.mybatis.pojo.ZxSfJuAccessRoom;
import com.apih5.service.ZxSfJuAccessRoomService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSfJuAccessRoomService")
public class ZxSfJuAccessRoomServiceImpl implements ZxSfJuAccessRoomService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSfJuAccessRoomMapper zxSfJuAccessRoomMapper;

    @Override
    public ResponseEntity getZxSfJuAccessRoomListByCondition(ZxSfJuAccessRoom zxSfJuAccessRoom) {
        if (zxSfJuAccessRoom == null) {
            zxSfJuAccessRoom = new ZxSfJuAccessRoom();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxSfJuAccessRoom.setCompanyId("");
        	zxSfJuAccessRoom.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxSfJuAccessRoom.setCompanyId(zxSfJuAccessRoom.getProjectId());
        	zxSfJuAccessRoom.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxSfJuAccessRoom.setProjectId(zxSfJuAccessRoom.getProjectId());
        }
        // 分页查询
        PageHelper.startPage(zxSfJuAccessRoom.getPage(),zxSfJuAccessRoom.getLimit());
        // 获取数据
        List<ZxSfJuAccessRoom> zxSfJuAccessRoomList = zxSfJuAccessRoomMapper.selectByZxSfJuAccessRoomList(zxSfJuAccessRoom);
        // 得到分页信息
        PageInfo<ZxSfJuAccessRoom> p = new PageInfo<>(zxSfJuAccessRoomList);

        return repEntity.okList(zxSfJuAccessRoomList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfJuAccessRoomDetail(ZxSfJuAccessRoom zxSfJuAccessRoom) {
        if (zxSfJuAccessRoom == null) {
            zxSfJuAccessRoom = new ZxSfJuAccessRoom();
        }
        // 获取数据
        ZxSfJuAccessRoom dbZxSfJuAccessRoom = zxSfJuAccessRoomMapper.selectByPrimaryKey(zxSfJuAccessRoom.getZxSfJuAccessRoomId());
        // 数据存在
        if (dbZxSfJuAccessRoom != null) {
            return repEntity.ok(dbZxSfJuAccessRoom);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSfJuAccessRoom(ZxSfJuAccessRoom zxSfJuAccessRoom) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSfJuAccessRoom.setZxSfJuAccessRoomId(UuidUtil.generate());
        zxSfJuAccessRoom.setCreateUserInfo(userKey, realName);
        int flag = zxSfJuAccessRoomMapper.insert(zxSfJuAccessRoom);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSfJuAccessRoom);
        }
    }

    @Override
    public ResponseEntity updateZxSfJuAccessRoom(ZxSfJuAccessRoom zxSfJuAccessRoom) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfJuAccessRoom dbZxSfJuAccessRoom = zxSfJuAccessRoomMapper.selectByPrimaryKey(zxSfJuAccessRoom.getZxSfJuAccessRoomId());
        if (dbZxSfJuAccessRoom != null && StrUtil.isNotEmpty(dbZxSfJuAccessRoom.getZxSfJuAccessRoomId())) {
           // 考核项目
           dbZxSfJuAccessRoom.setExamName(zxSfJuAccessRoom.getExamName());
           // 考核内容
           dbZxSfJuAccessRoom.setExamContext(zxSfJuAccessRoom.getExamContext());
           // 满分
           dbZxSfJuAccessRoom.setAllScore(zxSfJuAccessRoom.getAllScore());
           // 备注
           dbZxSfJuAccessRoom.setNotes(zxSfJuAccessRoom.getNotes());
           // 编辑时间
           dbZxSfJuAccessRoom.setEditTime(zxSfJuAccessRoom.getEditTime());
           // 排序
           dbZxSfJuAccessRoom.setOrderNo(zxSfJuAccessRoom.getOrderNo());
           // 所属机构（项目ID）
           dbZxSfJuAccessRoom.setProjectId(zxSfJuAccessRoom.getProjectId());
           // 项目名称
           dbZxSfJuAccessRoom.setProjectName(zxSfJuAccessRoom.getProjectName());
           // 所属公司ID
           dbZxSfJuAccessRoom.setCompanyId(zxSfJuAccessRoom.getCompanyId());
           // 所属公司名称
           dbZxSfJuAccessRoom.setCompanyName(zxSfJuAccessRoom.getCompanyName());
           // 备注
           dbZxSfJuAccessRoom.setRemarks(zxSfJuAccessRoom.getRemarks());
           // 排序
           dbZxSfJuAccessRoom.setSort(zxSfJuAccessRoom.getSort());
           // 共通
           dbZxSfJuAccessRoom.setModifyUserInfo(userKey, realName);
           flag = zxSfJuAccessRoomMapper.updateByPrimaryKey(dbZxSfJuAccessRoom);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSfJuAccessRoom);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSfJuAccessRoom(List<ZxSfJuAccessRoom> zxSfJuAccessRoomList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSfJuAccessRoomList != null && zxSfJuAccessRoomList.size() > 0) {
           ZxSfJuAccessRoom zxSfJuAccessRoom = new ZxSfJuAccessRoom();
           zxSfJuAccessRoom.setModifyUserInfo(userKey, realName);
           flag = zxSfJuAccessRoomMapper.batchDeleteUpdateZxSfJuAccessRoom(zxSfJuAccessRoomList, zxSfJuAccessRoom);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSfJuAccessRoomList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
