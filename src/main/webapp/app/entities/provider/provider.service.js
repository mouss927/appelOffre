(function() {
    'use strict';
    angular
        .module('monAppelOffreApp')
        .factory('Provider', Provider);

    Provider.$inject = ['$resource', 'DateUtils'];

    function Provider ($resource, DateUtils) {
        var resourceUrl =  'api/providers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.registrationDate = DateUtils.convertLocalDateFromServer(data.registrationDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.registrationDate = DateUtils.convertLocalDateToServer(copy.registrationDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.registrationDate = DateUtils.convertLocalDateToServer(copy.registrationDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
