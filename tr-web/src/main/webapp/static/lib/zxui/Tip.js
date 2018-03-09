define("zxui/Tip",["require","./lib","./Control"],function(require){var e=require("./lib"),t=e.dom,i=e.page,n=require("./Control"),r=function(t,i){var n=e.getTarget(t);if(!e.hasClass(n,i))if(n=e.getAncestorByClass(n,i),!n)return null;return n},o=n.extend({type:"Tip",options:{disabled:!1,main:"",arrow:!1,hideDelay:0,mode:"over",title:null,content:"",prefix:"ecl-ui-tip",triggers:"tooltips",flag:"_ecui_tips",offset:{x:0,y:0},tpl:'<div class="{prefix}-arrow {prefix}-arrow-top"><em></em><ins></ins></div><div class="{prefix}-title"></div><div class="{prefix}-body"></div>'},binds:"onResize, onDocClick, onShow, onHide, hide",init:function(e){e.hideDelay=e.hideDelay<0?o.HIDE_DELAY:e.hideDelay,this.disabled=e.disabled,this.title=e.title,this.content=e.content;var t=e.prefix,i=this.main=document.createElement("div");i.className=t,i.innerHTML=e.tpl.replace(/{prefix}/g,t),i.style.left="-2000px",this.events={over:{on:"mouseenter",un:"mouseleave"},click:{on:"click",un:"click"}}[e.mode]},render:function(){var t=this,i=this.main,n=this.options,r=this.events;if(!this.rendered){if(this.rendered=!0,document.body.appendChild(i),e.on(i,"click",function(e){t.fire("click",{event:e})}),"over"===this.options.mode)e.on(i,"mouseenter",function(){t.clear()}),e.on(i,"mouseleave",function(){t.clear(),t.timer=setTimeout(t.hide,n.hideDelay)});var o=this.elements={},a=n.prefix+"-";e.each("arrow,title,body".split(","),function(t){o[t]=e.q(a+t,i)[0]}),this.addTriggers(n.triggers)}if(!r&&this.triggers)this.show(this.triggers[0]);return this},addTriggers:function(t){var i=this,n=this.options,r=this.events,o=n.flag;if(this.triggers="string"==typeof t?e.q(n.triggers):t.length?t:[t],r)e.each(this.triggers,function(t){if(!e.hasClass(t,o))e.addClass(t,o),e.on(t,r.on,i.onShow)})},refresh:function(t,i){var n=this,r=this.events;if(t="string"==typeof t?e.q(t,i):t.length?t:[t],r){if(this.triggers)e.each(t,function(i){if(!t.parentElement)e.un(i,r.on,n.onShow),e.un(i,r.un,n.onHide)});this.addTriggers(t)}},clear:function(){clearTimeout(this.timer),clearTimeout(this.resizeTimer)},onResize:function(){clearTimeout(this.resizeTimer);var e=this;this.resizeTimer=setTimeout(function(){e.show(e.current)},100)},onDocClick:function(i){var n=this.main,r=e.getTarget(i);if(n!==r&&!~e.array.indexOf(this.triggers,r)&&!t.contains(n,r))this.hide()},onShow:function(t){var i=r(t,this.options.flag);if(this.clear(),i&&this.current!==i){var n=this.events;if(n){if(e.on(i,n.un,this.onHide),e.un(i,n.on,this.onShow),this.current)e.on(this.current,n.on,this.onShow),e.un(this.current,n.un,this.onHide);if("click"===this.options.mode)e.on(document,"click",this.onDocClick)}this.current=i,this.fire("beforeShow",{target:i,event:t}),this.show(i)}},onHide:function(){var e=this.options;if(this.clear(),e.hideDelay)this.timer=setTimeout(this.hide,e.hideDelay);else this.hide()},show:function(t){var i=this.options,n=this.elements;if(this.clear(),this.current=t,e.on(window,"resize",this.onResize),n.title.innerHTML=this.title||"",n.body.innerHTML=this.content,e[this.title?"show":"hide"](n.title),!i.arrow)e.hide(n.arrow);this.computePosition(),this.fire("show",{target:t})},hide:function(){var t=this.main,i=this.events,n=this.current;if(i&&n)if(e.on(n,i.on,this.onShow),e.un(n,i.un,this.onHide),"click"===this.options.mode){var r=/msie (\d+\.\d+)/i.test(navigator.userAgent)?document.documentMode||+RegExp.$1:void 0;if(7==r||8==r);else e.un(document,"click",this.onDocClick)}this.clear();var o=this.elements.arrow;t.style.left=-t.offsetWidth-o.offsetWidth+"px",this.current=null,e.un(window,"resize",this.onResize),this.fire("hide")},isVisible:function(){return!!this.current},setTitle:function(t){this.title=t||"";var i=this.elements;i.title.innerHTML=this.title,e[this.title?"show":"hide"](i.title)},setContent:function(e){this.content=e||"",this.elements.body.innerHTML=this.content},computePosition:function(){var e=this.options,n=this.current,r=this.main,o=this.elements.arrow,a=e.arrow,s=t.getPosition(n),l=e.prefix+"-arrow",h=s.top,u=s.left,c=n.offsetWidth,f=n.offsetHeight,d=u+c,p=h+f,g=u+c/2,m=h+f/2,y=r.offsetWidth,v=r.offsetHeight,b=o.firstChild.offsetWidth,_=o.firstChild.offsetHeight,x=i.getScrollTop(),w=i.getScrollLeft(),S=w+i.getViewWidth(),T=x+i.getViewHeight(),C=n.getAttribute("data-tooltips");if(C)a=/[trblc]{2}/.test(C)?C:"1";var E,A;if(!a||"1"===a){var k=c>y||u-(y-c)/2>0&&S>=d+(y-c)/2?"c":u+y>S?"r":"l",M=f>v||h-(v-f)/2>0&&T>=p+(v-f)/2?"c":h+v>T?"b":"t";if(T>=p+_+v)A="b",E=k;else if(S>=d+y+b)A="r",E=M;else if(h-v-_>=x)A="t",E=k;else if(u-y-b>=w)A="l",E=M;a=A+E}else A=a.charAt(0),E=a.charAt(1);var L={l:"left",r:"right",t:"top",b:"bottom"},z=e.offset;if(o.className=l+" "+l+"-"+L[A],b=o.firstChild.offsetWidth,_=o.firstChild.offsetHeight,{t:1,b:1}[A]){u={l:u,c:g-y/2,r:d-y}[E],h={t:h-_-v-z.y,b:p+_+z.y}[A];var O=(c-b)/2;o.style.left={c:(y-b)/2,l:O,r:y-Math.max(b,O)}[c>y?"c":E]+"px",o.style.top=""}else if({l:1,r:1}[A]){h={t:h,c:m-v/2,b:p-v}[E],u={l:u-b-y-z.x,r:d+b+z.x}[A];var N=(f-_)/2;o.style.top={c:(v-_)/2,t:N,b:v-Math.max(_,N)}[f>v?"c":E]+"px",o.style.left=""}t.setStyles(r,{left:"151px",top:"104px"})}});return o.HIDE_DELAY=500,o});