(function () {
    'use strict';

    angular
        .module('featureflowExampleAngularOneJavaApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
