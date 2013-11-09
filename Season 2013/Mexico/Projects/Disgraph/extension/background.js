var tabinfo = {};

function unactivateButton(tabId)
{
	tabinfo[tabId]["chrome_button_active"] = false;
	chrome.browserAction.setIcon({tabId: tabId, path: 'images/icon-19-disabled.png'});
	chrome.browserAction.setTitle({tabId: tabId, title: 'Fb Disabled'});
	chrome.tabs.executeScript(tabId, {code:'fbOff();'});
	console.debug('off '+tabId);
}

function activateButton(tabId)
{
	tabinfo[tabId]["chrome_button_active"] = true;
	chrome.browserAction.setIcon({tabId: tabId, path: 'images/icon-19-enabled.png'});
	chrome.browserAction.setTitle({tabId: tabId, title: 'Fb Enabled'});
	chrome.tabs.executeScript(tabId, {code:'fbOn();'});
	console.debug('on '+tabId);
}



chrome.browserAction.onClicked.addListener(function(tab) {
	if(typeof tabinfo[tab.id] != 'undefined')
	{
		if(tabinfo[tab.id]["chrome_button_active"])
		{
			unactivateButton(tab.id);
		}
		else
		{
			activateButton(tab.id);
		}
	}
	else
	{
		tabinfo[tab.id] = {};
		chrome.tabs.executeScript(tab.id, {file:"fbs.js"},function(result){
			activateButton(tab.id);	
		});
	}
});

chrome.tabs.onCreated.addListener(function(tab) {
	console.dir('a');
});

chrome.tabs.onActivated.addListener(function(activeInfo) {
	console.dir('b');
});

chrome.tabs.onReplaced.addListener(function(activeInfo) {
	console.dir('c');
});

chrome.tabs.onHighlighted.addListener(function(activeInfo) {
	console.dir('d');
	/*
	chrome.tabs.getCurrent(function(tab){
		console.dir(tab);
		chrome.tabs.executeScript({file:"fbs.js"},function(result){
			//activateButton(tab.id);	
		});
	});
	*/
});

chrome.tabs.onAttached.addListener(function(activeInfo) {
	console.dir('e');
});

chrome.tabs.onDetached.addListener(function(activeInfo) {
	console.dir('f');
});

chrome.tabs.onMoved.addListener(function(activeInfo) {
	console.dir('g');
});

chrome.tabs.onUpdated.addListener(function(tabId, changeInfo, tab) {
	if(typeof tabinfo[tabId] != 'undefined')
	{
		unactivateButton(tabId);
		delete tabinfo[tabId];
	}
});

chrome.tabs.onRemoved.addListener(function(tabId){
	if(typeof tabinfo[tabId] != 'undefined')
	{
		delete tabinfo[tabId];
	}
});