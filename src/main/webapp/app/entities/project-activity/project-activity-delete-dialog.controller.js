(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .controller('ProjectActivityDeleteController',ProjectActivityDeleteController);

    ProjectActivityDeleteController.$inject = ['$uibModalInstance', 'entity', 'ProjectActivity'];

    function ProjectActivityDeleteController($uibModalInstance, entity, ProjectActivity) {
        var vm = this;

        vm.projectActivity = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ProjectActivity.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
