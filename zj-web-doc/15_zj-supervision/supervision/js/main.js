var apiNames = {
    getDepartment: "getDepartment", // oa部门获取
    getMember: "getMember", // oa部门人员获取
    updateFiles: "updateFiles", // 文件上传
	
    getZjDdSupervisionList: "getZjDdSupervisionList", // 查询查询督导
    addZjDdSupervision: "addZjDdSupervision", // 新增督导
    updateZjDdSupervision: "updateZjDdSupervision", // 更新督导
    batchDeleteUpdateZjDdSupervision: "batchDeleteUpdateZjDdSupervision", // 批量删除督导
	getZjDdSupervisionDetail: "getZjDdSupervisionDetail",//督导详情
	addZjDdSupervisionDetail: "addZjDdSupervisionDetail",//详情页新增
	sendSupervision: "sendSupervision",//发布督导
	getSignatureTotalList: "getSignatureTotalList",//获取签字统计列表
	getFileListForWechatByUserId: "getFileListForWechatByUserId",//根据useID查询和UserID相关的附件列表（微信端）===前台传UserID  即 object_user_key
    addSignPictureToWord: "addSignPictureToWord",//微信端===接口  ==  详情页点击请签字===将图片插入到word最下面====传 附件地址 ，签字图片
    getSignatureCompanyTotalList:'getSignatureCompanyTotalList'
}
if($.fn.page){
	        $.fn.page.defaults = {
            pageSize: 10,
            pageBtnCount: 9,
            firstBtnText: '首页',
            lastBtnText: '尾页',
            prevBtnText: '上一页',
            nextBtnText: '下一页',
            showJump: true,
            jumpBtnText: 'GO',
            showPageSizes: true,
            pageSizeItems: [10, 50, 100,500,1000],
            remote: {
                pageIndexName: 'page',     //请求参数，当前页数，索引从1开始
                pageSizeName: 'limit',       //请求参数，每页数量
                totalName: 'totalNumber'              //指定返回数据的总数据量的字段名
            }
        };
}
window.lny = window.l = new Lny(apiNames,'http://192.168.1.104:8080/')
