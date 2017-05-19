(function() {
    'use strict';

    angular
        .module('monAppelOffreApp')
        .controller('ProjectPicDeleteController',ProjectPicDeleteController);

    ProjectPicDeleteController.$inject = ['$uibModalInstance', 'entity', 'ProjectPic'];

    function ProjectPicDeleteController($uibModalInstance, entity, ProjectPic) {
        var vm = this;

        vm.projectPic = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ProjectPic.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
