<?php
/**
 * Created by JetBrains PhpStorm.
 * User: guilherme
 * Date: 14/06/13
 * Time: 20:34
 * To change this template use File | Settings | File Templates.
 */

namespace VILLA\Init;

abstract class Bootstrap
{

    private $routes;

    public function __construct()
    {
        $this->initRoutes();
        $this->run($this->getUrl());
    }

    abstract protected function initRoutes();

    protected function setRoutes(array $routes)
    {
        $this->routes = $routes;
    }

    protected function run($url)
    {
        array_walk($this->routes, function($route) use($url)
        {
            if($url == $route['route']){
                $class = "App\\Controllers\\".ucfirst($route['controller']);
                $controller = new $class;
                $controller->$route['action']();
            }
        });
    }

    protected function getUrl()
    {
        $ar = explode('/', parse_url($_SERVER['REQUEST_URI'], PHP_URL_PATH));
        return "/".$ar[1];
    }
}