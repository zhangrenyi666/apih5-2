(window.webpackJsonp=window.webpackJsonp||[]).push([[168],{"h+hl":function(e,a,t){"use strict";t.r(a);var n=t("fbSu"),o=t.n(n),l=t("5T12"),r=t.n(l),s=t("qHWA"),i=t.n(s),d=t("rx6U"),p=t.n(d),c=t("4tJN"),m=t.n(c),f=t("Qsbb"),h=t.n(f),u=(t("9U97"),t("x/6R"),t("MRnW"),t("IdsT"),t("l9AF"),t("orcL"),t("w9C0"),t("XW+2")),b=(t("fx2i"),t("cZi0")),y=(t("3fBH"),t("g2mn")),g=t("UpC8"),C=t("C9HG"),I=t("eRGJ"),S=t.n(I),w=t("YqOB"),v=t("Id3l"),x=t("RNsw"),T=t("OR/T"),k=t("bn5B"),B=t("PnTt"),R=t("r0ML"),j=t.n(R),F=t("o6E8"),E=t.n(F),L=t("6eYn"),O=t("vcLm"),P=t("mrSZ");function N(e,a){var t=h()(e);if(m.a){var n=m()(e);a&&(n=n.filter(function(a){return p()(e,a).enumerable})),t.push.apply(t,n)}return t}function A(e){for(var a=1;a<arguments.length;a++){var t=null!=arguments[a]?arguments[a]:{};a%2?N(Object(t),!0).forEach(function(a){Object(w.a)(e,a,t[a])}):i.a?r()(e,i()(t)):N(Object(t)).forEach(function(a){o()(e,a,p()(t,a))})}return e}var D={antd:{rowKey:"thousandCheckBaseId",size:"small"},formItemLayout:{labelCol:{xs:{span:24},sm:{span:3}},wrapperCol:{xs:{span:24},sm:{span:21}}},paginationConfig:{position:"bottom"},drawerConfig:{width:"800px"},firstRowIsSearch:!1,isShowRowSelect:!1},q=function(e){function a(){var e,t;Object(v.a)(this,a);for(var n=arguments.length,o=new Array(n),l=0;l<n;l++)o[l]=arguments[l];return(t=Object(T.a)(this,(e=Object(k.a)(a)).call.apply(e,[this].concat(o)))).state={loading:!1,directoryId:null,inShow:"1",outShow:"1",visibleSend:!1,loadingSend:!1,tolCount:0,treeNodeKeys:{label:"evalPro",value:"thousandCheckBaseId",children:"getZjTzThousandCheckBaseList"},treeShow:!0},t.tableByRoot={antd:{rowKey:"codeId",locale:{emptyText:""},style:{display:"none"}},formConfig:[{isInTable:!1,form:{type:"string",label:"id",field:"codeId",hide:!0,placeholder:"\u8bf7\u8f93\u5165"}},{isInTable:!1,form:{type:"string",label:"id",field:"thousandCheckBaseId",hide:!0,placeholder:"\u8bf7\u8f93\u5165"}},{isInTable:!1,form:{type:"string",initialValue:"0",field:"interfaceFlag",hide:!0,placeholder:"\u8bf7\u8f93\u5165"}},{isInTable:!1,form:{type:"string",label:"codePid",field:"codePid",hide:!0,placeholder:"\u8bf7\u8f93\u5165"}},{isInTable:!1,form:{type:"string",label:"pidAll",field:"pidAll",hide:!0,placeholder:"\u8bf7\u8f93\u5165"}},{isInTable:!1,form:{type:"string",label:"pidNameAll",field:"pidNameAll",hide:!0,placeholder:"\u8bf7\u8f93\u5165"}},{isInTable:!1,form:{label:"\u6392\u5e8f",required:!0,precision:0,type:"number",field:"orderFlag",formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:3}},wrapperCol:{xs:{span:24},sm:{span:21}}}}},{isInTable:!1,form:{label:"\u9879\u76ee\u540d\u79f0",type:"string",required:!0,field:"evalPro",formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:3}},wrapperCol:{xs:{span:24},sm:{span:21}}}}},{isInTable:!1,form:{type:"textarea",label:"\u5907\u6ce8",field:"remarks",placeholder:"\u8bf7\u8f93\u5165",formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:3}},wrapperCol:{xs:{span:24},sm:{span:21}}}}}]},t.tableByInRoot={antd:{rowKey:"codeId",locale:{emptyText:""},style:{display:"none"}},formConfig:[{isInTable:!1,form:{field:"thousandCheckBaseId",hide:!0,label:"\u4e3b\u952eid",type:"string"}},{isInTable:!1,form:{type:"string",initialValue:"1",field:"interfaceFlag",hide:!0,placeholder:"\u8bf7\u8f93\u5165"}},{isInTable:!1,form:{label:"\u6392\u5e8f",required:!0,precision:0,type:"number",field:"orderFlag",formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:5}},wrapperCol:{xs:{span:24},sm:{span:19}}}}},{isInTable:!1,form:{label:"\u8bc4\u4ef7\u6307\u6807",required:!0,type:"string",field:"evalIndex",formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:5}},wrapperCol:{xs:{span:24},sm:{span:19}}}}},{isInTable:!1,form:{label:"\u8bc4\u5206\u5185\u5bb9\u53ca\u6263\u5206\u6807\u51c6",type:"textarea",field:"evalContent",formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:5}},wrapperCol:{xs:{span:24},sm:{span:19}}}}},{isInTable:!1,form:{label:"\u6807\u51c6\u914d\u5206",type:"number",required:!0,field:"score",formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:5}},wrapperCol:{xs:{span:24},sm:{span:19}}}}}]},t.handleCancelSend=function(){t.setState({visibleSend:!1,loadingSend:!1})},t}return Object(B.a)(a,e),Object(x.a)(a,[{key:"render",value:function(){var e=this,a=this.state,t=a.visibleSend,n=a.loadingSend,o=a.directoryId,l=a.outShow,r=a.treeNodeKeys,s=a.inShow,i=a.loading,d=a.treeShow,p=this.tableByRoot,c=this.tableByInRoot,m=this.table,f=this.props.loginAndLogoutInfo.loginInfo.userInfo.ext1;return j.a.createElement("div",{className:E.a.root},j.a.createElement("div",{className:E.a.left,ref:function(a){a&&(e.leftDom=a)}},j.a.createElement("div",{className:E.a.hr,ref:function(a){if(a){var t=function(e){var a=document.getElementsByClassName("ant-layout-content")[0].offsetLeft;n.leftDom.style["flex-basis"]=e.pageX-a+"px"},n=e;a.addEventListener("mousedown",function(a){e.onDragStartPos=a.pageX,document.addEventListener("mousemove",t,!1)},!1),document.addEventListener("mouseup",function(e){document.removeEventListener("mousemove",t,!1)},!1)}}}),d?j.a.createElement(P.a,{visible:!0,modalType:"common",nodeClick:function(a,t){"0"===a.props.dataRef.codePid&&e.setState({directoryId:a.props.dataRef.thousandCheckBaseId,outShow:a.props.dataRef.codePid,inShow:a.props.dataRef.codePid}),m&&m.clearSelectedRows()},myFetch:this.props.myFetch,selectModal:"0",btnShow:!1,selectText:!1,ref:function(a){return e.tree=a},nodeRender:function(e){var a="";return a=e.scoreSubtotal?e.evalPro+"("+e.scoreSubtotal+"\u5206)":e.evalPro,j.a.createElement("span",null,a)},rightMenuOption:function(a){return f&&"1"===f?"0"===a.codePid?[{label:"\u65b0\u589e\u8bc4\u4ef7\u6307\u6807",name:"diy",cb:function(a){if(e.tableByInRootObj){var t=a.props.dataRef,n=a.props.expanded,o={interfaceFlag:"1",codePid:t.thousandCheckBaseId,evalPro:t.evalPro};e.tableByInRootObj.action({drawerTitle:"\u65b0\u589e",name:"add",formBtns:[{name:"cancel",type:"dashed",label:"\u53d6\u6d88"},{name:"submit",type:"primary",label:"\u4fdd\u5b58",fetchConfig:{apiName:"addZjTzThousandCheckBase",otherParams:A({},o)},onClick:function(t){var o=e.tree.state.data||[],l=e.state.treeNodeKeys,r=l.value,s=l.children;if(a.props.expanded||a.onExpand(),n){var i=function e(a){for(var n=0;n<a.length;n++){var o=a[n];o[r]===t.data.codePid&&(a[n][s]||(a[n][s]=[]),a[n][s].unshift(t.data)),o[s]&&S()(o[s])&&o[s].length&&e(o[s])}return a}(Object(C.a)(o));e.tree.setTreeData(i)}e.setState({treeShow:!1},function(){e.setState({treeShow:!0})}),e.table&&e.table.refresh()}}]})}}},{label:"\u7f16\u8f91\u8bc4\u4ef7\u9879\u76ee",name:"editDiy11",cb:function(a){e.tableByRootObj.action({drawerTitle:"\u7f16\u8f91\u8bc4\u4ef7\u9879\u76ee",name:"edit",formBtns:[{name:"cancel",type:"dashed",label:"\u53d6\u6d88"},{name:"submit",type:"primary",label:"\u4fdd\u5b58",fetchConfig:{apiName:"updateZjTzThousandCheckBase",otherParams:{interfaceFlag:"0"}},onClick:function(a){var t=e.tree.state.data||[],n=e.state.treeNodeKeys,o=n.value,l=n.children,r=function e(t){for(var n=0;n<t.length;n++){var r=t[n];r[o]===a.data[o]&&(t[n]=A({},t[n],{},a.data)),r[l]&&S()(r[l])&&r[l].length&&e(r[l])}return t}(Object(C.a)(t));e.tree.setTreeData(r),e.setState({treeShow:!1},function(){e.setState({treeShow:!0})}),e.table&&e.table.refresh()}}]},a.props.dataRef)}},{label:"\u5220\u9664\u8bc4\u4ef7\u9879\u76ee",name:"del"}]:[{label:"\u7f16\u8f91\u8bc4\u4ef7\u6307\u6807",name:"editDiy",cb:function(a){e.tableByInRootObj.action({drawerTitle:"\u7f16\u8f91\u8bc4\u4ef7\u6307\u6807",name:"edit",formBtns:[{name:"cancel",type:"dashed",label:"\u53d6\u6d88"},{name:"submit",type:"primary",label:"\u4fdd\u5b58",fetchConfig:{apiName:"updateZjTzThousandCheckBase"},onClick:function(a){var t=e.tree.state.data||[],n=e.state.treeNodeKeys,o=n.value,l=n.children,r=function e(t){for(var n=0;n<t.length;n++){var r=t[n];r[o]===a.data[o]&&(t[n]=A({},t[n],{},a.data)),r[l]&&S()(r[l])&&r[l].length&&e(r[l])}return t}(Object(C.a)(t));e.tree.setTreeData(r),e.setState({treeShow:!1},function(){e.setState({treeShow:!0})}),e.table&&e.table.refresh()}}]},a.props.dataRef)}},{label:"\u5220\u9664\u8bc4\u4ef7\u6307\u6807",name:"del"}]:[]},rightMenuOptionByContainer:f&&"1"===f?[{label:"\u65b0\u589e\u8bc4\u4ef7\u9879\u76ee",name:"diy",cb:function(){e.tableByRootObj&&e.tableByRootObj.action({drawerTitle:"\u65b0\u589e",name:"add",formBtns:[{name:"cancel",type:"dashed",label:"\u53d6\u6d88"},{name:"submit",type:"primary",label:"\u4fdd\u5b58",fetchConfig:{apiName:"addZjTzThousandCheckBase",otherParams:{codePid:"0"}},onClick:function(a){var t=e.tree.getTreeData();t.push(a.data),e.tree.setTreeData(t),e.setState({treeShow:!1},function(){e.setState({treeShow:!0})})}}]})}},{label:"\u5bfc\u5165\u5343\u5206\u5236\u68c0\u67e5\u9879",name:"diyExport",cb:function(a){e.setState({visibleSend:!0}),e.setState({treeShow:!1},function(){e.setState({treeShow:!0})})}}]:[],fetchConfig:{parmasKey:"codePid",apiName:"getZjTzThousandCheckBaseList",searchApiName:"",params:{codePid:"0"},success:function(a){var t=0;if(a){for(var n=0;n<a.length;n++)a[n].scoreSubtotal&&(t+=a[n].scoreSubtotal);t>0&&e.setState({tolCount:t})}},nodeAddApiName:"addZjTzThousandCheckBase",nodeAddParamsFormat:function(e,a){return A({},e,{levelName:"\u672a\u547d\u540d",codePid:a.props.dataRef.codeId,pidAll:a.props.dataRef.pidAll,pidNameAll:a.props.dataRef.pidNameAll})},nodeAddCb:function(){e.table&&e.table.refresh()},nodeEditApiName:"pcUpdateBaseCodeOnTree",nodeEditCb:function(){e.table&&e.table.refresh()},nodeDelApiName:"batchDeleteUpdateZjTzThousandCheckBase",nodeDelParamsFormat:function(e,a){return[e]},nodeDelCb:function(a){e.setState({treeShow:!1},function(){e.setState({treeShow:!0})}),e.table&&e.table.refresh()}},keys:A({},r),draggable:!1}):null),j.a.createElement("div",{className:E.a.right},j.a.createElement(y.a,{spinning:i},"1"===s?null:j.a.createElement(L.a,Object(g.a)({},this.props,{firstRowIsSearch:!0,fetch:this.props.myFetch,upload:this.props.myUpload,wrappedComponentRef:function(a){e.table=a},headers:{token:this.props.loginAndLogoutInfo.loginInfo.token},fetchConfig:{apiName:"getZjTzThousandCheckBaseList",otherParams:{codePid:o,interfaceFlag:"1"}}},D,{formConfig:[{isInTable:!1,form:{field:"thousandCheckBaseId",hide:!0,label:"\u4e3b\u952eid",type:"string"}},{isInTable:!1,form:{field:"thousandCheckId",hide:!0,initialValue:o,label:"\u5916\u5c42\u7684id",type:"string"}},{table:{title:"\u6392\u5e8f",dataIndex:"orderFlag",key:"orderFlag"},form:{label:"\u6392\u5e8f",required:!0,precision:0,type:"number",field:"orderFlag",formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:5}},wrapperCol:{xs:{span:24},sm:{span:19}}}}},{table:{title:"\u8bc4\u4ef7\u6307\u6807",dataIndex:"evalIndex",onClick:"detail",key:"evalIndex"},form:{type:"string",required:!0,field:"evalIndex",formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:5}},wrapperCol:{xs:{span:24},sm:{span:19}}}}},{table:{title:"\u8bc4\u5206\u5185\u5bb9\u53ca\u6263\u5206\u6807\u51c6",dataIndex:"evalContent",tooltip:50,width:"50%",key:"evalContent"},form:{label:"\u8bc4\u5206\u5185\u5bb9\u53ca\u6263\u5206\u6807\u51c6",type:"textarea",field:"evalContent",formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:5}},wrapperCol:{xs:{span:24},sm:{span:19}}}}},{table:{title:"\u6807\u51c6\u914d\u5206",width:120,dataIndex:"score",key:"score"},form:{label:"\u6807\u51c6\u914d\u5206",type:"number",required:!0,field:"score",formItemLayoutForm:{labelCol:{xs:{span:24},sm:{span:5}},wrapperCol:{xs:{span:24},sm:{span:19}}}}}],actionBtns:[]})),j.a.createElement(L.a,Object(g.a)({wrappedComponentRef:function(a){e.tableByRootObj=a},fetch:this.props.myFetch,headers:{token:this.props.loginAndLogoutInfo.loginInfo.token}},p)),j.a.createElement(L.a,Object(g.a)({wrappedComponentRef:function(a){e.tableByInRootObj=a},fetch:this.props.myFetch,headers:{token:this.props.loginAndLogoutInfo.loginInfo.token}},c)),"1"===l&&"1"===s?j.a.createElement("div",{className:E.a.alert},"\u70b9\u51fb\u5de6\u4fa7\u8282\u70b9\u5373\u53ef\u67e5\u770b\u5343\u5206\u5236\u68c0\u67e5\u9879\u4fe1\u606f"):null)),j.a.createElement(u.a,{width:"500px",style:{top:"0"},title:"\u5bfc\u5165",visible:t,footer:null,onCancel:this.handleCancelSend,bodyStyle:{width:"500px"},centered:!0,destroyOnClose:this.handleCancelSend,wrapClassName:"modals"},j.a.createElement(y.a,{spinning:n},j.a.createElement(O.a,Object(g.a)({},this.props,{match:this.props.match,fetch:this.props.myFetch,upload:this.props.myUpload,headers:{token:this.props.loginAndLogoutInfo.loginInfo.token},formConfig:[{label:"\u9644\u4ef6",field:"fileList",required:!0,type:"files",showDownloadIcon:!0,onPreview:"bind:_docFilesByOfficeUrl",fetchConfig:{apiName:window.configs.domain+"upload",otherParams:{name:"\u5343\u5206\u5236\u8bbe\u7f6e"}}}],btns:[{name:"cancel",type:"dashed",label:"\u53d6\u6d88",isValidate:!1,onClick:function(){e.setState({visibleSend:!1,loadingSend:!1})}},{name:"submit",type:"primary",label:"\u4fdd\u5b58",onClick:function(a){e.setState({loadingSend:!0}),e.props.myFetch("batchImportZjTzThousandCheckBase",a.values).then(function(a){var t=a.success,n=a.message;t?(b.b.success(n),e.setState({visibleSend:!1,loadingSend:!1},function(){e.setState({treeShow:!1},function(){e.setState({treeShow:!0})})})):b.b.error(n)})}}],formItemLayout:{labelCol:{xs:{span:4},sm:{span:4}},wrapperCol:{xs:{span:20},sm:{span:20}}}})))))}}]),a}(R.Component);a.default=q},o6E8:function(e,a,t){e.exports={root:"_1AWLmVs47W2clQrciUxi8E",left:"_2MditRdWTb_zRgIW1WdWUd",tooltips:"_1xxEPqSnIEDVck33O92D4_",hr:"_3yc1wXfJjbdrqADnfQlGWk",right:"b3pZ-cPkdgCE6aI3lmqMg",alert:"_1Ha-kvtdXmnfVSTSuhpt28"}}}]);