(window.webpackJsonp=window.webpackJsonp||[]).push([[94],{"+f/J":function(t,e,n){"use strict";n.d(e,"a",function(){return s});var a=n("bqt4"),r=n.n(a),c=n("+n0K"),o=n("r0ML"),u=n.n(o),s=function(t){var e,n=Object(o.useRef)(!1),a=t.fieldConfig,s=t.fieldConfig.optionConfig,i=t.fns,f=i.tool,p=i.fetch,l=i.bind,m=t.form,b=t.qnnformData.match,h=t.funcCallBackParams,d=a.fetchConfig,O=Object(c.l)({label:"label",value:"value"},s),g=Object(o.useState)(a.optionData),j=Object(c.p)(g,2),w=j[0],v=j[1],C=Object(o.useState)(!1),k=Object(c.p)(C,2),D=k[0],P=k[1],x=Object(o.useState)(!1),E=Object(c.p)(x,2),J=E[0],N=E[1];e=function(){return n.current=!0},Object(o.useEffect)(function(){return function(){return e&&e()}},[]);var S=function(){var t=Object(c.t)(r.a.mark(function t(){var e,a,c,o,u,s,i,O,g;return r.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:if(d&&d.apiName){t.next=2;break}return t.abrupt("return");case 2:return e=d.apiName,a=d.params,c=d.otherParams,t.next=5,f.getFetchParams({params:a,otherParams:c,match:b,form:m,bind:l,funcCallBackParams:h});case 5:return o=t.sent,!n.current&&P(!0),t.next=9,p(e,o);case 9:u=t.sent,s=u.data,i=u.success,O=u.message,g=u.code,!n.current&&P(!1),i?(!n.current&&v(s),!n.current&&!J&&N(!0)):"-1"===g?f.msg.error(O):f.msg.warn(O);case 16:case"end":return t.stop()}},t)}));return function(){return t.apply(this,arguments)}}();return u.a.cloneElement(u.a.createElement(t.children,null),{onFocus:function(){!D&&!J&&d&&d.apiName&&S()},fetchData:S,optionData:w,optionConfig:O,fetchOptionDataIng:D,fetchOptionDataEd:J})}}}]);