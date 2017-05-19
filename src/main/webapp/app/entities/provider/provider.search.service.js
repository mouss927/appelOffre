(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .factory('ProviderSearch', ProviderSearch);

    ProviderSearch.$inject = ['$resource'];

    function ProviderSearch($resource) {
        var resourceUrl =  'api/_search/providers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
