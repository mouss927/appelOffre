(function() {
    'use strict';
    angular
        .module('monAppelOffreApp')
        .factory('ProviderEligibility', ProviderEligibility);

    ProviderEligibility.$inject = ['$resource'];

    function ProviderEligibility ($resource) {
        var resourceUrl =  'api/provider-eligibilities/:id';

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
