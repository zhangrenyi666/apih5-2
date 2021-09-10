var apiNames = {
    getDepartment:"getDepartment", // 资产状态列表
    getMember:"getMember", // 资产状态列表
    updaloadFiles:"updaloadFiles", // 资产状态列表		
	//getDepartment:"apiOaDepartmentListBySanDj", // 资产状态列表
    //getMember:"apiOaUserListBySanDj", // 资产状态列表
	getZjSanDjGradeFlowTotalList:"getZjSanDjGradeFlowTotalList",
	getHistory:"getHistory",

	exportExcelZjSanDjFlowPersonageList:"exportExcelZjSanDjFlowPersonageList",//
	//员工素质提升
	getZjSanDjFlowPersonageList:"getZjSanDjFlowPersonageList",
	batchDeleteUpdateZjSanDjFlowPersonage:"batchDeleteUpdateZjSanDjFlowPersonage",
	getZjSanDjFlowPersonageDetail:"getZjSanDjFlowPersonageDetail",//
	getZjSanDjFlowCollectiveList:"getZjSanDjFlowCollectiveList",
	batchDeleteUpdateZjSanDjFlowCollective:"batchDeleteUpdateZjSanDjFlowCollective",
	getZjSanDjFlowCollectiveDetail:"getZjSanDjFlowCollectiveDetail",//
	getZjSanDjFlowGroupAwardList:"getZjSanDjFlowGroupAwardList",
	getZjSanDjFlowGroupAwardDetail:"getZjSanDjFlowGroupAwardDetail",//
	getZjSanDjFlowSkillInnovationList:"getZjSanDjFlowSkillInnovationList",
	getZjSanDjFlowSkillInnovationDetail:"getZjSanDjFlowSkillInnovationDetail",//
	getZjSanDjFlowProprietaryTechnologyList:"getZjSanDjFlowProprietaryTechnologyList",
	getZjSanDjFlowProprietaryTechnologyDetail:"getZjSanDjFlowProprietaryTechnologyDetail",//
	getZjSanDjFlowPatentList:"getZjSanDjFlowPatentList",
	getZjSanDjFlowPatentDetail:"getZjSanDjFlowPatentDetail",//
	getZjSanDjFlowSuggestList:"getZjSanDjFlowSuggestList",
	getZjSanDjFlowSuggestDetail:"getZjSanDjFlowSuggestDetail",//
	getZjSanDjFlowPaperList:"getZjSanDjFlowPaperList",
	getZjSanDjFlowPaperDetail:"getZjSanDjFlowPaperDetail",//
	getZjSanDjFlowSkillStandardList:"getZjSanDjFlowSkillStandardList",
	getZjSanDjFlowSkillStandardDetail:"getZjSanDjFlowSkillStandardDetail",//
	getZjSanDjFlowQcList:"getZjSanDjFlowQcList",
	getZjSanDjFlowQcDetail:"getZjSanDjFlowQcDetail",//
	getZjSanDjFlowRdProjectsList:"getZjSanDjFlowRdProjectsList",
	getZjSanDjFlowRdProjectsDetail:"getZjSanDjFlowRdProjectsDetail",//
	getZjSanDjFlowConstructStandardList:"getZjSanDjFlowConstructStandardList",
	getZjSanDjFlowConstructStandardDetail:"getZjSanDjFlowConstructStandardDetail",//
	getZjSanDjFlowCertificateList:"getZjSanDjFlowCertificateList",
	batchDeleteUpdateZjSanDjFlowCertificate:"batchDeleteUpdateZjSanDjFlowCertificate",
	getZjSanDjFlowCertificateDetail:"getZjSanDjFlowCertificateDetail",//
	getZjSanDjFlowProfessionalList:"getZjSanDjFlowProfessionalList",
	getZjSanDjFlowProfessionalDetail:"getZjSanDjFlowProfessionalDetail",//
	
	getZjSanDjFlowComprehensiveList:"getZjSanDjFlowComprehensiveList",
	getZjSanDjFlowComprehensiveDetail:"getZjSanDjFlowComprehensiveDetail",//
	getZjSanDjFlowBusinessList:"getZjSanDjFlowBusinessList",
	batchDeleteUpdateZjSanDjFlowBusiness:"batchDeleteUpdateZjSanDjFlowBusiness",
	getZjSanDjFlowBusinessDetail:"getZjSanDjFlowBusinessDetail",//
	
	
	
	
	getZjSanDjPersonInfoList:"getZjSanDjPersonInfoList",
	addZjSanDjPersonInfo:"addZjSanDjPersonInfo",
	updateZjSanDjPersonInfo:"updateZjSanDjPersonInfo",
	batchDeleteUpdateZjSanDjPersonInfo:"batchDeleteUpdateZjSanDjPersonInfo",//
	addZjSanDjPersonTransferRecord:"addZjSanDjPersonTransferRecord",//人员调动新增
	getZjSanDjPersonTransferRecordList:"getZjSanDjPersonTransferRecordList",//人员调动列表
	
	
	
	batchDeleteUpdateZjSanDjFlowComprehensive:"batchDeleteUpdateZjSanDjFlowComprehensive",
	batchDeleteUpdateZjSanDjFlowConstructStandard:"batchDeleteUpdateZjSanDjFlowConstructStandard",
	batchDeleteUpdateZjSanDjFlowPaper:"batchDeleteUpdateZjSanDjFlowPaper",
	batchDeleteUpdateZjSanDjFlowPatent:"batchDeleteUpdateZjSanDjFlowPatent",
	batchDeleteUpdateZjSanDjFlowProfessional:"batchDeleteUpdateZjSanDjFlowProfessional",
	batchDeleteUpdateZjSanDjFlowProprietaryTechnology:"batchDeleteUpdateZjSanDjFlowProprietaryTechnology",
	batchDeleteUpdateZjSanDjFlowQc:"batchDeleteUpdateZjSanDjFlowQc",
	batchDeleteUpdateZjSanDjFlowRdProjects:"batchDeleteUpdateZjSanDjFlowRdProjects",
	batchDeleteUpdateZjSanDjFlowSkillInnovation:"batchDeleteUpdateZjSanDjFlowSkillInnovation",
	batchDeleteUpdateZjSanDjFlowSkillStandard:"batchDeleteUpdateZjSanDjFlowSkillStandard",
	batchDeleteUpdateZjSanDjFlowSuggest:"batchDeleteUpdateZjSanDjFlowSuggest",
	
	
	
	
	
	getZjSanDjDataList:"getZjSanDjDataList",//
	addZjSanDjData:"addZjSanDjData",//
	updateZjSanDjData:"updateZjSanDjData",//
	batchDeleteUpdateZjSanDjData:"batchDeleteUpdateZjSanDjData",//
	

	getZjSanDjPersonRegisterList:"getZjSanDjPersonRegisterList",//
	addZjSanDjPersonRegister:"addZjSanDjPersonRegister",//
	updateZjSanDjPersonRegister:"updateZjSanDjPersonRegister",//
	batchDeleteUpdateZjSanDjPersonRegister:"batchDeleteUpdateZjSanDjPersonRegister",//
	getZjSanDjPersonRegisterDetail:"getZjSanDjPersonRegisterDetail",//
	
	
	getZjSanDjCredentialList:"getZjSanDjCredentialList",//
	addZjSanDjCredential:"addZjSanDjCredential",//
	updateZjSanDjCredential:"updateZjSanDjCredential",//
	getZjSanDjCredentialDetails:"getZjSanDjCredentialDetails",//
	
	
	getZjSanDjProfessionalTitleList:"getZjSanDjProfessionalTitleList",//
	addZjSanDjProfessionalTitle:"addZjSanDjProfessionalTitle",//
	updateZjSanDjProfessionalTitle:"updateZjSanDjProfessionalTitle",//
	getZjSanDjProfessionalTitleDetail:"getZjSanDjProfessionalTitleDetail",//
	
	
	getZjSanDjTrainList:"getZjSanDjTrainList",//
	addZjSanDjTrain:"addZjSanDjTrain",//
	batchDeleteUpdateZjSanDjTrain:"batchDeleteUpdateZjSanDjTrain",//
	getZjSanDjTrainDetail:"getZjSanDjTrainDetail",//
	
	
	getZjSanDjPartyRegisterList:"getZjSanDjPartyRegisterList",//
	addZjSanDjPartyRegister:"addZjSanDjPartyRegister",//
	updateZjSanDjPartyRegister:"updateZjSanDjPartyRegister",//
	batchDeleteUpdateZjSanDjPartyRegister:"batchDeleteUpdateZjSanDjPartyRegister",//
	getZjSanDjPartyRegisterDetail:"getZjSanDjPartyRegisterDetail",//
	exportZjSanDjPartyRegister:"exportZjSanDjPartyRegister",//
	
	
	getZjSanDjReunionRegisterList:"getZjSanDjReunionRegisterList",//
	addZjSanDjReunionRegister:"addZjSanDjReunionRegister",//
	updateZjSanDjReunionRegister:"updateZjSanDjReunionRegister",//
	batchDeleteUpdateZjSanDjReunionRegister:"batchDeleteUpdateZjSanDjReunionRegister",//
	getZjSanDjReunionRegisterDetail:"getZjSanDjReunionRegisterDetail",//
	exportCorpZjSanDjReunionRegister:"exportCorpZjSanDjReunionRegister",//
	exportProZjSanDjReunionRegister:"exportProZjSanDjReunionRegister",//
	
	getZjSanDjUnionApplyList:"getZjSanDjUnionApplyList",//
	addZjSanDjUnionApply:"addZjSanDjUnionApply",//
	updateZjSanDjUnionApply:"updateZjSanDjUnionApply",//
	exportCorpZjSanDjUnionRegister:"exportCorpZjSanDjUnionRegister",//
	exportProZjSanDjUnionRegister:"exportProZjSanDjUnionRegister",//
	
	
	getZjSanDjOaTrainList:"getZjSanDjOaTrainList",//
	
	
	getZjSanDjCredDataList:"getZjSanDjCredDataList",//
	addZjSanDjCredData:"addZjSanDjCredData",//
	updateZjSanDjCredData:"updateZjSanDjCredData",//
	batchDeleteUpdateZjSanDjCredData:"batchDeleteUpdateZjSanDjCredData",//
	getZjSanDjCredDataListTwo:"getZjSanDjCredDataListTwo",//
	addZjSanDjCredDataTwo:"addZjSanDjCredDataTwo",//
	getZjSanDjCredDataAllList:"getZjSanDjCredDataAllList",//
	
	
	getZjSanDjProfessDataList:"getZjSanDjProfessDataList",
	addZjSanDjProfessData:"addZjSanDjProfessData",//
	updateZjSanDjProfessData:"updateZjSanDjProfessData",//
	batchDeleteUpdateZjSanDjProfessData:"batchDeleteUpdateZjSanDjProfessData",//
	getZjSanDjProfessDataListTwo:"getZjSanDjProfessDataListTwo",//
	addZjSanDjProfessDataTwo:"addZjSanDjProfessDataTwo",//
	getZjSanDjProfessDataAllList:"getZjSanDjProfessDataAllList",//
	
	
	getZjSanDjOaPersonList:"getZjSanDjOaPersonList",//
	
	getZjSanDjPartyApplyList:"getZjSanDjPartyApplyList",//
	addZjSanDjPartyApply:"addZjSanDjPartyApply",//
	updateZjSanDjPartyApply:"updateZjSanDjPartyApply",//
	getZjSanDjPartyApplyDetail:"getZjSanDjPartyApplyDetail",//
	
	
	
	getZjSanDjPartyFeeList:"getZjSanDjPartyFeeList",//
	addZjSanDjPartyFee:"addZjSanDjPartyFee",//
	updateZjSanDjPartyFee:"updateZjSanDjPartyFee",//
	batchDeleteUpdateZjSanDjPartyFee:"batchDeleteUpdateZjSanDjPartyFee",//
	
	getZjSanDjPartyMemberTransferList:"getZjSanDjPartyMemberTransferList",//
	addZjSanDjPartyMemberTransfer:"addZjSanDjPartyMemberTransfer",//
	updateZjSanDjPartyMemberTransfer:"updateZjSanDjPartyMemberTransfer",//
	batchDeleteUpdateZjSanDjPartyMemberTransfer:"batchDeleteUpdateZjSanDjPartyMemberTransfer",//
	getZjSanDjPartyMemberTransferDetail:"getZjSanDjPartyMemberTransferDetail",//
	
	
	
	getZjSanDjPartySelectionList:"getZjSanDjPartySelectionList",//
	addZjSanDjPartySelection:"addZjSanDjPartySelection",//
	updateZjSanDjPartySelection:"updateZjSanDjPartySelection",//
	batchDeleteUpdateZjSanDjPartySelection:"batchDeleteUpdateZjSanDjPartySelection",//
	getZjSanDjPartySelectionDetail:"getZjSanDjPartySelectionDetail",//
	
	getZjSanDjUnionApplyDetail:"getZjSanDjUnionApplyDetail",//
	getZjSanDjUnionRegisterList:"getZjSanDjUnionRegisterList",//
	updateZjSanDjUnionRegister:"updateZjSanDjUnionRegister",//
	
	
	projectExportZjSanDjPartyFee:"projectExportZjSanDjPartyFee",//
	allExportZjSanDjPartyFee:"allExportZjSanDjPartyFee",//
	importZjSanDjPersonRegister:"importZjSanDjPersonRegister",//
	
	
	
	getZjSanDjPartyMemberOrgList:"getZjSanDjPartyMemberOrgList",//
	addZjSanDjPartyMemberOrg:"addZjSanDjPartyMemberOrg",//
	updateZjSanDjPartyMemberOrg:"updateZjSanDjPartyMemberOrg",//
	batchDeleteUpdateZjSanDjPartyMemberOrg:"batchDeleteUpdateZjSanDjPartyMemberOrg",//
	getZjSanDjPartyOrgAddMemberList:"getZjSanDjPartyOrgAddMemberList",//
	addZjSanDjPartyOrgAddMember:"addZjSanDjPartyOrgAddMember",//
	getZjSanDjPartyMemberOrgDetail:"getZjSanDjPartyMemberOrgDetail",//
	getZjSanDjUnionMemberOrgDetail:"getZjSanDjUnionMemberOrgDetail",//
	getZjSanDjUnionApplyList1:"getZjSanDjUnionApplyList?apih5FlowStatus=2",
	
	
	
	getZjSanDjPartyBranchWorkRecordList:"getZjSanDjPartyBranchWorkRecordList",//
	addZjSanDjPartyBranchWorkRecord:"addZjSanDjPartyBranchWorkRecord",//
	updateZjSanDjPartyBranchWorkRecord:"updateZjSanDjPartyBranchWorkRecord",//
	batchDeleteUpdateZjSanDjPartyBranchWorkRecord:"batchDeleteUpdateZjSanDjPartyBranchWorkRecord",//
	getdZjSanDjPartyBranchWorkRecordDetail:"getdZjSanDjPartyBranchWorkRecordDetail",//
	
	
	getZjSanDjPartyReviewList:"getZjSanDjPartyReviewList",//
	addZjSanDjPartyReview:"addZjSanDjPartyReview",//
	updateZjSanDjPartyReview:"updateZjSanDjPartyReview",//
	batchDeleteUpdateZjSanDjPartyReview:"batchDeleteUpdateZjSanDjPartyReview",//
	getZjSanDjPartyReviewDetail:"getZjSanDjPartyReviewDetail",//
	exportCorpZjSanDjPartyReview:"exportCorpZjSanDjPartyReview",//
	exportProZjSanDjPartyReview:"exportProZjSanDjPartyReview",//
	
	
	
	getZjSanDjUnionFeeList:"getZjSanDjUnionFeeList",//
	addZjSanDjUnionFee:"addZjSanDjUnionFee",//
	updateZjSanDjUnionFee:"updateZjSanDjUnionFee",//
	batchDeleteUpdateZjSanDjUnionFee:"batchDeleteUpdateZjSanDjUnionFee",//
	projectExportZjSanDjUnionFee:"projectExportZjSanDjUnionFee",//
	allExportZjSanDjUnionFee:"allExportZjSanDjUnionFee",//
	
	
	getZjSanDjUnionMemberOrgList:"getZjSanDjUnionMemberOrgList",//
	addZjSanDjUnionMemberOrg:"addZjSanDjUnionMemberOrg",//
	updateZjSanDjUnionMemberOrg:"updateZjSanDjUnionMemberOrg",//
	batchDeleteUpdateZjSanDjUnionMemberOrg:"batchDeleteUpdateZjSanDjUnionMemberOrg",//
	getZjSanDjUnionOrgAddMemberList:"getZjSanDjUnionOrgAddMemberList",//
	addZjSanDjUnionOrgAddMember:"addZjSanDjUnionOrgAddMember",//
	
	
	getZjSanDjUnionMemberTransferList:"getZjSanDjUnionMemberTransferList",//
	addZjSanDjUnionMemberTransfer:"addZjSanDjUnionMemberTransfer",//
	updateZjSanDjUnionMemberTransfer:"updateZjSanDjUnionMemberTransfer",//
	batchDeleteUpdateZjSanDjUnionMemberTransfer:"batchDeleteUpdateZjSanDjUnionMemberTransfer",//
	getZjSanDjUnionMemberTransferDetail:"getZjSanDjUnionMemberTransferDetail",//
	
	
	getZjSanDjUnionMarriageList:"getZjSanDjUnionMarriageList",//
	addZjSanDjUnionMarriage:"addZjSanDjUnionMarriage",//
	updateZjSanDjUnionMarriage:"updateZjSanDjUnionMarriage",//
	batchDeleteUpdateZjSanDjUnionMarriage:"batchDeleteUpdateZjSanDjUnionMarriage",//
	getZjSanDjUnionMarriageDetail:"getZjSanDjUnionMarriageDetail",//
	
	
	getZjSanDjUnionPregnantWomanList:"getZjSanDjUnionPregnantWomanList",//
	addZjSanDjUnionPregnantWoman:"addZjSanDjUnionPregnantWoman",//
	updateZjSanDjUnionPregnantWoman:"updateZjSanDjUnionPregnantWoman",//
	batchDeleteUpdateZjSanDjUnionPregnantWoman:"batchDeleteUpdateZjSanDjUnionPregnantWoman",//
	getZjSanDjUnionPregnantWomanDetail:"getZjSanDjUnionPregnantWomanDetail",//
	exportZjSanDjUnionPregnantWoman:"exportZjSanDjUnionPregnantWoman",//
	
	exportZjSanDjUnionMarriage:"exportZjSanDjUnionMarriage",//
	
	
	
	
	getZjSanDjNotPartyList:"getZjSanDjNotPartyList",//
	getZjSanDjIsPartyList:"getZjSanDjIsPartyList",//
	getZjSanDjNotReunionList:"getZjSanDjNotReunionList",//
	getZjSanDjIsReunionList:"getZjSanDjIsReunionList",//
	getZjSanDjNotUnionList:"getZjSanDjNotUnionList",//
	getZjSanDjIsUnionList:"getZjSanDjIsUnionList",//
	
	
	
	getZjSanDjUnionMarriageBrokerList:"getZjSanDjUnionMarriageBrokerList",//
	addZjSanDjUnionMarriageBroker:"addZjSanDjUnionMarriageBroker",//
	updateZjSanDjUnionMarriageBroker:"updateZjSanDjUnionMarriageBroker",//
	batchDeleteUpdateZjSanDjUnionMarriageBroker:"batchDeleteUpdateZjSanDjUnionMarriageBroker",//
	getZjSanDjUnionMarriageBrokerListTwo:"getZjSanDjUnionMarriageBrokerListTwo",//
	addZjSanDjUnionMarriageBrokerTwo:"addZjSanDjUnionMarriageBrokerTwo",//
	updateZjSanDjUnionMarriageBrokerTwo:"updateZjSanDjUnionMarriageBrokerTwo",//
	
	getZjSanDjReunionRegisterDetail:"getZjSanDjReunionRegisterDetail",//
	getZjSanDjReunionFeeList:"getZjSanDjReunionFeeList",//
	addZjSanDjReunionFee:"addZjSanDjReunionFee",//
	updateZjSanDjReunionFee:"updateZjSanDjReunionFee",//
	projectExportZjSanDjReunionFee:"projectExportZjSanDjReunionFee",//
	allExportZjSanDjReunionFee:"allExportZjSanDjReunionFee",//
	
	
	
	getZjSanDjReunionMemberOrgList:"getZjSanDjReunionMemberOrgList",//
	addZjSanDjReunionMemberOrg:"addZjSanDjReunionMemberOrg",//
	updateZjSanDjReunionMemberOrg:"updateZjSanDjReunionMemberOrg",//
	batchDeleteUpdateZjSanDjReunionMemberOrg:"batchDeleteUpdateZjSanDjReunionMemberOrg",//
	getZjSanDjReunionOrgAddMemberList:"getZjSanDjReunionOrgAddMemberList",//
	addZjSanDjReunionOrgAddMember:"addZjSanDjReunionOrgAddMember",//
	getZjSanDjReunionMemberOrgDetail:"getZjSanDjReunionMemberOrgDetail",//
	
	
	getZjSanDjReunionMemberTransferList:"getZjSanDjReunionMemberTransferList",//
	addZjSanDjReunionMemberTransfer:"addZjSanDjReunionMemberTransfer",//
	updateZjSanDjReunionMemberTransfer:"updateZjSanDjReunionMemberTransfer",//
	batchDeleteUpdateZjSanDjReunionMemberTransfer:"batchDeleteUpdateZjSanDjReunionMemberTransfer",//
	getZjSanDjReunionMemberTransferDetail:"getZjSanDjReunionMemberTransferDetail",//
	
	exportZjSanDjUnionMarriageBroker:"exportZjSanDjUnionMarriageBroker",//
	
	
	

	getZjSanDjSuggestList:"getZjSanDjSuggestList",//
	addZjSanDjSuggest:"addZjSanDjSuggest",//
	updateZjSanDjSuggest:"updateZjSanDjSuggest",//
	getZjSanDjSuggestDetail:"getZjSanDjSuggestDetail",//
	
	
	getZjSanDjMonograpList:"getZjSanDjMonograpList",//
	addZjSanDjMonograp:"addZjSanDjMonograp",//
	updateZjSanDjMonograp:"updateZjSanDjMonograp",//
	getZjSanDjMonograpDetail:"getZjSanDjMonograpDetail",//
	
	getZjSanDjSkillList:"getZjSanDjSkillList",//
	addZjSanDjSkill:"addZjSanDjSkill",//
	updateZjSanDjSkill:"updateZjSanDjSkill",//
	getZjSanDjSkillDetail:"getZjSanDjSkillDetail",//
	
	getZjSanDjStyleGameList:"getZjSanDjStyleGameList",//
	addZjSanDjStyleGame:"addZjSanDjStyleGame",//
	getZjSanDjStyleGameDetail:"getZjSanDjStyleGameDetail",//
	updateZjSanDjStyleGame:"updateZjSanDjStyleGame",//
	
	getZjSanDjScientificList:"getZjSanDjScientificList",//
	addZjSanDjScientific:"addZjSanDjScientific",//
	updateZjSanDjScientific:"updateZjSanDjScientific",//
	getZjSanDjScientificDetail:"getZjSanDjScientificDetail",//
	
	getZjSanDjQcList:"getZjSanDjQcList",//
	addZjSanDjQc:"addZjSanDjQc",//
	updateZjSanDjQc:"updateZjSanDjQc",//
	getZjSanDjQcDetail:"getZjSanDjQcDetail",//
	
	getZjSanDjPaperList:"getZjSanDjPaperList",//
	addZjSanDjPaper:"addZjSanDjPaper",//
	updateZjSanDjPaper:"updateZjSanDjPaper",//
	getZjSanDjPaperDetail:"getZjSanDjPaperDetail",//
	
	
	getZjSanDjHonorList:"getZjSanDjHonorList",//
	addZjSanDjHonor:"addZjSanDjHonor",//
	updateZjSanDjHonor:"updateZjSanDjHonor",//
	getZjSanDjHonorDetail:"getZjSanDjHonorDetail",//
	
	getZjSanDjFourNewList:"getZjSanDjFourNewList",//
	addZjSanDjFourNew:"addZjSanDjFourNew",//
	updateZjSanDjFourNew:"updateZjSanDjFourNew",//
	getZjSanDjFourNewDetail:"getZjSanDjFourNewDetail",//
	
	
	getZjSanDjEducationList:"getZjSanDjEducationList",//
	addZjSanDjEducation:"addZjSanDjEducation",//
	updateZjSanDjEducation:"updateZjSanDjEducation",//
	getZjSanDjEducationDetail:"getZjSanDjEducationDetail",//
	
	getZjSanDjCollegeList:"getZjSanDjCollegeList",//
	addZjSanDjCollege:"addZjSanDjCollege",//
	updateZjSanDjCollege:"updateZjSanDjCollege",//
	getZjSanDjCollegeDetail:"getZjSanDjCollegeDetail",//
	
	
	getZjSanDjBusinessList:"getZjSanDjBusinessList",//
	addZjSanDjBusiness:"addZjSanDjBusiness",//
	updateZjSanDjBusiness:"updateZjSanDjBusiness",//
	getZjSanDjBusinessDetail:"getZjSanDjBusinessDetail",//
	
	
	
	
	getZjSanDjTrainList:"getZjSanDjTrainList",//
	addZjSanDjTrain:"addZjSanDjTrain",//
	getZjSanDjTrainDetail:"getZjSanDjTrainDetail",//
	
	getZjSanDjTrainerList:"getZjSanDjTrainerList",//
	addZjSanDjTrainer:"addZjSanDjTrainer",//
	updateZjSanDjTrainer:"updateZjSanDjTrainer",//
	
	
	
	getZjSanDjTutorApprenticeList:"getZjSanDjTutorApprenticeList",//
	addZjSanDjTutorApprentice:"addZjSanDjTutorApprentice",//
	updateZjSanDjTutorApprentice:"updateZjSanDjTutorApprentice",//
	batchDeleteUpdateZjSanDjTutorApprentice:"batchDeleteUpdateZjSanDjTutorApprentice",//
	addZjSanDjApprentice:"addZjSanDjApprentice",//
	updateZjSanDjApprentice:"updateZjSanDjApprentice",//
	getZjSanDjTutorApprenticeDetail:"getZjSanDjTutorApprenticeDetail",//
	
	
	
	
	getZjSanDjPartyIntroduceList:"getZjSanDjPartyIntroduceList",//
	addZjSanDjPartyIntroduce:"addZjSanDjPartyIntroduce",//
	updateZjSanDjPartyIntroduce:"updateZjSanDjPartyIntroduce",//
	getZjSanDjPartyIntroduceDetail:"getZjSanDjPartyIntroduceDetail",//
	
	getZjSanDjUnionIntroduceList:"getZjSanDjUnionIntroduceList",//
	addZjSanDjUnionIntroduce:"addZjSanDjUnionIntroduce",//
	updateZjSanDjUnionIntroduce:"updateZjSanDjUnionIntroduce",//
	getZjSanDjUnionIntroduceDetail:"getZjSanDjUnionIntroduceDetail",//
	
	getZjSanDjGradeTotalList:"getZjSanDjGradeTotalList",
	getZjSanDjGradeTotalDetailList:"getZjSanDjGradeTotalDetailList",//
	
	exportZjSanDjGradeTotal:"exportZjSanDjGradeTotal",//
	importZjSanDjGradeStandard:"importZjSanDjGradeStandard",//
	importZjSanDjGradeStandardForThree:"importZjSanDjGradeStandardForThree",//
	
	
	getFlowCountByAll:"getFlowCountByAll",
	
	
	
	
	//评分标准库
	getZjSanDjGradeStandardList:"getZjSanDjGradeStandardList",//	
	addZjSanDjGradeStandard:"addZjSanDjGradeStandard",//
	updateZjSanDjGradeStandard:"updateZjSanDjGradeStandard",//
	batchDeleteUpdateZjSanDjGradeStandard:"batchDeleteUpdateZjSanDjGradeStandard",//
/* 	getZjSanDjGradeStandardSelectList1:"getZjSanDjGradeStandardSelectList?gradeType=ZJJL",//
	getZjSanDjGradeStandardSelectList2:"getZjSanDjGradeStandardSelectList?gradeType=ZCJL",//
    getZjSanDjGradeStandardSelectList3:"getZjSanDjGradeStandardSelectList?gradeType=PXJL",//
	getZjSanDjGradeStandardSelectList4:"getZjSanDjGradeStandardSelectList?gradeType=RYJL",//	
	getZjSanDjGradeStandardSelectList5:"getZjSanDjGradeStandardSelectList?gradeType=KYCGJL",//
	getZjSanDjGradeStandardSelectList6:"getZjSanDjGradeStandardSelectList?gradeType=QCCGJL",//
	getZjSanDjGradeStandardSelectList7:"getZjSanDjGradeStandardSelectList?gradeType=XLJL",//	
	getZjSanDjGradeStandardSelectList8:"getZjSanDjGradeStandardSelectList?gradeType=JNJSJL",//	
	getZjSanDjGradeStandardSelectList9:"getZjSanDjGradeStandardSelectList?gradeType=LWJL",//
	getZjSanDjGradeStandardSelectList10:"getZjSanDjGradeStandardSelectList?gradeType=WTBSJL",//
	getZjSanDjGradeStandardSelectList11:"getZjSanDjGradeStandardSelectList?gradeType=ZSXYJL",//
	getZjSanDjGradeStandardSelectList12:"getZjSanDjGradeStandardSelectList?gradeType=ZZJL",//	
	getZjSanDjGradeStandardSelectList13:"getZjSanDjGradeStandardSelectList?gradeType=YWJLJXSHBJL",//	
	getZjSanDjGradeStandardSelectList14:"getZjSanDjGradeStandardSelectList?gradeType=JYJJSGXJL",//
	getZjSanDjGradeStandardSelectList15:"getZjSanDjGradeStandardSelectList?gradeType=SXYYJL",// */	
	/* getZjSanDjGradeStandardList1:"getZjSanDjGradeStandardList?gradeType=GR1",
	getZjSanDjGradeStandardList2:"getZjSanDjGradeStandardList?gradeType=JT2",
	getZjSanDjGradeStandardList3:"getZjSanDjGradeStandardList?gradeType=JT3",
	getZjSanDjGradeStandardList4:"getZjSanDjGradeStandardList?gradeType=JSCX4",
	getZjSanDjGradeStandardList5:"getZjSanDjGradeStandardList?gradeType=ZYKJ5",
	getZjSanDjGradeStandardList6:"getZjSanDjGradeStandardList?gradeType=ZL6",
	getZjSanDjGradeStandardList7:"getZjSanDjGradeStandardList?gradeType=HLJY7",
	getZjSanDjGradeStandardList8:"getZjSanDjGradeStandardList?gradeType=XSLU8",
	getZjSanDjGradeStandardList9:"getZjSanDjGradeStandardList?gradeType=JSBZ9",
	getZjSanDjGradeStandardList10:"getZjSanDjGradeStandardList?gradeType=QC10",
	getZjSanDjGradeStandardList11:"getZjSanDjGradeStandardList?gradeType=KYXM11",
	getZjSanDjGradeStandardList12:"getZjSanDjGradeStandardList?gradeType=SGBZ12", */
	//2级
	getZjSanDjGradeStandardListTwo:"getZjSanDjGradeStandardListTwo",
	addZjSanDjGradeStandardTwo:"addZjSanDjGradeStandardTwo",

	

	getZjSanDjGradeGroupByGradeTypeList:"getZjSanDjGradeGroupByGradeTypeList",
	getZjSanDjGradeDetailList:"getZjSanDjGradeDetailList",
	getZjSanDjGuideList:"getZjSanDjGuideList",
	getZjSanDjPersonRegisterForLeaveList:"getZjSanDjPersonRegisterForLeaveList",
	returnZjSanDjPersonRegister:"returnZjSanDjPersonRegister",
	
	apiOaDepartmentListBySanDj:"apiOaDepartmentListBySanDj",//
	apiOaUserListBySanDj:"apiOaUserListBySanDj"//
	
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
window.lny = window.l = new Lny(apiNames,'http://192.168.1.122:99/web/')
//window.lny = window.l = new Lny(apiNames,'http://192.168.1.122:8080/web/')
//window.lny = window.l = new Lny(apiNames,'http://weixin.fheb.cn:99/apiwoa/')
//window.lny = window.l = new Lny(apiNames,'http://weixin.fheb.cn:99/apisangs/')