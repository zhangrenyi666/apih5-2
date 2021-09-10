var apiNames = {
    getDepartment: "apiPostDepartment", // oa部门获取
    getMember: "apiPostMember", // oa部门人员获取
    updaloadFiles: "updaloadFiles", // 文件上传	
	
	getZjHaiKangOaMemberList: "getZjHaiKangOaMemberList",
    addZjHaiKangOaMember: "addZjHaiKangOaMember",
    updateZjHaiKangOaMember: "updateZjHaiKangOaMember",
    batchDeleteZjHaiKangOaMember: "batchDeleteZjHaiKangOaMember",
}

if ($.fn.page) {
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
        pageSizeItems: [10, 50, 100, 500, 1000],
        remote: {
            pageIndexName: 'page',     //请求参数，当前页数，索引从1开始
            pageSizeName: 'limit',       //请求参数，每页数量
            totalName: 'totalNumber'              //指定返回数据的总数据量的字段名
        }
    };
}
//window.lny = window.l = new Lny(apiNames, 'http://192.168.1.109:8080/')
window.lny = window.l = new Lny(apiNames, 'http://localhost:8080/web/')
