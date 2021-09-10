var apiNames = {
  getDepartment: "apiPostSysDepartmentList/2", // oa部门获取
  getMember: "apiPostMember", // oa部门人员获取
  updateFiles: "updateFiles", // 文件上传

  getZjLhddJointSupApplyList: "getZjLhddJointSupApplyList", //联合督导申请查列表
  addZjLhddJointSupApply: "addZjLhddJointSupApply", //新增
  updateZjLhddJointSupApply: "updateZjLhddJointSupApply", //更新
  updateZjLhddJointSupApplyForEdit:"updateZjLhddJointSupApplyForEdit",//可编辑的更新
  getZjLhddJointSupApplyDetailsByWorkId:"getZjLhddJointSupApplyDetailsByWorkId", //详情

  getZjLhddJointSupDataListList: "getZjLhddJointSupDataListList", //资料清单检查部门--列表
  addZjLhddJointSupDataList: "addZjLhddJointSupDataList", //
  updateZjLhddJointSupDataList: "updateZjLhddJointSupDataList", //
  batchDeleteUpdateZjLhddJointSupDataList:
    "batchDeleteUpdateZjLhddJointSupDataList", //
  getZjLhddJointSupDataListDetails: "getZjLhddJointSupDataListDetails", //查详情

  getZjLhddJointSupCheckItemList: "getZjLhddJointSupCheckItemList", //资料清单检查项-副--列表
  addZjLhddJointSupCheckItem: "addZjLhddJointSupCheckItem", //
  updateZjLhddJointSupCheckItem: "updateZjLhddJointSupCheckItem", //
  batchDeleteUpdateZjLhddJointSupCheckItem:
    "batchDeleteUpdateZjLhddJointSupCheckItem", //

  getZjLhddJointSupCheckIssueList: "getZjLhddJointSupCheckIssueList", //检查问题清单---列表
  addZjLhddJointSupCheckIssue: "addZjLhddJointSupCheckIssue", //
  updateZjLhddJointSupCheckIssue: "updateZjLhddJointSupCheckIssue", //
  batchDeleteUpdateZjLhddJointSupCheckIssue:
    "batchDeleteUpdateZjLhddJointSupCheckIssue", //
  submitZjLhddJointSupCheckIssue: "submitZjLhddJointSupCheckIssue", //
  getZjLhddJointSupCheckIssueDetails: "getZjLhddJointSupCheckIssueDetails", //

  getZjLhddJointSupCheckIssueDesList: "getZjLhddJointSupCheckIssueDesList", //问题描述----列表
  addZjLhddJointSupCheckIssueDes: "addZjLhddJointSupCheckIssueDes", //
  updateZjLhddJointSupCheckIssueDes: "updateZjLhddJointSupCheckIssueDes", //
  batchDeleteUpdateZjLhddJointSupCheckIssueDes:
    "batchDeleteUpdateZjLhddJointSupCheckIssueDes", //
  batchRejectZjLhddJointSupCheckIssueDes:
    "batchRejectZjLhddJointSupCheckIssueDes", //问题描述批量驳回

  addZjLhddJointSupCheckIssueReply: "addZjLhddJointSupCheckIssueReply", //问题回复
  updateZjLhddJointSupCheckIssueReply: "updateZjLhddJointSupCheckIssueReply", //
  getZjLhddJointSupCheckIssueReplyDetails:
    "getZjLhddJointSupCheckIssueReplyDetails", //

  //监督计划
  getZjJdjhYearSupPlanList: "getZjJdjhYearSupPlanList", //
  addZjJdjhYearSupPlan: "addZjJdjhYearSupPlan", //
  updateZjJdjhYearSupPlan: "updateZjJdjhYearSupPlan", //
  batchDeleteUpdateZjJdjhYearSupPlan: "batchDeleteUpdateZjJdjhYearSupPlan", //
  submitZjJdjhYearSupPlan: "submitZjJdjhYearSupPlan", //
  getZjJdjhYearSupPlanDetails: "getZjJdjhYearSupPlanDetails", //

  getZjJdjhYearSupMatterList: "getZjJdjhYearSupMatterList", //
  addZjJdjhYearSupMatter: "addZjJdjhYearSupMatter", //
  updateZjJdjhYearSupMatter: "updateZjJdjhYearSupMatter", //
  batchDeleteUpdateZjJdjhYearSupMatter: "batchDeleteUpdateZjJdjhYearSupMatter", //
  getZjJdjhYearSupMatterDetail: "getZjJdjhYearSupMatterDetail", //查详情

  getZjJdjhSupPlanExecutiveList: "getZjJdjhSupPlanExecutiveList", //
  addZjJdjhSupPlanExecutive: "addZjJdjhSupPlanExecutive", //
  updateZjJdjhSupPlanExecutive: "updateZjJdjhSupPlanExecutive", //
  exportExcelZjJdjhYearSupMatterList: "exportExcelZjJdjhYearSupMatterList", //导出

  //监督计划
  getZjJdjhYearSupPlanState2List: "getZjJdjhYearSupPlanState2List", //

  replySubmitToAuditor: "replySubmitToAuditor", //
  getZjJdjhSupPlanExecutiveDetail: "getZjJdjhSupPlanExecutiveDetail", //执行情况汇总统计查详情

  addPlanOutMatter: "addPlanOutMatter", //计划外事项新增
  addZjJdjhSupPlanExecuteDetail: "addZjJdjhSupPlanExecuteDetail", //新增执行详情
  updateZjJdjhSupPlanExecuteDetail: "updateZjJdjhSupPlanExecuteDetail", //更新执行详情
  getMatterAndExecuteDetailList: "getMatterAndExecuteDetailList", //
  getZjJdjhSupPlanExecutiveTotalList: "getZjJdjhSupPlanExecutiveTotalList", //执行情况汇总统计查详情
  exportExcelZjJdjhSupPlanExecutiveTotal:
    "exportExcelZjJdjhSupPlanExecutiveTotal", //导出执行情况

  startFlow: "startFlow",
  submitFlow: "submitFlow",
  getSubmitFlow: "getSubmitFlow",
  getTodoList: "getTodoList",
  getHasTodoList: "getHasTodoList",
  createOpenFlow: "createOpenFlow",
  openFlow: "openFlow",
  actionFlow: "actionFlow"
};
if ($.fn.page) {
  $.fn.page.defaults = {
    pageSize: 10,
    pageBtnCount: 9,
    firstBtnText: "首页",
    lastBtnText: "尾页",
    prevBtnText: "上一页",
    nextBtnText: "下一页",
    showJump: true,
    jumpBtnText: "GO",
    showPageSizes: true,
    pageSizeItems: [10, 50, 100, 500, 1000],
    remote: {
      pageIndexName: "page", //请求参数，当前页数，索引从1开始
      pageSizeName: "limit", //请求参数，每页数量
      totalName: "totalNumber" //指定返回数据的总数据量的字段名
    }
  };
}
//window.lny = window.l = new Lny(apiNames,'http://weixin.fheb.cn:98/apiwoa/')
window.lny = window.l = new Lny(apiNames,'http://192.168.1.223:8080/web/')
//window.lny = window.l = new Lny(apiNames, "http://wsy.apih5.com/web/");
