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
import com.apih5.mybatis.pojo.ZjLzehRebarSupermarket;
import com.apih5.service.ZjLzehRebarSupermarketService;

@RestController
public class ZjLzehRebarSupermarketController {

    @Autowired(required = true)
    private ZjLzehRebarSupermarketService zjLzehRebarSupermarketService;

    @ApiOperation(value="查询钢筋超市", notes="查询钢筋超市")
    @ApiImplicitParam(name = "zjLzehRebarSupermarket", value = "钢筋超市entity", dataType = "ZjLzehRebarSupermarket")
    @RequireToken
    @PostMapping("/getZjLzehRebarSupermarketList")
    public ResponseEntity getZjLzehRebarSupermarketList(@RequestBody(required = false) ZjLzehRebarSupermarket zjLzehRebarSupermarket) {
        return zjLzehRebarSupermarketService.getZjLzehRebarSupermarketListByCondition(zjLzehRebarSupermarket);
    }

    @ApiOperation(value="查询详情钢筋超市", notes="查询详情钢筋超市")
    @ApiImplicitParam(name = "zjLzehRebarSupermarket", value = "钢筋超市entity", dataType = "ZjLzehRebarSupermarket")
    @RequireToken
    @PostMapping("/getZjLzehRebarSupermarketDetails")
    public ResponseEntity getZjLzehRebarSupermarketDetails(@RequestBody(required = false) ZjLzehRebarSupermarket zjLzehRebarSupermarket) {
        return zjLzehRebarSupermarketService.getZjLzehRebarSupermarketDetails(zjLzehRebarSupermarket);
    }

    @ApiOperation(value="新增钢筋超市", notes="新增钢筋超市")
    @ApiImplicitParam(name = "zjLzehRebarSupermarket", value = "钢筋超市entity", dataType = "ZjLzehRebarSupermarket")
    @RequireToken
    @PostMapping("/addZjLzehRebarSupermarket")
    public ResponseEntity addZjLzehRebarSupermarket(@RequestBody(required = false) ZjLzehRebarSupermarket zjLzehRebarSupermarket) {
        return zjLzehRebarSupermarketService.saveZjLzehRebarSupermarket(zjLzehRebarSupermarket);
    }

    @ApiOperation(value="更新钢筋超市", notes="更新钢筋超市")
    @ApiImplicitParam(name = "zjLzehRebarSupermarket", value = "钢筋超市entity", dataType = "ZjLzehRebarSupermarket")
    @RequireToken
    @PostMapping("/updateZjLzehRebarSupermarket")
    public ResponseEntity updateZjLzehRebarSupermarket(@RequestBody(required = false) ZjLzehRebarSupermarket zjLzehRebarSupermarket) {
        return zjLzehRebarSupermarketService.updateZjLzehRebarSupermarket(zjLzehRebarSupermarket);
    }

    @ApiOperation(value="删除钢筋超市", notes="删除钢筋超市")
    @ApiImplicitParam(name = "zjLzehRebarSupermarketList", value = "钢筋超市List", dataType = "List<ZjLzehRebarSupermarket>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjLzehRebarSupermarket")
    public ResponseEntity batchDeleteUpdateZjLzehRebarSupermarket(@RequestBody(required = false) List<ZjLzehRebarSupermarket> zjLzehRebarSupermarketList) {
    	return zjLzehRebarSupermarketService.batchDeleteUpdateZjLzehRebarSupermarket(zjLzehRebarSupermarketList);
    }
    
    @ApiOperation(value="通过项目id更新钢筋超市", notes="通过项目id更新钢筋超市")
    @ApiImplicitParam(name = "zjLzehRebarSupermarket", value = "钢筋超市entity", dataType = "ZjLzehRebarSupermarket")
//    @RequireToken
    @PostMapping("/updateZjLzehRebarSupermarketByProjectId")
    public ResponseEntity updateZjLzehRebarSupermarketByProjectId(@RequestBody(required = false) ZjLzehRebarSupermarket zjLzehRebarSupermarket) {
    	return zjLzehRebarSupermarketService.updateZjLzehRebarSupermarketByProjectId(zjLzehRebarSupermarket);
    }
    
    @ApiOperation(value="通过项目id更新钢筋超市列表", notes="通过项目id更新钢筋超市列表")
    @ApiImplicitParam(name = "zjLzehRebarSupermarket", value = "钢筋超市entity", dataType = "ZjLzehRebarSupermarket")
//    @RequireToken
    @PostMapping("/getZjLzehPageDataForView")
    public ResponseEntity getZjLzehPageDataForView(@RequestBody(required = false) ZjLzehRebarSupermarket zjLzehRebarSupermarket) {
    	return zjLzehRebarSupermarketService.getZjLzehPageDataForView(zjLzehRebarSupermarket);
    }
    
    @ApiOperation(value="通过项目id更新钢筋超市列表", notes="通过项目id更新钢筋超市列表")
    @ApiImplicitParam(name = "zjLzehRebarSupermarket", value = "钢筋超市entity", dataType = "ZjLzehRebarSupermarket")
    @PostMapping("/getOtherZjLzehPageDataForView")
    public ResponseEntity getOtherZjLzehPageDataForView(@RequestBody(required = false) ZjLzehRebarSupermarket zjLzehRebarSupermarket) {
        return zjLzehRebarSupermarketService.getZjLzehPageDataForView(zjLzehRebarSupermarket);
    }
    
}
