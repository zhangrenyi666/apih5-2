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
import com.apih5.mybatis.pojo.ZxSfHazardRoomAtt;
import com.apih5.service.ZxSfHazardRoomAttService;

@RestController
public class ZxSfHazardRoomAttController {

    @Autowired(required = true)
    private ZxSfHazardRoomAttService zxSfHazardRoomAttService;

    @ApiOperation(value="查询重大危险源（项目）", notes="查询重大危险源（项目）")
    @ApiImplicitParam(name = "zxSfHazardRoomAtt", value = "重大危险源（项目）entity", dataType = "ZxSfHazardRoomAtt")
    @RequireToken
    @PostMapping("/getZxSfHazardRoomAttList")
    public ResponseEntity getZxSfHazardRoomAttList(@RequestBody(required = false) ZxSfHazardRoomAtt zxSfHazardRoomAtt) {
        return zxSfHazardRoomAttService.getZxSfHazardRoomAttListByCondition(zxSfHazardRoomAtt);
    }

    @ApiOperation(value="查询详情重大危险源（项目）", notes="查询详情重大危险源（项目）")
    @ApiImplicitParam(name = "zxSfHazardRoomAtt", value = "重大危险源（项目）entity", dataType = "ZxSfHazardRoomAtt")
    @RequireToken
    @PostMapping("/getZxSfHazardRoomAttDetail")
    public ResponseEntity getZxSfHazardRoomAttDetail(@RequestBody(required = false) ZxSfHazardRoomAtt zxSfHazardRoomAtt) {
        return zxSfHazardRoomAttService.getZxSfHazardRoomAttDetail(zxSfHazardRoomAtt);
    }

    @ApiOperation(value="新增重大危险源（项目）", notes="新增重大危险源（项目）")
    @ApiImplicitParam(name = "zxSfHazardRoomAtt", value = "重大危险源（项目）entity", dataType = "ZxSfHazardRoomAtt")
    @RequireToken
    @PostMapping("/addZxSfHazardRoomAtt")
    public ResponseEntity addZxSfHazardRoomAtt(@RequestBody(required = false) ZxSfHazardRoomAtt zxSfHazardRoomAtt) {
        return zxSfHazardRoomAttService.saveZxSfHazardRoomAtt(zxSfHazardRoomAtt);
    }

    @ApiOperation(value="更新重大危险源（项目）", notes="更新重大危险源（项目）")
    @ApiImplicitParam(name = "zxSfHazardRoomAtt", value = "重大危险源（项目）entity", dataType = "ZxSfHazardRoomAtt")
    @RequireToken
    @PostMapping("/updateZxSfHazardRoomAtt")
    public ResponseEntity updateZxSfHazardRoomAtt(@RequestBody(required = false) ZxSfHazardRoomAtt zxSfHazardRoomAtt) {
        return zxSfHazardRoomAttService.updateZxSfHazardRoomAtt(zxSfHazardRoomAtt);
    }

