(window.webpackJsonp=window.webpackJsonp||[]).push([[283],{"9aex":function(e,t,a){"use strict";a.r(t);var l=a("+n0K"),n=(a("3fBH"),a("r0ML")),i=a.n(n),o=(a("C/2i"),a("ID3L"),a("+K9n"),a("0DJy"),a("tpuf"),a("fx2i"),a("40Q9"),a("VpwF"),a("9M8h"),a("w9C0"),a("HJbo"),a("daJc"),a("Sw1S"),a("07Zm"),a("739E"),a("j0L1"),a("5fiz"),a("NExk"),a("ftLu"));t.default=function(e){var t=e.inputProps,a=e.fieldConfig,n=a.oldValue,s=void 0===n?[]:n,c=a.oldValueKey,r=void 0===c?{text:"text",time:"time",name:"name"}:c,m=a.voice,d=a.field,u=a.initialValue,f=a.locInfo,p=e.startVoice,v=e.qnnformData.style,y=e.fns,E=y.isMobile,b=y.tool,x=e.form,g=r.text,w=r.time,h=r.name,V=null;return s&&s.length&&(V=i.a.createElement("div",{className:v.textareaHisDom},s.map(function(e,t){var a=e[g],l=e[h],n=e[w],o=e.style1,s=e.style2,c=e.style3;return i.a.createElement("div",{key:t,style:{padding:"3px 0",borderTop:0!==t?"1px solid #ff9900":""}},i.a.createElement("div",{className:v.top},i.a.createElement("small",{style:s},l),i.a.createElement("small",{style:o},n)),i.a.createElement("div",{className:v.content},i.a.createElement("div",{style:c},a)))}))),E()&&!u&&f&&!x.getFieldValue([d])&&b.getLocAddressInfo().then(function(e){Array.isArray(d)&&d.length>1?x.setFieldsValue(Object(l.f)({},d[0],Object(l.f)({},d[1],e[f]))):x.setFieldsValue(Object(l.f)({},[d],e[f]))}),i.a.createElement("div",{className:s.length?v.haveHisDom:null},i.a.createElement("div",null,V),i.a.createElement("div",{style:{display:t.disabled&&V&&!u?"none":"auto"}},i.a.createElement(o.a.TextArea,Object(l.q)({autoSize:{minRows:3,maxRows:12}},t,{onChange:function(a){t.onChange(a.target.value,e)}})),E()&&m&&!t.disabled?i.a.createElement("img",{width:"24",src:l.u,onClick:function(){p(d)},alt:"voice",style:{position:"absolute",right:"3px",bottom:"6px"}}):null))}}}]);