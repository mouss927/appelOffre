(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .factory('ProjectPicSearch', ProjectPicSearch);

    ProjectPicSearch.$inject = ['$resource'];

    function ProjectPicSearch($resource) {
        var resourceUrl =  'api/_search/project-pics/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
