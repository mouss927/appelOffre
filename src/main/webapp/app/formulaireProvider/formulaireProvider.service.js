(function() {
    'use strict';
    angular
        .module('monAppelOffreApp')
        .factory('formulaireProvider', formulaireProvider);

    formulaireProvider.$inject = ['$resource', 'DateUtils'];

    function formulaireProvider ($resource, DateUtils) {
        var resourceUrl =  'api/formulaireProvider/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET'
           
            },
            'update': {
                method: 'PUT',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
         
            },
            'save': {
                method: 'POST',
                //headers: {'Content-Type': 'multipart/form-data'},
                transformRequest: function (data) {
	                var copy = angular.copy(data);
	                //copy.registrationDate = DateUtils.convertLocalDateToServer(copy.registrationDate);
	                return angular.toJson(copy);
                }
            
            }
        });
    }
})();
