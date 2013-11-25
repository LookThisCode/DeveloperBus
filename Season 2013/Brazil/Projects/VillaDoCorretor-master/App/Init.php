<?php

namespace App;

use VILLA\Init\Bootstrap;

class Init extends Bootstrap{

    protected function initRoutes()
    {
        $ar['home'] = ['route' => '/', 'controller' => 'index', 'action' => 'index'];
        $ar['json'] = ['route' => '/json', 'controller' => 'json', 'action' => 'index'];
        $ar['profile'] = ['route' => '/p', 'controller' => 'index', 'action' => 'profile'];
        $this->setRoutes($ar);
    }
} 