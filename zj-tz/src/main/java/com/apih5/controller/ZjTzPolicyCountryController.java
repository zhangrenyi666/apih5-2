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
import com.apih5.mybatis.pojo.ZjTzPolicyCountry;
import com.apih5.service.ZjTzPolicyCountryService;

@RestController
public class ZjTzPolicyCountryController {

    @Autowired(required = true)
    private ZjTzPolicyCountryService zjTzPolicyCountryService;

    @ApiOperation(value="查询宏观政策国家", notes="查询宏观政策国家")
    @ApiImplicitParam(name = "zjTzPolicyCountry", value = "宏观政策国家entity", dataType = "ZjTzPolicyCountry")
    @RequireToken
    @PostMapping("/getZjTzPolicyCountryList")
    public ResponseEntity getZjTzPolicyCountryList(@RequestBody(required = false) ZjTzPolicyCountry zjTzPolicyCountry) {
        return zjTzPolicyCountryService.getZjTzPolicyCountryListByCondition(zjTzPolicyCountry);
    }

    @ApiOperation(value="查询详情宏观政策国家", notes="查询详情宏观政策国家")
    @ApiImplicitParam(name = "zjTzPolicyCountry", value = "宏观政策国家entity", dataType = "ZjTzPolicyCountry")
    @RequireToken
    @PostMapping("/getZjTzPolicyCountryDetails")
    public ResponseEntity getZjTzPolicyCountryDetails(@RequestBody(required = false) ZjTzPolicyCountry zjTzPolicyCountry) {
        return zjTzPolicyCountryService.getZjTzPolicyCountryDetails(zjTzPolicyCountry);
    }

    @ApiOperation(value="新增宏观政策国家", notes="新增宏观政策国家")
    @ApiImplicitParam(name = "zjTzPolicyCountry", value = "宏观政策国家entity", dataType = "ZjTzPolicyCountry")
    @RequireToken
    @PostMapping("/addZjTzPolicyCountry")
    public ResponseEntity addZjTzPolicyCountry(@RequestBody(required = false) ZjTzPolicyCountry zjTzPolicyCountry) {
        return zjTzPolicyCountryService.saveZjTzPolicyCountry(zjTzPolicyCountry);
    }

    @ApiOperation(value="更新宏观政策国家", notes="更新宏观政策国家")
    @ApiImplicitParam(name = "zjTzPolicyCountry", value = "宏观政策国家entity", dataType = "ZjTzPolicyCountry")
    @RequireToken
    @PostMapping("/updateZjTzPolicyCountry")
    public ResponseEntity updateZjTzPolicyCountry(@RequestBody(required = false) ZjTzPolicyCountry zjTzPolicyCountry) {
        return zjTzPolicyCountryService.updateZjTzPolicyCountry(zjTzPolicyCountry);
    }

    @ApiOperation(value="删除宏观政策国家", notes="删除宏观政策国家")
    @ApiImplicitParam(name = "zjTzPolicyCountryList", value = "宏观政策国家List", dataType = "List<ZjTzPolicyCountry>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzPolicyCountry")
    public ResponseEntity batchDeleteUpdateZjTzPolicyCountry(@RequestBody(required = false) List<ZjTzPolicyCountry> zjTzPolicyCountryList) {
        return zjTzPolicyCountryService.batchDeleteUpdateZjTzPolicyCountry(zjTzPolicyCountryList);
    }
    
    //--扩展
    @ApiOperation(value="更新宏观政策国家-预案推送", notes="更新宏观政策国家-预案推送")
    @ApiImplicitParam(name = "zjTzPolicyCountry", value = "宏观政策国家entity", dataType = "ZjTzPolicyCountry")
    @RequireToken
    @PostMapping("/updateZjTzPolicyCountryPush")
    public ResponseEntity updateZjTzPolicyCountryPush(@RequestBody(required = false) ZjTzPolicyCountry zjTzPolicyCountry) {
        return zjTzPolicyCountryService.updateZjTzPolicyCountryPush(zjTzPolicyCountry);
    }
    
    @ApiOperation(value="更新宏观政策国家-广而告之", notes="更新宏观政策国家-广而告之")
    @ApiImplicitParam(name = "zjTzPolicyCountry", value = "宏观政策国家entity", dataType = "ZjTzPolicyCountry")
    @RequireToken
    @PostMapping("/updateZjTzPolicyCountryHomeShow")
    public ResponseEntity updateZjTzPolicyCountryHomeShow(@RequestBody(required = false) ZjTzPolicyCountry zjTzPolicyCountry) {
        return zjTzPolicyCountryService.updateZjTzPolicyCountryHomeShow(zjTzPolicyCountry);
    }
    
    @ApiOperation(value="批量发布宏观政策国家", notes="批量发布宏观政策国家")
    @ApiImplicitParam(name = "zjTzPolicyCountryList", value = "宏观政策国家List", dataType = "List<ZjTzPolicyCountry>")
    @RequireToken
    @PostMapping("/batchDeleteReleaseZjTzPolicyCountry")
    public ResponseEntity batchDeleteReleaseZjTzPolicyCountry(@RequestBody(required = false) List<ZjTzPolicyCountry> zjTzPolicyCountryList) {
        return zjTzPolicyCountryService.batchDeleteReleaseZjTzPolicyCountry(zjTzPolicyCountryList);
    }
    
    @ApiOperation(value="批量撤回宏观政策国家", notes="批量撤回宏观政策国家")
    @ApiImplicitParam(name = "zjTzPolicyCountryList", value = "宏观政策国家List", dataType = "List<ZjTzPolicyCountry>")
    @RequireToken
    @PostMapping("/batchDeleteRecallZjTzPolicyCountry")
    public ResponseEntity batchDeleteRecallZjTzPolicyCountry(@RequestBody(required = false) List<ZjTzPolicyCountry> zjTzPolicyCountryList) {
        return zjTzPolicyCountryService.batchDeleteRecallZjTzPolicyCountry(zjTzPolicyCountryList);
    }
    
    @ApiOperation(value="批量导出宏观政策国家附件", notes="批量导出宏观政策国家附件")
    @ApiImplicitParam(name = "zjTzPolicyCountryList", value = "宏观政策国家List", dataType = "List<ZjTzSpecialYearPoint>")
    @RequireToken
    @PostMapping("/batchExportZjTzPolicyCountryFile")
    public ResponseEntity batchExportZjTzPolicyCountryFile(@RequestBody(required = false) List<ZjTzPolicyCountry> zjTzPolicyCountryList) {
        return zjTzPolicyCountryService.batchExportZjTzPolicyCountryFile(zjTzPolicyCountryList);
    }

    @ApiOperation(value="广而告之宏观政策", notes="广而告之宏观政策")
    @ApiImplicitParam(name = "zjTzPolicyCountry", value = "宏观政策国家entity", dataType = "ZjTzPolicyCountry")
    @RequireToken
    @PostMapping("/getZjTzPolicyCountryHomeAdvertMacro")
    public ResponseEntity getZjTzPolicyCountryHomeAdvertMacro(@RequestBody(required = false) ZjTzPolicyCountry zjTzPolicyCountry) {
        return zjTzPolicyCountryService.getZjTzPolicyCountryHomeAdvertMacro(zjTzPolicyCountry);
    }
}
