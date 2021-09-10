package com.apih5.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzPolicyLocal;
import com.apih5.service.ZjTzPolicyLocalService;

@RestController
public class ZjTzPolicyLocalController {

    @Autowired(required = true)
    private ZjTzPolicyLocalService zjTzPolicyLocalService;

    @ApiOperation(value="查询宏观政策地方", notes="查询宏观政策地方")
    @ApiImplicitParam(name = "zjTzPolicyLocal", value = "宏观政策地方entity", dataType = "ZjTzPolicyLocal")
    @RequireToken
    @PostMapping("/getZjTzPolicyLocalList")
    public ResponseEntity getZjTzPolicyLocalList(@RequestBody(required = false) ZjTzPolicyLocal zjTzPolicyLocal) {
        return zjTzPolicyLocalService.getZjTzPolicyLocalListByCondition(zjTzPolicyLocal);
    }

    @ApiOperation(value="查询详情宏观政策地方", notes="查询详情宏观政策地方")
    @ApiImplicitParam(name = "zjTzPolicyLocal", value = "宏观政策地方entity", dataType = "ZjTzPolicyLocal")
    @RequireToken
    @PostMapping("/getZjTzPolicyLocalDetails")
    public ResponseEntity getZjTzPolicyLocalDetails(@RequestBody(required = false) ZjTzPolicyLocal zjTzPolicyLocal) {
        return zjTzPolicyLocalService.getZjTzPolicyLocalDetails(zjTzPolicyLocal);
    }

    @ApiOperation(value="新增宏观政策地方", notes="新增宏观政策地方")
    @ApiImplicitParam(name = "zjTzPolicyLocal", value = "宏观政策地方entity", dataType = "ZjTzPolicyLocal")
    @RequireToken
    @PostMapping("/addZjTzPolicyLocal")
    public ResponseEntity addZjTzPolicyLocal(@RequestBody(required = false) ZjTzPolicyLocal zjTzPolicyLocal) {
        return zjTzPolicyLocalService.saveZjTzPolicyLocal(zjTzPolicyLocal);
    }

    @ApiOperation(value="更新宏观政策地方", notes="更新宏观政策地方")
    @ApiImplicitParam(name = "zjTzPolicyLocal", value = "宏观政策地方entity", dataType = "ZjTzPolicyLocal")
    @RequireToken
    @PostMapping("/updateZjTzPolicyLocal")
    public ResponseEntity updateZjTzPolicyLocal(@RequestBody(required = false) ZjTzPolicyLocal zjTzPolicyLocal) {
        return zjTzPolicyLocalService.updateZjTzPolicyLocal(zjTzPolicyLocal);
    }

    @ApiOperation(value="删除宏观政策地方", notes="删除宏观政策地方")
    @ApiImplicitParam(name = "zjTzPolicyLocalList", value = "宏观政策地方List", dataType = "List<ZjTzPolicyLocal>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzPolicyLocal")
    public ResponseEntity batchDeleteUpdateZjTzPolicyLocal(@RequestBody(required = false) List<ZjTzPolicyLocal> zjTzPolicyLocalList) {
        return zjTzPolicyLocalService.batchDeleteUpdateZjTzPolicyLocal(zjTzPolicyLocalList);
    }

  //--扩展
    @ApiOperation(value="更新宏观政策地方-预案推送", notes="更新宏观政策地方-预案推送")
    @ApiImplicitParam(name = "zjTzPolicyLocal", value = "宏观政策地方entity", dataType = "ZjTzPolicyLocal")
    @RequireToken
    @PostMapping("/updateZjTzPolicyLocalPush")
    public ResponseEntity updateZjTzPolicyLocalPush(@RequestBody(required = false) ZjTzPolicyLocal zjTzPolicyLocal) {
        return zjTzPolicyLocalService.updateZjTzPolicyLocalPush(zjTzPolicyLocal);
    }
    
