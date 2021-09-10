var apiNames = {
    getDepartment: "apiPostDepartment2", // oa部门获取
    getMember: "user/getSysUserList", // oa部门人员获取

    updateFiles: "updateFiles", // 文件上传
    getVoteList: "getVoteList", // 投票列表
    getVoteDetails: "getVoteDetails", // 投票详情
    addVote: "addVote", // 投票新增
    updateVote: "updateVote", // 投票修改
    batchDeleteVote: "batchDeleteVote", // 投票删除&批量删除
    sendVoteInfo: "sendVoteInfo", // 投票发布
    getVoteCandidateList: "getVoteCandidateList", // 被投票者列表
    getVoteCandidateDetails: "getVoteCandidateDetails", // 被投票者详情
    addVoteCandidate: "addVoteCandidate", // 被投票者新增
    updateVoteCandidate: "updateVoteCandidate", // 被投票者修改
    batchDeleteVoteCandidate: "batchDeleteVoteCandidate", // 被投票者删除&批量删除
    getQuestionsList: "getQuestionsList", // 投票问题列表
    getQuestionsDetails: "getQuestionsDetails", // 投票问题详情
    addQuestions: "addQuestions", // 投票问题新增
    updateQuestions: "updateQuestions", // 投票问题修改
    batchDeleteQuestions: "batchDeleteQuestions", // 投票问题删除&批量删除
    getTotalCandidateList: "getTotalCandidateList", // 投票统计列表
    getTotalCandidateDetailsList: "getTotalCandidateDetailsList", // 投票统计详情列表
	getTotalCandidateListForFraction: "getTotalCandidateListForFraction", // 投票统计详情列表
    getTotalCandidateDetailsListForFraction: "getTotalCandidateDetailsListForFraction", // 投票统计详情列表
    getVoteListByVoter: "getVoteListByVoter", // 投票发起列表
    sendVoteInfo: "sendVoteInfo", // 投票发布
    getVoteDetailedByPc : "getVoteDetailedByPc",
	copyVoteDetails : "copyVoteDetails",
	exportCandidateList : "exportCandidateList",
    getVoteCandidateListByPc : "getVoteCandidateListByPc",
    saveVoteResult : "saveVoteResult",
    saveAnswerInfoPc:"saveAnswerInfoPc",
    getCandidateDetailsListByCandidateId:"getCandidateDetailsListByCandidateId",
	getExternalVoteListByVoter:"getExternalVoteListByVoter",//外部人员访问投票数据
	getVoteDetailed:"getVoteDetailed",//投票手机端预览接口
	addAnswerInfo:"addAnswerInfo",//投票手机端预览接口
	getVoteDetailedPreview:"getVoteDetailedPreview",//投票手机端预览接口
	getZjVoteTemporaryStaffListByCondition:"getZjVoteTemporaryStaffListByCondition",//查询临时人员
	addZjVoteTemporaryStaff:"addZjVoteTemporaryStaff",//新增临时人员
	updateZjVoteTemporaryStaff:"updateZjVoteTemporaryStaff",//修改临时人员
	batchDeleteUpdateZjVoteTemporaryStaff:"batchDeleteUpdateZjVoteTemporaryStaff",//批量删除临时人员
	importZjVoteTemporaryStaffList:"importZjVoteTemporaryStaffList",//批量导入临时人员
}
// window.apih5 = window.l = new Apih5(apiNames, "http://qyh.apih5.com/")
//window.apih5 = window.l = new Apih5(apiNames, "http://wechat.zjyjhw.com:8080/hwwoa/")
// window.apih5 = window.l = new Apih5(apiNames,'http://wechat.zjyjhw.com/apihaiwei/')
window.apih5 = window.l = new Apih5(apiNames,'http://192.168.1.123:8080/web/')