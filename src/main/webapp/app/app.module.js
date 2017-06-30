(function() {
    'use strict';

    angular
        .module('featureflowExampleAngularOneJavaApp', [
            'ngStorage',
            'tmh.dynamicLocale',
            'pascalprecht.translate',
            'ngResource',
            'ngCookies',
            'ngAria',
            'ngCacheBuster',
            'ngFileUpload',
            'ui.bootstrap',
            'ui.bootstrap.datetimepicker',
            'ui.router',
            'infinite-scroll',
            // jhipster-needle-angularjs-add-module JHipster will add new module here
            'angular-loading-bar',
            'ng-featureflow'
        ])
        .run(run);

    run.$inject = ['stateHandler', 'translationHandler'];

    function run(stateHandler, translationHandler) {
        stateHandler.initialize();
        translationHandler.initialize();
    }

    angular
        .module('featureflowExampleAngularOneJavaApp')
        .config(['featureflowProvider', function(featureflowProvider){
            var FF_API_KEY = 'js-env-24a91bec0f844269809f9e43d7a985cf';
            featureflowProvider.init(FF_API_KEY, {});
        }]);
})();
