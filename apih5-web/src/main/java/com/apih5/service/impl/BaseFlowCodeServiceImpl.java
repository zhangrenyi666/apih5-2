package com.apih5.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.LoggerUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.BaseFlowCodeMapper;
import com.apih5.mybatis.pojo.BaseFlowCode;
import com.apih5.service.BaseFlowCodeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("baseFlowCodeService")
public class BaseFlowCodeServiceImpl implements BaseFlowCodeService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private BaseFlowCodeMapper baseFlowCodeMapper;

    @Override
    public ResponseEntity getBaseFlowCodeListByCondition(BaseFlowCode baseFlowCode) {
        if (baseFlowCode == null) {
            baseFlowCode = new BaseFlowCode();
        }
        // 分页查询
        PageHelper.startPage(baseFlowCode.getPage(),baseFlowCode.getLimit());
        // 获取数据
        List<BaseFlowCode> baseFlowCodeList = baseFlowCodeMapper.selectByBaseFlowCodeList(baseFlowCode);
        // 得到分页信息
        PageInfo<BaseFlowCode> p = new PageInfo<>(baseFlowCodeList);

        return repEntity.okList(baseFlowCodeList, p.getTotal());
    }

    @Override
    public ResponseEntity saveBaseFlowCode(BaseFlowCode baseFlowCode) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        baseFlowCode.setFlowCodeId(UuidUtil.generate());
        baseFlowCode.setCreateUserInfo(userKey, realName);
        int flag = baseFlowCodeMapper.insert(baseFlowCode);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", baseFlowCode);
        }
    }

    @Override
    public ResponseEntity updateBaseFlowCode(BaseFlowCode baseFlowCode) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        BaseFlowCode dbbaseFlowCode = baseFlowCodeMapper.selectByPrimaryKey(baseFlowCode.getFlowCodeId());
        if (dbbaseFlowCode != null && StrUtil.isNotEmpty(dbbaseFlowCode.getFlowCodeId())) {
           // 共通
           dbbaseFlowCode.setApih5FlowId(baseFlowCode.getApih5FlowId());
           dbbaseFlowCode.setApih5FlowName(baseFlowCode.getApih5FlowName());
           dbbaseFlowCode.setApih5NodeId(baseFlowCode.getApih5NodeId());
           dbbaseFlowCode.setApih5NodeName(baseFlowCode.getApih5NodeName());
           dbbaseFlowCode.setModifyUserInfo(userKey, realName);
           flag = baseFlowCodeMapper.updateByPrimaryKey(dbbaseFlowCode);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",baseFlowCode);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateBaseFlowCode(List<BaseFlowCode> baseFlowCodeList) {
        int flag = 0;
        if (baseFlowCodeList != null && baseFlowCodeList.size() > 0) {
           flag = baseFlowCodeMapper.batchDeleteUpdateBaseFlowCode(baseFlowCodeList);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",baseFlowCodeList);
        }
   }

	@Override
	public ResponseEntity baseFlowCodeImport(BaseFlowCode baseFlowCode) {
		if(baseFlowCode == null){
			baseFlowCode = new BaseFlowCode();
		}
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        try {  
//        	String file = "target//"+baseFlowCode.getXmlFileList().get(0).getRelativeUrl();
            //通过DocumentBuilder对象的parse方法返回一个Document对象  
//		    File f = new File("D://HorizonWorkflow(中交用印申请V8).xml"); 
        	String filePath = Apih5Properties.getFilePath()+baseFlowCode.getXmlFileList().get(0).getRelativeUrl();
		    File file = new File(filePath); 
		    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();   
		    DocumentBuilder builder = factory.newDocumentBuilder();   
		    Document doc = builder.parse(file);   
		    //获取流程Id
        	baseFlowCode.setApih5FlowId(doc.getElementsByTagName("FlowID").item(0).getFirstChild().getNodeValue());
        	//根据流程Id去表里查数据，如果有则全部删除。
        	List<BaseFlowCode> dbbaseFlowCode = baseFlowCodeMapper.selectByFlowId(baseFlowCode.getApih5FlowId());
        	if(dbbaseFlowCode.size()>0){
        		baseFlowCodeMapper.updateByFlowId(baseFlowCode);
        	}
        	//获取流程名称
        	baseFlowCode.setApih5FlowName(doc.getElementsByTagName("FlowName").item(0).getFirstChild().getNodeValue());
            //通过Document对象的getElementsByTagName()返根节点的一个list集合  
            NodeList booklist = doc.getElementsByTagName("node");  
            for(int i =0; i<booklist.getLength(); i++){
            	//设定一个字符串集合，用来装一个book的所有属性
            	List<String> str = new ArrayList<>();
                //循环遍历获取每一个book  
                Node book = booklist.item(i);  
                //通过Node对象的getAttributes()方法获取全的属性值  
                NamedNodeMap bookmap = book.getAttributes();  
                //循环遍每一个book的属性值  
                for(int j = 0; j<bookmap.getLength(); j++){  
                    Node node = bookmap.item(j);  
                    str.add(node.getNodeValue());
                } 
                if(str.get(0).contains("Node")){
                    baseFlowCode.setFlowCodeId(UuidUtil.generate());
                    baseFlowCode.setApih5NodeId(str.get(0));
                    baseFlowCode.setApih5NodeName(str.get(1)+"-"+str.get(0));
                    baseFlowCode.setCreateUserInfo(userKey, realName);
                    baseFlowCodeMapper.insert(baseFlowCode);
                }
                //解析book节点的子节点
                NodeList childlist = book.getChildNodes();  
                for(int t = 0; t<childlist.getLength(); t++){  
                    //区分出text类型的node以及element类型的node  
                    if(childlist.item(t).getNodeType() == Node.ELEMENT_NODE){  
                    }  
                }  
               
            }  
        } catch (Exception e) {  
        	LoggerUtils.printDebugLogger(logger, "-----2");
        	repEntity.layerMessage("no", "导入失败");
        }  
		return repEntity.ok("导入数据成功！");
	}

	@Override
	public ResponseEntity getFlowNameSelectList(BaseFlowCode baseFlowCode) {
		BaseFlowCode bean = new BaseFlowCode();
		bean.setApih5FlowName("请选择");
		// 获取数据
        List<BaseFlowCode> baseFlowCodeList = baseFlowCodeMapper.selectGroupByFlowIdList(baseFlowCode);
        if(baseFlowCodeList.size()>0){
//        	baseFlowCodeList.parallelStream().forEach((s)->{
        	for(BaseFlowCode code : baseFlowCodeList){
        		bean.setApih5NodeName("请选择");
        		List<BaseFlowCode> list = baseFlowCodeMapper.selectByFlowId(code.getApih5FlowId());
        		for(BaseFlowCode flow: list){
        			List<BaseFlowCode> codeList = baseFlowCodeMapper.selectByFlowId(code.getApih5FlowId());
        			flow.setBaseFlowCodeList1(codeList);
        		}
                if(list.size()>0){
                	list.add(0, bean);
                }else{
                	list.add(bean);	
                }
                code.setBaseFlowCodeList(list);
        	}
        	baseFlowCodeList.add(0, bean);	
//        	});
        }else{
        	baseFlowCodeList.add(bean);	
        }
        return repEntity.ok(baseFlowCodeList);
	}

	@Override
	public ResponseEntity getNodeNameSelectListByFlowName(BaseFlowCode baseFlowCode) {
		BaseFlowCode bean = new BaseFlowCode();
		bean.setApih5FlowName("请选择");
		// 获取数据
		List<BaseFlowCode> baseFlowCodeList = baseFlowCodeMapper.selectByFlowId(baseFlowCode.getApih5FlowId());
        if(baseFlowCodeList.size()>0){
        	baseFlowCodeList.add(0, bean);
        }else{
        	baseFlowCodeList.add(bean);	
        }

        return repEntity.ok(baseFlowCodeList);
	}
}
