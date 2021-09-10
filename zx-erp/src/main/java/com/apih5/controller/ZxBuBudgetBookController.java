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
import com.apih5.mybatis.pojo.ZxBuBudgetBook;
import com.apih5.service.ZxBuBudgetBookService;

@RestController
public class ZxBuBudgetBookController {

    @Autowired(required = true)
    private ZxBuBudgetBookService zxBuBudgetBookService;

    @ApiOperation(value="查询预算书", notes="查询预算书")
    @ApiImplicitParam(name = "zxBuBudgetBook", value = "预算书entity", dataType = "ZxBuBudgetBook")
    @RequireToken
    @PostMapping("/getZxBuBudgetBookList")
    public ResponseEntity getZxBuBudgetBookList(@RequestBody(required = false) ZxBuBudgetBook zxBuBudgetBook) {
        return zxBuBudgetBookService.getZxBuBudgetBookListByCondition(zxBuBudgetBook);
    }

    @ApiOperation(value="查询详情预算书", notes="查询详情预算书")
    @ApiImplicitParam(name = "zxBuBudgetBook", value = "预算书entity", dataType = "ZxBuBudgetBook")
    @RequireToken
    @PostMapping("/getZxBuBudgetBookDetail")
    public ResponseEntity getZxBuBudgetBookDetail(@RequestBody(required = false) ZxBuBudgetBook zxBuBudgetBook) {
        return zxBuBudgetBookService.getZxBuBudgetBookDetail(zxBuBudgetBook);
    }

    @ApiOperation(value="新增预算书", notes="新增预算书")
    @ApiImplicitParam(name = "zxBuBudgetBook", value = "预算书entity", dataType = "ZxBuBudgetBook")
    @RequireToken
    @PostMapping("/addZxBuBudgetBook")
    public ResponseEntity addZxBuBudgetBook(@RequestBody(required = false) ZxBuBudgetBook zxBuBudgetBook) {
        return zxBuBudgetBookService.saveZxBuBudgetBook(zxBuBudgetBook);
    }

    @ApiOperation(value="更新预算书", notes="更新预算书")
    @ApiImplicitParam(name = "zxBuBudgetBook", value = "预算书entity", dataType = "ZxBuBudgetBook")
    @RequireToken
    @PostMapping("/updateZxBuBudgetBook")
    public ResponseEntity updateZxBuBudgetBook(@RequestBody(required = false) ZxBuBudgetBook zxBuBudgetBook) {
        return zxBuBudgetBookService.updateZxBuBudgetBook(zxBuBudgetBook);
    }

    @ApiOperation(value="删除预算书", notes="删除预算书")
    @ApiImplicitParam(name = "zxBuBudgetBookList", value = "预算书List", dataType = "List<ZxBuBudgetBook>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxBuBudgetBook")
    public ResponseEntity batchDeleteUpdateZxBuBudgetBook(@RequestBody(required = false) List<ZxBuBudgetBook> zxBuBudgetBookList) {
        return zxBuBudgetBookService.batchDeleteUpdateZxBuBudgetBook(zxBuBudgetBookList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
