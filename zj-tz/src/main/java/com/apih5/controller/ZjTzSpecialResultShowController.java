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
import com.apih5.mybatis.pojo.ZjTzSpecialResultShow;
import com.apih5.service.ZjTzSpecialResultShowService;

@RestController
public class ZjTzSpecialResultShowController {

    @Autowired(required = true)
    private ZjTzSpecialResultShowService zjTzSpecialResultShowService;

    @ApiOperation(value="查询专项活动-成果展示", notes="查询专项活动-成果展示")
    @ApiImplicitParam(name = "zjTzSpecialResultShow", value = "专项活动-成果展示entity", dataType = "ZjTzSpecialResultShow")
    @RequireToken
    @PostMapping("/getZjTzSpecialResultShowList")
    public ResponseEntity getZjTzSpecialResultShowList(@RequestBody(required = false) ZjTzSpecialResultShow zjTzSpecialResultShow) {
        return zjTzSpecialResultShowService.getZjTzSpecialResultShowListByCondition(zjTzSpecialResultShow);
    }

    @ApiOperation(value="查询详情专项活动-成果展示", notes="查询详情专项活动-成果展示")
    @ApiImplicitParam(name = "zjTzSpecialResultShow", value = "专项活动-成果展示entity", dataType = "ZjTzSpecialResultShow")
    @RequireToken
    @PostMapping("/getZjTzSpecialResultShowDetails")
    public ResponseEntity getZjTzSpecialResultShowDetails(@RequestBody(required = false) ZjTzSpecialResultShow zjTzSpecialResultShow) {
        return zjTzSpecialResultShowService.getZjTzSpecialResultShowDetails(zjTzSpecialResultShow);
    }

    @ApiOperation(value="新增专项活动-成果展示", notes="新增专项活动-成果展示")
    @ApiImplicitParam(name = "zjTzSpecialResultShow", value = "专项活动-成果展示entity", dataType = "ZjTzSpecialResultShow")
    @RequireToken
    @PostMapping("/addZjTzSpecialResultShow")
    public ResponseEntity addZjTzSpecialResultShow(@RequestBody(required = false) ZjTzSpecialResultShow zjTzSpecialResultShow) {
        return zjTzSpecialResultShowService.saveZjTzSpecialResultShow(zjTzSpecialResultShow);
    }

    @ApiOperation(value="更新专项活动-成果展示", notes="更新专项活动-成果展示")
    @ApiImplicitParam(name = "zjTzSpecialResultShow", value = "专项活动-成果展示entity", dataType = "ZjTzSpecialResultShow")
    @RequireToken
    @PostMapping("/updateZjTzSpecialResultShow")
    public ResponseEntity updateZjTzSpecialResultShow(@RequestBody(required = false) ZjTzSpecialResultShow zjTzSpecialResultShow) {
        return zjTzSpecialResultShowService.updateZjTzSpecialResultShow(zjTzSpecialResultShow);
    }

    @ApiOperation(value="删除专项活动-成果展示", notes="删除专项活动-成果展示")
    @ApiImplicitParam(name = "zjTzSpecialResultShowList", value = "专项活动-成果展示List", dataType = "List<ZjTzSpecialResultShow>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzSpecialResultShow")
    public ResponseEntity batchDeleteUpdateZjTzSpecialResultShow(@RequestBody(required = false) List<ZjTzSpecialResultShow> zjTzSpecialResultShowList) {
        return zjTzSpecialResultShowService.batchDeleteUpdateZjTzSpecialResultShow(zjTzSpecialResultShowList);
    }
    
    @ApiOperation(value="广而告之专项活动-成果展示", notes="广而告之专项活动-成果展示")
    @ApiImplicitParam(name = "zjTzSpecialResultShow", value = "专项活动-成果展示entity", dataType = "ZjTzSpecialResultShow")
    @RequireToken
    @PostMapping("/toHomeShowZjTzSpecialResultShow")
    public ResponseEntity toHomeShowZjTzSpecialResultShow(@RequestBody(required = false) ZjTzSpecialResultShow zjTzSpecialResultShow) {
        return zjTzSpecialResultShowService.toHomeShowZjTzSpecialResultShow(zjTzSpecialResultShow);
    }

    @ApiOperation(value="批量发布专项活动-成果展示", notes="批量发布专项活动-成果展示")
    @ApiImplicitParam(name = "zjTzSpecialResultShowList", value = "专项活动-成果展示List", dataType = "List<ZjTzSpecialResultShow>")
    @RequireToken
    @PostMapping("/batchReleaseZjTzSpecialResultShow")
    public ResponseEntity batchReleaseZjTzSpecialResultShow(@RequestBody(required = false) List<ZjTzSpecialResultShow> zjTzSpecialResultShowList) {
        return zjTzSpecialResultShowService.batchReleaseZjTzSpecialResultShow(zjTzSpecialResultShowList);
    }

    @ApiOperation(value="批量撤回专项活动-成果展示", notes="批量撤回专项活动-成果展示")
    @ApiImplicitParam(name = "zjTzSpecialResultShowList", value = "专项活动-成果展示List", dataType = "List<ZjTzSpecialResultShow>")
    @RequireToken
    @PostMapping("/batchRecallZjTzSpecialResultShow")
    public ResponseEntity batchRecallZjTzSpecialResultShow(@RequestBody(required = false) List<ZjTzSpecialResultShow> zjTzSpecialResultShowList) {
        return zjTzSpecialResultShowService.batchRecallZjTzSpecialResultShow(zjTzSpecialResultShowList);
    }
    
    @ApiOperation(value="批量导出专项活动-成果展示附件", notes="批量导出专项活动-成果展示附件")
    @ApiImplicitParam(name = "zjTzSpecialResultShowList", value = "专项活动-成果展示List", dataType = "List<ZjTzSpecialResultShow>")
    @RequireToken
    @PostMapping("/batchExportZjTzSpecialResultShowFile")
    public ResponseEntity batchExportZjTzSpecialResultShowFile(@RequestBody(required = false) List<ZjTzSpecialResultShow> zjTzSpecialResultShowList) {
        return zjTzSpecialResultShowService.batchExportZjTzSpecialResultShowFile(zjTzSpecialResultShowList);
    }
}
