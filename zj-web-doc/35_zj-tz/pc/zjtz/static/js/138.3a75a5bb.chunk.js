(window.webpackJsonp=window.webpackJsonp||[]).push([[138],{IU6x:function(e,t,a){"use strict";a.r(t);a("zIPO");var o=a("UpC8"),i=(a("fx2i"),a("cZi0")),n=a("Id3l"),l=a("RNsw"),r=a("OR/T"),s=a("bn5B"),d=a("PnTt"),p=(a("w9C0"),a("XW+2")),c=a("r0ML"),m=a.n(c),f=a("6eYn"),u=a("lscN"),b=a.n(u),I=p.a.confirm,y={antd:{rowKey:function(e){return e.compSupReportId},size:"small"},drawerConfig:{width:"1000px"},paginationConfig:{position:"bottom"},firstRowIsSearch:!1,isShowRowSelect:!0,formItemLayout:{labelCol:{xs:{span:24},sm:{span:3}},wrapperCol:{xs:{span:24},sm:{span:21}}}},h=function(e){function t(e){var a;return Object(n.a)(this,t),(a=Object(r.a)(this,Object(s.a)(t).call(this,e))).state={companyId:e.loginAndLogoutInfo.loginInfo.userInfo.companyList[0].companyId,companyName:e.loginAndLogoutInfo.loginInfo.userInfo.companyList[0].companyName},a}return Object(d.a)(t,e),Object(l.a)(t,[{key:"componentDidMount",value:function(){}},{key:"render",value:function(){var e=this,t=this.state.companyId,a=this.props.loginAndLogoutInfo.loginInfo.userInfo.curCompany.projectId;return m.a.createElement("div",{className:b.a.root},m.a.createElement(f.a,Object(o.a)({},this.props,{fetch:this.props.myFetch,upload:this.props.myUpload,headers:{token:this.props.loginAndLogoutInfo.loginInfo.token},wrappedComponentRef:function(t){e.table=t},fetchConfig:{apiName:"getZjTzCompSupReportReplyList",otherParams:{projectId:a}}},y,{formConfig:[{isInTable:!1,form:{field:"compSupReportId",type:"string",hide:!0}},{isInTable:!1,form:{field:"companyId",type:"string",initialValue:t,hide:!0}},{isInSearch:!1,table:{title:"\u6807\u9898",dataIndex:"title",key:"title",width:300,tooltip:23,onClick:"detail"},form:{type:"string",field:"title",placeholder:"\u8bf7\u8f93\u5165",required:!0,disabled:!0,editDisabled:!0,addDisabled:!0,spanForm:12,formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:6}},wrapperCol:{xs:{span:24},sm:{span:18}}}}},{isInTable:!1,form:{label:"\u68c0\u67e5\u65e5\u671f",type:"date",required:!0,field:"checkDate",disabled:!0,editDisabled:!0,addDisabled:!0,spanForm:12,formItemLayout:{labelCol:{xs:{span:21},sm:{span:6}},wrapperCol:{xs:{span:21},sm:{span:18}}}}},{isInTable:!1,form:{label:"\u7763\u67e5\u5206\u7c7b",field:"supClassId",disabled:!0,editDisabled:!0,addDisabled:!0,type:"select",optionConfig:{label:"itemName",value:"itemId"},fetchConfig:{apiName:"getBaseCodeSelect",otherParams:{itemId:"duChaFenLei"}},required:!0,spanForm:12,formItemLayout:{labelCol:{xs:{span:21},sm:{span:6}},wrapperCol:{xs:{span:21},sm:{span:18}}}}},{table:{title:"\u7763\u67e5\u90e8\u95e8",dataIndex:"supDeptId",key:"supDeptId",type:"select",filter:!0},form:{field:"supDeptId",type:"select",required:!0,editDisabled:!0,addDisabled:!0,optionConfig:{label:"itemName",value:"itemId"},fetchConfig:{apiName:"getBaseCodeSelect",otherParams:{itemId:"duChaBuMen"}},placeholder:"\u8bf7\u9009\u62e9",spanForm:12,formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:6}},wrapperCol:{xs:{span:24},sm:{span:18}}}}},{table:{title:"\u7763\u67e5\u5bf9\u8c61",dataIndex:"projectId",key:"projectId",type:"select"},form:{label:"\u7763\u67e5\u5bf9\u8c61",field:"projectId",type:"selectByPaging",required:!0,editDisabled:!0,addDisabled:!0,optionConfig:{label:"projectName",value:"projectId"},fetchConfig:{apiName:"getZjTzProManageList"},placeholder:"\u8bf7\u9009\u62e9",formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:3}},wrapperCol:{xs:{span:24},sm:{span:21}}}}},{table:{title:"\u68c0\u67e5\u65e5\u671f",dataIndex:"checkDate",width:120,format:"YYYY-MM-DD",key:"checkDate"},isInForm:!1},{table:{title:"\u4e0b\u8fbe\u8bf4\u660e",dataIndex:"contentDesc",key:"contentDesc",width:140,tooltip:23},isInForm:!1},{table:{title:"\u53d1\u5e03\u5bf9\u8c61",dataIndex:"correctiveUserName",key:"correctiveUserName"},isInForm:!1},{table:{title:"\u4e0b\u8fbe\u72b6\u6001",dataIndex:"correctiveName",key:"correctiveName",width:120},isInForm:!1},{isInTable:!1,form:{type:"component",field:"diySK1",Component:function(e){return m.a.createElement("div",{style:{width:"100%",backgroundColor:"#eeeded",height:"32px",lineHeight:"32px",borderRadius:"2px",paddingLeft:"10px",marginBottom:"10px",marginTop:"10px"}},"\u7763\u5bdf\u8981\u70b9\u901a\u62a5\u5185\u5bb9\uff08\u5206\u8868\u626c\u3001\u5efa\u8bae\u3001\u6574\u6539\u3001\u5904\u7f5a\u56db\u4e2a\u65b9\u9762\u4f9d\u6b21\u586b\u5199\uff09")}}},{isInTable:!1,form:{type:"qnnTable",field:"zjTzSupContentList",colStyle:{paddingLeft:12},incToForm:!0,qnnTableConfig:{actionBtnsPosition:"top",antd:{rowKey:"supContentId",size:"small"},drawerConfig:{width:"1000px"},limit:999,curPage:1,paginationConfig:!1,firstRowIsSearch:!1,isShowRowSelect:!1,formItemLayout:{labelCol:{xs:{span:24},sm:{span:4}},wrapperCol:{xs:{span:24},sm:{span:20}}},wrappedComponentRef:function(t){e.tables=t},rowDisabledCondition:function(e,t){return"1"===e.typeId||"0"===e.needCorrectiveId},formConfig:[{isInTable:!1,form:{label:"\u4e3b\u952eid",field:"supContentId",hide:!0}},{isInForm:!1,table:{width:80,align:"center",title:"\u5e8f\u53f7",dataIndex:"no",key:"no",render:function(e,t,a){return a+1}}},{table:{title:"\u7c7b\u578b",dataIndex:"typeId",key:"typeId",tdEdit:!1,type:"select"},form:{label:"\u7c7b\u578b",type:"select",field:"typeId",placeholder:"\u8bf7\u9009\u62e9",optionConfig:{label:"itemName",value:"itemId"},fetchConfig:{apiName:"getBaseCodeSelect",otherParams:{itemId:"leiXing"}}}},{table:{title:"\u7ec6\u76ee",width:300,tooltip:23,dataIndex:"detail",key:"detail",tdEdit:!0},form:{type:"textarea",autoSize:{minRows:1,maxRows:4},field:"detail",placeholder:"\u8bf7\u8f93\u5165"}},{table:{title:"\u662f\u5426\u9700\u8981\u6574\u6539",dataIndex:"needCorrectiveId",key:"needCorrectiveId",fetchConfig:{field:"needCorrectiveId",type:"select",placeholder:"\u8bf7\u8f93\u5165",optionData:[{label:"\u662f",value:"1"},{label:"\u5426",value:"0"}]},type:"select"},form:{label:"\u662f\u5426\u9700\u8981\u6574\u6539",field:"needCorrectiveId",type:"select",placeholder:"\u8bf7\u8f93\u5165",optionData:[{label:"\u662f",value:"1"},{label:"\u5426",value:"0"}]}},{table:{title:"\u6574\u6539\u5b8c\u6210\u65f6\u95f4",dataIndex:"correctiveDate",key:"correctiveDate",format:"YYYY-MM-DD",tdEdit:!0},form:{label:"\u6574\u6539\u5b8c\u6210\u65f6\u95f4",field:"correctiveDate",type:"date",placeholder:"\u8bf7\u9009\u62e9"}},{table:{title:"\u6574\u6539\u843d\u5b9e\u60c5\u51b5",width:300,tooltip:23,dataIndex:"correctiveCase",key:"correctiveCase",tdEdit:!0},form:{field:"correctiveCase",type:"textarea",autoSize:{minRows:1,maxRows:4},placeholder:"\u8bf7\u8f93\u5165"}}],actionBtns:[]}}},{isInTable:!1,form:{label:"\u68c0\u67e5\u65b9\u9644\u4ef6",field:"zjTzFileList",disabled:!0,editDisabled:!0,addDisabled:!0,type:"files",fetchConfig:{apiName:window.configs.domain+"upload",otherParams:{name:"\u6295\u8d44\u4e8b\u4e1a\u90e8-\u9879\u76ee\u6574\u6539\u843d\u5b9e\u56de\u590d"}},showDownloadIcon:!0,onPreview:"bind:_docFilesByOfficeUrl"}},{isInTable:!1,form:{label:"",field:"otherTYpe",hide:!0,type:"string",initialValue:"2"}},{isInTable:!1,form:{label:"\u9879\u76ee\u65b9\u9644\u4ef6",field:"replyFileList",type:"files",fetchConfig:{apiName:window.configs.domain+"upload",otherParams:{name:"\u6295\u8d44\u4e8b\u4e1a\u90e8-\u9879\u76ee\u6574\u6539\u843d\u5b9e\u56de\u590d"}},showDownloadIcon:!0,onPreview:"bind:_docFilesByOfficeUrl"}},{isInTable:!1,form:{field:"registerDate",type:"date",label:"\u767b\u8bb0\u65e5\u671f",required:!0,addDisabled:!0,editDisabled:!0,placeholder:"\u8bf7\u8f93\u5165",spanForm:12,formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:6}},wrapperCol:{xs:{span:24},sm:{span:18}}},initialValue:function(){return new Date}}},{isInTable:!1,form:{field:"registerPerson",type:"string",label:"\u767b\u8bb0\u7528\u6237",required:!0,addDisabled:!0,editDisabled:!0,placeholder:"\u8bf7\u8f93\u5165",spanForm:12,formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:6}},wrapperCol:{xs:{span:24},sm:{span:18}}},initialValue:function(e){return e.loginAndLogoutInfo.loginInfo.userInfo.realName}}}],method:{isCanSubmit:function(e,t){for(var a=e.params.zjTzSupContentList,o="ok",n=0;n<a.length;n++)if("1"===a[n].needCorrectiveId&&(!a[n].correctiveCase||!a[n].correctiveDate)){o="unOk";break}"ok"===o?t(!0):(i.b.error("\u8868\u683c\u5185\u5fc5\u586b\u9879\u672a\u586b\u5199\uff0c\u4e0d\u80fd\u4fdd\u5b58\uff01"),t(!1))},reply:function(){e.table.clearSelectedRows()},shangBaoClick:function(t){e.table.clearSelectedRows();var a=t.props.myFetch;console.log(t.selectedRows[0].compSupReportId),I({title:"\u786e\u5b9a\u4e0a\u62a5\u4e48?",okText:"\u786e\u8ba4",cancelText:"\u53d6\u6d88",onOk:function(){a("reportZjTzCompSupReport",{compSupReportId:t.selectedRows[0].compSupReportId}).then(function(t){t.data;var a=t.success,o=t.message;a?(i.b.success(o),e.table.refresh()):i.b.error(o)})}})}},actionBtns:{apiName:"getSysMenuBtn",otherParams:function(e){var t=e.Pprops;return{menuParentId:t.routerInfo.routeData[t.routerInfo.curKey]._value,tableField:"projectInfo"}}}})))}}]),t}(c.Component);t.default=h},lscN:function(e,t,a){}}]);