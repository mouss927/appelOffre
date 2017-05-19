(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .factory('RegistrationSearch', RegistrationSearch);

    RegistrationSearch.$inject = ['$resource'];

    function RegistrationSearch($resource) {
        var resourceUrl =  'api/_search/registrations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
