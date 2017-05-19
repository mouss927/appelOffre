(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .factory('ProviderActivitySearch', ProviderActivitySearch);

    ProviderActivitySearch.$inject = ['$resource'];

    function ProviderActivitySearch($resource) {
        var resourceUrl =  'api/_search/provider-activities/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
