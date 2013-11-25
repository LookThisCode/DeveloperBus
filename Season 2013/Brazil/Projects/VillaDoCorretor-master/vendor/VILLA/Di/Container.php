<?php
/**
 * Created by JetBrains PhpStorm.
 * User: guilherme
 * Date: 14/06/13
 * Time: 23:11
 * To change this template use File | Settings | File Templates.
 */

namespace VILLA\Di;


class Container {

    public static function getClass($name)
    {
        $strclass = "\\App\\Models\\".ucfirst($name);
        $class = new $strclass(\App\Init::getDB());
        return $class;
    }
}