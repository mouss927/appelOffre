(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .factory('QuoteSearch', QuoteSearch);

    QuoteSearch.$inject = ['$resource'];

    function QuoteSearch($resource) {
        var resourceUrl =  'api/_search/quotes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