    @ApiOperation(value="更新宏观政策地方-广而告之", notes="更新宏观政策地方-广而告之")
    @ApiImplicitParam(name = "zjTzPolicyLocal", value = "宏观政策地方entity", dataType = "ZjTzPolicyLocal")
    @RequireToken
    @PostMapping("/updateZjTzPolicyLocalHomeShow")
    public ResponseEntity updateZjTzPolicyLocalHomeShow(@RequestBody(required = false) ZjTzPolicyLocal zjTzPolicyLocal) {
        return zjTzPolicyLocalService.updateZjTzPolicyLocalHomeShow(zjTzPolicyLocal);
    }
    
    @ApiOperation(value="批量发布宏观政策地方", notes="批量发布宏观政策地方")
    @ApiImplicitParam(name = "zjTzPolicyLocalList", value = "宏观政策地方List", dataType = "List<ZjTzPolicyLocal>")
    @RequireToken
    @PostMapping("/batchDeleteReleaseZjTzPolicyLocal")
    public ResponseEntity batchDeleteReleaseZjTzPolicyLocal(@RequestBody(required = false) List<ZjTzPolicyLocal> zjTzPolicyLocalList) {
        return zjTzPolicyLocalService.batchDeleteReleaseZjTzPolicyLocal(zjTzPolicyLocalList);
    }
    
    @ApiOperation(value="批量撤回宏观政策地方", notes="批量撤回宏观政策地方")
    @ApiImplicitParam(name = "zjTzPolicyLocalList", value = "宏观政策地方List", dataType = "List<ZjTzPolicyLocal>")
    @RequireToken
    @PostMapping("/batchDeleteRecallZjTzPolicyLocal")
    public ResponseEntity batchDeleteRecallZjTzPolicyLocal(@RequestBody(required = false) List<ZjTzPolicyLocal> zjTzPolicyLocalList) {
        return zjTzPolicyLocalService.batchDeleteRecallZjTzPolicyLocal(zjTzPolicyLocalList);
    }
    
    @ApiOperation(value="批量上报宏观政策地方", notes="批量上报宏观政策地方")
    @ApiImplicitParam(name = "zjTzPolicyLocalList", value = "宏观政策地方List", dataType = "List<ZjTzPolicyLocal>")
    @RequireToken
    @PostMapping("/batchReportZjTzPolicyLocal")
    public ResponseEntity batchReportZjTzPolicyLocal(@RequestBody(required = false) List<ZjTzPolicyLocal> zjTzPolicyLocalList) {
        return zjTzPolicyLocalService.batchReportZjTzPolicyLocal(zjTzPolicyLocalList);
    }
    
    @ApiOperation(value="批量退回宏观政策地方", notes="批量退回宏观政策地方")
    @ApiImplicitParam(name = "zjTzPolicyLocalList", value = "宏观政策地方List", dataType = "List<ZjTzPolicyLocal>")
    @RequireToken
    @PostMapping("/batchReturnZjTzPolicyLocal")
    public ResponseEntity batchReturnZjTzPolicyLocal(@RequestBody(required = false) List<ZjTzPolicyLocal> zjTzPolicyLocalList) {
        return zjTzPolicyLocalService.batchReturnZjTzPolicyLocal(zjTzPolicyLocalList);
    }
    
    @ApiOperation(value="批量导出宏观政策地方附件", notes="批量导出宏观政策地方附件")
    @ApiImplicitParam(name = "zjTzPolicyLocalList", value = "宏观政策地方List", dataType = "List<ZjTzSpecialYearPoint>")
    @RequireToken
    @PostMapping("/batchExportZjTzPolicyLocalFile")
    public ResponseEntity batchExportZjTzPolicyLocalFile(@RequestBody(required = false) List<ZjTzPolicyLocal> zjTzPolicyLocalList) {
        return zjTzPolicyLocalService.batchExportZjTzPolicyLocalFile(zjTzPolicyLocalList);
    }
}
