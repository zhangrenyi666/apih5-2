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
import com.apih5.mybatis.pojo.ZjTzRunQuarterly;
import com.apih5.service.ZjTzRunQuarterlyService;

@RestController
public class ZjTzRunQuarterlyController {

    @Autowired(required = true)
    private ZjTzRunQuarterlyService zjTzRunQuarterlyService;

    @ApiOperation(value="查询运营季报管理", notes="查询运营季报管理")
    @ApiImplicitParam(name = "zjTzRunQuarterly", value = "运营季报管理entity", dataType = "ZjTzRunQuarterly")
    @RequireToken
    @PostMapping("/getZjTzRunQuarterlyList")
    public ResponseEntity getZjTzRunQuarterlyList(@RequestBody(required = false) ZjTzRunQuarterly zjTzRunQuarterly) {
        return zjTzRunQuarterlyService.getZjTzRunQuarterlyListByCondition(zjTzRunQuarterly);
    }

    @ApiOperation(value="查询详情运营季报管理", notes="查询详情运营季报管理")
    @ApiImplicitParam(name = "zjTzRunQuarterly", value = "运营季报管理entity", dataType = "ZjTzRunQuarterly")
    @RequireToken
    @PostMapping("/getZjTzRunQuarterlyDetails")
    public ResponseEntity getZjTzRunQuarterlyDetails(@RequestBody(required = false) ZjTzRunQuarterly zjTzRunQuarterly) {
        return zjTzRunQuarterlyService.getZjTzRunQuarterlyDetails(zjTzRunQuarterly);
    }

    @ApiOperation(value="新增运营季报管理", notes="新增运营季报管理")
    @ApiImplicitParam(name = "zjTzRunQuarterly", value = "运营季报管理entity", dataType = "ZjTzRunQuarterly")
    @RequireToken
    @PostMapping("/addZjTzRunQuarterly")
    public ResponseEntity addZjTzRunQuarterly(@RequestBody(required = false) ZjTzRunQuarterly zjTzRunQuarterly) {
        return zjTzRunQuarterlyService.saveZjTzRunQuarterly(zjTzRunQuarterly);
    }

    @ApiOperation(value="更新运营季报管理", notes="更新运营季报管理")
    @ApiImplicitParam(name = "zjTzRunQuarterly", value = "运营季报管理entity", dataType = "ZjTzRunQuarterly")
    @RequireToken
    @PostMapping("/updateZjTzRunQuarterly")
    public ResponseEntity updateZjTzRunQuarterly(@RequestBody(required = false) ZjTzRunQuarterly zjTzRunQuarterly) {
        return zjTzRunQuarterlyService.updateZjTzRunQuarterly(zjTzRunQuarterly);
    }

    @ApiOperation(value="删除运营季报管理", notes="删除运营季报管理")
    @ApiImplicitParam(name = "zjTzRunQuarterlyList", value = "运营季报管理List", dataType = "List<ZjTzRunQuarterly>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzRunQuarterly")
    public ResponseEntity batchDeleteUpdateZjTzRunQuarterly(@RequestBody(required = false) List<ZjTzRunQuarterly> zjTzRunQuarterlyList) {
        return zjTzRunQuarterlyService.batchDeleteUpdateZjTzRunQuarterly(zjTzRunQuarterlyList);
    }

}
