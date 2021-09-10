var apiNames = {
    getDepartment: "getDepartment", // oa部门获取
    getMember: "getMember", // oa部门人员获取
    updateFiles: "updateFiles", // 文件上传
    getZjInvoiceList:"getZjInvoiceList", //获取发票列表
    addZjInvoice:"addZjInvoice", //新增发票
    updateZjInvoice:"updateZjInvoice", //修改发票
    batchDeleteUpdateZjInvoice:"batchDeleteUpdateZjInvoice", //批量删除发票
}
 //window.lny = window.l = new Lny(apiNames,'http://192.168.1.113:8080/wdplus-web/')
//window.lny = window.l = new Lny(apiNames,'http://weixin.fheb.cn:89/zjtz/')
// window.lny = window.l = new Lny(apiNames,"http://localhost:8080/wdplus-web/")
//window.lny = window.l = new Lny(apiNames,'http://localhost:8080/wdplus-web/')
window.lny = window.l = new Lny(apiNames,'http://localhost:8080/')