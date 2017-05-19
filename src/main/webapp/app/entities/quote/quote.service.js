(function() {
    'use strict';
    angular
        .module('monAppelOffreApp')
        .factory('Quote', Quote);

    Quote.$inject = ['$resource'];

    function Quote ($resource) {
        var resourceUrl =  'api/quotes/:id';

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
