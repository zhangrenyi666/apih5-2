package com.apih5.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.CheckOnlyMapper;
import com.apih5.mybatis.pojo.CheckOnly;
import com.apih5.service.CheckOnlyService;

@Service("checkOnlyService")
public class CheckOnlyServiceImpl implements CheckOnlyService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private CheckOnlyMapper checkOnlyMapper;
    @Override
    public ResponseEntity checkOnlyByObj(CheckOnly checkOnly) {

        boolean exist = false;
        String objName = checkOnly.getObjName();
//        try {
//            TableName table = Class.forName(objName).getAnnotation(TableName.class);
//            if (table != null) {
//                String tableName = table.value();
//                checkOnly.setTableName(tableName);
//                String condStrPack = " 1=1 ";
//                if(!Utils.isBlank(checkOnly.getCondStr())){
//                    String condStr = checkOnly.getCondStr();
//                    String[] condStrArr =  condStr.split(";");
//                    for(int i=0;i<condStrArr.length;i++){
//                        String s = condStrArr[i];
//                        String[] ss = s.split(":");
//                        if(ss.length==3){
//                            condStrPack = condStrPack +" and "+ss[0]+ss[1]+"'"+ss[2]+"'";
//                        }
//                    }
//                }
//                checkOnly.setCondStrPack(condStrPack);
//                Integer existCount = checkOnlyMapper.checkOnlyByObj(checkOnly);
//                if(existCount>0){
//                    exist = true;
//                }
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

        if(exist){
            repEntity.setSuccess(false);
            repEntity.setCode("1111");
            repEntity.setMessage(checkOnly.getMessage());
            return repEntity;
        }else{
            Map map = new HashMap();
            map.put("msg","唯一性检验验证通过");
            return repEntity.ok(map);
        }
    }

    /**
     * checkOnly中不含对象值
     * @param checkOnly
     * @param object
     * @return
     */
    @Override
    public ResponseEntity checkOnlyForAspect(CheckOnly checkOnly,Object object) {

        boolean exist = false;
        String objName = checkOnly.getObjName();
//        try {
//            TableName table = null;
//            if(StrUtil.isEmpty(objName)){
//                Class<?> objectClass = object.getClass();
//                table = objectClass.getAnnotation(TableName.class);
//            }else{
//                table = Class.forName(objName).getAnnotation(TableName.class);
//            }
//            if (table != null) {
//                String tableName = table.value();
//                checkOnly.setTableName(tableName);
//                String condStrPack = " 1=1 ";
//                if(!Utils.isBlank(checkOnly.getCondStr())){
//                    String condStr = checkOnly.getCondStr();
//                    String[] condStrArr =  condStr.split(";");
//                    for(int i=0;i<condStrArr.length;i++){
//                        String s = condStrArr[i];
//                        String[] ss = s.split(":");
//                        if(ss.length==2){
//                            String fieldValue = String.valueOf(ReflectUtil.getFieldValue(object,ss[0]));
//                            condStrPack = condStrPack +" and "+ss[0]+ss[1]+"'"+fieldValue+"'";
//                        }
//                    }
//                }
//                checkOnly.setCondStrPack(condStrPack);
//                Integer existCount = checkOnlyMapper.checkOnlyByObj(checkOnly);
//                if(existCount>0){
//                    exist = true;
//                }
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

        if(exist){
            repEntity.setSuccess(false);
            repEntity.setCode("1111");
            repEntity.setMessage(checkOnly.getMessage());
            return repEntity;
        }else{
            Map map = new HashMap();
            map.put("msg","唯一性检验验证通过");
            return repEntity.ok(map);
        }
    }
}
