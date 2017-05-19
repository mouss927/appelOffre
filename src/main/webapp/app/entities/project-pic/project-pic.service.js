(function() {
    'use strict';
    angular
        .module('monAppelOffreApp')
        .factory('ProjectPic', ProjectPic);

    ProjectPic.$inject = ['$resource'];

    function ProjectPic ($resource) {
        var resourceUrl =  'api/project-pics/:id';

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
