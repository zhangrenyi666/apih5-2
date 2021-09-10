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
import com.apih5.mybatis.pojo.ZxCtWorkBook;
import com.apih5.service.ZxCtWorkBookService;

@RestController
public class ZxCtWorkBookController {

    @Autowired(required = true)
    private ZxCtWorkBookService zxCtWorkBookService;

    @ApiOperation(value="查询业主合同管理-业主合同台账-清单书(原表iecc_WorkBook", notes="查询业主合同管理-业主合同台账-清单书(原表iecc_WorkBook")
    @ApiImplicitParam(name = "zxCtWorkBook", value = "业主合同管理-业主合同台账-清单书(原表iecc_WorkBookentity", dataType = "ZxCtWorkBook")
    @RequireToken
    @PostMapping("/getZxCtWorkBookList")
    public ResponseEntity getZxCtWorkBookList(@RequestBody(required = false) ZxCtWorkBook zxCtWorkBook) {
        return zxCtWorkBookService.getZxCtWorkBookListByCondition(zxCtWorkBook);
    }

    @ApiOperation(value="查询详情业主合同管理-业主合同台账-清单书(原表iecc_WorkBook", notes="查询详情业主合同管理-业主合同台账-清单书(原表iecc_WorkBook")
    @ApiImplicitParam(name = "zxCtWorkBook", value = "业主合同管理-业主合同台账-清单书(原表iecc_WorkBookentity", dataType = "ZxCtWorkBook")
    @RequireToken
    @PostMapping("/getZxCtWorkBookDetail")
    public ResponseEntity getZxCtWorkBookDetail(@RequestBody(required = false) ZxCtWorkBook zxCtWorkBook) {
        return zxCtWorkBookService.getZxCtWorkBookDetail(zxCtWorkBook);
    }

    @ApiOperation(value="新增业主合同管理-业主合同台账-清单书(原表iecc_WorkBook", notes="新增业主合同管理-业主合同台账-清单书(原表iecc_WorkBook")
    @ApiImplicitParam(name = "zxCtWorkBook", value = "业主合同管理-业主合同台账-清单书(原表iecc_WorkBookentity", dataType = "ZxCtWorkBook")
    @RequireToken
    @PostMapping("/addZxCtWorkBook")
    public ResponseEntity addZxCtWorkBook(@RequestBody(required = false) ZxCtWorkBook zxCtWorkBook) {
        return zxCtWorkBookService.saveZxCtWorkBook(zxCtWorkBook);
    }

    @ApiOperation(value="更新业主合同管理-业主合同台账-清单书(原表iecc_WorkBook", notes="更新业主合同管理-业主合同台账-清单书(原表iecc_WorkBook")
    @ApiImplicitParam(name = "zxCtWorkBook", value = "业主合同管理-业主合同台账-清单书(原表iecc_WorkBookentity", dataType = "ZxCtWorkBook")
    @RequireToken
    @PostMapping("/updateZxCtWorkBook")
    public ResponseEntity updateZxCtWorkBook(@RequestBody(required = false) ZxCtWorkBook zxCtWorkBook) {
        return zxCtWorkBookService.updateZxCtWorkBook(zxCtWorkBook);
    }

    @ApiOperation(value="删除业主合同管理-业主合同台账-清单书(原表iecc_WorkBook", notes="删除业主合同管理-业主合同台账-清单书(原表iecc_WorkBook")
    @ApiImplicitParam(name = "zxCtWorkBookList", value = "业主合同管理-业主合同台账-清单书(原表iecc_WorkBookList", dataType = "List<ZxCtWorkBook>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtWorkBook")
    public ResponseEntity batchDeleteUpdateZxCtWorkBook(@RequestBody(required = false) List<ZxCtWorkBook> zxCtWorkBookList) {
        return zxCtWorkBookService.batchDeleteUpdateZxCtWorkBook(zxCtWorkBookList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="删除业主合同管理-业主合同台账-清单书(原表iecc_WorkBook", notes="删除业主合同管理-业主合同台账-清单书(原表iecc_WorkBook")
    @ApiImplicitParam(name = "zxCtWorkBookList", value = "业主合同管理-业主合同台账-清单书(原表iecc_WorkBookList", dataType = "List<ZxCtWorkBook>")
    @RequireToken
    @PostMapping("/deleteAllZxCtWorkBook")
    public ResponseEntity deleteAllZxCtWorkBook(@RequestBody(required = false) ZxCtWorkBook zxCtWorkBook) {
        return zxCtWorkBookService.deleteAllZxCtWorkBook(zxCtWorkBook);
    }
    
    @ApiOperation(value="业主台账合同-清单-合同数量确认", notes="业主台账合同-清单-合同数量确认")
    @ApiImplicitParam(name = "zxCtWorkBookList", value = "业主合同管理-业主合同台账-清单书(原表iecc_WorkBookList", dataType = "List<ZxCtWorkBook>")
    @RequireToken
    @PostMapping("/zxCtContractQuantity")
    public ResponseEntity zxCtContractQuantity(@RequestBody(required = false) ZxCtWorkBook zxCtWorkBook) {
    	return zxCtWorkBookService.zxCtContractQuantity(zxCtWorkBook);
    }
    
    @ApiOperation(value="业主台账合同-清单-核定数量确认", notes="业主台账合同-清单-核定数量确认")
    @ApiImplicitParam(name = "zxCtWorkBookList", value = "业主合同管理-业主合同台账-清单书(原表iecc_WorkBookList", dataType = "List<ZxCtWorkBook>")
    @RequireToken
    @PostMapping("/zxCtVerificationQuantity")
    public ResponseEntity zxCtVerificationQuantity(@RequestBody(required = false) ZxCtWorkBook zxCtWorkBook) {
    	return zxCtWorkBookService.zxCtVerificationQuantity(zxCtWorkBook);
    }
}
