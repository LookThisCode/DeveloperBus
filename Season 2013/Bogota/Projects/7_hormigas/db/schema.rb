# encoding: UTF-8
# This file is auto-generated from the current state of the database. Instead
# of editing this file, please use the migrations feature of Active Record to
# incrementally modify your database, and then regenerate this schema definition.
#
# Note that this schema.rb definition is the authoritative source for your
# database schema. If you need to create the application database on another
# system, you should be using db:schema:load, not running all the migrations
# from scratch. The latter is a flawed and unsustainable approach (the more migrations
# you'll amass, the slower it'll run and the greater likelihood for issues).
#
# It's strongly recommended that you check this file into your version control system.

ActiveRecord::Schema.define(version: 20131116041134) do

  create_table "comentarios", force: true do |t|
    t.string   "comentario"
    t.integer  "distribuidor_id"
    t.integer  "productor_id"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  add_index "comentarios", ["distribuidor_id"], name: "index_comentarios_on_distribuidor_id", using: :btree
  add_index "comentarios", ["productor_id"], name: "index_comentarios_on_productor_id", using: :btree

  create_table "distribuidors", force: true do |t|
    t.string   "nombre"
    t.string   "nit_cedula"
    t.string   "nombre_representante_legal"
    t.string   "logo_o_foto"
    t.string   "descripcion"
    t.string   "motivacion_registro"
    t.integer  "calificacion"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.integer  "user_id"
  end

  create_table "imagen_locals", force: true do |t|
    t.string   "url"
    t.integer  "local_id"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  add_index "imagen_locals", ["local_id"], name: "index_imagen_locals_on_local_id", using: :btree

  create_table "keywords", force: true do |t|
    t.string   "tag"
    t.string   "descripcion"
    t.integer  "productor_id"
    t.integer  "distribuidor_id"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  add_index "keywords", ["distribuidor_id"], name: "index_keywords_on_distribuidor_id", using: :btree
  add_index "keywords", ["productor_id"], name: "index_keywords_on_productor_id", using: :btree

  create_table "locals", force: true do |t|
    t.string   "ciudad"
    t.string   "pais"
    t.float    "lat"
    t.float    "lon"
    t.string   "comentario"
    t.integer  "distribuidor_id"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  add_index "locals", ["distribuidor_id"], name: "index_locals_on_distribuidor_id", using: :btree

  create_table "negocio_cerrados", force: true do |t|
    t.string   "acuerdo"
    t.string   "condiciones"
    t.string   "calificacion_transaccion_distribuidor"
    t.string   "calificacion_transaccion_productor"
    t.integer  "distribuidor_id"
    t.integer  "producto_id"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  add_index "negocio_cerrados", ["distribuidor_id"], name: "index_negocio_cerrados_on_distribuidor_id", using: :btree
  add_index "negocio_cerrados", ["producto_id"], name: "index_negocio_cerrados_on_producto_id", using: :btree

  create_table "productors", force: true do |t|
    t.string   "nombre"
    t.string   "nit_cedula"
    t.string   "logo_o_foto"
    t.string   "descripcion"
    t.string   "motivacion_registro"
    t.integer  "calificacion"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.integer  "user_id"
  end

  create_table "productos", force: true do |t|
    t.string   "nombre"
    t.string   "descripcion"
    t.string   "url_imagen"
    t.integer  "productor_id"
    t.datetime "created_at"
    t.datetime "updated_at"
  end

  add_index "productos", ["productor_id"], name: "index_productos_on_productor_id", using: :btree

  create_table "users", force: true do |t|
    t.string   "name"
    t.string   "provider"
    t.string   "uid"
    t.text     "email"
    t.string   "encripted_password"
    t.string   "salt"
    t.datetime "created_at"
    t.datetime "updated_at"
    t.integer  "distribuidor_id"
    t.integer  "productor_id"
  end

end
