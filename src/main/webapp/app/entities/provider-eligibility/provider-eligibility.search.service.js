(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .factory('ProviderEligibilitySearch', ProviderEligibilitySearch);

    ProviderEligibilitySearch.$inject = ['$resource'];

    function ProviderEligibilitySearch($resource) {
        var resourceUrl =  'api/_search/provider-eligibilities/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
