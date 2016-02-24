
module.exports = function (grunt) {

    grunt.task.loadNpmTasks('grunt-typescript');

    grunt.registerTask('default', ['typescript']);
   // grunt.registerTask('test', ['qunit']);

    grunt.initConfig({

        pkg:grunt.file.readJSON("package.json"),

        typescript:{//Compile all typescripts
            base:{
                src:['src/ts/application/**/*.ts'],
                dest:'src/js/build/'
            },
            options:{
                target: 'es5',
                module:'amd',
                jsdoc:false,
                comments: false,
                declarations:false
            }
        }

       /* ,

        qunit:{
            files:['src/js/tests/!**!/!*.html']
        }*/

    });



};

