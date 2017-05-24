(function() {
    'use strict';
    angular
        .module('monAppelOffreApp')
        .factory('EligibleProject', EligibleProject);

    EligibleProject.$inject = ['$resource', 'DateUtils'];

    function EligibleProject ($resource, DateUtils) {
        var resourceUrl =  'api/eligiblePojects';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateSend = DateUtils.convertLocalDateFromServer(data.dateSend);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateSend = DateUtils.convertLocalDateToServer(copy.dateSend);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateSend = DateUtils.convertLocalDateToServer(copy.dateSend);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
