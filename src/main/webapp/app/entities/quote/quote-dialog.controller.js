(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .controller('QuoteDialogController', QuoteDialogController);

    QuoteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Quote', 'Review', 'Provider', 'Project'];

    function QuoteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Quote, Review, Provider, Project) {
        var vm = this;

        vm.quote = entity;
        vm.clear = clear;
        vm.save = save;
        vm.reviewqs = Review.query({filter: 'quoter-is-null'});
        $q.all([vm.quote.$promise, vm.reviewqs.$promise]).then(function() {
            if (!vm.quote.reviewQ || !vm.quote.reviewQ.id) {
                return $q.reject();
            }
            return Review.get({id : vm.quote.reviewQ.id}).$promise;
        }).then(function(reviewQ) {
            vm.reviewqs.push(reviewQ);
        });
        vm.providers = Provider.query();
        vm.projects = Project.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.quote.id !== null) {
                Quote.update(vm.quote, onSaveSuccess, onSaveError);
            } else {
                Quote.save(vm.quote, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('monAppelOffreApp:quoteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
