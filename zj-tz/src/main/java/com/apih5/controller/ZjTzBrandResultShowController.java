package com.apih5.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzBrandResultShow;
import com.apih5.service.ZjTzBrandResultShowService;

@RestController
public class ZjTzBrandResultShowController {

    @Autowired(required = true)
    private ZjTzBrandResultShowService zjTzBrandResultShowService;

    @ApiOperation(value="查询品牌建设-成果展示", notes="查询品牌建设-成果展示")
    @ApiImplicitParam(name = "zjTzBrandResultShow", value = "品牌建设-成果展示entity", dataType = "ZjTzBrandResultShow")
    @RequireToken
    @PostMapping("/getZjTzBrandResultShowList")
    public ResponseEntity getZjTzBrandResultShowList(@RequestBody(required = false) ZjTzBrandResultShow zjTzBrandResultShow) {
        return zjTzBrandResultShowService.getZjTzBrandResultShowListByCondition(zjTzBrandResultShow);
    }

    @ApiOperation(value="查询详情品牌建设-成果展示", notes="查询详情品牌建设-成果展示")
    @ApiImplicitParam(name = "zjTzBrandResultShow", value = "品牌建设-成果展示entity", dataType = "ZjTzBrandResultShow")
    @RequireToken
    @PostMapping("/getZjTzBrandResultShowDetails")
    public ResponseEntity getZjTzBrandResultShowDetails(@RequestBody(required = false) ZjTzBrandResultShow zjTzBrandResultShow) {
        return zjTzBrandResultShowService.getZjTzBrandResultShowDetails(zjTzBrandResultShow);
    }

    @ApiOperation(value="新增品牌建设-成果展示", notes="新增品牌建设-成果展示")
    @ApiImplicitParam(name = "zjTzBrandResultShow", value = "品牌建设-成果展示entity", dataType = "ZjTzBrandResultShow")
    @RequireToken
    @PostMapping("/addZjTzBrandResultShow")
    public ResponseEntity addZjTzBrandResultShow(@RequestBody(required = false) ZjTzBrandResultShow zjTzBrandResultShow) {
        return zjTzBrandResultShowService.saveZjTzBrandResultShow(zjTzBrandResultShow);
    }

    @ApiOperation(value="更新品牌建设-成果展示", notes="更新品牌建设-成果展示")
    @ApiImplicitParam(name = "zjTzBrandResultShow", value = "品牌建设-成果展示entity", dataType = "ZjTzBrandResultShow")
    @RequireToken
    @PostMapping("/updateZjTzBrandResultShow")
    public ResponseEntity updateZjTzBrandResultShow(@RequestBody(required = false) ZjTzBrandResultShow zjTzBrandResultShow) {
        return zjTzBrandResultShowService.updateZjTzBrandResultShow(zjTzBrandResultShow);
    }

    @ApiOperation(value="删除品牌建设-成果展示", notes="删除品牌建设-成果展示")
    @ApiImplicitParam(name = "zjTzBrandResultShowList", value = "品牌建设-成果展示List", dataType = "List<ZjTzBrandResultShow>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzBrandResultShow")
    public ResponseEntity batchDeleteUpdateZjTzBrandResultShow(@RequestBody(required = false) List<ZjTzBrandResultShow> zjTzBrandResultShowList) {
        return zjTzBrandResultShowService.batchDeleteUpdateZjTzBrandResultShow(zjTzBrandResultShowList);
    }
    
    @ApiOperation(value="广而告之品牌建设-成果展示", notes="广而告之品牌建设-成果展示")
    @ApiImplicitParam(name = "zjTzBrandResultShow", value = "品牌建设-成果展示entity", dataType = "ZjTzBrandResultShow")
    @RequireToken
    @PostMapping("/toHomeShowZjTzBrandResultShow")
    public ResponseEntity toHomeShowZjTzBrandResultShow(@RequestBody(required = false) ZjTzBrandResultShow zjTzBrandResultShow) {
        return zjTzBrandResultShowService.toHomeShowZjTzBrandResultShow(zjTzBrandResultShow);
    }

    @ApiOperation(value="批量发布品牌建设-成果展示", notes="批量发布品牌建设-成果展示")
    @ApiImplicitParam(name = "zjTzBrandResultShowList", value = "品牌建设-成果展示List", dataType = "List<ZjTzBrandResultShow>")
    @RequireToken
    @PostMapping("/batchReleaseZjTzBrandResultShow")
    public ResponseEntity batchReleaseZjTzBrandResultShow(@RequestBody(required = false) List<ZjTzBrandResultShow> zjTzBrandResultShowList) {
        return zjTzBrandResultShowService.batchReleaseZjTzBrandResultShow(zjTzBrandResultShowList);
    }
    
    @ApiOperation(value="批量撤回品牌建设-成果展示", notes="批量撤回品牌建设-成果展示")
    @ApiImplicitParam(name = "zjTzBrandResultShowList", value = "品牌建设-成果展示List", dataType = "List<ZjTzBrandResultShow>")
    @RequireToken
    @PostMapping("/batchRecallZjTzBrandResultShow")
    public ResponseEntity batchRecallZjTzBrandResultShow(@RequestBody(required = false) List<ZjTzBrandResultShow> zjTzBrandResultShowList) {
        return zjTzBrandResultShowService.batchRecallZjTzBrandResultShow(zjTzBrandResultShowList);
    }
    
    @ApiOperation(value="批量导出品牌建设-成果展示附件", notes="批量导出品牌建设-成果展示附件")
    @ApiImplicitParam(name = "zjTzBrandResultShowList", value = "品牌建设-成果展示List", dataType = "List<ZjTzBrandResultShow>")
    @RequireToken
    @PostMapping("/batchExportZjTzBrandResultShowFile")
    public ResponseEntity batchExportZjTzBrandResultShowFile(@RequestBody(required = false) List<ZjTzBrandResultShow> zjTzBrandResultShowList) {
        return zjTzBrandResultShowService.batchExportZjTzBrandResultShowFile(zjTzBrandResultShowList);
    }
    
    @ApiOperation(value="广而告之品牌建设-成果展示", notes="广而告之品牌建设-成果展示")
    @ApiImplicitParam(name = "zjTzBrandResultShow", value = "品牌建设-成果展示entity", dataType = "ZjTzBrandResultShow")
    @RequireToken
    @PostMapping("/getHomeAdvertZjTzBrandResultShow")
    public ResponseEntity getHomeAdvertZjTzBrandResultShow(@RequestBody(required = false) ZjTzBrandResultShow zjTzBrandResultShow) {
        return zjTzBrandResultShowService.getHomeAdvertZjTzBrandResultShow(zjTzBrandResultShow);
    }
    
    @ApiOperation(value="导出品牌建设-成果展示", notes="导出品牌建设-成果展示")
    @ApiImplicitParam(name = "zjTzBrandResultShow", value = "品牌建设-成果展示entity", dataType = "ZjTzBrandResultShow")
//    @RequireToken
    @PostMapping("/exportZjTzBrandResultShowList")
    public ResponseEntity exportZjTzBrandResultShowList(@RequestBody(required = false) ZjTzBrandResultShow zjTzBrandResultShow, HttpServletResponse response) {
        return zjTzBrandResultShowService.exportZjTzBrandResultShowList(zjTzBrandResultShow, response);
    }
}
