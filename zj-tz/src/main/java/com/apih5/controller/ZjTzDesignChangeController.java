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
import com.apih5.mybatis.pojo.ZjTzDesignChange;
import com.apih5.service.ZjTzDesignChangeService;

@RestController
public class ZjTzDesignChangeController {

    @Autowired(required = true)
    private ZjTzDesignChangeService zjTzDesignChangeService;

    @ApiOperation(value="查询设计变更管理", notes="查询设计变更管理")
    @ApiImplicitParam(name = "zjTzDesignChange", value = "设计变更管理entity", dataType = "ZjTzDesignChange")
    @RequireToken
    @PostMapping("/getZjTzDesignChangeList")
    public ResponseEntity getZjTzDesignChangeList(@RequestBody(required = false) ZjTzDesignChange zjTzDesignChange) {
        return zjTzDesignChangeService.getZjTzDesignChangeListByCondition(zjTzDesignChange);
    }

    @ApiOperation(value="查询详情设计变更管理", notes="查询详情设计变更管理")
    @ApiImplicitParam(name = "zjTzDesignChange", value = "设计变更管理entity", dataType = "ZjTzDesignChange")
    @RequireToken
    @PostMapping("/getZjTzDesignChangeDetails")
    public ResponseEntity getZjTzDesignChangeDetails(@RequestBody(required = false) ZjTzDesignChange zjTzDesignChange) {
        return zjTzDesignChangeService.getZjTzDesignChangeDetails(zjTzDesignChange);
    }

    @ApiOperation(value="新增设计变更管理", notes="新增设计变更管理")
    @ApiImplicitParam(name = "zjTzDesignChange", value = "设计变更管理entity", dataType = "ZjTzDesignChange")
    @RequireToken
    @PostMapping("/addZjTzDesignChange")
    public ResponseEntity addZjTzDesignChange(@RequestBody(required = false) ZjTzDesignChange zjTzDesignChange) {
        return zjTzDesignChangeService.saveZjTzDesignChange(zjTzDesignChange);
    }

    @ApiOperation(value="更新设计变更管理", notes="更新设计变更管理")
    @ApiImplicitParam(name = "zjTzDesignChange", value = "设计变更管理entity", dataType = "ZjTzDesignChange")
    @RequireToken
    @PostMapping("/updateZjTzDesignChange")
    public ResponseEntity updateZjTzDesignChange(@RequestBody(required = false) ZjTzDesignChange zjTzDesignChange) {
        return zjTzDesignChangeService.updateZjTzDesignChange(zjTzDesignChange);
    }

    @ApiOperation(value="删除设计变更管理", notes="删除设计变更管理")
    @ApiImplicitParam(name = "zjTzDesignChangeList", value = "设计变更管理List", dataType = "List<ZjTzDesignChange>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzDesignChange")
    public ResponseEntity batchDeleteUpdateZjTzDesignChange(@RequestBody(required = false) List<ZjTzDesignChange> zjTzDesignChangeList) {
        return zjTzDesignChangeService.batchDeleteUpdateZjTzDesignChange(zjTzDesignChangeList);
    }

}
