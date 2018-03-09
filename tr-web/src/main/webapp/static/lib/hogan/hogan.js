define("hogan/hogan",["require"],function(){var t={};return function(t,e){function i(t,e,i){var s;if(e&&"object"==typeof e)if(null!=e[t])s=e[t];else if(i&&e.get&&"function"==typeof e.get)s=e.get(t);return s}function s(t,e,i,s){function o(){}function a(){}o.prototype=t,a.prototype=t.subs;var r,n=new o;n.subs=new a,n.subsText={},n.ib();for(r in e)n.subs[r]=e[r],n.subsText[r]=s;for(r in i)n.partials[r]=i[r];return n}function o(t){return String(null===t||void 0===t?"":t)}function a(t){return t=o(t),p.test(t)?t.replace(r,"&amp;").replace(n,"&lt;").replace(h,"&gt;").replace(l,"&#39;").replace(d,"&quot;"):t}t.Template=function(t,e,i,s){t=t||{},this.r=t.code||this.r,this.c=i,this.options=s||{},this.text=e||"",this.partials=t.partials||{},this.subs=t.subs||{},this.ib()},t.Template.prototype={r:function(){return""},v:a,t:o,render:function(t,e,i){return this.ri([t],e||{},i)},ri:function(t,e,i){return this.r(t,e,i)},ep:function(t,e){var i=this.partials[t],o=e[i.name];if(i.instance&&i.base==o)return i.instance;if("string"==typeof o){if(!this.c)throw new Error("No compiler available.");o=this.c.compile(o,this.options)}if(!o)return null;if(this.partials[t].base=o,i.subs){if(void 0===this.activeSub)e.stackText=this.text;o=s(o,i.subs,i.partials,e.stackText||this.text)}return this.partials[t].instance=o,o},rp:function(t,e,i,s){var o=this.ep(t,i);if(!o)return"";else return o.ri(e,i,s)},rs:function(t,e,i){var s=t[t.length-1];if(!c(s))return void i(t,e,this);for(var o=0;o<s.length;o++)t.push(s[o]),i(t,e,this),t.pop()},s:function(t,e,i,s,o,a,r){var n;if(c(t)&&0===t.length)return!1;if("function"==typeof t)t=this.ms(t,e,i,s,o,a,r);if(n=!!t,!s&&n&&e)e.push("object"==typeof t?t:e[e.length-1]);return n},d:function(t,e,s,o){var a,r=t.split("."),n=this.f(r[0],e,s,o),h=this.options.modelGet,l=null;if("."===t&&c(e[e.length-2]))n=e[e.length-1];else for(var d=1;d<r.length;d++)if(a=i(r[d],n,h),null!=a)l=n,n=a;else n="";if(o&&!n)return!1;if(!o&&"function"==typeof n)e.push(l),n=this.mv(n,e,s),e.pop();return n},f:function(t,e,s,o){for(var a=!1,r=null,n=!1,h=this.options.modelGet,l=e.length-1;l>=0;l--)if(r=e[l],a=i(t,r,h),null!=a){n=!0;break}if(!n)return o?!1:"";if(!o&&"function"==typeof a)a=this.mv(a,e,s);return a},ls:function(t,e,i,s,a){var r=this.options.delimiters;return this.options.delimiters=a,this.b(this.ct(o(t.call(e,s)),e,i)),this.options.delimiters=r,!1},ct:function(t,e,i){if(this.options.disableLambda)throw new Error("Lambda features disabled.");return this.c.compile(t,this.options).render(e,i)},b:e?function(t){this.buf.push(t)}:function(t){this.buf+=t},fl:e?function(){var t=this.buf.join("");return this.buf=[],t}:function(){var t=this.buf;return this.buf="",t},ib:function(){this.buf=e?[]:""},ms:function(t,e,i,s,o,a,r){var n,h=e[e.length-1],l=t.call(h);if("function"==typeof l)if(s)return!0;else return n=this.activeSub&&this.subsText[this.activeSub]?this.subsText[this.activeSub]:this.text,this.ls(l,h,i,n.substring(o,a),r);return l},mv:function(t,e,i){var s=e[e.length-1],a=t.call(s);if("function"==typeof a)return this.ct(o(a.call(s)),s,i);else return a},sub:function(t,e,i,s){var o=this.subs[t];if(o)this.activeSub=t,o(e,i,this,s),this.activeSub=!1}};var r=/&/g,n=/</g,h=/>/g,l=/\'/g,d=/\"/g,p=/[&<>\"\']/,c=Array.isArray||function(t){return"[object Array]"===Object.prototype.toString.call(t)}}("undefined"!=typeof exports?exports:t),function(t){function e(t){if("}"===t.n.substr(t.n.length-1))t.n=t.n.substring(0,t.n.length-1)}function i(t){if(t.trim)return t.trim();else return t.replace(/^\s*|\s*$/g,"")}function s(t,e,i){if(e.charAt(i)!=t.charAt(0))return!1;for(var s=1,o=t.length;o>s;s++)if(e.charAt(i+s)!=t.charAt(s))return!1;return!0}function o(e,i,s,n){var h=[],l=null,d=null,p=null;for(d=s[s.length-1];e.length>0;){if(p=e.shift(),d&&"<"==d.tag&&!(p.tag in v))throw new Error("Illegal content in < super tag.");if(t.tags[p.tag]<=t.tags.$||a(p,n))s.push(p),p.nodes=o(e,p.tag,s,n);else if("/"==p.tag){if(0===s.length)throw new Error("Closing tag without opener: /"+p.n);if(l=s.pop(),p.n!=l.n&&!r(p.n,l.n,n))throw new Error("Nesting error: "+l.n+" vs. "+p.n);return l.end=p.i,h}else if("\n"==p.tag)p.last=0==e.length||"\n"==e[0].tag;h.push(p)}if(s.length>0)throw new Error("missing closing tag: "+s.pop().n);return h}function a(t,e){for(var i=0,s=e.length;s>i;i++)if(e[i].o==t.n)return t.tag="#",!0}function r(t,e,i){for(var s=0,o=i.length;o>s;s++)if(i[s].c==t&&i[s].o==e)return!0}function n(t){var e=[];for(var i in t)e.push('"'+l(i)+'": function(c,p,t,i) {'+t[i]+"}");return"{ "+e.join(",")+" }"}function h(t){var e=[];for(var i in t.partials)e.push('"'+l(i)+'":{name:"'+l(t.partials[i].name)+'", '+h(t.partials[i])+"}");return"partials: {"+e.join(",")+"}, subs: "+n(t.subs)}function l(t){return t.replace(_,"\\\\").replace(g,'\\"').replace(y,"\\n").replace(m,"\\r")}function d(t){return~t.indexOf(".")?"d":"f"}function p(t,e){var i="<"+(e.prefix||""),s=i+t.n+x++;return e.partials[s]={name:t.n,partials:{}},e.code+='t.b(t.rp("'+l(s)+'",c,p,"'+(t.indent||"")+'"));',s}function c(t,e){e.code+="t.b(t.t(t."+d(t.n)+'("'+l(t.n)+'",c,p,0)));'}function u(t){return"t.b("+t+");"}var f=/\S/,g=/\"/g,y=/\n/g,m=/\r/g,_=/\\/g;t.tags={"#":1,"^":2,"<":3,$:4,"/":5,"!":6,">":7,"=":8,_v:9,"{":10,"&":11,_t:12},t.scan=function(o,a){function r(){if(_.length>0)v.push({tag:"_t",text:new String(_)}),_=""}function n(){for(var e=!0,i=S;i<v.length;i++)if(e=t.tags[v[i].tag]<t.tags._v||"_t"==v[i].tag&&null===v[i].text.match(f),!e)return!1;return e}function h(t,e){if(r(),t&&n()){for(var i,s=S;s<v.length;s++)if(v[s].text){if((i=v[s+1])&&">"==i.tag)i.indent=v[s].text.toString();v.splice(s,1)}}else if(!e)v.push({tag:"\n"});x=!1,S=v.length}function l(t,e){var s="="+z,o=t.indexOf(s,e),a=i(t.substring(t.indexOf("=",e)+1,o)).split(" ");return T=a[0],z=a[a.length-1],o+s.length-1}var d=o.length,p=0,c=1,u=2,g=p,y=null,m=null,_="",v=[],x=!1,b=0,S=0,T="{{",z="}}";if(a)a=a.split(" "),T=a[0],z=a[1];for(b=0;d>b;b++)if(g==p)if(s(T,o,b))--b,r(),g=c;else if("\n"==o.charAt(b))h(x);else _+=o.charAt(b);else if(g==c){if(b+=T.length-1,m=t.tags[o.charAt(b+1)],y=m?o.charAt(b+1):"_v","="==y)b=l(o,b),g=p;else{if(m)b++;g=u}x=b}else if(s(z,o,b)){if(v.push({tag:y,n:i(_),otag:T,ctag:z,i:"/"==y?x-T.length:b+z.length}),_="",b+=z.length-1,g=p,"{"==y)if("}}"==z)b++;else e(v[v.length-1])}else _+=o.charAt(b);return h(x,!0),v};var v={_t:!0,"\n":!0,$:!0,"/":!0};t.stringify=function(e){return"{code: function (c,p,i) { "+t.wrapMain(e.code)+" },"+h(e)+"}"};var x=0;t.generate=function(e,i,s){x=0;var o={code:"",subs:{},partials:{}};if(t.walk(e,o),s.asString)return this.stringify(o,i,s);else return this.makeTemplate(o,i,s)},t.wrapMain=function(t){return'var t=this;t.b(i=i||"");'+t+"return t.fl();"},t.template=t.Template,t.makeTemplate=function(t,e,i){var s=this.makePartials(t);return s.code=new Function("c","p","i",this.wrapMain(t.code)),new this.template(s,e,this,i)},t.makePartials=function(t){var e,i={subs:{},partials:t.partials,name:t.name};for(e in i.partials)i.partials[e]=this.makePartials(i.partials[e]);for(e in t.subs)i.subs[e]=new Function("c","p","t","i",t.subs[e]);return i},t.codegen={"#":function(e,i){i.code+="if(t.s(t."+d(e.n)+'("'+l(e.n)+'",c,p,1),c,p,0,'+e.i+","+e.end+',"'+e.otag+" "+e.ctag+'")){t.rs(c,p,function(c,p,t){',t.walk(e.nodes,i),i.code+="});c.pop();}"},"^":function(e,i){i.code+="if(!t.s(t."+d(e.n)+'("'+l(e.n)+'",c,p,1),c,p,1,0,0,"")){',t.walk(e.nodes,i),i.code+="};"},">":p,"<":function(e,i){var s={partials:{},code:"",subs:{},inPartial:!0};t.walk(e.nodes,s);var o=i.partials[p(e,i)];o.subs=s.subs,o.partials=s.partials},$:function(e,i){var s={subs:{},code:"",partials:i.partials,prefix:e.n};if(t.walk(e.nodes,s),i.subs[e.n]=s.code,!i.inPartial)i.code+='t.sub("'+l(e.n)+'",c,p,i);'},"\n":function(t,e){e.code+=u('"\\n"'+(t.last?"":" + i"))},_v:function(t,e){e.code+="t.b(t.v(t."+d(t.n)+'("'+l(t.n)+'",c,p,0)));'},_t:function(t,e){e.code+=u('"'+l(t.text)+'"')},"{":c,"&":c},t.walk=function(e,i){for(var s,o=0,a=e.length;a>o;o++)s=t.codegen[e[o].tag],s&&s(e[o],i);return i},t.parse=function(t,e,i){return i=i||{},o(t,"",[],i.sectionTags||[])},t.cache={},t.cacheKey=function(t,e){return[t,!!e.asString,!!e.disableLambda,e.delimiters,!!e.modelGet].join("||")},t.compile=function(e,i){i=i||{};var s=t.cacheKey(e,i),o=this.cache[s];if(o)return o;else return o=this.generate(this.parse(this.scan(e,i.delimiters),e,i),e,i),this.cache[s]=o}}("undefined"!=typeof exports?exports:t),t.template=t.Template,t}),define("hogan",["hogan/hogan"],function(t){return t});