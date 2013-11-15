# ruby encoding: utf-8
ImagenLocal.delete_all
Comentario.delete_all
Keyword.delete_all
Negociacion.delete_all
Producto.delete_all
Distribuidor.delete_all
Productor.delete_all
User.delete_all

###Distribuidores
distribuidor = Distribuidor.create(nombre: "Ventas y servicios al detal", nit_cedula: "23423123", nombre_representante_legal: "Carolina Duarte", logo_o_foto: "company1.png", descripcion: "Compañia dedicada a la venta de productos inmobiliarios", motivacion_registro: "Me parece interesante su sistema", calificacion: "4")
distribuidor.keyword.create(tag: "madera")
distribuidor.keyword.create(tag: "detal")
distribuidor.keyword.create(tag: "peluches")
distribuidor.keyword.create(tag: "utensilios cocina")