(window.webpackJsonp=window.webpackJsonp||[]).push([[234],{E5uW:function(e,t,a){"use strict";a.r(t);a("9U97"),a("x/6R"),a("MRnW"),a("IdsT"),a("l9AF"),a("orcL"),a("w9C0");var l=a("XW+2"),o=a("C9HG"),i=a("UpC8"),n=(a("fx2i"),a("cZi0")),r=a("Id3l"),s=a("RNsw"),d=a("OR/T"),p=a("bn5B"),c=a("PnTt"),f=a("r0ML"),y=a.n(f),h=a("6eYn"),m=a("vcLm"),u=a("U8v4"),b=a("xoHy"),I=a.n(b),w={fetchConfig:{apiName:"getZjTzPolicyCountryReplyList",otherParams:{replyFlag:"1"}}},g=function(e){function t(){var e;return Object(r.a)(this,t),(e=Object(d.a)(this,Object(p.a)(t).call(this))).state={visible:!1,replyData:[]},e}return Object(c.a)(t,e),Object(s.a)(t,[{key:"render",value:function(){var e=this,t=this.state,a=t.visible,r=t.replyData;return y.a.createElement("div",{className:I.a.root},y.a.createElement(h.a,Object(i.a)({},this.props,{fetch:this.props.myFetch,upload:this.props.myUpload,headers:{token:this.props.loginAndLogoutInfo.loginInfo.token},wrappedComponentRef:function(t){e.table=t}},w,{method:{willExecuteClick:function(t){t.btnCallbackFn.clearSelectedRows(),1===t.selectedRows.length?e.setState({replyData:t.selectedRows,visible:!0}):n.b.error("\u8bf7\u9009\u62e9\u4e00\u6761\u6570\u636e\uff01")}},actionBtns:{apiName:"getSysMenuBtn",otherParams:function(e){var t=e.Pprops;return{menuParentId:t.routerInfo.routeData[t.routerInfo.curKey]._value,tableField:"projectInfo"}}}},window.PolicyResponsesPage,{formConfig:[{isInTable:!1,form:{field:"policyId",type:"string",hide:!0}},{isInTable:!1,form:{field:"policyReplyId",type:"string",hide:!0}},{isInSearch:!1,table:{title:"\u6807\u9898",dataIndex:"title",key:"title",width:200,onClick:"detail",fixed:"left"},form:{type:"string",placeholder:"\u8bf7\u8f93\u5165",required:!0}},{isInSearch:!1,isInForm:!1,table:{title:"\u6700\u8fd1\u56de\u590d\u5185\u5bb9",dataIndex:"replyInfo",key:"replyInfo",width:150,fixed:"left"}},{isInSearch:!1,table:{title:"\u6587\u53f7",dataIndex:"symbolNo",key:"symbolNo",width:150},form:{type:"string",required:!0,placeholder:"\u8bf7\u8f93\u5165"}},{table:{title:"\u53d1\u6587\u90e8\u95e8",dataIndex:"departmentName",key:"departmentName",width:100},form:{field:"departmentName",type:"string",required:!0,placeholder:"\u8bf7\u8f93\u5165"}},{table:{title:"\u7cfb\u7edf\u53d1\u5e03\u65e5\u671f",dataIndex:"sysDate",key:"sysDate",format:"YYYY-MM-DD",width:120},form:{type:"date",required:!0,placeholder:"\u8bf7\u8f93\u5165"}},{table:{title:"\u539f\u6587\u53d1\u5e03\u65e5\u671f",dataIndex:"releaseDate",key:"releaseDate",format:"YYYY-MM-DD",width:120},form:{required:!0,type:"date",placeholder:"\u8bf7\u8f93\u5165"}},{table:{title:"\u767b\u8bb0\u7528\u6237",dataIndex:"registerUser",key:"registerUser",width:100},form:{required:!0,type:"string",placeholder:"\u8bf7\u8f93\u5165"}},{table:{title:"\u662f\u5426\u6709\u6548\u6587\u4ef6",dataIndex:"effectiveName",key:"effectiveName",width:120},form:{type:"radio",required:!0,label:"\u662f\u5426\u6709\u6548\u6587\u4ef6",field:"effectiveId",optionData:[{label:"\u5426",value:"0"},{label:"\u662f",value:"1"}]}},{table:{title:"\u5206\u6790\u62a5\u544a",dataIndex:"report",key:"report",width:150},form:{type:"textarea",required:!0,placeholder:"\u8bf7\u8f93\u5165"}},{isInTable:!1,form:{label:"\u9644\u4ef6",field:"zjTzFileList",type:"files",showDownloadIcon:!0,onPreview:"bind:_docFilesByOfficeUrl",fetchConfig:{apiName:window.configs.domain+"upload",otherParams:{name:"\u56fd\u5bb6\u653f\u7b56\u56de\u590d"}}}},{isInForm:!1,table:{title:"\u64cd\u4f5c",fixed:"right",dataIndex:"action",key:"action",align:"center",noHaveSearchInput:!0,showType:"tile",width:80,btns:[{name:"PolicyDetail",render:function(e){return"<a>\u56de\u590d\u8bb0\u5f55</a>"},onClick:function(e){var t=e.props.myPublic.appInfo.mainModule,a=e.rowData.policyReplyId;e.props.dispatch(Object(u.push)("".concat(t,"PolicyResponsesDetail/").concat(a)))}}]}}]})),a?y.a.createElement("div",null,y.a.createElement(l.a,{width:"40%",style:{paddingBottom:"0",top:"0"},title:"\u56de\u590d",visible:a,footer:null,onCancel:this.handleCancel,bodyStyle:{padding:"10px",overflow:"hidden"},centered:!0,closable:!1,maskClosable:!1,wrapClassName:"PlansToPush"},y.a.createElement(m.a,{form:this.props.form,history:this.props.history,match:this.props.match,fetch:this.props.myFetch,upload:this.props.myUpload,headers:{token:this.props.loginAndLogoutInfo.loginInfo.token},formItemLayout:{labelCol:{xs:{span:7},sm:{span:7}},wrapperCol:{xs:{span:17},sm:{span:17}}},formConfig:[{type:"textarea",label:"\u5e94\u5bf9\u9884\u6848\u53ca\u5176\u6548\u679c",field:"replyInfo",required:!0,placeholder:"\u8bf7\u8f93\u5165"},{label:"\u9644\u4ef6",field:"zjTzPolicyCountryReplyFileList",type:"files",showDownloadIcon:!0,onPreview:"bind:_docFilesByOfficeUrl",fetchConfig:{apiName:window.configs.domain+"upload",otherParams:{name:"\u56fd\u5bb6\u653f\u7b56\u56de\u590d"}}}],btns:[{name:"cancel",type:"dashed",label:"\u53d6\u6d88",isValidate:!1,onClick:function(t){e.setState({visible:!1})}},{name:"submit",type:"primary",label:"\u786e\u5b9a",onClick:function(t){var a;r[0].replyInfo=t.values.replyInfo,r[0].zjTzPolicyCountryReplyFileList=t.values.zjTzPolicyCountryReplyFileList,(a=t.btnfns).fetchByCb.apply(a,["updateZjTzPolicyCountryReply"].concat(Object(o.a)(r),[function(t){t.data;var a=t.success,l=t.message;a?(n.b.success(l),e.setState({visible:!1}),e.table.refresh()):n.b.error(l)}]))}}]}))):null)}}]),t}(f.Component);t.default=g}}]);