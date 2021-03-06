(function (global, factory) {
    typeof exports === 'object' && typeof module !== 'undefined' ? module.exports = factory() :
    typeof define === 'function' && define.amd ? define(factory) :
    (global.ActionSheet = factory());
}(this, (function () { 'use strict';

function __$styleInject(css, returnValue) {
  if (typeof document === 'undefined') {
    return returnValue;
  }
  css = css || '';
  var head = document.head || document.getElementsByTagName('head')[0];
  var style = document.createElement('style');
  style.type = 'text/css';
  if (style.styleSheet){
    style.styleSheet.cssText = css;
  } else {
    style.appendChild(document.createTextNode(css));
  }
  head.appendChild(style);
  return returnValue;
}
__$styleInject("\n.pb-container{\n    position: absolute;\n    left: 0;\n    top: 0;\n    z-index: 999999\n}\n\n.pb-cover{\n    width: 100%;\n    height: 100%;\n    background: rgba(0, 0, 0, .2);\n}\n\n.pb-buttons{\n    position: absolute;\n    left: 0;\n    bottom: 0;\n    width: 100%;\n}\n\n.pb-button{\n    background: #FFF;\n    padding: 10px;\n    border-radius: 5px;\n    margin: 5px;\n    text-align: center;\n}\n\n\n@-webkit-keyframes pb-easein{\n\tfrom { opacity: 0; }\n\tto { opacity: 1; }\n}\n\n\n@keyframes pb-easein{\n\tfrom { opacity: 0; }\n\tto { opacity: 1; }\n}\n@-webkit-keyframes pb-easeout{\n\tfrom { opacity: 1; }\n\tto { opacity: 0; }\n}\n@keyframes pb-easeout{\n\tfrom { opacity: 1; }\n\tto { opacity: 0; }\n}\n\n.pb-in{\n    -webkit-animation: pb-easein .30s forwards;\n            animation: pb-easein .30s forwards;\n}\n.pb-out{\n    -webkit-animation: pb-easeout .30s forwards;\n            animation: pb-easeout .30s forwards;\n}\n\n@-webkit-keyframes pb-buttons-easein{\n\tfrom {\n\t\t-webkit-transform: translate(0, 100%) translateZ(0);\n\t\t        transform: translate(0, 100%) translateZ(0);\n\t}\n\tto {\n\t\t-webkit-transform: translate(0, 0) translateZ(0);\n\t\t        transform: translate(0, 0) translateZ(0);\n\t}\n}\n\n@keyframes pb-buttons-easein{\n\tfrom {\n\t\t-webkit-transform: translate(0, 100%) translateZ(0);\n\t\t        transform: translate(0, 100%) translateZ(0);\n\t}\n\tto {\n\t\t-webkit-transform: translate(0, 0) translateZ(0);\n\t\t        transform: translate(0, 0) translateZ(0);\n\t}\n}\n\n@-webkit-keyframes pb-buttons-easeout{\n\tfrom {\n\t\t-webkit-transform: translate(0, 0) translateZ(0);\n\t\t        transform: translate(0, 0) translateZ(0);\n\t}\n\tto {\n\t\t-webkit-transform: translate(0, 100%) translateZ(0);\n\t\t        transform: translate(0, 100%) translateZ(0);\n\t}\n}\n\n@keyframes pb-buttons-easeout{\n\tfrom {\n\t\t-webkit-transform: translate(0, 0) translateZ(0);\n\t\t        transform: translate(0, 0) translateZ(0);\n\t}\n\tto {\n\t\t-webkit-transform: translate(0, 100%) translateZ(0);\n\t\t        transform: translate(0, 100%) translateZ(0);\n\t}\n}\n\n.pb-in .pb-buttons{\n    -webkit-animation: pb-buttons-easein .30s forwards;\n            animation: pb-buttons-easein .30s forwards;\n}\n\n.pb-out .pb-buttons{\n    -webkit-animation: pb-buttons-easeout .30s forwards;\n            animation: pb-buttons-easeout .30s forwards;\n}",undefined);

function keyValue(args, getter, setter){
    var attrs = {}, 
        keys, 
        key = args[0], 
        value = args[1];
    
    if(typeof key === 'object'){
        attrs = key;
    }else if(args.length === 1){
        return this[0] ? getter(this[0]) : null;
    }else{
        attrs[key] = value;
    };

    keys = Object.keys(attrs);
    
    return this.each(function(el){
        keys.forEach(function(key){
            setter(el, key, attrs);
        });
    });
};

// ???????????????????????????????????????????????????
function tethys(selector, context){

    var nodes = [];
    
    // ????????????????????????Node?????????
    if(selector.each && selector.on){
        // tethys ??????
        return selector;
    }else if(typeof selector === 'string'){
        // html??????????????????
        if(selector.match(/^[^\b\B]*\</)){
            // html??????
            nodes = tethys.parseHtml(selector);
        }else{
            // ?????????
            nodes = (context || document).querySelectorAll(selector);
        };
    }else if(Array.isArray(selector) || selector.constructor === NodeList){
        // ????????????????????????NodeList
        nodes = selector;
    }else if(selector.constructor === Node){
        // ??????
        nodes = [selector];
    }else{
        throw 'error param';
    };

    // ???Node???appendChild???????????????????????????????????????Node?????????????????????NodeList?????????
    // ???????????????????????????????????????NodeList???????????????Node?????????
    nodes = Array.prototype.map.call(nodes, function(n){
        return n;
    });

    // ???????????????dom????????????
    tethys.extend(nodes, tethys.fn);

    return nodes;
};

// ??????
tethys.extend = function(){
    var args = arguments, 
        deep = false, 
        dest, 
        prop = Array.prototype;

    if (typeof args[0] === 'boolean') {
        deep = prop.shift.call(args);
    };

    dest = prop.shift.call(args);
    
    prop.forEach.call(args, function (src) {
        Object.keys(src).forEach(function (key) {
            if (deep && typeof src[key] === 'object' && typeof dest[key] === 'object') {
                extend(true, dest[key], src[key]);
            } else if (typeof src[key] !== 'undefined') {
                dest[key] = src[key];
            };
        });
    });
    return dest;
};

// ????????????
tethys.merge = function(ary1, ary2){
    (ary2 || []).forEach(function(n){
        ary1.push(n);
    });
};

// ???html???????????????NodeList
tethys.parseHtml = function(str){
    var div = document.createElement('DIV');
    div.innerHTML = str;
    return div.childNodes;
};

// ????????????
tethys.tpl = function(s, o) {
    var SUBREGEX = /\{\s*([^|}]+?)\s*(?:\|([^}]*))?\s*\}/g;
    return s.replace ? s.replace(SUBREGEX, function (match, key) {
        return typeof o[key] === 'undefined' ? match : o[key];
    }) : s;
};

// 
tethys.fn = {

    // ??????
    each: function(fn){
        
        Array.prototype.forEach.call(this || [], fn);

        return this;
    },

    // ????????????
    on: function(events, fn){

        events = events.split(/\s*\,\s*/);

        return this.each(function(el){

            fn = fn.bind(el);

            events.forEach(function(event){
                el.addEventListener(event, fn);
            });
        });
    },

    // ??????css
    // css('color', 'red')
    // css({ color: 'red' })
    css: function(key, value){
        
        var format = function(key){
            return key.replace(/(-([a-z]))/g, function(s, s1, s2){
                return s2.toUpperCase();
            });
        };

        return keyValue.call(this, arguments, function(el){
            return el.style[format(key)];
        }, function(el, key, attrs){
            el.style[format(key)] = attrs[key] + '';
        });
    },

    // ????????????????????????
    attr: function(key, value){

        return keyValue.call(this, arguments, function(el){
            return el.getAttribute(key);
        }, function(el, key, attrs){
            el.setAttribute(key, attrs[key] + '');
        });
    },

    // ???????????????class
    hasClass: function(cls){
        var has = false, reg = new RegExp('\\b' + cls + '\\b');

        this.each(function(el){
            has = has || !!el.className.match(reg);
        });
        
        return has;
    },

    // ??????class
    addClass: function(cls, type){
        var reg = new RegExp('\\b' + cls + '\\b');
        
        // ??????????????????????????????class
        return this.each(function(el){
            var name = el.className;

            if(typeof name !== 'string') return;
            
            if(type === 'remove'){
                // remove
                if(name.match(reg)) {
                    el.className = name.replace(reg, '');
                }
            }else{
                // add
                if(!name.match(reg)) {
                    el.className += ' ' + cls;
                }
            }
        });
    },

    // ??????class
    removeClass: function(cls){
        return this.addClass(cls, 'remove');
    },

    // ??????html
    html: function(html){
        return this.each(function(el){
            el.innerHTML = html;
        });
    },
    
    // ??????
    show: function(){
        return this.each(function(el){
            if(el.style.display === 'none'){
                el.style.display = el.getAttribute('o-d') || '';
            };
        });
    },
    
    // ??????
    hide: function(){
        return this.each(function(el){
            if(el.style.display !== 'none') {
                el.setAttribute('o-d', el.style.display);
                el.style.display = 'none';
            };
        });
    },

    // ??????????????????
    toggle: function(){
        return this.each(function(el){
            var e = $(el);
            e.css("display") == "none" ? e.show() : e.hide();
        });
    },

    // ????????????
    append: function(child){
        
        var children = tethys(child);
        
        return this.each(function(el){
            children.each(function(child, i){
                el.appendChild(child);
            });
        });
    },

    // ??????
    find: function(selector){
        var nodes = [];

        this.each(function(el){
            tethys(selector, el).each(function(node){
                nodes.push(node);
            });
        });

        return tethys(nodes); 
    }

};

/*!
* tap.js
* Copyright (c) 2015 Alex Gibson 
* https://github.com/alexgibson/tap.js/
* Released under MIT license
*/

function Tap(el) {
    this.el = typeof el === 'object' ? el : document.getElementById(el);
    this.moved = false; //flags if the finger has moved
    this.startX = 0; //starting x coordinate
    this.startY = 0; //starting y coordinate
    this.hasTouchEventOccured = false; //flag touch event
    this.el.addEventListener('touchstart', this, false);
    this.el.addEventListener('mousedown', this, false);
}

// return true if left click is in the event, handle many browsers
Tap.prototype.leftButton = function(event) {
    // modern & preferred:  MSIE>=9, Firefox(all)
    if ('buttons' in event) {
        // https://developer.mozilla.org/docs/Web/API/MouseEvent/buttons
        return event.buttons === 1;
    } else {
        return 'which' in event ?
            // 'which' is well defined (and doesn't exist on MSIE<=8)
            // https://developer.mozilla.org/docs/Web/API/MouseEvent/which
            event.which === 1 :
            // for MSIE<=8 button is 1=left (0 on all other browsers)
            // https://developer.mozilla.org/docs/Web/API/MouseEvent/button
            event.button === 1;
    }
};

Tap.prototype.start = function(e) {
    if (e.type === 'touchstart') {

        this.hasTouchEventOccured = true;
        this.el.addEventListener('touchmove', this, false);
        this.el.addEventListener('touchend', this, false);
        this.el.addEventListener('touchcancel', this, false);

    } else if (e.type === 'mousedown' && this.leftButton(e)) {

        this.el.addEventListener('mousemove', this, false);
        this.el.addEventListener('mouseup', this, false);
    }

    this.moved = false;
    this.startX = e.type === 'touchstart' ? e.touches[0].clientX : e.clientX;
    this.startY = e.type === 'touchstart' ? e.touches[0].clientY : e.clientY;
};

Tap.prototype.move = function(e) {
    //if finger moves more than 10px flag to cancel
    var x = e.type === 'touchmove' ? e.touches[0].clientX : e.clientX;
    var y = e.type === 'touchmove' ? e.touches[0].clientY : e.clientY;
    if (Math.abs(x - this.startX) > 10 || Math.abs(y - this.startY) > 10) {
        this.moved = true;
    }
};

Tap.prototype.end = function(e) {
    var evt;

    this.el.removeEventListener('touchmove', this, false);
    this.el.removeEventListener('touchend', this, false);
    this.el.removeEventListener('touchcancel', this, false);
    this.el.removeEventListener('mouseup', this, false);
    this.el.removeEventListener('mousemove', this, false);

    if (!this.moved) {
        //create custom event
        try {
            evt = new window.CustomEvent('tap', {
                bubbles: true,
                cancelable: true
            });
        } catch (e) {
            evt = document.createEvent('Event');
            evt.initEvent('tap', true, true);
        }

        //prevent touchend from propagating to any parent
        //nodes that may have a tap.js listener attached
        e.stopPropagation();

        // dispatchEvent returns false if any handler calls preventDefault,
        if (!e.target.dispatchEvent(evt)) {
            // in which case we want to prevent clicks from firing.
            e.preventDefault();
        }
    }
};

Tap.prototype.cancel = function() {
    this.hasTouchEventOccured = false;
    this.moved = false;
    this.startX = 0;
    this.startY = 0;
};

Tap.prototype.destroy = function() {
    this.el.removeEventListener('touchstart', this, false);
    this.el.removeEventListener('touchmove', this, false);
    this.el.removeEventListener('touchend', this, false);
    this.el.removeEventListener('touchcancel', this, false);
    this.el.removeEventListener('mousedown', this, false);
    this.el.removeEventListener('mouseup', this, false);
    this.el.removeEventListener('mousemove', this, false);
};

Tap.prototype.handleEvent = function(e) {
    switch (e.type) {
        case 'touchstart': this.start(e); break;
        case 'touchmove': this.move(e); break;
        case 'touchend': this.end(e); break;
        case 'touchcancel': this.cancel(e); break;
        case 'mousedown': this.start(e); break;
        case 'mouseup': this.end(e); break;
        case 'mousemove': this.move(e); break;
    }
};

const tpl = 
    '<div class="pb-container">\
        <div class="pb-cover"></div>\
        <div class="pb-buttons"></div>\
    </div>';

const buttonTpl = '<div class="pb-button">{text}</div>';

var ActionSheet = function(opt){

    // ????????????
    opt = tethys.extend({
        buttons: {},
        inTime: 500,
        outTime: 500
    }, opt);
    
    // ??????
    this.render().update(opt.buttons);
};

// ??????????????????
function bindTapEvent(el, fn){
    new Tap(el);
    el.addEventListener('tap', fn, false);
}

ActionSheet.prototype = {

    // ???????????????
    render: function(){
        var doc = document.documentElement;

        this.el = tethys(tpl);

        this.el.hide().css({
            width: doc.clientWidth + 'px',
            height: doc.clientHeight + 'px'
        });

        bindTapEvent(this.el.find('.pb-cover')[0], this.hide.bind(this));

        tethys('body').append(this.el);
        
        return this;
    },

    // ??????
    show: function(){


        this.el.show();
        this.el.addClass('pb-in');

        setTimeout(function(){
            this.el.removeClass('pb-in');
        }.bind(this), 350);

        return this;
    },

    // ??????
    hide: function(){

        this.el.addClass('pb-out');

        setTimeout(function(){
            this.el.removeClass('pb-out').hide();
        }.bind(this), 300);
        
        return this;
    },

    // ????????????
    update: function(buttons){
        var buttonContainer = this.el.find('.pb-buttons');

        // ??????????????????
        buttonContainer.html('');

        // ??????????????????
        buttons['??????'] = this.hide.bind(this);
        
        // ??????????????????
        Object.keys(buttons).forEach(function(key){
            var n = buttons[key],
                btn = tethys(tethys.tpl(buttonTpl, {
                    text: key
                }));

            // ??????tap??????
            bindTapEvent(btn[0], function(e){

                e.stopPropagation();
                e.preventDefault();
                
                // ??????????????????????????????
                // ??????????????????????????????url????????????
                if(typeof this.action === 'function'){
                    this.action.call(this.context, e);
                }else if(typeof this.action === 'string'){
                    location.href = this.action;
                };
            }.bind({action: n, context: this}));

            // ?????????????????????
            buttonContainer.append(btn);
        }.bind(this));

        return this;
    }
};

return ActionSheet;

})));