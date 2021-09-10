package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxCtWorkToMUMapper;
import com.apih5.mybatis.pojo.ZxCtWorkToMU;
import com.apih5.service.ZxCtWorkToMUService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxCtWorkToMUService")
public class ZxCtWorkToMUServiceImpl implements ZxCtWorkToMUService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtWorkToMUMapper zxCtWorkToMUMapper;

    @Override
    public ResponseEntity getZxCtWorkToMUListByCondition(ZxCtWorkToMU zxCtWorkToMU) {
        if (zxCtWorkToMU == null) {
            zxCtWorkToMU = new ZxCtWorkToMU();
        }
        // 分页查询
        PageHelper.startPage(zxCtWorkToMU.getPage(),zxCtWorkToMU.getLimit());
        // 获取数据
        List<ZxCtWorkToMU> zxCtWorkToMUList = zxCtWorkToMUMapper.selectByZxCtWorkToMUList(zxCtWorkToMU);
        // 得到分页信息
        PageInfo<ZxCtWorkToMU> p = new PageInfo<>(zxCtWorkToMUList);

        return repEntity.okList(zxCtWorkToMUList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtWorkToMUDetail(ZxCtWorkToMU zxCtWorkToMU) {
        if (zxCtWorkToMU == null) {
            zxCtWorkToMU = new ZxCtWorkToMU();
        }
        // 获取数据
        ZxCtWorkToMU dbZxCtWorkToMU = zxCtWorkToMUMapper.selectByPrimaryKey(zxCtWorkToMU.getId());
        // 数据存在
        if (dbZxCtWorkToMU != null) {
            return repEntity.ok(dbZxCtWorkToMU);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtWorkToMU(ZxCtWorkToMU zxCtWorkToMU) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtWorkToMU.setId(UuidUtil.generate());
        zxCtWorkToMU.setCreateUserInfo(userKey, realName);
        int flag = zxCtWorkToMUMapper.insert(zxCtWorkToMU);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtWorkToMU);
        }
    }

    @Override
    public ResponseEntity updateZxCtWorkToMU(ZxCtWorkToMU zxCtWorkToMU) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtWorkToMU dbZxCtWorkToMU = zxCtWorkToMUMapper.selectByPrimaryKey(zxCtWorkToMU.getId());
        if (dbZxCtWorkToMU != null && StrUtil.isNotEmpty(dbZxCtWorkToMU.getId())) {
           // 项目机构
           dbZxCtWorkToMU.setOrgID(zxCtWorkToMU.getOrgID());
           // 管理单元
           dbZxCtWorkToMU.setMuID(zxCtWorkToMU.getMuID());
           // 清单书
           dbZxCtWorkToMU.setWorkBookID(zxCtWorkToMU.getWorkBookID());
           // 工程量清单
           dbZxCtWorkToMU.setWorkID(zxCtWorkToMU.getWorkID());
           // 当前清单已经挂到管理单元上的总接量
           dbZxCtWorkToMU.setTotalLinkQuantity(zxCtWorkToMU.getTotalLinkQuantity());
           // 挂接数量
           dbZxCtWorkToMU.setLinkQuantity(zxCtWorkToMU.getLinkQuantity());
           // 挂接单价
           dbZxCtWorkToMU.setLinkPrice(zxCtWorkToMU.getLinkPrice());
           // 原挂接数量
           dbZxCtWorkToMU.setOriQuantity(zxCtWorkToMU.getOriQuantity());
           // 原挂接单价
           dbZxCtWorkToMU.setOriPrice(zxCtWorkToMU.getOriPrice());
           // 预计变更后数量
           dbZxCtWorkToMU.setExpectChangeQty(zxCtWorkToMU.getExpectChangeQty());
           // 预计变更后单价
           dbZxCtWorkToMU.setExpectChangePrice(zxCtWorkToMU.getExpectChangePrice());
           // 备注
           dbZxCtWorkToMU.setRemarks(zxCtWorkToMU.getRemarks());
           // 排序
           dbZxCtWorkToMU.setSort(zxCtWorkToMU.getSort());
           // 共通
           dbZxCtWorkToMU.setModifyUserInfo(userKey, realName);
           flag = zxCtWorkToMUMapper.updateByPrimaryKey(dbZxCtWorkToMU);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtWorkToMU);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtWorkToMU(List<ZxCtWorkToMU> zxCtWorkToMUList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtWorkToMUList != null && zxCtWorkToMUList.size() > 0) {
           ZxCtWorkToMU zxCtWorkToMU = new ZxCtWorkToMU();
           zxCtWorkToMU.setModifyUserInfo(userKey, realName);
           flag = zxCtWorkToMUMapper.batchDeleteUpdateZxCtWorkToMU(zxCtWorkToMUList, zxCtWorkToMU);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtWorkToMUList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

	@Override
	public ResponseEntity addAllWorkToMUByOrgID(ZxCtWorkToMU zxCtWorkToMU) {
//		OrgBO orgBO = (OrgBO)iOrgBiz.findByPk(OrgBO.class,orgID);
		// 业主承包的清单才可以取到机构
//		if(orgBO != null) {
//			IQueryFilter worksFilter = getQueryFilter(WorkBO.class);
//			worksFilter.addEQCond("orgID",orgID);
//			worksFilter.addEQCond("isLeaf","1");
//			List worksList = findObjectList(worksFilter);
//			Iterator it = worksList.iterator();
//			IManageUnitBiz iService = (IManageUnitBiz) SpringBeanTools.getBean("ieccManageUnitBiz");
//			String muID = iService.doDefaultMuID(orgID);
//			SimpleManageUnitBO manageUnitBO = (SimpleManageUnitBO) iService.findByPk(SimpleManageUnitBO.class, muID);
//			if (manageUnitBO.getIsLeaf() == MUConstant.NOT_LEAF) {
//				throw new IEMSRollBackException("新增类型清单不能选择叶子节点管理单元");
//			}
//			while(it.hasNext()){
//				WorkBO workBO = (WorkBO)it.next();
//				IWorkToMUBiz iWorkToMUBiz = (IWorkToMUBiz) SpringBeanTools.getBean("ieccWorkToMUBiz");
//				WorkToMUBO linkBO = new WorkToMUBO();
//				linkBO.setID(PKCreator.getPrimaryKey());
//				SimpleWorkBO work = new SimpleWorkBO();
//				work.setID(workBO.getID());
//				linkBO.setWorkBO(work);
//				linkBO.setWorkID(work.getID());// add by xiongyy on 2008/08/06 to fix
//				// validate
//				linkBO.setManageUnitBO(manageUnitBO);
//				linkBO.setMuID(manageUnitBO.getID());// add by xiongyy on 2008/08/06
//				// to fix validate
//				linkBO.setOrgID(workBO.getOrgID());
//				linkBO.setLinkQuantity(workBO.getQuantity());
//				linkBO.setLinkPrice(workBO.getPrice());
//				linkBO.setWorkBookID(workBO.getWorkBookID());
//				iWorkToMUBiz.addObject(linkBO);
//			}
//		}
		return repEntity.ok("成功！");
	}
}
