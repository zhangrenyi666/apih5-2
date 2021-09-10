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
import com.apih5.mybatis.pojo.ZjConsumableApplyBook;
import com.apih5.service.ZjConsumableApplyBookService;

@RestController
public class ZjConsumableApplyBookController {

    @Autowired(required = true)
    private ZjConsumableApplyBookService zjConsumableApplyBookService;

    @ApiOperation(value="查询耗材申领台账", notes="查询耗材申领台账")
    @ApiImplicitParam(name = "zjConsumableApplyBook", value = "耗材申领台账entity", dataType = "ZjConsumableApplyBook")
    @RequireToken
    @PostMapping("/getZjConsumableApplyBookList")
    public ResponseEntity getZjConsumableApplyBookList(@RequestBody(required = false) ZjConsumableApplyBook zjConsumableApplyBook) {
        return zjConsumableApplyBookService.getZjConsumableApplyBookListByCondition(zjConsumableApplyBook);
    }

    @ApiOperation(value="查询详情耗材申领台账", notes="查询详情耗材申领台账")
    @ApiImplicitParam(name = "zjConsumableApplyBook", value = "耗材申领台账entity", dataType = "ZjConsumableApplyBook")
    @RequireToken
    @PostMapping("/getZjConsumableApplyBookDetails")
    public ResponseEntity getZjConsumableApplyBookDetails(@RequestBody(required = false) ZjConsumableApplyBook zjConsumableApplyBook) {
        return zjConsumableApplyBookService.getZjConsumableApplyBookDetails(zjConsumableApplyBook);
    }

    @ApiOperation(value="新增耗材申领台账", notes="新增耗材申领台账")
    @ApiImplicitParam(name = "zjConsumableApplyBook", value = "耗材申领台账entity", dataType = "ZjConsumableApplyBook")
    @RequireToken
    @PostMapping("/addZjConsumableApplyBook")
    public ResponseEntity addZjConsumableApplyBook(@RequestBody(required = false) ZjConsumableApplyBook zjConsumableApplyBook) {
        return zjConsumableApplyBookService.saveZjConsumableApplyBook(zjConsumableApplyBook);
    }

    @ApiOperation(value="更新耗材申领台账", notes="更新耗材申领台账")
    @ApiImplicitParam(name = "zjConsumableApplyBook", value = "耗材申领台账entity", dataType = "ZjConsumableApplyBook")
    @RequireToken
    @PostMapping("/updateZjConsumableApplyBook")
    public ResponseEntity updateZjConsumableApplyBook(@RequestBody(required = false) ZjConsumableApplyBook zjConsumableApplyBook) {
        return zjConsumableApplyBookService.updateZjConsumableApplyBook(zjConsumableApplyBook);
    }

    @ApiOperation(value="删除耗材申领台账", notes="删除耗材申领台账")
    @ApiImplicitParam(name = "zjConsumableApplyBookList", value = "耗材申领台账List", dataType = "List<ZjConsumableApplyBook>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjConsumableApplyBook")
    public ResponseEntity batchDeleteUpdateZjConsumableApplyBook(@RequestBody(required = false) List<ZjConsumableApplyBook> zjConsumableApplyBookList) {
        return zjConsumableApplyBookService.batchDeleteUpdateZjConsumableApplyBook(zjConsumableApplyBookList);
    }
    
    @ApiOperation(value="导出耗材申领台账", notes="导出耗材申领台账")
    @ApiImplicitParam(name = "zjConsumableApplyBook", value = "耗材申领台账entity", dataType = "ZjConsumableApplyBook")
    @PostMapping("/ureportZjConsumableApplyBookList")
    public List<ZjConsumableApplyBook> ureportZjConsumableApplyBookList(@RequestBody(required = false) ZjConsumableApplyBook zjConsumableApplyBook) {
        return zjConsumableApplyBookService.ureportZjConsumableApplyBookList(zjConsumableApplyBook);
    }


}
