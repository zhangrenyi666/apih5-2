(window.webpackJsonp=window.webpackJsonp||[]).push([[38],{"4las":function(t,e,o){},QQP0:function(t,e,o){"use strict";var n=o("daqd"),i=o.n(n),r=o("qtOx"),a=o.n(r),f=o("cvzg"),s=o.n(f),u=o("/sAY"),c=o.n(u),l=o("bPvv"),p=o.n(l),d=o("xJmu"),h=o.n(d),v=o("hRJb"),g=o.n(v),y=o("r0ML"),m=o("Pc05"),x=o.n(m),w=o("syDz"),P=o("yOGk"),b=o("vy0m"),T=o("j6EV"),N=o.n(T),O=o("rCHE");function S(t){var e,o=function(){if(null==e){for(var o=arguments.length,n=new Array(o),i=0;i<o;i++)n[i]=arguments[i];e=Object(O.a)(function(o){return function(){e=null,t.apply(void 0,N()(o))}}(n))}};return o.cancel=function(){return O.a.cancel(e)},o}function E(){return function(t,e,o){var n=o.value,i=!1;return{configurable:!0,get:function(){if(i||this===t.prototype||this.hasOwnProperty(e))return n;var o=S(n.bind(this));return i=!0,Object.defineProperty(this,e,{value:o,configurable:!0,writable:!0}),i=!1,o}}}}var j=o("NwZv");function k(t){return t!==window?t.getBoundingClientRect():{top:0,bottom:window.innerHeight}}function z(t,e,o){if(void 0!==o&&e.top>t.top-o)return o+e.top}function B(t,e,o){if(void 0!==o&&e.bottom<t.bottom+o)return o+(window.innerHeight-e.bottom)}var C=["resize","scroll","touchstart","touchmove","touchend","pageshow","load"],L=[];function R(t,e){if(t){var o=L.find(function(e){return e.target===t});o?o.affixList.push(e):(o={target:t,affixList:[e],eventHandlers:{}},L.push(o),C.forEach(function(e){o.eventHandlers[e]=Object(j.a)(t,e,function(){o.affixList.forEach(function(t){t.lazyUpdatePosition()})})}))}}function A(t){var e=L.find(function(e){var o=e.affixList.some(function(e){return e===t});return o&&(e.affixList=e.affixList.filter(function(e){return e!==t})),o});e&&0===e.affixList.length&&(L=L.filter(function(t){return t!==e}),C.forEach(function(t){var o=e.eventHandlers[t];o&&o.remove&&o.remove()}))}var F,H=function(t,e,o,n){var i,r=arguments.length,a=r<3?e:null===n?n=Object.getOwnPropertyDescriptor(e,o):n;if("object"===("undefined"===typeof Reflect?"undefined":g()(Reflect))&&"function"===typeof Reflect.decorate)a=Reflect.decorate(t,e,o,n);else for(var f=t.length-1;f>=0;f--)(i=t[f])&&(a=(r<3?i(a):r>3?i(e,o,a):i(e,o))||a);return r>3&&a&&Object.defineProperty(e,o,a),a};function U(){return"undefined"!==typeof window?window:null}!function(t){t[t.None=0]="None",t[t.Prepare=1]="Prepare"}(F||(F={}));var M=function(t){p()(o,t);var e=h()(o);function o(){var t;return s()(this,o),(t=e.apply(this,arguments)).state={status:F.None,lastAffix:!1,prevTarget:null},t.getOffsetTop=function(){var e=t.props.offsetBottom,o=t.props.offsetTop;return void 0===e&&void 0===o&&(o=0),o},t.getOffsetBottom=function(){return t.props.offsetBottom},t.savePlaceholderNode=function(e){t.placeholderNode=e},t.saveFixedNode=function(e){t.fixedNode=e},t.measure=function(){var e=t.state,o=e.status,n=e.lastAffix,i=t.props.onChange,r=t.getTargetFunc();if(o===F.Prepare&&t.fixedNode&&t.placeholderNode&&r){var a=t.getOffsetTop(),f=t.getOffsetBottom(),s=r();if(s){var u={status:F.None},c=k(s),l=k(t.placeholderNode),p=z(l,c,a),d=B(l,c,f);void 0!==p?(u.affixStyle={position:"fixed",top:p,width:l.width,height:l.height},u.placeholderStyle={width:l.width,height:l.height}):void 0!==d&&(u.affixStyle={position:"fixed",bottom:d,width:l.width,height:l.height},u.placeholderStyle={width:l.width,height:l.height}),u.lastAffix=!!u.affixStyle,i&&n!==u.lastAffix&&i(u.lastAffix),t.setState(u)}}},t.prepareMeasure=function(){t.setState({status:F.Prepare,affixStyle:void 0,placeholderStyle:void 0})},t.render=function(){var e=t.context.getPrefixCls,o=t.state,n=o.affixStyle,r=o.placeholderStyle,f=t.props,s=f.prefixCls,u=f.children,c=x()(a()({},e("affix",s),n)),l=Object(w.a)(t.props,["prefixCls","offsetTop","offsetBottom","target","onChange"]);return y.createElement(P.a,{onResize:function(){t.updatePosition()}},y.createElement("div",i()({},l,{ref:t.savePlaceholderNode}),n&&y.createElement("div",{style:r,"aria-hidden":"true"}),y.createElement("div",{className:c,ref:t.saveFixedNode,style:n},y.createElement(P.a,{onResize:function(){t.updatePosition()}},u))))},t}return c()(o,[{key:"getTargetFunc",value:function(){var t=this.context.getTargetContainer,e=this.props.target;return void 0!==e?e:t||U}},{key:"componentDidMount",value:function(){var t=this,e=this.getTargetFunc();e&&(this.timeout=setTimeout(function(){R(e(),t),t.updatePosition()}))}},{key:"componentDidUpdate",value:function(t){var e=this.state.prevTarget,o=this.getTargetFunc(),n=null;o&&(n=o()||null),e!==n&&(A(this),n&&(R(n,this),this.updatePosition()),this.setState({prevTarget:n})),t.offsetTop===this.props.offsetTop&&t.offsetBottom===this.props.offsetBottom||this.updatePosition(),this.measure()}},{key:"componentWillUnmount",value:function(){clearTimeout(this.timeout),A(this),this.updatePosition.cancel(),this.lazyUpdatePosition.cancel()}},{key:"updatePosition",value:function(){this.prepareMeasure()}},{key:"lazyUpdatePosition",value:function(){var t=this.getTargetFunc(),e=this.state.affixStyle;if(t&&e){var o=this.getOffsetTop(),n=this.getOffsetBottom(),i=t();if(i&&this.placeholderNode){var r=k(i),a=k(this.placeholderNode),f=z(a,r,o),s=B(a,r,n);if(void 0!==f&&e.top===f||void 0!==s&&e.bottom===s)return}}this.prepareMeasure()}}]),o}(y.Component);M.contextType=b.b,H([E()],M.prototype,"updatePosition",null),H([E()],M.prototype,"lazyUpdatePosition",null);e.a=M},"m2t+":function(t,e,o){"use strict";o("j9gX"),o("4las")}}]);