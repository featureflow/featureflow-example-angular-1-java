(function() {
    'use strict';

    angular
        .module('featureflowExampleAngularOneJavaApp')
        .config(alertServiceConfig);

    alertServiceConfig.$inject = ['AlertServiceProvider'];

    function alertServiceConfig(AlertServiceProvider) {
        // set below to true to make alerts look like toast
        AlertServiceProvider.showAsToast(false);
    }
})();
