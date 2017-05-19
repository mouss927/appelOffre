'use strict';

describe('Controller Tests', function() {

    describe('Activity Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockActivity, MockProviderActivity, MockProjectActivity;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockActivity = jasmine.createSpy('MockActivity');
            MockProviderActivity = jasmine.createSpy('MockProviderActivity');
            MockProjectActivity = jasmine.createSpy('MockProjectActivity');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Activity': MockActivity,
                'ProviderActivity': MockProviderActivity,
                'ProjectActivity': MockProjectActivity
            };
            createController = function() {
                $injector.get('$controller')("ActivityDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'monAppelOffreApp:activityUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
