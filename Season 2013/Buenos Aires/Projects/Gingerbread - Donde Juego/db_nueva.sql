SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


CREATE TABLE IF NOT EXISTS `secciones` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  `nombreVisible` varchar(50) NOT NULL,
  `administrativo` tinyint(1) NOT NULL DEFAULT '0',
  `visible` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `administrativo` (`administrativo`),
  KEY `visible` (`visible`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;


INSERT INTO `secciones` (`id`, `nombre`, `nombreVisible`, `administrativo`, `visible`) VALUES
(1, 'permisos', 'Permisos', 1, 1),
(2, 'actividades', 'Actividades', 1, 1),
(3, 'usuarios', 'Usuarios', 1, 1);

-- ----------------------------------------------------

CREATE TABLE IF NOT EXISTS `usuarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `usuario` varchar(50) NOT NULL,
  `password` char(40) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `salt` char(10) NOT NULL,
  `borrado` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `borrado` (`borrado`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;


INSERT INTO `usuarios` (`id`, `usuario`, `password`, `salt`, `borrado`) VALUES
(1, 'sigma', '98147977e4a3ed0ba48c45ba29c376ab04d8a528', 'BHYadVlk0L', 0);

-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `actividades` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `idUsuario` int(11) unsigned NOT NULL,
  `descripcion` varchar(300) NOT NULL,
  `fecha` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idUsuario` (`idUsuario`),
  FOREIGN KEY (idUsuario) REFERENCES usuarios(id)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1;


INSERT INTO `actividades` (`id`, `idUsuario`, `descripcion`, `fecha`) VALUES
(1, 1, 'Se ha creado el administrador', NOW());

-- --------------------------------------------------------

CREATE TABLE IF NOT EXISTS `permisos` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `usuario` int(11) NOT NULL,
  `seccion` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `usuario` (`usuario`),
  KEY `seccion` (`seccion`),
  FOREIGN KEY (usuario) REFERENCES usuarios(id),
  FOREIGN KEY (seccion) REFERENCES secciones(id)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1;

INSERT INTO `permisos` (`id`, `usuario`, `seccion`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 1, 3);