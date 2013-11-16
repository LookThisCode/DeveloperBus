/*

 Copyright (C) 2013 by Sergio Daniel Lozano (@zheref)

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.

 */

/**
 * TitanJS, library for PhoneGap Tablet Applications
 * @author Zheref, Sergio Daniel Lozano Garcia
 * @since 07-08-13
 * @requires:
 * Cordova (phonegap.com) for Titan.* and Titan.Journey
 * Underscore (underscorejs.org) for Titan.* and Titan.Utils
 * JQuery (jquery.com) or Zepto.js for Titan.* and Titan.Journey, Titan.View
 * @recommended:
 * Knockout for Titan.View
 * HTML5SQL for Titan.Database
 * Accounting.js for Titan.Utils
 * Handlebars for Titan.strategy
 * JS-Signals for Titan.Signals
 * @pattern module
 * @version 0.5.30
 */

var Titan = (function() {

    var Titan = new (function() {
        var self = this;

        /**
         * @module singleton
         */
        this.App = function(customApp, options) {

            return (function(customApp, options) {
                var innerApp;

                // The instance fields
                function TitanApplication(options) {
                    options = options || {};

                    var platform = options.platform || Titan.Platform.current;

                    this.getPlatform = function() {
                        return platform;
                    };

                    this.setPlatform = function(newPlatform) {
                        Titan.Platform.set(newPlatform);
                        platform = Titan.Platform.get();
                    };

                    var state = options.state || "";

                    this.getState = function() {
                        return state;
                    };

                    this.setState = function(newState) {
                        if (typeof (newState) !== 'undefined') {
                            var isValid = true;
                            isValid += newState === self.AppState.DEVELOPMENT_TIME ||
                                newState === self.AppState.PRODUCTION_TIME;

                            if (isValid) {
                                state = newState;
                            } else {
                                throw "TitanJS: The app-state specified is not a valid " +
                                    "recognized state. Please use the corresponding " +
                                    "Titan.AppState enumeration to specify it.";
                            }
                        } else {
                            throw "TitanJS: No app-state has been specified.";
                        }
                    };

                }

                // The class fields
                var _static = {
                    getInstance: function(customApp, options) {
                        if (innerApp === undefined) {
                            var baseApp = new TitanApplication(options);
                            customApp = customApp || {};
                            innerApp = $.extend(baseApp, customApp);
                        }
                        return innerApp;
                    }
                };

                return _static.getInstance(customApp, options);
            })(customApp, options);

        };

        /**
         * @public
         * @enum
         */
        this.AppState = {
            DEVELOPMENT_TIME: "Development Time",
            PRODUCTION_TIME: "Production Time"
        };

        /**
         * @public
         * @module
         */
        this.Behaviour = new (function() {
            var selfBehaviour = this;

            this.progress = function() {
                return new promise.Promise();
            };

            this.exe = function(action, param1, param2) {
                if (!_.isUndefined(action)) {
                    if (!_.isUndefined(param1)) {
                        if (!_.isUndefined(param2)) {
                            action(param1, param2);
                        } else {
                            action(param1);
                        }
                    } else {
                        action();
                    }
                } else {
                    Titan.Debug.log("TitanJS: (Titan.behaviour.attempt) No action has been " +
                        "passed as parameter");
                }
            };

            this.attempt = this.exe;

            // TODO
            this.Attempt = function(action, params) {

                this.run = function() {

                };

                return this;

            };

            this.doNothing = function() { };

            this.wait = function(seconds, to) {
//                var progress = self.Behaviour.progress();
                setTimeout(function() {
//                    progress.done();
                    selfBehaviour.exe(to);
                }, seconds * 1000);
//                return progress;
            };

            this.TitanPromise = function() {

            };

            var loopElements = [];
            var loopIterator = 0;
            var loopAsyncMethod = selfBehaviour.doNothing;

            /**
             * @private
             * @method
             * @param {function()} atSuccess
             * @param {function()} atError
             */
            var recursiveIterate = function(atSuccess, atError) {
                var loopCurrent = loopElements[loopIterator];
                loopAsyncMethod(loopCurrent, function() {
                    if (loopIterator < loopElements.length - 1) {
                        loopIterator++;
                        recursiveIterate(atSuccess, atError);
                    } else {
                        loopElements = [];
                        loopIterator = 0;
                        loopAsyncMethod = selfBehaviour.doNothing;
                        selfBehaviour.exe(atSuccess);
                    }
                }, function() {
                    self.Debug.error("TitanJS: A problem was presented while " +
                        "iterating recursive async loop");
                    selfBehaviour.exe(atError);
                    loopElements = [];
                    loopIterator = 0;
                    loopAsyncMethod = selfBehaviour.doNothing;
                });
            };

            /**
             * @public
             * @async
             * @method
             * @param {Array} elements
             * @param {function(?, function(), function())} atEach
             * @param {function()} atSuccess
             * @param {function()} atError
             */
            this.recursiveAsyncLoop = function(elements, atEach, atSuccess, atError) {
                loopElements = elements;
                loopIterator = 0;
                loopAsyncMethod = atEach;
                if (elements.length > 0) {
                    recursiveIterate(atSuccess, atError);
                } else {
                    Titan.Behaviour.exe(atSuccess);
                }
            };

            return selfBehaviour;
        })();

        /**
         * @backward
         * @deprecated
         */
        this.behavior = this.Behaviour;

        this.Camera = new (function() {
            var selfCamera = this;

            /**
             * @public
             * @async
             * @method
             * @param {function(String)} returner
             * @param {function(String)} atError
             */
            this.choosePhotoFromLibrary = function (returner, atError) {
                if (typeof (navigator) !== 'undefined') {
                    navigator.camera.getPicture(function (imgData) {
                        returner(imgData);
                    }, function (error) {
                        Titan.Debug.error('TitanJS: A problem has been raised while trying to ' +
                            'take photo from gallery. Reason:');
                        Titan.Debug.error('TitanJS: ' + error);
                        atError(error);
                    }, {
                        destinationType: navigator.camera.DestinationType.FILE_URI,
                        sourceType: navigator.camera.PictureSourceType.PHOTOLIBRARY,
                        allowEdit: true
                    });
                } else {
                    throw "Impossible to geolocate: Cordova library has not been loaded or " +
                        "rendered to its use";
                }
            };

            /**
             * @public
             * @async
             * @method
             * @param {number} quality
             * @param {function(String)} returner
             * @param {function(String)} atError
             */
            this.takePhoto = function (quality, returner, atError) {
                if (typeof (navigator) !== 'undefined') {
                    navigator.camera.getPicture(function (imgData) {
                        returner(imgData);
                    }, function (error) {
                        Titan.Debug.error('TitanJS: A problem has been raised while trying to ' +
                            'take photo from camera. Reason:');
                        Titan.Debug.error('TitanJS: ' + error);
                        atError(error);
                    }, {
                        quality: quality,
                        destinationType: navigator.camera.DestinationType.FILE_URI,
                        sourceType: navigator.camera.PictureSourceType.CAMERA,
                        allowEdit: true
                    });
                } else {
                    throw "Impossible to geolocate: Cordova library has not been loaded or " +
                        "rendered to its use";
                }
            };

            return selfCamera;
        })();

        /**
         * @backward
         * @deprecated
         */
        this.camera = this.Camera;

        this.Chrono = new (function() {
            var selfChrono = this;

            /**
             * @backward
             * @deprecated
             */
            this.wait = self.Behaviour.wait;

            /**
             * @public
             * @method
             * @param {(String|number)=} format
             * @param {Date=} date
             * @returns {string}
             * TODO: Optimize
             */
            this.formattedDate = function (format, date) {

                if (_.isUndefined(date)) {
                    date = new Date();
                }

                if (_.isUndefined(format)) {
                    format = 5;
                }

                var monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
                var curr_date = date.getDate();
                var curr_month = date.getMonth();
                curr_month = curr_month + 1;
                var curr_year = date.getFullYear();
                var curr_min = date.getMinutes();
                var curr_hr = date.getHours();
                var curr_sc = date.getSeconds();

                if (curr_month.toString().length == 1) {
                    curr_month = '0' + curr_month;
                }

                if (curr_date.toString().length == 1) {
                    curr_date = '0' + curr_date;
                }

                if (curr_hr.toString().length == 1) {
                    curr_hr = '0' + curr_hr;
                }

                if (curr_min.toString().length == 1) {
                    curr_min = '0' + curr_min;
                }

                //dd-mm-yyyy
                if (format === 1) {
                    return curr_date + "-" + curr_month + "-" + curr_year;
                }

                //yyyy-mm-dd
                else if (format === 2) {
                    return curr_year + "-" + curr_month + "-" + curr_date;
                }

                //dd/mm/yyyy
                else if (format === 3) {
                    return curr_date + "/" + curr_month + "/" + curr_year;
                }

                // MM/dd/yyyy HH:mm:ss
                else if (format === 4) {
                    return curr_month + "/" + curr_date + "/" + curr_year + " " +
                        curr_hr + ":" + curr_min + ":" + curr_sc;
                }

                // yyyy-mm-dd HH:mm:ss
                else if (format === 5) {
                    return curr_year + "-" + curr_month + "-" + curr_date + " " +
                        curr_hr + ":" + curr_min + ":" + curr_sc;
                }

            };

            /**
             * @public
             * @method
             * @returns {string}
             */
            this.standardDate = function() {
                var fechaActual = new Date();
                var dia = fechaActual.getDate();
                var mes = fechaActual.getMonth();
                var anio = fechaActual.getFullYear();

                return dia + '-' + mes + '-' + anio;
            };

            /**
             * @public
             * @method
             * @returns {string}
             */
            this.linearCompleteDate = function () {

                var date = new Date();
                var format = 5;

                var monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
                var curr_date = date.getDate();
                var curr_month = date.getMonth();
                curr_month = curr_month + 1;
                var curr_year = date.getFullYear();
                var curr_min = date.getMinutes();
                var curr_hr = date.getHours();
                var curr_sc = date.getSeconds();

                if (curr_month.toString().length == 1) {
                    curr_month = '0' + curr_month;
                }

                if (curr_date.toString().length == 1) {
                    curr_date = '0' + curr_date;
                }

                if (curr_hr.toString().length == 1) {
                    curr_hr = '0' + curr_hr;
                }

                if (curr_min.toString().length == 1) {
                    curr_min = '0' + curr_min;
                }

                // yyyy-mm-dd HH:mm:ss
                return curr_year + "" + curr_month + "" + curr_date + "" +
                    curr_hr + "" + curr_min + "" + curr_sc;
            };

            this.getShortLinearDate = function() {
                var date = new Date();

                var curr_date = date.getDate();
                var curr_month = date.getMonth();
                curr_month = curr_month + 1;
                var curr_year = date.getYear();

                if (curr_month.toString().length == 1) {
                    curr_month = '0' + curr_month;
                }

                if (curr_date.toString().length == 1) {
                    curr_date = '0' + curr_date;
                }

                return curr_year.toString().substr(1, 2) + "" + curr_month + "" + curr_date;
            };
            
            /**
             * @public
             * @method
             */
            this.getTimestamp = function() {
                var now = (new Date()).getTime();
                self.Data.log("TitanJS: Timestamp reported = " + now);
                return now;
            };
            
            /**
             * @public
             * @method
             */
            this.startMeter = function(key) {
                var now = selfChrono.getTimestamp();
                self.Data.write("meter_" + key + "_start", now);
            };
            
            /**
             * @public
             * @method
             */
            this.stopMeter = function(key) {
                var now = selfChrono.getTimestamp();
                if (self.Data.isSet("meter_" + key_+ "_start")) {
                    var then = self.Data.read("meter_" + key + "_start");
                    var measured = now - then;
                    self.Notifier.log("TitanJS: Chronometer from " + then + " to " + now + " with difference = " + measured + " has been finished");
                    self.Data.write("meter_" + key + "_stop", now);
                } else {
                    throw "The " + key + "-keyed chronometer hasn't been started";
                }
            };
            
            /**
             * @public
             * @method
             */
            this.getMetric = function(key) {
                if (self.Data.isSet("meter_" + key_+ "_start") && self.Data.isSet("meter_" + key_+ "_stop")) {
                    var start = self.Data.read("meter_" + key + "_start");
                    var end = self.Data.read("meter_" + key + "_stop");
                    var metric = end - start;
                    self.Notifier.log("TitanJS: Memory of chronometer from " + then + " to " + now + " with difference = " + metric);
                } else {
                    throw "The " + key + "-keyed chronometer hasn't been started and/or stopped";
                }
            };

            return selfChrono;
        })();


        /**
         * @backward
         * @deprecated
         */
        this.chrono = this.Chrono;

        /**
         * @public
         * @module
         */
        this.Data = new (function() {
            var selfData = this;

            /**
             * @public
             * @method
             * @param {!String} key
             * @returns {boolean}
             */
            this.isSet = function(key) {
                if (!_.isUndefined(localStorage)) {
                    self.Debug.log('TitanJS: The value of "' + key + '" was checked');
                    return localStorage.getItem(key) !== null;
                } else {
                    throw "TitanJS: localStorage is not available";
                }
            };

            /**
             * @public
             * @shortcut
             */
            this.is = this.isSet;

            /**
             * @public
             * @method
             * @param {!String} key
             * @returns {String}
             */
            this.read = function (key) {
                if (!_.isUndefined(localStorage)) {
                    self.Debug.log('TitanJS: The value of "' + key + '" was read: ' +
                        localStorage.getItem(key));
                    return localStorage.getItem(key);
                } else {
                    throw "TitanJS: localStorage is not available";
                }
            };

            /**
             * @public
             * @method
             * @param {!String} key
             * @param {!String} value
             * @returns {*}
             */
            this.write = function (key, value) {
                if (!_.isUndefined(localStorage)) {
                    self.Debug.log('TitanJS: The value of "' + key + '" was written: ' + value);
                    return localStorage.setItem(key, value);
                } else {
                    throw "TitanJS: localStorage is not available";
                }
            };

            /**
             * @public
             * @method
             * @param {!String} key
             * @returns {*}
             */
            this.erase = function (key) {
                if (!_.isUndefined(localStorage)) {
                    self.Debug.log('TitanJS: The value of "' + key + '" was erased');
                    return localStorage.removeItem(key);
                } else {
                    throw "TitanJS: localStorage is not available";
                }
            };

            return selfData;
        })();

        /**
         * @public
         * @module
         */
        this.Database = new (function() {
            var selfDatabase = this;

            var wsdb = null;
            var dbName = "";
            var dbDesc = "";
            var dbVers = "1.0";
            var dbSize = 0;

            /**
             * @public
             * @async
             * @method
             * @param {Object} configuration
             * @param {function()} atFinish
             */
            this.init = function(configuration, atFinish) {
                dbName = configuration.name || "Database";
                dbDesc = configuration.description || "";
                dbSize = configuration.size || "1.0";

                wsdb = window.openDatabase(dbName, dbVers, dbDesc, dbSize);
                self.Behaviour.exe(atFinish);
            };

            /**
             * @backward
             * @deprecated
             */
            this.initializeWebSqlDatabase = this.init;

            /**
             * @public
             * @method
             * @returns {?Database}
             */
            this.get = function() {
                if (!_.isNull(wsdb)) {
                    return wsdb;
                } else {
                    self.Debug.log("TitanJS: The database hasn't been initialized. NULL returned");
                    return null;
                }
            };

            /**
             * @public
             * @shortcut
             */
            this.getDatabase = this.get;

            /**
             * @backward
             * @deprecated
             */
            this.getWebSqlDatabase = this.get;

            /**
             * Ejecuta la accion definida por execute bajo el contexto de una
             * transaccion en WebSql
             * @public
             * @async
             * @method
             * @param {function(SQLTransaction)} execute Funcion a ejecutarse bajo el contexto de una transaccion WebSql
             * @param {function()} atError Funcion a ejecutarse en caso de que le ajecucion de {execute} falle
             * @param {function()} atSuccess Funcion a ejecutarse en caso de que la ejecucion de {execute} sea exitosa
             */
            this.getNewTransaction = function(execute, atSuccess, atError) {
                var db = selfDatabase.get();
                db.transaction(execute, atError, atSuccess);
            };

            /**
             * @backward
             * @deprecated
             */
            this.transact = this.getNewTransaction;

            /**
             * Ejecuta una consulta de lectura en la base de datos WebSql iniciada
             * @public
             * @async
             * @method
             * @param {String} query La cadena de consulta SELECT
             * @param {Array} data Los datos de interpolacion
             * @param {(function(SQLTransaction, SQLResultSet)|SQLStatementCallback)} atExito Funcion a ejecutarse en caso de que la consulta sea exitosa
             * @param {(function()|SQLStatementErrorCallback)} atError Funcion a ejecutarse en caso de que la consulta falle
             * @param {SQLTransaction=} transact Transaccion con la cual trabajar en caso de no necesitar crear una nueva
             */
            this.query = function (query, data, atExito, atError, transact) {
                if (_.isUndefined(transact)) {
                    this.getNewTransaction(function(transaction) {
                        self.Debug.log("TitanJS QUERY: " + query);
                        transaction.executeSql(query, data, atExito, atError);
                    }, self.Behaviour.doNothing, atError);
                } else {
                    self.Debug.log('QUERY: ' + query);
                    transact.executeSql(query, data, atExito, atError);
                }
            };

            /**
             * Procesa un lote completo de codigo SQL a traves de la libreria HTML5SQL
             * @public
             * @async
             * @method
             * @param {String} query La cadena con el lote de codigo SQL
             * @param {function()} atFinish Accion a ejecutar cuando se termine de procesar satisfactoriamente
             * @param {function()} atError Accion a ejecutar en caso de que se presente un error al intentarlo
             */
            this.queryBatch = function(query, atFinish, atError) {
                if (typeof (html5sql) !== "undefined") {
                    html5sql.openDatabase(dbName, dbDesc, dbSize);
                    html5sql.process(query, atFinish, atError);
                }
                else {
                    throw "TitanJS: HTML5SQL hasn't been included as one of your libraries";
                }
            };

            /**
             * @backward
             * @deprecated
             */
            this.processSQL = this.queryBatch;

            /**
             * Crea la tabla sobre la base de datos que creo la transaccion pasada como parametro
             * @param {Object} schema El esquema de datos de la tabla con sus columnas descritas
             * @param {SQLTransaction} transaction El objeto de transaccion generado por la base de datos
             * @param {function()=} atSuccess Callback que se ejecuta cuando este procedimiento atomico termina exitosamente
             * @param {function()=} atError Callback que se ejecuta cuando este procedimiento termina con errores o no termina
             */
            this.createTable = function (schema, transaction, atSuccess, atError) {
                self.Debug.log("TitanJS: The table '" + schema.name + "' will be created.");
                var query = 'CREATE TABLE IF NOT EXISTS `' + schema.name + '` ( ';
                query += schema.columns.join(", ");
                query += ');';
                self.Debug.log("TitanJS QUERY: " + query);

                //noinspection JSValidateTypes
                transaction.executeSql(query, [], function() {
                    self.Debug.log('Se proceso con exito la creacion de la tabla ' + schema.name);
                    self.Behaviour.exe(atSuccess);
                }, function() {
                    Titan.Debug.error("TitanJS: The table '" + schema.name +
                        "' couldn't be created successfully.");
                    self.Behaviour.exe(atError);
                });
            };

            /**
             * Procesa la consulta SELECT (query) pasada como parametro y retorna
             * los resultados ejecutando {atEach} por cada uno. Al finalizar el ultimo
             * resultado ejecuta {atExito}.
             * Si no se encuentran resultados se ejecuta {atSinResultados}
             * Si falla el procesamiento de la consulta se ejecuta {atError}
             * @public
             * @async
             * @method
             * @param {String} query La cadena de consulta SQL. Debe ser una consulta tipo SELECT
             * @param {Array} data El arreglo de valores dinamicos a pasar a la consulta por cada ?
             * @param {function(?)} atEach Accion a ejecutar por cada elemento encontrado
             * @param {function()=} atSuccess Accion a ejecutar al finalizar el ultimo elemento
             * @param {function()=} atError Accion a ejecutar en caso de que falle el procesamiento
             * @param {function()=} atNoResults Accion a ejecutar en caso de que no se encuentren resultado
             */
            this.list = function(query, data, atEach, atSuccess, atError, atNoResults) {
                selfDatabase.query(query, data, function (transaction, results) {
                    // Verificamos si se retornaron resultados
                    if (results.rows.length > 0) {
                        // Navegamos los resultados
                        for (var i = 0; i < results.rows.length; i++) {
                            atEach(results.rows.item(i));
                            // Verificamos si se trata del ultimo
                            if (i === results.rows.length - 1) {
                                Titan.Behaviour.exe(atSuccess);
                            }
                        }
                    } else {
                        Titan.Behaviour.exe(atNoResults);
                    }
                }, function (error) {
                    Titan.Debug.error('TitanJS: Error trying to process query: "' + query +
                        '" Reason: ' + error.code + ": " + error.message);
                    Titan.Behaviour.exe(atError, error);
                });
            };

            /**
             * @public
             * @async
             * @method
             * @param {!String} tablename
             * @param {String} conditions
             * @param {function()} atEach
             * @param {function()=} atSuccess
             * @param {function()=} atError
             * @param {function()=} atNoResults
             */
            this.listTable = function(tablename, conditions, atEach, atSuccess, atError, atNoResults) {
                var query;

                if (conditions !== "") {
                    query = "SELECT * FROM `" + tablename + "` WHERE " + conditions;
                } else {
                    query = "SELECT * FROM `" + tablename + "`";
                }

                selfDatabase.list(query, [], atEach, atSuccess, atError, atNoResults);
            };

            /**
             * @public
             * @async
             * @method
             * @param {String} tableName
             * @param {String} fieldName
             * @param {String} conditions
             * @param {function(Object)} returner
             * @param {function()} atError
             */
            this.listField = function(tableName, fieldName, conditions, returner, atError) {
                var query, array = [];

                if (conditions !== '') {
                    query = "SELECT " + fieldName + " FROM " + tableName + " WHERE " + conditions;
                } else {
                    query = "SELECT " + fieldName + " FROM " + tableName;
                }

                selfDatabase.list(query, [], function(element) {
                    array.push(element[fieldName]);
                }, function() {
                    returner(array);
                }, function(error) {
                    self.Debug.error('TitanJS: Error trying to bring the ' +
                        'field from the table. Reason: ');
                    self.Debug.error(error.code + ": " + error.message);
                    self.Behaviour.exe(atError);
                }, function() {
                    returner([]);
                });
            };

            /**
             * @public
             * @async
             * @method
             * @param {String} tableName
             * @param {String} fieldName
             * @param {String} conditions
             * @param {function(Object)} returner
             * @param {function()} atError
             * @param {Object} options
             */
            this.listDistinctField = function(tableName, fieldName, conditions, returner, atError, options) {
                var query, array = [];

                if (conditions !== '') {
                    query = "SELECT DISTINCT " + fieldName + " FROM " + tableName + " WHERE " + conditions;
                } else {
                    query = "SELECT DISTINCT " + fieldName + " FROM " + tableName;
                }

                if (!_.isUndefined(options)) {
                    if (self.Utils.isDefined(options.orderBy)) {
                        query += " ORDER BY " + options.orderBy;
                    }
                }

                selfDatabase.list(query, [], function(element) {
                    array.push(element[fieldName]);
                }, function() {
                    returner(array);
                }, function(error) {
                    self.Debug.error('TitanJS: Error trying to bring the ' +
                        ' distinct field from the table. Reason: ');
                    self.Debug.error(error.code + ": " + error.message);
                    self.Behaviour.exe(atError, error);
                }, function() {
                    returner([]);
                });
            };

            /**
             * @backward
             * @deprecated
             */
            this.getDistinctByTable = this.listDistinctField;

            /**
             * Retrieve and returns the first record found by the SELECT query specified
             * @public
             * @async
             * @method
             * @param {String} query The SELECT query to be executed to retrieve the record
             * @param {function()=} returner Callback to be executed to return the field in the record
             * @param {function()=} atNotfound Action to be executed in case record not found
             * @param {function()=} atError Action to be executed when an error is raised by the operation
             */
            this.getFirstByQuery = function(query, returner, atNotfound, atError) {
                query += " LIMIT 0, 1";
                selfDatabase.query(query, [], function (transaction, results) {
                    if (results.rows.length > 0) {
                        returner(results.rows.item(0));
                    } else {
                        Titan.Behaviour.exe(atNotfound);
                    }
                }, function(error) {
                    Titan.Debug.error('TitanJS: Error trying to bring the record from ' +
                        'the table. Reason:');
                    Titan.Debug.error(error);
                    Titan.Behaviour.exe(atError);
                });
            };

            /**
             * Retrieve and return the first record found by looking in the table specified
             * and given the specified conditions to filter
             * @public
             * @async
             * @method
             * @param {String} tableName The name of the table to query
             * @param {String} conditions Series of conditions separated by AND to filter the query
             * @param {function} returner Callback to be executed to return the field in the record
             * @param {function} atNotfound Action to be executed in case record not found
             * @param {function} atError Action to be executed when an error is raised by the operation
             */
            this.getFirstBy = function(tableName, conditions, returner, atNotfound, atError) {
                var query;

                if (conditions !== Titan.Utils.String.EMPTY) {
                    query = "SELECT * FROM " + tableName + " WHERE " + conditions;
                } else {
                    query = "SELECT * FROM " + tableName;
                }

                query += " LIMIT 0, 1";

                selfDatabase.query(query, [], function (transaction, results) {
                    if (results.rows.length > 0) {
                        returner(results.rows.item(0));
                    } else {
                        self.Behaviour.exe(atNotfound);
                    }
                }, function (error) {
                    self.Debug.error("TitanJS: Error trying to bring the record from " +
                        "the table. Reason:");
                    self.Debug.error(error);
                    self.Behaviour.exe(atError);
                });
            };

            /**
             *
             * @param {String} tableName
             * @param {String} id
             * @param {function(Object)} returner
             * @param {function()=} atNotfound
             * @param {function()=} atError
             */
            this.retrieve = function (tableName, id, returner, atNotfound, atError) {
                var query = "SELECT * FROM " + tableName + " WHERE ID = '" + id + "' LIMIT 0, 1";
                selfDatabase.query(query, [], function (transaction, results) {
                    if (results.rows.length > 0) {
                        returner(results.rows.item(0));
                    } else {
                        self.Behaviour.exe(atNotfound);
                    }
                }, function(error) {
                    self.Debug.error('TitanJS: Error trying to bring the ' +
                        'record from the table. Reason:');
                    self.Debug.error(error.code + ": " + error.message);
                    self.Behaviour.exe(atError);
                });
            };

            /**
             * Retrieve and return the value of the specified of the first record found by looking
             * in the table specified and given the specified conditions to filter
             * @param {String} tableName The name of the table to query
             * @param {String} fieldName The name of the column to retrieve its field value and return it
             * @param {String} conditions Series of conditions separated by AND to filter the query
             * @param {function} returner Callback to be executed to return the field in the record
             * @param {function} atNotfound Action to be executed in case record not found
             * @param {function} atError Action to be executed when an error is raised by the operation
             */
            this.getFirstFieldBy = function(tableName, fieldName, conditions,
                                            returner, atNotfound, atError) {
                var query;

                if (conditions !== "") {
                    query = "SELECT " + fieldName + " FROM " + tableName + " WHERE " + conditions;
                } else {
                    query = "SELECT " + fieldName + " FROM " + tableName;
                }

                selfDatabase.query(query, [], function (transaction, results) {
                    if (results.rows.length > 0) {
                        var item = results.rows.item(0);
                        var val = item[fieldName];
                        returner(val);
                    } else {
                        self.Behaviour.exe(atNotfound);
                    }
                }, function (error) {
                    self.Debug.error("TitanJS: Error trying to bring the field of " +
                                                        "the record from the table. Reason: ");
                    self.Debug.error(error.code + ": " + error.message);
                    self.Behaviour.exe(atError);
                });

            };

            /**
             * Retrieve and return the count value of the specified of all the recorda found by looking
             * in the table specified and given the specified conditions to filter
             * @public
             * @async
             * @method
             * @param {String} tableName The name of the table to query
             * @param {String} fieldName The name of the column to retrieve its count field value and return it
             * @param {String} conditions Series of conditions separated by AND to filter the query
             * @param {function(*)} returner Callback to be executed to return the count field in the record
             * @param {function(SQLError)=} atError Action to be executed when an error is raised by the operation
             */
            this.getCountFieldBy = function (tableName, fieldName, conditions, returner, atError) {
                var query;

                if (conditions !== "") {
                    query = "SELECT COUNT(" + fieldName + ") FROM " + tableName + " WHERE " + conditions;
                } else {
                    query = "SELECT COUNT(" + fieldName + ") FROM " + tableName;
                }

                selfDatabase.query(query, [], function (transaction, results) {
                    var item = results.rows.item(0);
                    var val = item["COUNT(" + fieldName + ")"];
                    returner(val);
                }, function (error) {
                    self.Debug.error("TitanJS: Error trying to bring the count " +
                        "field of the record from the table. Reason:");
                    self.Debug.error(error.code + ": " + error.message);
                    self.Behaviour.exe(atError);
                });
            };

            /**
             * Elimina todos los registros que cumplan con la condicion que la columna con el
             * nombre especificado sea igual al valor especificado.
             * @public
             * @async
             * @method
             * @param {!String} tableName El nombre de la tabla
             * @param {?String} fieldkey El nombre de la columna a asociar
             * @param {String} id El valor a buscar
             * @param {function()=} atSuccess Accion que se ejecuta cuando se eliminan con exito
             * @param {function(SQLError)=} atError Accion que se ejecuta cuando sucede un error
             */
            this.deleteBy = function(tableName, fieldkey, id, atSuccess, atError) {
                var query = "DELETE FROM " + tableName + " WHERE " + fieldkey + " = '" + id + "'";
                selfDatabase.query(query, [], function(transaction, results) {
                    Titan.utils.tryTo(atSuccess);
                }, function (error) {
                    self.Debug.error('TitanJS: Error trying to bring the ' +
                        'record from the table. Reason: ');
                    self.Debug.error(error.code + ": " + error.message);
                    self.Behaviour.exe(atError);
                });
            };

            /**
             * Elimina todos los registros cuya column ID sea igual al valor especificado.
             * @public
             * @async
             * @method
             * @param {!String} tableName El nombre de la tabla
             * @param {String} id El valor a buscar como ID
             * @param {function()=} atSuccess Accion que se ejecuta cuando se eliminan con exito
             * @param {function(SQLError)} atError Accion que se ejecuta cuando sucede un error
             */
            this.delete = function (tableName, id, atSuccess, atError) {
                selfDatabase.deleteBy(tableName, 'ID', id, atSuccess, atError);
            };

            this.deleteWhere = function(tablename, where, atSuccess, atError) {
                var query = "DELETE FROM " + tablename + " WHERE " + where;
                this.query(query, [], function (transaction, results) {
                    self.Behaviour.exe(atSuccess);
                }, function (error) {
                    self.Debug.error('TitanJS: Error trying to delete the record from the table');
                    self.Debug.error('Reason: ' + error.code);
                    self.Behaviour.exe(atError);
                });
            };

            /**
             * @public
             * @async
             * @method
             * @param {String} tablename
             * @param {Object} jsonRecord
             * @param {function(SQLTransaction)} atSuccess
             * @param {function()} atError
             * @param {SQLTransaction=} transact
             */
            this.insertInto = function(tablename, jsonRecord, atSuccess, atError, transact) {
                var query = "INSERT INTO " + tablename + " ";
                var fieldNames = [];
                var values = [];

                for (var key in jsonRecord) {
                    var val = jsonRecord[key];
                    if (typeof (val) !== "undefined") {
                        fieldNames.push('`' + key + '`');
                        values.push('"' + val + '"');
                    }
                }

                var fieldNamesString = "(" + fieldNames.join(", ") + ")";
                var valuesString = "(" + values.join(", ") + ")";
                query += fieldNamesString + " VALUES " + valuesString;

                selfDatabase.query(query, [], function (transaction, resultset) {
                    self.Behaviour.exe(atSuccess, transaction);
                }, function(error) {
                    self.Debug.error('Error trying to insert record. Reason: ');
                    self.Debug.error(error.code + ": " + error.message);
                    self.Behaviour.exe(atError);
                }, transact);
            };

            /**
             * @public
             * @async
             * @method
             * @param {String} tablename La tabla a la cual se quiere actualizar el/los registros
             * @param {Object} jsonRecord El objeto con los pares llave-valor para cada uno de los campos a actualizar en el/los registros
             * @param {String} conditions Las condiciones que deben cumplir los registros para ser parte de la actualizacion
             * @param {function()} atSuccess Se ejecuta cuando se finaliza correctamente la transaccion
             * @param {function()} atError Se ejecuta cuando se presenta un error ejecutando la transaccion
             * @param {SQLTransaction=} transact La transaccion a reutilizar en caso de querer reusar una previa
             */
            this.updateWhere = function(tablename, jsonRecord, conditions, atSuccess,
                                        atError, transact) {
                var query = "UPDATE " + tablename + " SET ";
                var pairs = [];
                for (var key in jsonRecord) {
                    var val = jsonRecord[key];
                    pairs.push('`' + key + '`=' + '"' + val + '"');
                }
                var pairsString = pairs.join(", ");
                query += pairsString + " WHERE " + conditions;

                selfDatabase.query(query, [], function (transaction, resultset) {
                    self.Behaviour.exe(atSuccess, transaction);
                }, function(error) {
                    self.Debug.error('Error trying to update record. Reason: ');
                    self.Debug.error(error.code + ": " + error.message);
                    self.Behaviour.exe(atError);
                }, transact);
            };

            /**
             * Vacia la tabla con el nombre dado como parametro dejandola con cero registros
             * cantidadDisponible para llenar desde cero.
             * @public
             * @async
             * @method
             * @param {String=} tablename El nombre de la tabla a vaciar
             * @param {function=} atExito Accion a ejecutar si el borrado total tiene exito
             * @param {function=} atError Accion a ejecutar si el borrado total fracasa
             */
            this.empty = function (tablename, atExito, atError) {
                var query = "DELETE FROM " + tablename;
                this.query(query, [], atExito, atError);
            };

            /**
             * @backward
             * @deprecated
             */
            this.isSet = self.Data.isSet;

            /**
             * @backward
             * @deprecated
             */
            this.read = self.Data.read;

            /**
             * @backward
             * @deprecated
             */
            this.write = self.Data.write;

            /**
             * @backward
             * @deprecated
             */
            this.erase = self.Data.erase;

            /**
             * @private
             * @param tablename
             * @constructor
             */
            var AutoIncrementer = function(tablename) {
                var tn = tablename,
                    gotten = false;

                this.getId = function() {
                    var regName = tablename + "_next_id",
                        data = self.Data;

                    if (data.isSet(regName)) {
                        gotten = true;
                        return data.read(regName);
                    } else {
                        data.write(regName, "1");
                        gotten = true;
                        return 1;
                    }
                };

                this.advance = function() {
                    var regName = tablename + "_next_id",
                        data = self.Data;

                    if (gotten) {
                        var current = self.Utils.safeNumberize(data.read(regName));
                        ++current;
                        data.write(regName, current);
                    } else {
                        throw "TitanJS: The autoincrementer hasn't retrieved an id yet";
                    }
                };
            };

            this.getAutoincrementFor = function(tablename) {
                return new AutoIncrementer(tablename);
            };

            return selfDatabase;

        })();

        /**
         * @backward
         * @deprecated
         */
        this.localData = this.Database;

        /**
         * @public
         * @module
         */
        this.Debug = new (function() {
            var selfDebug = this;

            this.Kind = {
                /**
                 * Constante que define una notificacion informativa
                 * @public
                 * @constant
                 */
                INFO: "Information Notification",

                /**
                 * Constante que define una notificacion de error
                 * @public
                 * @constant
                 */
                ERROR: "Error Notification",

                /**
                 * Constante que define una notificacion de advertencia
                 * @public
                 * @constant
                 */
                WARNING: "Warning Notification",

                /**
                 * Constante que define una notificacion de alerta
                 * @public
                 * @constant
                 */
                ALERT: "Alert Notification",

                /**
                 * Constante que defina una notificacion de progreso
                 * @public
                 * @constant
                 */
                PROGRESS: "Progress Notification",

                /**
                 * Constante que define una notificacion de actualizacion de estado de progreso
                 * @public
                 * @constant
                 */
                NOTIFY_PROGRESS: "Notify Progress Notification",

                /**
                 * Constante que define una notificacion de cierre de progreso
                 * @public
                 * @constant
                 */
                END_PROGRESS: "End Progress Notification"
            };

            /**
             * @backward
             * @deprecated
             */
            this.KIND_INFO = selfDebug.Kind.INFO;

            /**
             * @backward
             * @deprecated
             */
            this.KIND_ERROR = selfDebug.Kind.ERROR;

            /**
             * @backward
             * @deprecated
             */
            this.KIND_WARNING = selfDebug.Kind.WARNING;

            /**
             * @backward
             * @deprecated
             */
            this.KIND_ALERT = selfDebug.Kind.ALERT;

            /**
             * @backward
             * @deprecated
             */
            this.KIND_PROGRESS = selfDebug.Kind.PROGRESS;

            /**
             * @backward
             * @deprecated
             */
            this.KIND_CLOSE_PROGRESS = selfDebug.Kind.END_PROGRESS;

            /**
             * Variable que determina el Logger propio que se maneja para la aplicacion
             * @private
             * @method
             * @param {String} t El texto a mostrar
             * @param {String}  k El tipo de notificacion a mostrar
             */
            var defaultOwnNotifier = function(t, k) {};

            /**
             * Variable que determina el Logger propio que se maneja para la aplicacion
             * @private
             * @method
             * @param {String} t El texto a mostrar
             * @param {String}  k El tipo de notificacion a mostrar
             */
            var ownNotifier = defaultOwnNotifier;

            /**
             * @private
             * @method
             * @returns {boolean}
             */
            var notifierIsDefined = function() {
                return typeof (ownNotifier) === "function" &&
                    ownNotifier !== defaultOwnNotifier;
            };

            /**
             * Permite establecer el metodo propio para registrar eventos
             * @public
             * @method
             * @param {function(String, String)} notifier El metodo para registrar eventos
             */
            this.setNotifier = function (notifier) {
                if (!_.isUndefined(notifier)) {
                    if (typeof (notifier) === "function") {
                        ownNotifier = notifier;
                    } else {
                        throw "TitanJS: The parameter to become the notifier IS NOT a function.";
                    }
                } else {
                    throw "TitanJS: No parameter has been passed to become the notifier.";
                }
            };

            //noinspection UnusedDefinition
            /**
             * @backward
             * @deprecated
             */
            this.setOwnLogger = this.setNotifier;

            /**
             * Permite mostrar un mensaje emergente
             * @public
             * @method
             * @param {string} text The text to show in the alert dialog
             */
            this.alert = function (text) {
                selfDebug.log("TitanJS Alert: " + text);
                if (notifierIsDefined()) {
                    ownNotifier(text, selfDebug.Kind.ALERT);
                } else if (typeof (navigator) !== "undefined") {
                    navigator.notification.alert(text);
                } else {
                    alert(text);
                }
            };

            /**
             * @backward
             * @deprecated
             */
            this.popup = this.alert;

            /**
             * Permite mostrar un mensaje emergente de progreso
             * @public
             * @method
             * @param {string} text The text to show in the progress dialog
             */
            this.progress = function(text) {
                selfDebug.log("TitanJS Start Progress: " + text);
                if (notifierIsDefined()) {
                    ownNotifier(text, selfDebug.Kind.PROGRESS);
                }
            };

            /**
             * Notify and changes the progress text but not order to show the progress dialog
             * @public
             * @method
             * @param {string} text the text to show in the progress dialog
             */
            this.notifyProgress = function(text) {
                selfDebug.log("TitanJS Notify Progress: " + text);
                if (notifierIsDefined()) {
                    ownNotifier(text, selfDebug.Kind.NOTIFY_PROGRESS);
                }
            };

            /**
             * Hides and ends the progress of the process that started the waiting dialog.
             * @public
             * @method
             */
            this.endProgress = function () {
                selfDebug.log("TitanJS End Progress");
                if (notifierIsDefined()) {
                    ownNotifier(self.Utils.String.EMPTY, selfDebug.Kind.END_PROGRESS);
                }
            };

            /**
             * @backward
             * @deprecated
             */
            this.progressEnd = this.endProgress;

            /**
             * Permite registrar un evento informativo en el registro de eventos
             * @public
             * @method
             * @param {string} text El texto a registrar
             */
            this.log = function (text) {
                console.log(text);
                if (notifierIsDefined()) {
                    ownNotifier(text, selfDebug.Kind.INFO);
                }
            };

            /**
             * Permite registrar un evento de error en el registro de eventos
             * @public
             * @method
             * @param {string} text El texto a registrar
             */
            this.error = function (text) {
                console.error(text);
                if (notifierIsDefined()) {
                    ownNotifier(text, selfDebug.Kind.ERROR);
                }
            };
            
            /**
             * Permite reportar un error en el registro de eventos
             * @public
             * @method
             * @param {string} text El texto a registrar
             */
            this.report = function(message, signature, jserror) {
                (new self.Error.Crash(message, signature, jserror)).report();
            };

            /**
             * @public
             * @async
             * @method
             * @param {string} title
             * @param {string} texto
             * @param {string} confirmButtonText
             * @param {string} cancelButtonText
             * @param {function(String)} resultCallback
             * @param {Function} atAborted
             */
            this.prompt = function (title, texto, confirmButtonText, cancelButtonText,
                                    resultCallback, atAborted) {

                if (typeof (navigator) !== 'undefined') {
                    navigator.notification.prompt(texto, function (response) {
                        var buttonIndex = response.buttonIndex;
                        if (buttonIndex === 1) {
                            resultCallback(response.input1);
                        } else if (buttonIndex === 2) {
                            atAborted();
                        } else {
                            Titan.Debug.log('A non specified code was resolved');
                        }
                    }, title, [confirmButtonText, cancelButtonText]);
                } else {
                    var input1 = prompt(texto, "NULL");
                    if (input1 === "NULL") {
                        atAborted();
                    } else {
                        resultCallback(input1);
                    }
                }
            };

            /**
             * @public
             * @async
             * @method
             * @param {String} title
             * @param {String} text
             * @param {String} confirmButtonText
             * @param {String} abortButtonText
             * @param {function()} atConfirmed
             * @param {function()} atAborted
             */
            this.binaryConfirm = function(title, text, confirmButtonText, abortButtonText,
                                          atConfirmed, atAborted) {

                if (typeof (navigator) !== 'undefined') {
                    navigator.notification.confirm(text, function(buttonIndex) {
                        if (buttonIndex === 1) {
                            atConfirmed();
                        } else if (buttonIndex === 2) {
                            atAborted();
                        } else {
                            Titan.Debug.error('An unexpected and behavior-undefined index was detected');
                        }
                    }, title, [confirmButtonText, abortButtonText]);
                } else {
                    throw "TitanJS: Currently the binary confirm is just supported on top of " +
                        "Cordova, and Cordova couldn't be found";
                }
            };

            /**
             *
             * @param {String} title
             * @param {String} text
             * @param {Array.<String>} buttonLabels
             * @param {Object.<String, function()>} actions
             */
            this.optionsConfirm = function(title, text, buttonLabels, actions) {

                if (typeof (navigator) !== 'undefined') {
                    navigator.notification.confirm(text, function(buttonIndex) {
                        var indexAsString = buttonIndex.toString();
                        var action = actions[indexAsString];
                        if (!action) {
                            Titan.Debug.error('An unexpected and behavior-undefined index ' +
                                'was detected');
                        } else {
                            action();
                        }
                    }, title, buttonLabels);
                } else {
                    throw "TitanJS: Currently the options confirm is just supported on top of " +
                        "Cordova, and Cordova couldn't be found";
                }

            };

        })();

        /**
         * @backward
         * @deprecated
         */
        this.debug = this.Debug;

        this.Error = new (function() {
            var selfError = this;

            /**
             * @public
             * @method
             * @param {String} errorCode
             * @return {String}
             */
            this.identifyError = function(errorCode) {
                switch (errorCode) {
                    case FileTransferError.FILE_NOT_FOUND_ERR:
                        return "File not found";
                    case FileTransferError.INVALID_URL_ERR:
                        return "Invalid URL";
                    case FileTransferError.CONNECTION_ERR:
                        return "Connection Error";
                    case FileTransferError.ABORT_ERR:
                        return "Abort Error";
                    default:
                        return "No identified Error";
                }
            };

            /**
             * @public
             * @method
             * @param {String} message
             * @param {String=} signature
             * @returns {{message: *, timestamp: number, signature: string, crash: Function}}
             * @constructor
             */
            this.Crash = function(message, signature, jserror) {
                signature = signature || "UnknownSource";

                return {
                    message: message,
                    time: new Date(),
                    timestamp: this.time.getTime(),
                    signature: signature,
                    error: jserror,
                    describe: function() {
                        return "TitanJS Error: " + this.signature + " has thrown an " +
                            "error at " + this.time.toLocaleTimeString() + " timestamp saying: " + this.message;
                    },
                    crash: function() {
                        throw new Error(this.describe());
                    },
                    report: function() {
                        self.Debug.error(this.describe());
                    }
                };
            };
            
            this.report = function(message, signature, jserror) {
                (new self.Error.Crash(message, signature, jserror)).report();
            };

            return selfError;
        })();

        /**
         * @public
         * @module
         */
        this.FileSystem = new (function() {
            var selfFileSystem = this;

            var filesystem = null;

            var directoryentry = null;

            var fileentry = null;

            /**
             * @public
             * @async
             * @method
             * @param {function()=} atFinish
             * @param {function()=} atError
             */
            this.open = function(atFinish, atError) {
                window.requestFileSystem(LocalFileSystem.PERSISTENT, 0, function(fs) {
                    Titan.Debug.log('TitanJS: FileSystem opened successfully');
                    filesystem = fs;
                    self.Behaviour.exe(atFinish);
                }, function(error) {
                    Titan.Debug.error('TitanJS: Failed to get FileSystem: ' + error.code);
                    self.Behaviour.exe(atError);
                });
            };

            /**
             * @public
             * @method
             */
            this.close = function() {
                filesystem = null;
            };

            this.prepareDirectory = function (path, atFinish, atError) {
                filesystem.root.getDirectory(path, {
                    create: true,
                    exclusive: false
                }, function(d) {
                    directoryentry = d;
                    self.Behaviour.exe(atFinish);
                }, function(error) {
                    Titan.Debug.log('TitanJS: Impossible to get the directory: ' + error.code);
                    self.Behaviour.exe(atError);
                });
            };

            this.download = function (url, where, targetFilename, atFinish, atError) {
                selfFileSystem.prepareDirectory(where, function() {
                    var ft = new FileTransfer();

                    ft.onprogress = function (prEvent) {
                        if (prEvent.lengthComputable) {
                            var perc = prEvent.loaded / prEvent.total;
                            var percentage = ((perc * 100) / 2);
                            self.Debug.log('Percentage: ' + percentage + '%');
                            self.Debug.progress('Download Progress: ' + percentage + '%');
                        } else {
                            self.Debug.log('Downloading...');
                        }
                    };

                    var uri = encodeURI(url);
                    var filePath = where + "/" + targetFilename;

                    ft.download(url, filePath, function(entry) {
                        self.Debug.log('File downloaded: ' + entry.fullPath);
                        self.Behaviour.exe(atFinish);
                    }, function (error) {
                        self.Debug.log('Error intentando descargar: ' + error.source);
                        self.Behaviour.exe(atError);
                    });
                }, function() {
                    self.Debug.error("The directory couldn't be loaded");
                });
            };

            return selfFileSystem;
        })();

        /**
         * @backward
         * @deprecated
         */
        this.fs = this.FileSystem;

        /**
         * @public
         * @shortcut
         */
        this.FS = this.FileSystem;

        /**
         * In charge of managing the application deployment and first execution
         * @package com.instartius.titan.journey
         */
        this.Journey = new (function() {
            var selfJourney;

            /**
             * Manages the execution of the application and execute the start action once everything
             * is ready to deploy
             * @public
             * @async
             * @method
             * @param {function()} start Method that must be runned just after the launch of the app.
             */
            this.launch = function(start) {

                if (Titan.Platform.isNative()) {
                    $(document).ready(function() {
                        start();
                    });

                } else {
                    document.addEventListener('deviceready', function() {
                        $(document).ready(function() {
                            start();
                        });
                    });
                }

            };

            /**
             * Stop and closes the process corresponding de app in the device
             * @public
             * @method
             */
            this.finish = function() {
                if (Titan.Platform.isNative()) {
                    if (Titan.Platform.is(Titan.Platform.IOS)) {
                        self.debug.log("TitanJS: Due to a Apple Store Policy, iOS apps " +
                            "shouldn't be able to close an application from itself.");
                    }
                    if (!_.isUndefined(navigator)) {
                        navigator.device.exitApp();
                    }
                }
            };

            return selfJourney;
        })();

        /**
         * @backward
         * @deprecated
         */
        this.journey = this.Journey;

        this.Measurings = new (function() {
            var selfMeasurings = this;

            /**
             * Compares and returns wheter a string begins with the specified start or not
             * @public
             * @method
             * @param {String} stringValue
             * @param {String} startValue
             */
            this.stringStartsWith = function(stringValue, startValue) {
                return stringValue.indexOf(startValue) === 0;
            };

            return selfMeasurings;
        })();

        /**
         * @backward
         * @deprecated
         */
        this.measurings = this.Measurings;

        this.Models = new (function() {
            var selfModels = this;

            var models = [];

            this.register = function(model) {
                models.push(model);
            };

        })();

        /**
         * @shortcut
         */
        this.Notifier = this.Debug;

        /**
         * Administrador que permite manipular y establecer en que plataforma se esta ejecutando
         * actualmente la aplicacion para asi mismo tener control sobre los distintos dispositivos
         * y APIs a aprovechar
         */
        this.Platform = new (function() {
            var selfPlatform = this;

            /**
             * @public
             * @constant
             * @type {string}
             */
            this.NONE = 'Non-specified';

            /**
             * @public
             * @constant
             * @type {string}
             */
            this.WEBKIT = 'Webkit';

            /**
             * @public
             * @constant
             * @type {string}
             */
            this.NATIVE = 'Native';

            /**
             * @public
             * @constant
             * @type {string}
             */
            this.IOS = 'iOS';

            /**
             * @public
             * @constant
             * @type {string}
             */
            this.ANDROID = 'Android';

            /**
             * @public
             * @constant
             * @type {string}
             */
            this.WINDOWS = 'Windows RT';

            /**
             * @public
             * @constant
             * @type {string}
             */
            this.WINDOWS_PHONE = 'Windows Phone';

            /**
             * @public
             * @constant
             * @type {string}
             */
            this.FIREFOX = 'FireFox OS';

            /**
             * @public
             * @constant
             * @type {string}
             * @backward
             * @deprecated
             */
            this.PLATFORM_WEBKIT = this.WEBKIT;

            /**
             * @public
             * @constant
             * @type {string}
             * @backward
             * @deprecated
             */
            this.PLATFORM_NATIVE = this.NATIVE;

            /**
             * @private
             * @field
             * @type {string}
             */
            var current = this.NONE;

            /**
             * @public
             * @method
             * @type {Function(string)}
             * @param {string} platform The platform to define for the app
             */
            this.set = function(platform) {
                if (typeof (platform) !== 'undefined') {
                    var isValid = true;
                    isValid += platform === selfPlatform.ANDROID ||
                        platform === selfPlatform.FIREFOX ||
                        platform === selfPlatform.IOS ||
                        platform === selfPlatform.WINDOWS ||
                        platform === selfPlatform.NATIVE ||
                        platform === selfPlatform.NONE ||
                        platform === selfPlatform.WEBKIT ||
                        platform === selfPlatform.WINDOWS_PHONE;

                    if (isValid) {
                        current = platform;
                    } else {
                        throw "TitanJS: The platform specified is not a valid recognized " +
                            "platform. Please use the corresponding Titan.Platform enumeration " +
                            "to specify it.";
                    }

                }
            };

            /**
             * @public
             * @method
             * @returns {string}
             */
            this.get = function() {
                return current;
            };

            /**
             * @public
             * @method
             * @type {Function(string)}
             * @param {string} platform The platform to define for the app
             * @backward
             * @deprecated
             */
            this.setPlatform = this.set;

            /**
             * Determina si la plataforma actual es nativa o no
             * @returns {boolean}
             * @public
             */
            this.isNative = function () {
                return current === selfPlatform.NATIVE;
            };

            /**
             * @public
             * @method
             * @param {string} platform
             * @returns {boolean}
             */
            this.is = function(platform) {
                if (typeof (platform) !== 'undefined') {
                    var isValid = true;
                    isValid += platform === selfPlatform.ANDROID || selfPlatform.FIREFOX ||
                        selfPlatform.IOS || selfPlatform.WINDOWS ||
                        selfPlatform.NATIVE || selfPlatform.NONE ||
                        selfPlatform.WEBKIT ||
                        selfPlatform.WINDOWS_PHONE;

                    if (isValid) {
                        return current === platform;
                    } else {
                        throw "TitanJS: The platform trying to verify to be the app's platform" +
                            " is not a valid platform. Please use the corresponding Titan.Platform" +
                            " enumeration to specify it.";
                    }
                } else {
                    throw "TitanJS: No platform has been specified to verify with.";
                }
            };

            return selfPlatform;

        })();

        /**
         * @public
         * @package
         * @backward
         * @deprecated
         */
        this.platformManager = this.Platform;

        /**
         * @public
         * @module
         */
        this.Proxy = new (function() {
            var selfProxy = this;

            var proxyUrl = "";

            var uploadUrls = {};

            var restUrls = {};

            /**
             * @public
             * @method
             * @param {String} url
             */
            this.setProxyUrl = function(url) {
                if (!_.isUndefined(url)) {
                    if (url !== self.Utils.String.EMPTY) {
                        proxyUrl = url;
                    } else {
                        self.Debug.error("The Proxy URL hasn't been assigned due to it's an empty string");
                    }
                } else {
                    throw "TitanJS: No parameter has been passed to become the global Proxy URL";
                }
            };

            /**
             * @public
             * @method
             * @param {String} alias
             * @param {String} url
             */
            this.addUploadUrl = function(alias, url) {
                if (!_.isUndefined(alias) && !_.isUndefined(url)) {
                    if (alias !== self.Utils.String.EMPTY && url !== self.Utils.String.EMPTY) {
                        if (typeof (uploadUrls[alias]) === "undefined") {
                            uploadUrls[alias] = url;
                            self.Debug.log("The upload URL was setted");
                        }
                    } else {
                        throw "TitanJS: Either alias or url parameters are invalid empty strings";
                    }
                } else {
                    throw "TitanJS: Either alias or url parameters hasn't been passed";
                }
            };

            /**
             * @public
             * @method
             * @param {String} alias
             */
            this.getUploadUrl = function(alias) {
                return encodeURI(uploadUrls[alias]);
            };

            /**
             * @public
             * @method
             * @param {String} alias
             * @param {String} url
             */
            this.addRestUrl = function(alias, url) {
                if (!_.isUndefined(alias) && !_.isUndefined(url)) {
                    if (alias !== "" && url !== "") {
                        if (!(alias in restUrls)) {
                            restUrls[alias] = url;
                            self.Debug.log("The Rest URL was setted");
                        }
                    } else {
                        throw "TitanJS: Either alias or url parameters are invalid empty strings";
                    }
                } else {
                    throw "TitanJS: Either alias or url parameter hasn't been passed";
                }
            };

            /**
             * @public
             * @method
             * @param {String} alias
             * @returns {*}
             */
            this.getRestUrl = function(alias) {
                return encodeURI(restUrls[alias]);
            };

            this.getRestRoute = function(alias) {
                var url = restUrls[alias],
                    route = "";
                for (var i = 1; i < arguments.length; i++) {
                    route += "/" + arguments[i];
                }
                return encodeURI(url + route);
            };

            /**
             * Descarga y ejecuta/procesa un Script SQL desde la uri dada como parametro
             * @public
             * @async
             * @method
             * @param uri {String} URI Accesor del SQL Script. El SQL Script debe ser una consulta INSERT
             * @param atFinalizar {function} Accion a ejecutar cuando la consulta se procesa con exito
             * @param atError {function} Accion a ejecutar cuando se presenta un problema intentando descargar el Script
             * @param atProblem {function} Accion a ejecutar cuando se presenta un problema intentando procesar el Script
             */
            this.processRemoteInsertSql = function (uri, atFinalizar, atError, atProblem) {
                Titan.Debug.log('TitanJS: Trying to download SQL script from ' + uri + '...');
                jQuery.get(uri, function (data, textStatus, jqXHR) {
                    Titan.Debug.log('TitanJS: SQL Script downloaded successfully from ' + uri + '...');
                    Titan.Database.processSQL(data, function () {
                        Titan.Debug.log('TitanJS: Script was processed successfully');
                        if (!_.isUndefined(atFinalizar)) {
                            atFinalizar();
                        }
                    }, function (processActionError) {
                        Titan.Debug.error('TitanJS: Table processed unsuccessfully');
                        Titan.Debug.error(processActionError);
                        if (!_.isUndefined(atProblem)) {
                            atProblem(processActionError);
                        }
                    });
                }).fail(function (downloadActionError) {
                        Titan.Debug.error('TitanJS: SQL Script was downloaded unsuccessfully');
                        if (!_.isUndefined(atError)) {
                            atError(downloadActionError);
                        }
                    });
            };

            /**
             * @public
             * @async
             * @method
             * @param url
             * @param jsonData
             * @param atResponse
             * @param atFail
             * @param atOffline
             */
            this.push = function(url, jsonData, atResponse, atFail, atOffline) {
                if (self.Utils.isOnline()) {
                    jsonData = jsonData || {};
                    $.post(url, { "data": jsonData }, function(responseData) {
                        self.Behaviour.exe(atResponse, responseData);
                    }).fail(function() {
                            self.Behaviour.exe(atFail);
                        });
                } else {
                    self.Behaviour.exe(atOffline);
                }
            };

            /**
             * @backward
             * @deprecated
             */
            this.pushData = this.push;

            /**
             * Envia los datos por POST a la URL especificada, llevando el registro con el ID
             * especificado en la tabla especificada.
             * @public
             * @async
             * @method
             * @param {!String} url La direccion a la cual se va a enviar el registro
             * @param {String} tablename El nombre de la tabla de la cual se va a obtener el registro
             * @param {String} id El ID que tiene el registro que estamos buscando
             * @param {function()=} atResponse Accion a ejecutar con el response devuelto por el servidor
             * @param {function()=} atError Accion a ejecutar cuando se presente un error en el request
             * @param {function()=} atOffline Accion a ejecutar cuando se detecta que se ha perdido la
             * conexion a Internet
             */
            this.pushRecord = function(url, tablename, id, atResponse, atError, atOffline) {
                self.Database.retrieve(tablename, id, function(record) {
                    T.Debug.log("Se pretende enviar " + record);
                    selfProxy.push(url, record, atResponse, atError, atOffline);
                }, function() {
                    T.Debug.log("TitanJS: No record has been found to push to the server in" +
                        " the table '" + tablename + "' with ID = '" + id + "'");
                    Titan.Behaviour.exe(atError);
                }, function() {
                    T.Debug.log("TitanJS: A problem has occured while trying to get record from" +
                        " the table '" + tablename + "' with ID = '" + id + "'");
                    Titan.Behaviour.exe(atError);
                });
            };

            /**
             * @public
             * @async
             * @method
             * @param {String} aliasUrl The alias of the Upload URL saved previously
             * @param {String} filePath The local filepath to the file
             * @param {String} filename The name to save the file on the server
             * @param {String} mimetype The mimetype of the file to transfer
             * @param {function()} atResponse The action to be executed after transfer success
             * @param {function()} atError The action to be executed at any error in transfer
             */
            this.pushFile = function(aliasUrl, filePath, filename, mimetype, atResponse, atError) {
                //noinspection UnnecessaryLocalVariableJS
                var s = self,
                    p = self.Proxy,
                    d = self.Debug;
                if (s.isCordovaPresent()) {
                    var options = new FileUploadOptions();
                    options.fileKey = "file";
                    options.fileName = filename;
                    options.mimeType = mimetype;
                    d.log(options);
                    var url = p.getUploadUrl(aliasUrl);
                    var ft = new FileTransfer();
                    d.log("Trying to upload file " + options.fileName);
                    ft.upload(filePath, url, function(ftr) {
                        d.log("TitanJS: '" + options.fileName +
                            "' file uploaded successfully. Results: ");
                        d.log("bytesSent: " + ftr.bytesSent);
                        d.log("responseCode: " + ftr.responseCode);
                        d.log("response: " + ftr.response);
                        s.Behaviour.exe(atResponse);
                    }, function(fte) {
                        d.error("TitanJS: Error sending file. " + fte.code +
                            ": " + s.Error.identifyError(fte.code));
                        s.Behaviour.exe(atError);
                    }, options);
                } else {
                    throw "TitanJS: Cordova Dependency Library hasn't been included";
                }
            };

            /**
             * @public
             * @shortcut
             */
            this.upload = this.pushFile;

            return selfProxy;
        })();
        
        this.Tests = new (function() {
            var selfTests = this;
            
            this.assert = function(description, given, expected) {
                Titan.Notifier.log((given === expected) + ": " + description);
            };
            
        })();

        /**
         * @backward
         * @deprecated
         */
        this.remoteData = this.Proxy;

        this.Session = new (function() {
            var selfSession = this;

            var sessionKinds = [];

            this.addSessionKind = function(key, name) {
                sessionKinds.push({
                    key: key,
                    name: name
                })
            };

            this.addSessionKind("user", "User");

            /**
             * @public
             * @method
             * @param {String} sessionKindKey
             * @param {String} usercode
             * @param {String} userFullname
             * @param {Object=} properties
             */
            this.login = function(sessionKindKey, usercode, userFullname, properties) {
                var user = {
                    usercode: usercode,
                    userFullname: userFullname,
                    properties: properties,
                    startedAt: (new Date()).getTime()
                };
                self.Data.write(sessionKindKey, self.Utils.stringify(user));
            };

            /**
             * @public
             * @method
             * @param sessionKindKey
             */
            this.logout = function(sessionKindKey) {
                self.Data.erase(sessionKindKey);
            };

            /**
             * @public
             * @method
             * @param {String} sessionKind
             * @returns {{usercode: String, userFullname: String, properties: Object, startedAt: number}}
             */
            this.getLoggedAs = function(sessionKind) {
                return self.Utils.jsonify(self.Data.read(sessionKind));
            };

            return selfSession;
        })();

        this.Signals = new (function() {
            var selfSignals = this;

            /**
             * Return a new smoke signal gun so that can be attached and eventually be triggered
             * for it to execute its according action strategy
             * @public
             * @method
             * @returns {signals.Signal}
             */
            this.gun = function() {
                if (!_.isUndefined(signals)) {
                    return new signals.Signal();
                } else {
                    throw "Signals hasn't been included inside de JS libraries";
                }
            };

            /**
             * Fires the signal gun triggering to event
             * @public
             * @method
             * @param {signals.Signal} gun
             * @param {Object=} signal The object with the params to be passed to the gun and its
             * corresponding action plan.
             */
            this.fire = function(gun, signal) {
                gun.dispatch(signal);
            };

            /**
             * Attach an action strategy to the gun and to all its possible signals
             * @public
             * @method
             * @param {signals.Signal} gun The gun to attach its signal
             * @param {function()} action Action strategy to be executed when the gun signal is fired
             */
            this.plan = function(gun, action) {
                gun.add(action);
            };

            return selfSignals;
        })();

        /**
         * @backward
         * @deprecated
         */
        this.signals = this.Signals;

        this.Synchro = new (function() {
            var selfSynchro = this;



            return selfSynchro;
        })();

        this.Utils = new (function() {
            var selfUtils = this;

            this.String = {
                EMPTY: ""
            };

            /**
             * Convierte la cantidad en MB pasado como parametro en su equivalente en Bytes.
             * @public
             * @method
             * @param {number} mb La cantidad de MegaBytes
             * @returns {number}
             */
            this.convertMegaBytesToBytes = function (mb) {
                return mb * 1024 * 1024;
            };

            /**
             * Convierte el nombre de un recurso en su equivalente con extension .html
             * @public
             * @method
             * @param resourceName Nombre del recurso
             * @returns {string} Nombre del documento HTML accesible
             */
            this.htmlizeFileName = function (resourceName) {
                return resourceName + '.html';
            };

            /**
             * Format the number to a priced format number
             * @public
             * @method
             * @param {String} price
             * @returns {String}
             */
            this.pricify = function(price) {
                if (!_.isUndefined(accounting)) {
                    return accounting.formatMoney(price, "$", 2, ",", ".");
                } else {
                    throw "TitanJS: The accounting.js library hasn't been loaded";
                }
            };

            /**
             * Determina si el dispositivo esta conectado a Internet o no
             * @public
             * @method
             * @return {boolean} Valor especificando si el dispositivo esta Online o no.
             */
            this.isOnline = function () {
                if (typeof (navigator) !== 'undefined') {
                    var networkState = navigator.connection.type;
                    self.Debug.log("The connection has been detected as: " + networkState);
                    return networkState === Connection.WIFI ||
                        networkState === Connection.CELL ||
                        networkState === Connection.CELL_2G ||
                        networkState === Connection.CELL_3G ||
                        networkState === Connection.CELL_4G;
                } else {
                    throw "TitanJS: Impossible to detect connection: Cordova library " +
                        "has not been loaded or ready to its use";
                }
            };

            /**
             * Geolocate and returns de coordinates object with the needed information
             * @public
             * @method
             * @param {function(Coordinates)} atSuccess
             * @param {function(PositionError)} atError
             */
            this.geolocate = function (atSuccess, atError) {
                if (typeof (navigator) !== 'undefined') {
                    navigator.geolocation.getCurrentPosition(function (position) {
                        atSuccess(position.coords);
                    }, function (error) {
                        Titan.Debug.error('TitanJS: A problem has been raised while trying to ' +
                            'geolocate. Reason:');
                        Titan.Debug.error('TitanJS: code: ' + error.code +
                            ', message: ' + error.message);
                        if (!_.isUndefined(atError)) {
                            atError(error);
                        }
                    });
                } else {
                    throw "TitanJS: Impossible to geolocate: Cordova library " +
                        "has not been loaded or ready to its use";
                }
            };

            /**
             * Checks whether a certain {value} is something or not
             * @public
             * @method
             * @param {*} value
             * @returns {boolean}
             */
            this.isSomething = function (value) {
                return value !== 0.0 && value !== "" && value !== 0 && value !== null &&
                    value !== undefined && typeof (value) !== 'undefined' &&
                    typeof (value) !== 'null' && !_.isUndefined(value) &&
                    !_.isNull(value) && !_.isNaN(value);
            };

            /**
             * Checks whether a certain value is defined (not null neither undefined) or not
             * @public
             * @method
             * @param value
             */
            this.isDefined = function(value) {
                return !_.isUndefined(value) && !_.isNull(value);
            };

            /**
             * Clone and returns a copy of the object instead of
             * a reference of it.
             * @public
             * @method
             * @param {Object} object The object to clone
             * @returns {Object} The copy created
             */
            this.clone = function(object) {
                return jQuery.extend(true, {}, object);
            };

            /**
             * Converts or parse string to its equivalent number
             * If the string is empty return 0
             * @public
             * @method
             * @param {String} text
             * @returns {number}
             */
            this.safeNumberize = function(text) {
                if (text === "" || text === " " || typeof(text) === "undefined" || typeof (text) === "null" || text === null || text === "null") {
                    return 0;
                } else if (typeof (text) === 'number'){
                    return text;
                } else {
                    return parseInt(text);
                }
            };

            /**
             * If val is something diferrent to null, undefined or NaN, returns it back. If not,
             * return the default value specified.
             * @public
             * @method
             * @param {*} val
             * @param {*} def
             * @returns {*}
             */
            this.refOrDefault = function(val, def) {
                if (Titan.utils.isSomething(val)) {
                    return val;
                } else {
                    return def;
                }
            };

            /**
             * Convierte un objeto JSON en su String correspondiente
             * @public
             * @method
             * @param {Object} obj
             * @returns {String}
             */
            this.stringify = function(obj) {
                return JSON.stringify(obj);
            };

            /**
             * Converts an object-formatted string to the such object
             * @public
             * @method
             * @param {String} strobj
             * @returns {Object}
             */
            this.jsonify = function(strobj) {
                return JSON.parse(strobj);
            };

            /**
             * @backward
             * @deprecated
             */
            this.tryTo = function(action) {
                self.Behaviour.exe(action);
            };

            return selfUtils;
        })();

        /**
         * @backward
         * @deprecated
         */
        this.utils = this.Utils;

        this.View = new (function() {
            var selfView = this;

            /**
             * Identificador que tiene el objeto dentro del DOM que se encarga de hacer las veces del
             * Viewport de la aplicacion, que es el contenedor global HTML cuyo contenido se reemplazara
             * por cada cambio de pagina
             * @private
             * @field
             * @type {String}
             */
            var viewportId = '';

            /**
             * Object that stores the id of the different viewports that can have the application
             * paired with a key as an alias to access that viewport
             * @private
             * @field
             * @type {Object}
             */
            var additionalViewportIds = {};

            /**
             * @private
             * @constant
             * @type {string}
             */
            var NO_VIEW = 'No view';

            /**
             * @private
             * @field
             * @type {string}
             */
            var previousView = NO_VIEW;

            /**
             * @private
             * @field
             * @type {string}
             */
            var currentView = 'startup';

            /**
             * @private
             * @field
             * @type {Array.<string>}
             */
            var possibleFollowingViews = [];

            /**
             * @private
             * @field
             * @type {string}
             */
            var forwardView = NO_VIEW;

            /**
             *
             * @returns {string}
             */
            this.getCurrent = function() {
                return currentView;
            };

            /**
             * Establece el identificador que tiene el objeto dentro del DOM que se encarga de hacer
             * las veces del Viewport de la aplicacion, que es el contenedor global HTML cuyo contenido
             * se reemplazara por cada cambio de pagina
             * @public
             * @method
             * @param {!String} viewportid viewportid El id (sin #) del Viewport
             */
            this.setViewportId = function(viewportid) {
                if (viewportid !== '') {
                    viewportId = viewportid;
                }
            };

            /**
             * @public
             * @method
             * @returns {String}
             */
            this.getViewportId = function() {
                return viewportId;
            };

            /**
             * Adds a key-value pair for alias-viewportId
             * @public
             * @method
             * @param {String} alias
             * @param {String} viewportid El id (sin #) del Viewport
             */
            this.addViewportId = function(alias, viewportid) {
                if (_.isUndefined(alias) || _.isUndefined(viewportid)) {
                    throw "TitanJS: Trying to add a viewport without specifying an alias or the viewportId";
                } else {
                    additionalViewportIds[alias] = viewportid;
                }
            };

            /**
             * Enlaza el modelo de vista pasado como parametro a la vista correspondiente al Viewport
             * previamente definido.
             * @public
             * @method
             * @param {Object} viewmodel El modelo de vista a enlazar
             * @param {String=} alias The viewport alias to bind to in case willing to bind another one
             */
            this.bind = function (viewmodel, alias) {
                if (typeof (ko) !== 'undefined') {
                    if (!_.isUndefined(alias)) {
                        var viewportId = additionalViewportIds[alias];
                        if (!viewportId) {
                            throw "TitanJS: The viewportId aliased '" +
                                alias + "' could not be found";
                        } else {
                            //noinspection JSDuplicatedDeclaration
                            var viewport = $('#' + viewportId)[0];
                            ko.cleanNode(viewport);
                            ko.applyBindings(viewmodel, viewport);
                        }
                    } else {
                        if (viewportId !== '') {
                            //noinspection JSDuplicatedDeclaration
                            var viewport = $('#' + selfView.getViewportId())[0];
                            ko.cleanNode(viewport);
                            ko.applyBindings(viewmodel, viewport);
                        } else {
                            throw "TitanJS: The general viewport Id hasn't been specified";
                        }
                    }
                }
                else throw "TitanJS: Knockout hasn't been included as one of your libraries.";
            };

            /**
             * Render the HTML tree inside contained in the HTML document and insert it to the
             * specified viewport replacing all its previous content
             * @public
             * @async
             * @method
             * @param {String} name Name of HTML resource file (without extension)
             * @param {function(JQuery=)=} atRenderFinish Action to run once render is complete
             * @param {String=} alias The alias of the viewport's viewportId to change to
             */
            this.change = function (name, atRenderFinish, alias) {

                if (typeof ($) !== 'undefined') {

                    // JQuery or Zepto available to use
                    if (!_.isUndefined(alias)) {
                        // to aliased additional viewport
                        var viewportId = additionalViewportIds[alias];

                        if (!viewportId) {
                            throw "The viewportId aliased '" + alias + "' could not be found";
                        } else {
                            //noinspection JSDuplicatedDeclaration
                            var viewport = $('#' + viewportId);
                            if (typeof (ko) !== "undefined") {
                                ko.cleanNode(viewport[0]);
                            }
                            viewport.load(self.Utils.htmlizeFileName(name), function() {
                                if (!_.isUndefined(atRenderFinish)) {
                                    atRenderFinish(viewport);
                                }
                            });
                        }
                    } else {
                        // to general viewport
                        if (viewportId !== '') {
                            //noinspection JSDuplicatedDeclaration
                            var viewport = $('#' + selfView.getViewportId());
                            //noinspection JSUnresolvedFunction,JSUnresolvedVariable
                            if (typeof (ko) !== "undefined") {
                                ko.cleanNode(viewport[0]);
                            }
                            viewport.load(Titan.utils.htmlizeFileName(name), function() {
                                if (!_.isUndefined(atRenderFinish)) {
                                    atRenderFinish(viewport);
                                }
                            });
                        } else {
                            throw "The general viewport Id hasn't been specified";
                        }
                    }

                } else {
                    throw "JQuery or Zepto ($) haven't been included inside JS libraries";
                }
                // function ends
            };

            return selfView;
        })();

        /**
         * @backward
         * @deprecated
         */
        this.viewManager = this.View;

        /**
         * @public
         * @shortcut
         */
        this.Views = this.View;

        /**
         * @public
         * @method
         * @returns {boolean}
         */
        this.isCordovaPresent = function() {
            return typeof (navigator) !== "undefined";
        };

        return self;

    })();

    return Titan;

})();

var Jaeger = (function() {

    var j = new (function() {
        this.holaMundo = function() {
            alert("Hola mundo");
        };

        return this;
    })();

    return j;

})();