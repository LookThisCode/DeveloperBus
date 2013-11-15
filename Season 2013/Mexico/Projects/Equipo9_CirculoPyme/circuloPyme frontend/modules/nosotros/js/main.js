(function(App){
	"use strict";

	var Module = function(){
		this.a = App;
		this.url = this.a.current._url;
		this.module = this.a._data.modules[this.a.current.name];

		this.getSubCategory();
		this.subMenuFunctionality();
	}

	Module.prototype.subMenuFunctionality = function() {
		var lis = document.querySelectorAll('#sub-menu>li');
		var t = this;
		for(var i = 0, len = lis.length; i < len; i++){
			var li = lis[i];
			li.addEventListener('click', function(){
				if(this.className !== 'active'){
					t.activeSubCategory(this);
					var sub = this.childNodes[0];
					var sub = sub.getAttribute('data-ltag');
					t.callHTML(sub);
				}
			}, false);
		}
	};

	Module.prototype.activeSubCategory = function(li) {
		var lis = document.querySelectorAll('#sub-menu>li');
		for(var i = 0, len = lis.length; i < len; i++){
			var l = lis[i];
			l.className = '';
		}

		if(li !== null){
			li.className = 'active';
		}
	};

	Module.prototype.callHTML = function(sub) {
		var t = this;

		var parameters = {
			js: {load: false},
			css: {load: false},
			html: {load: true, file: sub+'.html'},
			translate: {load: true, file: sub+'.xml'}
		}
		this.a.current.start(function(){
			var location = t.url[0]+'/'+sub;
			dhtmlHistory.add(location, {message: "Module: " +t.url[0]+"\n subCategory: "+sub});
		}, parameters);
	};

	Module.prototype.getSubCategory = function() {
		var keys = Object.keys(this.module);
		//Carga la primera categoría
		var sub;
		if(this.url.length <= 1 || !this.module.hasOwnProperty(this.url[1])){
			sub = keys[0];
		}else{
			sub = this.url[1];
		}

		var li = document.querySelector('#sub-menu>li>a[data-ltag="'+sub+'"]');
		li = li.parentNode;

		this.activeSubCategory(li);
		this.callHTML(sub);
	};

	var m = new Module();
})(App);