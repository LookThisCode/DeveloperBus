<?php


namespace App\Controllers;

use VILLA\Controller\Action,
    APP\Models,
    VILLA\Di\Container;

class Index extends Action {

    public function index()
    {
        $this->render('index');
    }

    public function profile(){
        $this->render('profile');
    }

    public function json(){



    }


}