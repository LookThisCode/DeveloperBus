'use strict';
var taskApp = angular.module('taskApp', [
    'ngResource',
    'ui.bootstrap'
  ]);
taskApp.config([
  '$routeProvider',
  function ($routeProvider) {
    $routeProvider.when('/', {
      templateUrl: 'views/project.html',
      controller: 'ProjectCtrl'
    }).otherwise({ redirectTo: '/' });
  }
]);
taskApp.directive('masonryWallDir', function () {
  return {
    controller: [
      '$scope',
      '$element',
      '$attrs',
      function ($scope, $element, $attrs) {
        var itemSelector, masonryOptions;
        itemSelector = $attrs.masonryWallDir;
        this.wallContainer = $element;
        this.masonryOptions = _.assign({}, $scope.$eval($attrs.masonryWallOptions), { itemSelector: itemSelector });
        this.masonry = new Masonry(this.wallContainer[0], this.masonryOptions);
        this.masonry.bindResize();
        var self = this;
        this.debouncedReload = _.debounce(function () {
          self.masonry.reloadItems();
          self.masonry.layout();
        }, 100);
      }
    ]
  };
});
taskApp.directive('masonryItemDir', function () {
  return {
    scope: true,
    require: '^masonryWallDir',
    link: function (scope, element, attributes, masonryWallDirCtrl) {
      imagesLoaded(element, function () {
        console.debug(scope.$first);
        if (scope.$first) {
          masonryWallDirCtrl.masonry.prepended(element);
        } else {
          masonryWallDirCtrl.masonry.appended(element);
        }
      });
      scope.$watch(function () {
        return element.height();
      }, function (newHeight, oldHeight) {
        if (newHeight != oldHeight) {
          masonryWallDirCtrl.masonry.layout();
        }
      }, true);
      scope.$on('$destroy', masonryWallDirCtrl.debouncedReload);
    }
  };
});
'use strict';
taskApp.factory('ProjectService', [
  '$resource',
  function ($resource) {
    return $resource('project.json');
  }
]);
taskApp.controller('ProjectCtrl', [
  '$scope',
  'ProjectService',
  function ($scope, ProjectService) {
    $scope.attCollapsed = true;
    $scope.commentCollapsed = true;
    $scope.isCollapsed = true;
    ProjectService.get({}, function (project) {
      $scope.project = project;
      console.debug($scope.project);
    });
    $scope.questCollapse = function (progress) {
      if (progress == 100)
        return true;
      return false;
    };
    $scope.createQuest = function () {
    };
    $scope.modalQuest = function (item) {
    };
  }
]);