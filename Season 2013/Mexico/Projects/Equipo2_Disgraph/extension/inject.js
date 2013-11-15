setTimeout(function(){
	//injectJs(chrome.extension.getURL("inject2.js"));
},3000);

function enableScripts()
{
	injectCss(chrome.extension.getURL("FBStyle.css"));
	injectCss(chrome.extension.getURL("animate.css"));
	injectJs(chrome.extension.getURL("jquery.js"));
	injectJs(chrome.extension.getURL("inject2.js"));
}

function disableScripts()
{
	//injectJs(chrome.extension.getURL("disable.js"));
}

function injectCss(link) 
{
	var scr = document.createElement("link");
	scr.rel = 'stylesheet';
	scr.href=link;
	(document.head || document.body || document.documentElement).appendChild(scr);
}

function injectJs(link) 
{
	var scr = document.createElement("script");
	scr.type="text/javascript";
	scr.src=link;
	(document.head || document.body || document.documentElement).appendChild(scr);
}
