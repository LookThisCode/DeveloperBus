/*function navigateToDashBoard()
{
    
    chrome.tabs.create({'url': "http://google.com"},function(tab){
        //Recibe nueva pagina
    });
}*/

function navigateToDashBoard()
{
    //alert("hola");
    chrome.tabs.query({'active': true}, function(tabs) {chrome.tabs.update(tabs[0].id, {url: "www.facebook.com"});
}

document.addEventListener('DOMContentLoaded', function () {
    
});