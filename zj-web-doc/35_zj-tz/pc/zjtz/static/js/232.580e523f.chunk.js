(window.webpackJsonp=window.webpackJsonp||[]).push([[232],{orMH:function(e,t,a){"use strict";a.r(t);a("9U97"),a("x/6R"),a("zIPO"),a("MRnW"),a("IdsT"),a("l9AF"),a("orcL");var l=a("C9HG"),s=a("UpC8"),o=(a("fx2i"),a("cZi0")),n=a("Id3l"),r=a("RNsw"),c=a("OR/T"),i=a("bn5B"),d=a("PnTt"),p=(a("w9C0"),a("XW+2")),u=a("r0ML"),h=a.n(u),f=a("6eYn"),b=a("vcLm"),w=a("U8v4"),m=(a("ID3L"),a("u2xe")),y=a.n(m),I=p.a.confirm,R={antd:{rowKey:function(e){return e.policyId},size:"small",scroll:{y:.65*document.documentElement.clientHeight}},drawerConfig:{width:"900px"},paginationConfig:{position:"bottom"},firstRowIsSearch:!1,formItemLayout:{labelCol:{xs:{span:24},sm:{span:4}},wrapperCol:{xs:{span:24},sm:{span:20}}}},g=function(e){function t(){var e;return Object(n.a)(this,t),(e=Object(c.a)(this,Object(i.a)(t).call(this))).state={visible:!1,PlansToPushData:[]},e}return Object(d.a)(t,e),Object(r.a)(t,[{key:"render",value:function(){var e=this,t=this.state,a=t.PlansToPushData,n=t.visible,r=this.props.loginAndLogoutInfo.loginInfo.userInfo,c=r.realName,i=r.ext1,d=r.curCompany,u=d.projectShortName,m=d.projectId;return h.a.createElement("div",{className:y.a.root},h.a.createElement(f.a,Object(s.a)({},this.props,{fetch:this.props.myFetch,upload:this.props.myUpload,headers:{token:this.props.loginAndLogoutInfo.loginInfo.token},wrappedComponentRef:function(t){e.table=t},fetchConfig:{apiName:"getZjTzPolicyLocalList",otherParams:{projectId:m}},method:{addClick:function(e){"all"===m&&(e.btnCallbackFn.closeDrawer(),e.btnCallbackFn.clearSelectedRows(),o.b.warn("\u8bf7\u9009\u62e9\u53f3\u4e0a\u89d2\u9879\u76ee\uff01"))},editClick:function(t){e.table.clearSelectedRows(),"1"===t.selectedRows[0].statusId?(o.b.warn("\u5df2\u4e0a\u62a5\u7684\u6570\u636e\u4e0d\u80fd\u4fee\u6539\uff01"),t.btnCallbackFn.closeDrawer()):"1"===t.selectedRows[0].releaseId?(t.btnCallbackFn.closeDrawer(),o.b.warn("\u5df2\u53d1\u5e03\u7684\u4e0d\u80fd\u4fee\u6539!"),e.table.clearSelectedRows()):e.table.clearSelectedRows()},faBuClick:function(t){e.table.clearSelectedRows();var a=t.props.myFetch;if(t.selectedRows.length>0){for(var l=[],s=[],n=0;n<t.selectedRows.length;n++)"1"===t.selectedRows[n].releaseId&&l.push(t.selectedRows[n].releaseId),"2"!==t.selectedRows[n].statusId&&"0"!==t.selectedRows[n].statusId||(console.log(t.selectedRows[n].statusId),s.push(t.selectedRows[n].statusId));s.length>0?o.b.warn("\u672a\u4e0a\u62a5/\u88ab\u9000\u56de\u7684\u6570\u636e\u4e0d\u80fd\u53d1\u5e03\uff01"):l.length>0?o.b.warn("\u5df2\u53d1\u5e03\u7684\u6d88\u606f\u4e0d\u80fd\u53d1\u5e03\uff01"):I({title:"\u786e\u5b9a\u53d1\u5e03\u4e48?",okText:"\u786e\u8ba4",cancelText:"\u53d6\u6d88",onOk:function(){a("batchDeleteReleaseZjTzPolicyLocal",t.selectedRows).then(function(t){var a=t.success,l=t.message;a?(o.b.success(l),e.table.refresh()):o.b.error(l)})}})}else o.b.warn("\u8bf7\u9009\u62e9\u6570\u636e")},cheHuiClick:function(t){e.table.clearSelectedRows();var a=t.props.myFetch;if(t.selectedRows.length>0){for(var l=[],s=[],n=0;n<t.selectedRows.length;n++)"0"===t.selectedRows[n].releaseId&&l.push(t.selectedRows[n].releaseId),"0"!==t.selectedRows[n].statusId&&"2"!==t.selectedRows[n].statusId||s.push(t.selectedRows[n].statusId);s.length>0?o.b.warn("\u8bf7\u9009\u62e9\u5df2\u53d1\u5e03\u7684\u6570\u636e\uff01"):l.length>0?o.b.warn("\u672a\u53d1\u5e03\u7684\u6d88\u606f\u4e0d\u80fd\u64a4\u56de\uff01"):I({title:"\u786e\u5b9a\u64a4\u56de\u4e48?",okText:"\u786e\u8ba4",cancelText:"\u53d6\u6d88",onOk:function(){a("batchDeleteRecallZjTzPolicyLocal",t.selectedRows).then(function(t){var a=t.success,l=t.message;a?(o.b.success(l),e.table.refresh()):o.b.error(l)})}})}else o.b.warn("\u8bf7\u9009\u62e9\u6570\u636e")},fenXiBaoGao:function(t){e.table.clearSelectedRows(),t.selectedRows.length>0?1===t.selectedRows.length?"1"===t.selectedRows[0].statusId?"1"===t.selectedRows[0].releaseId?e.setState({PlansToPushData:t.selectedRows,visible:!0}):o.b.warn("\u672a\u53d1\u5e03\u7684\u6d88\u606f\u4e0d\u80fd\u63a8\u9001\uff01"):o.b.warn("\u672a\u4e0a\u62a5/\u88ab\u9000\u56de\u7684\u6d88\u606f\u4e0d\u80fd\u63a8\u9001\uff01"):o.b.warn("\u8bf7\u9009\u62e9\u4e00\u6761\u6570\u636e"):o.b.warn("\u8bf7\u9009\u62e9\u6570\u636e")},guangErGaoZhi:function(t){e.table.clearSelectedRows(),t.selectedRows.length>0?1===t.selectedRows.length?"1"===t.selectedRows[0].statusId?"1"===t.selectedRows[0].releaseId?I({title:"\u786e\u5b9a\u5e7f\u800c\u544a\u4e4b\u5230\u9996\u9875\u4e48\uff1f",okText:"\u786e\u8ba4",cancelText:"\u53d6\u6d88",onOk:function(){e.props.myFetch("updateZjTzPolicyLocalHomeShow",t.selectedRows[0]).then(function(t){var a=t.success,l=t.message;t.data;a?(o.b.success(l),e.table.refresh()):o.b.error(l)})}}):o.b.warn("\u672a\u53d1\u5e03\u7684\u6d88\u606f\u4e0d\u80fd\u5e7f\u800c\u544a\u4e4b\uff01"):o.b.warn("\u672a\u4e0a\u62a5/\u88ab\u9000\u56de\u7684\u6d88\u606f\u4e0d\u80fd\u63a8\u9001\uff01"):o.b.warn("\u8bf7\u9009\u62e9\u4e00\u6761\u6570\u636e"):o.b.warn("\u8bf7\u9009\u62e9\u6570\u636e")},filestExport:function(t){e.table.clearSelectedRows();var a=t.props.myFetch;t.selectedRows.length>0?I({title:"\u786e\u5b9a\u5bfc\u51fa\u9644\u4ef6\u4e48?",okText:"\u786e\u8ba4",cancelText:"\u53d6\u6d88",onOk:function(){a("batchExportZjTzPolicyLocalFile",t.selectedRows).then(function(t){var a=t.success,l=t.message,s=t.data;a?(o.b.success(l),window.location.href=s,e.table.refresh(),e.table.clearSelectedRows()):o.b.error(l)})}}):o.b.warn("\u8bf7\u9009\u62e9\u6570\u636e")},delClick:function(t){var a=t.props.myFetch,l=[],s=[];if(t.selectedRows.length>0){for(var n=0;n<t.selectedRows.length;n++)"1"===t.selectedRows[n].releaseId&&l.push(t.selectedRows[n].releaseId);for(var r=0;r<t.selectedRows.length;r++)"1"===t.selectedRows[r].statusId&&s.push(t.selectedRows[r].statusId);s.length>0?(t.btnCallbackFn.closeDrawer(),o.b.warn("\u5df2\u4e0a\u62a5\u7684\u6570\u636e\u4e0d\u80fd\u5220\u9664!"),e.table.clearSelectedRows()):l.length>0?(t.btnCallbackFn.closeDrawer(),o.b.warn("\u5df2\u53d1\u5e03\u7684\u4e0d\u80fd\u5220\u9664!"),e.table.clearSelectedRows()):I({title:"\u786e\u5b9a\u5220\u9664\u4e48?",okText:"\u786e\u8ba4",cancelText:"\u53d6\u6d88",onOk:function(){a("batchDeleteUpdateZjTzPolicyLocal",t.selectedRows).then(function(t){var a=t.success,l=t.message;a?(o.b.success(l),e.table.refresh(),e.table.clearSelectedRows()):o.b.error(l)})}})}else o.b.warn("\u8bf7\u9009\u62e9\u6570\u636e!")},shangBaoClick:function(t){e.table.clearSelectedRows();var a=t.props.myFetch;if(t.selectedRows.length>0){for(var l=[],s=0;s<t.selectedRows.length;s++)"1"===t.selectedRows[s].statusId&&l.push(t.selectedRows[s].statusId);l.length>0?o.b.warn("\u5df2\u4e0a\u62a5\u7684\u6d88\u606f\u4e0d\u80fd\u4e0a\u62a5\uff01"):I({title:"\u786e\u5b9a\u4e0a\u62a5\u4e48?",okText:"\u786e\u8ba4",cancelText:"\u53d6\u6d88",onOk:function(){a("batchReportZjTzPolicyLocal",t.selectedRows).then(function(t){var a=t.success,l=t.message;a?(o.b.success(l),e.table.refresh()):o.b.error(l)})}})}else o.b.warn("\u8bf7\u9009\u62e9\u6570\u636e")},tuiHuiClick:function(t){e.table.clearSelectedRows();var a=t.props.myFetch;if(t.selectedRows.length>0){for(var l=[],s=[],n=0;n<t.selectedRows.length;n++)"0"!==t.selectedRows[n].statusId&&"2"!==t.selectedRows[n].statusId||l.push(t.selectedRows[n].statusId),"1"===t.selectedRows[n].releaseId&&s.push(t.selectedRows[n].releaseId);s.length>0?o.b.warn("\u5df2\u53d1\u5e03\u7684\u6d88\u606f\u4e0d\u80fd\u9000\u56de\uff01"):l.length>0?o.b.warn("\u672a\u4e0a\u62a5/\u88ab\u9000\u56de\u7684\u6d88\u606f\u4e0d\u80fd\u9000\u56de\uff01"):I({title:"\u786e\u5b9a\u9000\u56de\u4e48?",okText:"\u786e\u8ba4",cancelText:"\u53d6\u6d88",onOk:function(){a("batchReturnZjTzPolicyLocal",t.selectedRows).then(function(t){var a=t.success,l=t.message;a?(o.b.success(l),e.table.refresh()):o.b.error(l)})}})}else o.b.warn("\u8bf7\u9009\u62e9\u6570\u636e")}},actionBtns:{apiName:"getSysMenuBtn",otherParams:function(e){var t=e.Pprops;return{menuParentId:t.routerInfo.routeData[t.routerInfo.curKey]._value,tableField:"projectInfo"}}}},R,{desc:"1"===i?"\u53d1\u5e03\uff1a\u4f7f\u6570\u636e\u5168\u5e73\u53f0\u7528\u6237\u53ef\u89c1\uff1b\u5e7f\u800c\u544a\u4e4b\uff1a\u5c06\u5df2\u53d1\u5e03\u7684\u6570\u636e\u5c55\u793a\u81f3\u9996\u9875":null,formConfig:[{isInTable:!1,form:{field:"policyId",type:"string",hide:!0}},{isInTable:!1,form:{field:"projectId",type:"string",initialValue:m,hide:!0}},{isInTable:!1,form:{label:"\u767b\u8bb0\u9879\u76ee",field:"projectShortName",type:"string",initialValue:u,placeholder:"\u8bf7\u8f93\u5165",required:!0,addDisabled:!0,editDisabled:!0}},{table:{title:"\u6807\u9898",dataIndex:"title",key:"title",width:300,onClick:"detail",filter:!0,fixed:"left"},form:{type:"string",placeholder:"\u8bf7\u8f93\u5165",required:!0}},{isInTable:!1,form:{type:"string",label:"\u6587\u53f7",field:"symbolNo",required:!0,placeholder:"\u8bf7\u8f93\u5165"}},{table:{title:"\u6240\u5c5e\u7701\u4efd",dataIndex:"provinceId",key:"provinceId",width:100,filter:!0,type:"select"},form:{type:"select",showSearch:!0,optionConfig:{label:"itemName",value:"itemId"},fetchConfig:{apiName:"getBaseCodeSelect",otherParams:{itemId:"xingzhengquhuadaima"}},placeholder:"\u8bf7\u8f93\u5165"}},{isInTable:!1,form:{type:"date",label:"\u7cfb\u7edf\u53d1\u5e03\u65e5\u671f",field:"sysDate",required:!0,initialValue:new Date,placeholder:"\u8bf7\u9009\u62e9"}},{table:{title:"\u53d1\u6587\u90e8\u95e8",filter:!0,dataIndex:"departmentName",key:"departmentName",width:120,tooltip:23},form:{field:"departmentName",label:"\u53d1\u6587\u90e8\u95e8",type:"string",required:!0,placeholder:"\u8bf7\u8f93\u5165"}},{table:{title:"\u539f\u6587\u751f\u6548\u65e5\u671f",filter:!0,dataIndex:"effectDate",key:"effectDate",format:"YYYY-MM-DD",width:120},form:{required:!0,type:"date",placeholder:"\u8bf7\u9009\u62e9"}},{table:{title:"\u539f\u6587\u53d1\u5e03\u65e5\u671f",filter:!0,dataIndex:"releaseDate",key:"releaseDate",format:"YYYY-MM-DD",width:120},form:{required:!0,type:"date",placeholder:"\u8bf7\u9009\u62e9"}},{isInTable:!1,form:{required:!0,type:"string",label:"\u767b\u8bb0\u7528\u6237",field:"registerUser",initialValue:c,placeholder:"\u8bf7\u8f93\u5165",addDisabled:!0,editDisabled:!0}},{table:{title:"\u662f\u5426\u6709\u6548\u6587\u4ef6",filter:!0,dataIndex:"effectiveId",key:"effectiveId",width:120,type:"select"},form:{type:"select",required:!0,label:"\u662f\u5426\u6709\u6548\u6587\u4ef6",field:"effectiveId",initialValue:"1",optionData:[{label:"\u5426",value:"0"},{label:"\u662f",value:"1"}]}},{isInTable:!1,form:{type:"textarea",label:"\u5206\u6790\u62a5\u544a",field:"report",required:!0,autoSize:{minRows:2},placeholder:"\u8bf7\u8f93\u5165"}},{isInForm:!1,table:{title:"\u56de\u590d\u6570/\u63a8\u9001\u6570",dataIndex:"pushInfoReply",key:"pushInfoReply",width:120,render:function(e,t){return h.a.createElement("a",null,t.pushInfoReply,"/",t.pushInfoAll)},onClick:function(e){var t=e.props.myPublic.appInfo.mainModule,a=e.rowData.policyId;e.props.dispatch(Object(w.push)("".concat(t,"PlaceDetail/").concat(a)))}}},{table:{title:"\u662f\u5426\u5728\u9996\u9875\u5e7f\u800c\u544a\u4e4b",dataIndex:"homeShow",filter:!0,width:160,key:"homeShow",render:function(e){return"0"===e?"\u5426":"\u662f"}},isInForm:!1,form:{type:"select",field:"homeShow",hide:!0,optionData:[{label:"\u5426",value:"0"},{label:"\u662f",value:"1"}]}},{table:{title:"\u53d1\u5e03\u72b6\u6001",dataIndex:"releaseName",key:"releaseName",filter:!0,width:100},isInForm:!1,form:{type:"select",field:"releaseId",optionData:[{label:"\u672a\u53d1\u5e03",value:"0"},{label:"\u53d1\u5e03",value:"1"}]}},{table:{title:"\u4e0a\u62a5\u72b6\u6001",width:120,dataIndex:"statusId",key:"statusId",type:"select"},isInForm:!1,form:{type:"select",field:"statusId",optionData:[{label:"\u672a\u4e0a\u62a5",value:"0"},{label:"\u5df2\u4e0a\u62a5",value:"1"},{label:"\u88ab\u9000\u56de",value:"2"}]}},{isInTable:!1,form:{label:"\u9644\u4ef6",field:"zjTzFileList",type:"files",showDownloadIcon:!0,onPreview:"bind:_docFilesByOfficeUrl",fetchConfig:{apiName:window.configs.domain+"upload",otherParams:{name:"\u5730\u65b9\u653f\u7b56"}}}},{isInForm:!1,table:{title:"\u64cd\u4f5c",fixed:"right",dataIndex:"action",key:"action",align:"center",noHaveSearchInput:!0,showType:"tile",width:80,btns:[{name:"PlaceDetail",render:function(e){return"<a>\u9884\u6848\u8be6\u60c5</a>"},onClick:function(e){var t=e.props.myPublic.appInfo.mainModule,a=e.rowData.policyId;e.props.dispatch(Object(w.push)("".concat(t,"PlaceDetail/").concat(a)))}}]}}]})),n?h.a.createElement("div",null,h.a.createElement(p.a,{width:"40%",style:{paddingBottom:"0",top:"0"},title:"\u9884\u6848\u63a8\u9001",visible:n,footer:null,onCancel:this.handleCancel,bodyStyle:{padding:"10px",overflow:"hidden"},centered:!0,closable:!1,maskClosable:!1,wrapClassName:"PlansToPush"},h.a.createElement(b.a,{form:this.props.form,history:this.props.history,match:this.props.match,fetch:this.props.myFetch,upload:this.props.myUpload,headers:{token:this.props.loginAndLogoutInfo.loginInfo.token},formItemLayout:{labelCol:{xs:{span:7},sm:{span:7}},wrapperCol:{xs:{span:17},sm:{span:17}}},formConfig:[{label:"\u63a8\u9001\u7ed9",field:"zjTzPolicyLocalReplyList",type:"treeSelect",required:!0,treeSelectOption:{help:!0,selectType:"2",fetchConfig:{apiName:"getSysDepartmentUserAllTree"},useCollect:!0,collectApi:"appGetSysFrequentContactsList",collectApiByAdd:"appAddSysFrequentContacts",collectApiByDel:"appRemoveSysFrequentContacts",search:!0,searchPlaceholder:"\u59d3\u540d\u3001\u8d26\u53f7\u3001\u7535\u8bdd",searchParamsKey:"search",searchOtherParams:{pageSize:999}}}],btns:[{name:"cancel",type:"dashed",label:"\u53d6\u6d88",isValidate:!1,onClick:function(t){e.setState({visible:!1})}},{name:"submit",type:"primary",label:"\u786e\u5b9a",onClick:function(t){var s;a[0].zjTzPolicyLocalReplyList=t.values.zjTzPolicyLocalReplyList,(s=t.btnfns).fetchByCb.apply(s,["updateZjTzPolicyLocalPush"].concat(Object(l.a)(a),[function(t){t.data;var a=t.success,l=t.message;a?(o.b.success(l),e.setState({visible:!1}),e.table.refresh()):o.b.error(l)}]))}}]}))):null)}}]),t}(u.Component);t.default=g}}]);