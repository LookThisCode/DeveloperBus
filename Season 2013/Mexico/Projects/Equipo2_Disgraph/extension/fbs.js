var scriptsEnabled = false;

function fbOn()
{
	if(typeof enableScripts != 'undefined')
	{
		if(!scriptsEnabled)
		{
			scriptsEnabled = true;
			enableScripts();
		}
	}
}

function fbOff()
{
}