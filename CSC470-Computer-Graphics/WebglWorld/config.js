/**
 * Application configuration declaration.
 */
/*
require.config({

    baseUrl:'../src/js/',

    paths:{
        jquery : 'libs/jquery/jquery',
        bootstrap:'libs/bootstrap/bootstrap.min',
        build:'build',
        qunit:'../../node_modules/qunitjs/qunit/qunit'
    },

    shim:{
        jquery:{
            exports:'$'
        },
        bootstrap:{
            'deps':['jquery']
        }
    }
});

requirejs(['build/application/Application'], function(app){
    app.Application.getInstance().main();
});*/

require.config({

    baseUrl:'../node_modules/',

    paths:{
        jquery : 'jquery/dist/jquery.min',
        bootstrap:'bootstrap/dist/js/bootstrap.min',
        build:'../src/js/build'
    },

    shim:{
        jquery:{
            exports:'$'
        },
        bootstrap:{
            'deps':['jquery']
        }
    }

});

requirejs(['build/application/Application', 'bootstrap'], function(app){
    app.Application.getInstance().main();
});