    @ApiOperation(value="删除重大危险源（项目）", notes="删除重大危险源（项目）")
    @ApiImplicitParam(name = "zxSfHazardRoomAttList", value = "重大危险源（项目）List", dataType = "List<ZxSfHazardRoomAtt>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSfHazardRoomAtt")
    public ResponseEntity batchDeleteUpdateZxSfHazardRoomAtt(@RequestBody(required = false) List<ZxSfHazardRoomAtt> zxSfHazardRoomAttList) {
        return zxSfHazardRoomAttService.batchDeleteUpdateZxSfHazardRoomAtt(zxSfHazardRoomAttList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="查询重大危险源过程（区域）", notes="查询重大危险源过程（区域）")
    @ApiImplicitParam(name = "zxSfHazardRoomAtt", value = "重大危险源（项目）entity", dataType = "ZxSfHazardRoomAtt")
    @RequireToken
    @PostMapping("/getZxSfHazardRoomAttProArea")
    public ResponseEntity getZxSfHazardRoomAttProArea(@RequestBody(required = false) ZxSfHazardRoomAtt zxSfHazardRoomAtt) {
        return zxSfHazardRoomAttService.getZxSfHazardRoomAttProArea(zxSfHazardRoomAtt);
    }
    
    @ApiOperation(value="查询重大危险源行为（活动）或设备=环境", notes="查询重大危险源行为（活动）或设备=环境")
    @ApiImplicitParam(name = "zxSfHazardRoomAtt", value = "重大危险源（项目）entity", dataType = "ZxSfHazardRoomAtt")
    @RequireToken
    @PostMapping("/getZxSfHazardRoomAttDoing")
    public ResponseEntity getZxSfHazardRoomAttDoing(@RequestBody(required = false) ZxSfHazardRoomAtt zxSfHazardRoomAtt) {
        return zxSfHazardRoomAttService.getZxSfHazardRoomAttDoing(zxSfHazardRoomAtt);
    }
    
    @ApiOperation(value="查询重大危险源危险因素", notes="查询重大危险源危险因素")
    @ApiImplicitParam(name = "zxSfHazardRoomAtt", value = "重大危险源（项目）entity", dataType = "ZxSfHazardRoomAtt")
    @RequireToken
    @PostMapping("/getZxSfHazardRoomAttRiskFactors")
    public ResponseEntity getZxSfHazardRoomAttRiskFactors(@RequestBody(required = false) ZxSfHazardRoomAtt zxSfHazardRoomAtt) {
        return zxSfHazardRoomAttService.getZxSfHazardRoomAttRiskFactors(zxSfHazardRoomAtt);
    }


    @ApiOperation(value="查询重大危险源报表", notes="查询重大危险源报表")
    @ApiImplicitParam(name = "zxSfHazardRoomAtt", value = "重大危险源（项目）entity", dataType = "ZxSfHazardRoomAtt")
    @RequireToken
    @PostMapping("/getZxSfHazardRoomAttUreportFormList")
    public ResponseEntity getZxSfHazardRoomAttUreportFormList(@RequestBody(required = false) ZxSfHazardRoomAtt zxSfHazardRoomAtt) {
        return zxSfHazardRoomAttService.getUreportFormList(zxSfHazardRoomAtt);
    }

    @ApiOperation(value="导出重大危险源报表", notes="导出重大危险源报表")
    @ApiImplicitParam(name = "zxSfHazardRoomAtt", value = "重大危险源（项目）entity", dataType = "ZxSfHazardRoomAtt")
    @PostMapping("/UreportZxSfHazardRoomAttForm")
    public List<ZxSfHazardRoomAtt> UreportZxSfHazardRoomAttForm(@RequestBody(required = false) ZxSfHazardRoomAtt zxSfHazardRoomAtt) {
        return zxSfHazardRoomAttService.UreportForm(zxSfHazardRoomAtt);
    }


    @ApiOperation(value="查询重大危险源报表(公司)", notes="查询重大危险源报表(公司)")
    @ApiImplicitParam(name = "zxSfHazardRoomAtt", value = "重大危险源（项目）entity", dataType = "ZxSfHazardRoomAtt")
    @RequireToken
    @PostMapping("/getZxSfHazardRoomAttUreportFormComList")
    public ResponseEntity getZxSfHazardRoomAttUreportFormComList(@RequestBody(required = false) ZxSfHazardRoomAtt zxSfHazardRoomAtt) {
        return zxSfHazardRoomAttService.getUreportFormComList(zxSfHazardRoomAtt);
    }

    @ApiOperation(value="导出重大危险源报表(公司)", notes="导出重大危险源报表(公司)")
    @ApiImplicitParam(name = "zxSfHazardRoomAtt", value = "重大危险源（项目）entity", dataType = "ZxSfHazardRoomAtt")
    @PostMapping("/UreportZxSfHazardRoomAttFormCom")
    public List<ZxSfHazardRoomAtt> UreportZxSfHazardRoomAttFormCom(@RequestBody(required = false) ZxSfHazardRoomAtt zxSfHazardRoomAtt) {
        return zxSfHazardRoomAttService.UreportFormCom(zxSfHazardRoomAtt);
    }
}
