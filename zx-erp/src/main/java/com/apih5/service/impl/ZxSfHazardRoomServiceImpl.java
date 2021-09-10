package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxSfHazardRoomAttMapper;
import com.apih5.mybatis.dao.ZxSfHazardRoomMapper;
import com.apih5.mybatis.pojo.ZxCrProjectEvaluation;
import com.apih5.mybatis.pojo.ZxCrProjectEvaluationScore;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.mybatis.pojo.ZxSfHazardRoom;
import com.apih5.mybatis.pojo.ZxSfHazardRoomAtt;
import com.apih5.service.ZxSfHazardRoomAttService;
import com.apih5.service.ZxSfHazardRoomService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxSfHazardRoomService")
public class ZxSfHazardRoomServiceImpl implements ZxSfHazardRoomService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSfHazardRoomMapper zxSfHazardRoomMapper;
    
    @Autowired(required = true)
    private ZxSfHazardRoomAttMapper zxSfHazardRoomAttMapper;
    
    @Override
    public ResponseEntity getZxSfHazardRoomListByCondition(ZxSfHazardRoom zxSfHazardRoom) {
        if (zxSfHazardRoom == null) {
            zxSfHazardRoom = new ZxSfHazardRoom();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxSfHazardRoom.setCompanyId("");
        	zxSfHazardRoom.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxSfHazardRoom.setCompanyId(zxSfHazardRoom.getProjectId());
        	zxSfHazardRoom.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxSfHazardRoom.setProjectId(zxSfHazardRoom.getProjectId());
        }
        // 分页查询
        PageHelper.startPage(zxSfHazardRoom.getPage(),zxSfHazardRoom.getLimit());
        // 获取数据
        List<ZxSfHazardRoom> zxSfHazardRoomList = zxSfHazardRoomMapper.selectByZxSfHazardRoomList(zxSfHazardRoom);
        
        // 查询重大危险源（项目）
     		for (ZxSfHazardRoom ZxSfHazardRoom1 : zxSfHazardRoomList) {
     			ZxSfHazardRoomAtt zxSfHazardRoomAtt = new ZxSfHazardRoomAtt();
     			zxSfHazardRoomAtt.setParentID(ZxSfHazardRoom1.getZxSfHazardRoomId());
     			List<ZxSfHazardRoomAtt> zxSfHazardRoomAttListAll = zxSfHazardRoomAttMapper.selectByZxSfHazardRoomAttList(zxSfHazardRoomAtt);
 
     			ZxSfHazardRoom1.setZxSfHazardRoomAttList(zxSfHazardRoomAttListAll);
     		}
        // 得到分页信息
        PageInfo<ZxSfHazardRoom> p = new PageInfo<>(zxSfHazardRoomList);

        return repEntity.okList(zxSfHazardRoomList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfHazardRoomDetail(ZxSfHazardRoom zxSfHazardRoom) {
        if (zxSfHazardRoom == null) {
            zxSfHazardRoom = new ZxSfHazardRoom();
        }
        // 获取数据
        ZxSfHazardRoom dbZxSfHazardRoom = zxSfHazardRoomMapper.selectByPrimaryKey(zxSfHazardRoom.getZxSfHazardRoomId());
        // 数据存在
        if (dbZxSfHazardRoom != null) {
            return repEntity.ok(dbZxSfHazardRoom);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSfHazardRoom(ZxSfHazardRoom zxSfHazardRoom) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSfHazardRoom.setZxSfHazardRoomId(UuidUtil.generate());
        zxSfHazardRoom.setCreateUserInfo(userKey, realName);
        int flag = zxSfHazardRoomMapper.insert(zxSfHazardRoom);
        // 新增重大危险源（项目）
        List<ZxSfHazardRoomAtt> zxSfHazardRoomAttList = zxSfHazardRoom.getZxSfHazardRoomAttList();
		if (zxSfHazardRoomAttList != null && zxSfHazardRoomAttList.size() > 0) {
			for (ZxSfHazardRoomAtt zxSfHazardRoomAtt : zxSfHazardRoomAttList) {
				zxSfHazardRoomAtt.setParentID(zxSfHazardRoom.getZxSfHazardRoomId());
				zxSfHazardRoomAtt.setZxSfHazardRoomAttId((UuidUtil.generate()));
				zxSfHazardRoomAtt.setCreateUserInfo(userKey, realName);
				zxSfHazardRoomAttMapper.insert(zxSfHazardRoomAtt);
			}
		}

        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSfHazardRoom);
        }
    }

    @Override
    public ResponseEntity updateZxSfHazardRoom(ZxSfHazardRoom zxSfHazardRoom) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfHazardRoom dbZxSfHazardRoom = zxSfHazardRoomMapper.selectByPrimaryKey(zxSfHazardRoom.getZxSfHazardRoomId());
        if (dbZxSfHazardRoom != null && StrUtil.isNotEmpty(dbZxSfHazardRoom.getZxSfHazardRoomId())) {
           // 机构名称
           dbZxSfHazardRoom.setOrgName(zxSfHazardRoom.getOrgName());
           // 机构ID
           dbZxSfHazardRoom.setOrgID(zxSfHazardRoom.getOrgID());
           // 编辑时间
           dbZxSfHazardRoom.setEditTime(zxSfHazardRoom.getEditTime());
           // 名称
           dbZxSfHazardRoom.setRoomName(zxSfHazardRoom.getRoomName());
           // 填报日期
           dbZxSfHazardRoom.setCreateDate(zxSfHazardRoom.getCreateDate());
           // 填报人
           dbZxSfHazardRoom.setCreator(zxSfHazardRoom.getCreator());
           // 备注
           dbZxSfHazardRoom.setNotes(zxSfHazardRoom.getNotes());
           // 所属机构（项目ID）
           dbZxSfHazardRoom.setProjectId(zxSfHazardRoom.getProjectId());
           // 项目名称
           dbZxSfHazardRoom.setProjectName(zxSfHazardRoom.getProjectName());
           // 所属公司ID
           dbZxSfHazardRoom.setCompanyId(zxSfHazardRoom.getCompanyId());
           // 所属公司名称
           dbZxSfHazardRoom.setCompanyName(zxSfHazardRoom.getCompanyName());
           // 备注
           dbZxSfHazardRoom.setRemarks(zxSfHazardRoom.getRemarks());
           // 排序
           dbZxSfHazardRoom.setSort(zxSfHazardRoom.getSort());
           // 共通
           dbZxSfHazardRoom.setModifyUserInfo(userKey, realName);
           flag = zxSfHazardRoomMapper.updateByPrimaryKey(dbZxSfHazardRoom);
           
        // 修改再新增(重大危险源（明细）)
           ZxSfHazardRoomAtt zxSfHazardRoomAttSelect = new ZxSfHazardRoomAtt();
           zxSfHazardRoomAttSelect.setParentID(zxSfHazardRoom.getZxSfHazardRoomId());
			List<ZxSfHazardRoomAtt> zxSfHazardRoomAtt = zxSfHazardRoomAttMapper.selectByZxSfHazardRoomAttList(zxSfHazardRoomAttSelect);
			if (zxSfHazardRoomAtt != null && zxSfHazardRoomAtt.size() > 0) {
				zxSfHazardRoomAttSelect.setModifyUserInfo(userKey, realName);
				zxSfHazardRoomAttMapper.batchDeleteUpdateZxSfHazardRoomAtt(zxSfHazardRoomAtt, zxSfHazardRoomAttSelect);
			}
			List<ZxSfHazardRoomAtt> zxSfHazardRoomAttListAll = zxSfHazardRoom.getZxSfHazardRoomAttList();
			if (zxSfHazardRoomAttListAll != null && zxSfHazardRoomAttListAll.size() > 0) {
				for (ZxSfHazardRoomAtt zxSfHazardRoomAtt1 : zxSfHazardRoomAttListAll) {
					zxSfHazardRoomAtt1.setParentID(zxSfHazardRoom.getZxSfHazardRoomId());
					zxSfHazardRoomAtt1.setZxSfHazardRoomAttId(UuidUtil.generate());
					zxSfHazardRoomAtt1.setCreateUserInfo(userKey, realName);
					zxSfHazardRoomAttMapper.insert(zxSfHazardRoomAtt1);
				}
			}
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSfHazardRoom);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSfHazardRoom(List<ZxSfHazardRoom> zxSfHazardRoomList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSfHazardRoomList != null && zxSfHazardRoomList.size() > 0) {
        	for(ZxSfHazardRoom zxSfHazardRoom1 : zxSfHazardRoomList) {
        		 ZxSfHazardRoomAtt zxSfHazardRoomAttSelect = new ZxSfHazardRoomAtt();
                 zxSfHazardRoomAttSelect.setParentID(zxSfHazardRoom1.getZxSfHazardRoomId());
      			List<ZxSfHazardRoomAtt> zxSfHazardRoomAtt = zxSfHazardRoomAttMapper.selectByZxSfHazardRoomAttList(zxSfHazardRoomAttSelect);
      			if (zxSfHazardRoomAtt != null && zxSfHazardRoomAtt.size() > 0) {
      				zxSfHazardRoomAttSelect.setModifyUserInfo(userKey, realName);
      				zxSfHazardRoomAttMapper.batchDeleteUpdateZxSfHazardRoomAtt(zxSfHazardRoomAtt, zxSfHazardRoomAttSelect);
      			}
        		
        		 ZxSfHazardRoom zxSfHazardRoom = new ZxSfHazardRoom();
                 zxSfHazardRoom.setModifyUserInfo(userKey, realName);
                 flag = zxSfHazardRoomMapper.batchDeleteUpdateZxSfHazardRoom(zxSfHazardRoomList, zxSfHazardRoom);
        	}
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSfHazardRoomList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
