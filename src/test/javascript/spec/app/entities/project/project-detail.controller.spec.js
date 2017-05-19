'use strict';

describe('Controller Tests', function() {

    describe('Project Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockProject, MockQuote, MockProviderEligibility, MockProjectActivity, MockCustomer, MockProjectPic;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockProject = jasmine.createSpy('MockProject');
            MockQuote = jasmine.createSpy('MockQuote');
            MockProviderEligibility = jasmine.createSpy('MockProviderEligibility');
            MockProjectActivity = jasmine.createSpy('MockProjectActivity');
            MockCustomer = jasmine.createSpy('MockCustomer');
            MockProjectPic = jasmine.createSpy('MockProjectPic');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Project': MockProject,
                'Quote': MockQuote,
                'ProviderEligibility': MockProviderEligibility,
                'ProjectActivity': MockProjectActivity,
                'Customer': MockCustomer,
                'ProjectPic': MockProjectPic
            };
            createController = function() {
                $injector.get('$controller')("ProjectDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'monAppelOffreApp:projectUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
