'use strict';

describe('Controller Tests', function() {

    describe('Provider Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockProvider, MockUser, MockQuote, MockProviderEligibility, MockProviderActivity, MockRegistration;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockProvider = jasmine.createSpy('MockProvider');
            MockUser = jasmine.createSpy('MockUser');
            MockQuote = jasmine.createSpy('MockQuote');
            MockProviderEligibility = jasmine.createSpy('MockProviderEligibility');
            MockProviderActivity = jasmine.createSpy('MockProviderActivity');
            MockRegistration = jasmine.createSpy('MockRegistration');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Provider': MockProvider,
                'User': MockUser,
                'Quote': MockQuote,
                'ProviderEligibility': MockProviderEligibility,
                'ProviderActivity': MockProviderActivity,
                'Registration': MockRegistration
            };
            createController = function() {
                $injector.get('$controller')("ProviderDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'monAppelOffreApp:providerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
