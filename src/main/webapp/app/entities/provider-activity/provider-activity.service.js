(function() {
    'use strict';
    angular
        .module('monAppelOffreApp')
        .factory('ProviderActivity', ProviderActivity);

    ProviderActivity.$inject = ['$resource'];

    function ProviderActivity ($resource) {
        var resourceUrl =  'api/provider-activities/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
