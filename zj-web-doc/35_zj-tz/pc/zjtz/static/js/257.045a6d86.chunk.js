(window.webpackJsonp=window.webpackJsonp||[]).push([[257],{PLP8:function(e,t,a){"use strict";a.r(t);a("8Qi2"),a("IdsT"),a("l9AF");var i=a("UpC8"),o=(a("fx2i"),a("cZi0")),n=a("Id3l"),r=a("RNsw"),d=a("OR/T"),l=a("bn5B"),s=a("PnTt"),p=a("r0ML"),c=a.n(p),m=a("6eYn"),f=a("Seuk"),h=a.n(f),b=a("U32T"),u={antd:{rowKey:function(e){return e.executiveMeetChangeRecordId},size:"small"},drawerConfig:{width:"1000px"},paginationConfig:{position:"bottom"},firstRowIsSearch:!1,formItemLayout:{labelCol:{xs:{span:24},sm:{span:4}},wrapperCol:{xs:{span:24},sm:{span:20}}}},I=function(e){function t(e){var a;return Object(n.a)(this,t),(a=Object(d.a)(this,Object(l.a)(t).call(this,e))).state={rowData:e.match.params.rowData?JSON.parse(decodeURIComponent(e.match.params.rowData)):{}},a}return Object(s.a)(t,e),Object(r.a)(t,[{key:"render",value:function(){var e=this,t=this.state.rowData;return c.a.createElement("div",{className:h.a.root},c.a.createElement(m.a,Object(i.a)({},this.props,{fetch:this.props.myFetch,upload:this.props.myUpload,headers:{token:this.props.loginAndLogoutInfo.loginInfo.token},wrappedComponentRef:function(t){e.table=t},getRowSelection:function(t){return{getCheckboxProps:function(t){return{name:t.name,disabled:t.executiveMeetChangeRecordId!==e.table.state.data[0].executiveMeetChangeRecordId}}}}},u,{fetchConfig:{apiName:"getZjTzExecutiveMeetChangeRecordList",otherParams:{executiveMeetId:t.executiveMeetId}},method:{goBack:function(t){e.props.dispatch(Object(b.c)())},buttonHide:function(){return"1"===t.statusId||"2"===t.statusId},buttonDisabled:function(e){return!(e.btnCallbackFn.getSelectedRows().length>0)},editClick:function(t){e.table.clearSelectedRows()},diysubmitClick:function(a){a.btnCallbackFn.setBtnsLoading("add","diysubmitClick"),t.personnelList=a._formData.personnelList,e.props.myFetch("updateZjTzExecutiveMeet",t).then(function(e){var t=e.success,i=e.message;t?(o.b.success(i),a.btnCallbackFn.closeDrawer(!1),a.btnCallbackFn.refresh(),a.btnCallbackFn.setBtnsLoading("remove","diysubmitClick")):(o.b.error(i),a.btnCallbackFn.setBtnsLoading("remove","diysubmitClick"))})},diydelClick:function(a){a.response.success?(t.personnelList=e.table.state.data[0].personnelList,e.props.myFetch("updateZjTzExecutiveMeet",t).then(function(e){e.success,e.message})):e.table.clearSelectedRows()}},actionBtns:{apiName:"getSysMenuBtn",otherParams:function(e){var t=e.Pprops;return{menuParentId:t.routerInfo.routeData[t.routerInfo.curKey]._value,tableField:"projectInfo"}}},formConfig:[{isInTable:!1,form:{field:"executiveMeetId",type:"string",hide:!0}},{isInTable:!1,form:{field:"executiveMeetChangeRecordId",type:"string",hide:!0}},{table:{title:"\u9879\u76ee\u540d\u79f0",filter:!0,width:300,fixed:"left",dataIndex:"projectId",key:"projectId",type:"select",fieldsConfig:{field:"projectId",type:"selectByPaging",optionConfig:{label:"projectName",value:"projectId"},fetchConfig:{apiName:"getZjTzProManageList"}}},form:{field:"projectId",type:"select",optionConfig:{label:"projectName",value:"projectId"},fetchConfig:{apiName:"getZjTzProManageList"},editDisabled:!0,placeholder:"\u8bf7\u9009\u62e9"}},{isInSearch:!1,table:{title:"\u8463\u4e8b\u4f1a\u671f\u6b21",onClick:"detail",width:100,dataIndex:"periodName",key:"periodName"},form:{type:"select",required:!0,field:"periodId",optionConfig:{label:"itemName",value:"itemId"},fetchConfig:{apiName:"getBaseCodeSelect",otherParams:{itemId:"huiYiJieCi"}},onChange:function(t){for(var a=0;a<e.table.state.data.length;a++)if(t===e.table.state.data[a].periodId){o.b.error("\u8be5\u9879\u76ee\u4e0b\u8fd9\u4e2a\u5c4a\u6b21\u5df2\u7ecf\u6dfb\u52a0\uff0c\u8bf7\u53bb\u53d8\u66f4\u5427\uff01");break}},allowClear:!0,placeholder:"\u8bf7\u9009\u62e9",editDisabled:!0,spanForm:12,formItemLayout:{labelCol:{xs:{span:24},sm:{span:8}},wrapperCol:{xs:{span:24},sm:{span:16}}}}},{isInSearch:!1,table:{title:"\u586b\u62a5\u65e5\u671f",format:"YYYY-MM-DD",dataIndex:"businessDate",key:"businessDate",width:100},form:{type:"date",placeholder:"\u8bf7\u9009\u62e9",required:!0,editDisabled:!0,spanForm:12,formItemLayout:{labelCol:{xs:{span:24},sm:{span:8}},wrapperCol:{xs:{span:24},sm:{span:16}}}}},{isInSearch:!1,table:{title:"\u4efb\u804c\u671f\u9650\u5f00\u59cb\u65f6\u95f4",format:"YYYY-MM-DD",dataIndex:"startDate",key:"startDate",width:150},form:{type:"date",placeholder:"\u8bf7\u9009\u62e9",required:!0,editDisabled:!0,spanForm:12,formItemLayout:{labelCol:{xs:{span:24},sm:{span:8}},wrapperCol:{xs:{span:24},sm:{span:16}}}}},{isInSearch:!1,table:{title:"\u4efb\u804c\u671f\u9650\u7ed3\u675f\u65f6\u95f4",format:"YYYY-MM-DD",dataIndex:"endDate",key:"endDate",width:150},form:{type:"date",placeholder:"\u8bf7\u9009\u62e9",required:!0,editDisabled:!0,spanForm:12,formItemLayout:{labelCol:{xs:{span:24},sm:{span:8}},wrapperCol:{xs:{span:24},sm:{span:16}}}}},{isInSearch:!1,table:{title:"\u5907\u6ce8",dataIndex:"remarks",key:"remarks",width:150},form:{type:"textarea",editDisabled:!0,placeholder:"\u8bf7\u8f93\u5165"}},{isInTable:!1,form:{label:"\u9644\u4ef6",field:"zjTzFileList",type:"files",showDownloadIcon:!0,onPreview:"bind:_docFilesByOfficeUrl",editDisabled:!0,fetchConfig:{apiName:window.configs.domain+"upload"}}},{isInSearch:!1,table:{title:"\u53d8\u66f4\u6b21\u6570",dataIndex:"changeNum",key:"changeNum",width:80},isInForm:!1},{isInTable:!1,form:{type:"qnnTable",field:"personnelList",incToForm:!0,qnnTableConfig:{actionBtnsPosition:"top",antd:{rowKey:"executivePersonnelId",size:"small"},paginationConfig:{position:"bottom"},wrappedComponentRef:function(t){e.tables=t},formConfig:[{isInTable:!1,form:{label:"\u4e3b\u952eid",field:"executivePersonnelId",hide:!0}},{table:{title:"\u5e8f\u53f7",dataIndex:"index",key:"index",width:50,fixed:"left",render:function(e,t,a){return a+1}},isInForm:!1},{table:{title:"\u80a1\u4e1c\u540d\u79f0",dataIndex:"shareholderName",key:"shareholderName",tdEdit:!0,fixed:"left",width:150,tooltip:8},form:{type:"string",placeholder:"\u8bf7\u8f93\u5165"}},{table:{title:"\u80a1\u6bd4",dataIndex:"proportion",key:"proportion",tdEdit:!0,width:100},form:{type:"number",placeholder:"\u8bf7\u8f93\u5165"}},{table:{title:"\u8463\u4e8b\u957f",dataIndex:"directorz",key:"directorz",tdEdit:!0,width:100,tooltip:5},form:{type:"string",placeholder:"\u8bf7\u8f93\u5165"}},{table:{title:"\u8463\u4e8b",dataIndex:"director",key:"director",tdEdit:!0,width:100,tooltip:5},form:{type:"string",placeholder:"\u8bf7\u8f93\u5165"}},{table:{title:"\u76d1\u4e8b\u957f",dataIndex:"supervisorz",key:"supervisorz",tdEdit:!0,width:100,tooltip:5},form:{type:"string",placeholder:"\u8bf7\u8f93\u5165"}},{table:{title:"\u76d1\u4e8b",dataIndex:"supervisor",key:"supervisor",tdEdit:!0,width:100,tooltip:5},form:{type:"string",placeholder:"\u8bf7\u8f93\u5165"}},{table:{title:"\u9879\u76ee\u516c\u53f8\u603b\u7ecf\u7406",dataIndex:"manager",key:"manager",tdEdit:!0,width:120,tooltip:6},form:{type:"string",placeholder:"\u8bf7\u8f93\u5165"}},{table:{title:"\u603b\u5de5",dataIndex:"chief1",key:"chief1",tdEdit:!0,width:100,tooltip:5},form:{type:"string",placeholder:"\u8bf7\u8f93\u5165"}},{table:{title:"\u603b\u4f1a",dataIndex:"chief2",key:"chief2",tdEdit:!0,width:100,tooltip:5},form:{type:"string",placeholder:"\u8bf7\u8f93\u5165"}},{table:{title:"\u603b\u7ecf",dataIndex:"chief3",key:"chief3",tdEdit:!0,width:100,tooltip:5},form:{type:"string",placeholder:"\u8bf7\u8f93\u5165"}},{table:{title:"\u5907\u6ce8",dataIndex:"remarks",key:"remarks",tdEdit:!0,width:150,tooltip:8},form:{type:"string",placeholder:"\u8bf7\u8f93\u5165"}},{table:{title:"\u6392\u5e8f\u53f7",dataIndex:"orderNum",key:"orderNum",tdEdit:!0,width:80},form:{type:"string",placeholder:"\u8bf7\u8f93\u5165"}}],actionBtns:[{name:"addRow",icon:"plus",type:"primary",label:"\u65b0\u589e\u884c"},{name:"del",icon:"delete",type:"danger",label:"\u5220\u9664"}]}}}]})))}}]),t}(p.Component);t.default=I}}]);