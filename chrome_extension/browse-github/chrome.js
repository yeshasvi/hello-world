function getAction () {
	alert("Click happened !!!");
}

chrome.browserAction.onClicked.addListener(getAction);