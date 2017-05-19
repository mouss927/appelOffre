(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .factory('ProjectActivitySearch', ProjectActivitySearch);

    ProjectActivitySearch.$inject = ['$resource'];

    function ProjectActivitySearch($resource) {
        var resourceUrl =  'api/_search/project-activities/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
