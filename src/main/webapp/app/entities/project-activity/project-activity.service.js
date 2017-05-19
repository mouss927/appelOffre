(function() {
    'use strict';
    angular
        .module('monAppelOffreApp')
        .factory('ProjectActivity', ProjectActivity);

    ProjectActivity.$inject = ['$resource'];

    function ProjectActivity ($resource) {
        var resourceUrl =  'api/project-activities/:id';

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
