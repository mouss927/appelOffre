(function() {
    'use strict';
    angular
        .module('monAppelOffreApp')
        .factory('Registration', Registration);

    Registration.$inject = ['$resource', 'DateUtils'];

    function Registration ($resource, DateUtils) {
        var resourceUrl =  'api/registrations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.subcriptionDate = DateUtils.convertLocalDateFromServer(data.subcriptionDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.subcriptionDate = DateUtils.convertLocalDateToServer(copy.subcriptionDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.subcriptionDate = DateUtils.convertLocalDateToServer(copy.subcriptionDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
