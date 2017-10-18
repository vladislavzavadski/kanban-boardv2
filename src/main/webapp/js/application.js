var app = angular.module('myApp', ['dragularModule', 'ngRoute']);

app.run();

app.controller('Basic', ['$element', 'dragularService', '$scope', '$rootScope', '$http', '$q', function TodoCtrl($element, dragularService, $scope, $rootScope, $http, $q) {

    var containers = $element.children().children();

    console.log(containers);
    console.log('lol');

    //$rootScope.tasks = [];
    dragularService('.containerVertical', {
        scope: $scope
       // containersModel: $rootScope.tasks
    });

    $scope.$on('dragulardrop', function (e, e1) {

//        console.log(e, e1, c1);
    });

    $scope.color = function (priority) {

        switch (priority){
            case 'CRITICAL':{
                return 'red';
            }
            case 'HIGH':{
                return 'orange';
            }
            case 'NORMAL':{
                return 'green';
            }
            case 'LOW':{
                return 'white';
            }
        }
    };

    $scope.changeStatus = function (taskId) {

        var projectId = $rootScope.projectId;

        var data = {
            id: taskId,
            taskStatus:{
                id: this.sts.id
            },
            project:{
                id: projectId
            }
        };

        $http.put('/task', data).then(function (response) {
            var getStatuses = $http.get('/status/'+projectId);
            var getTasks = $http.get('/project/'+projectId);

            //$('#create-task').modal('toggle');

            $q.all([getStatuses, getTasks]).then(function (response) {

                $rootScope.taskStatuses = response[0].data;

                var ids = getStatIds(response[0].data);
                var sortedTasks = getSortedTasks(ids, response[1].data.tasks);

                console.log(sortedTasks);
                $rootScope.tasks = [];
                $rootScope.tasks.push.apply($rootScope.tasks, sortedTasks);
                // $rootScope.projectId = $routeParams.projectId;
            });
        });
        
    }
}]);

app.controller('LoginController', ['$scope', '$http', '$location', '$rootScope', function ($scope, $http, $location, $rootScope) {
    $scope.login = function () {
        $scope.cred.remember = true;
        $http({
            method : 'POST',
            url : '/login',
            data : $.param($scope.cred),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(
            function (response) {
                $('#modal-1').modal('toggle');
                $rootScope.currentUser = response.data;
                $location.path("/main");
                $scope.failLogin = false;
            },
            function (response) {
                $scope.failLogin = true;
            }
        );
    };

    $scope.logout = function () {
        $http.get('/logout').then(function (response) {
            $location.path('/index');
        });
    }
}]);

app.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when("/",
        {
            template : '<h1>Hello</h1>',
            controller : 'StartController'
        }).when("/main", {
            templateUrl : '/main',
            controller : 'MainPageController'
    }).when('/index', {
        templateUrl : '/index'
    }).when("/board/:projectId", {
        templateUrl : '/board',
        controller : 'BoardController'
    });
}]);

app.controller('BoardController', ['$q', '$http', '$routeParams', '$rootScope', function ($q, $http, $routeParams, $rootScope) {

    var getStatuses = $http.get('/status/'+$routeParams.projectId);
    var getTasks = $http.get('/project/'+$routeParams.projectId);

    $q.all([getStatuses, getTasks]).then(function (response) {

        $rootScope.taskStatuses = response[0].data;

        var ids = getStatIds(response[0].data);
        var sortedTasks = getSortedTasks(ids, response[1].data.tasks);

        console.log(sortedTasks);
        $rootScope.tasks = [];
        $rootScope.tasks.push.apply($rootScope.tasks, sortedTasks);
        $rootScope.projectId = $routeParams.projectId;
    });


}]);

function getStatIds(arr) {
    var ids = [];

    for(var i=0; i<arr.length; i++){
        ids.push(arr[i].id);
    }
    return ids;
}

function getSortedTasks(statusIds, allTasks) {

    var sortedTasks = [];

    for(var i=0; i<statusIds.length; i++){

        var tasksWithStatus = [];

        for(var j=0; j<allTasks.length; j++){
            if(allTasks[j].taskStatus.id == statusIds[i]){
                tasksWithStatus.push(allTasks[j]);
            }
        }

        sortedTasks.push(tasksWithStatus);
    }

    return sortedTasks;

}

app.controller('StartController', ['$http', '$location', function ($http, $location) {

    $http.get('/authenticated').then(function (response) {
        if(response.data === true){
            $location.path('/main');
        }
        else {
            $location.path('/index');
        }
    });

}]);

app.controller('MainPageController', ['$q', '$http', '$scope', function ($q, $http, $scope) {
    var getCurrentUser = $http.get('/currentuser');
    var projects = $http.get('/projects');

    $q.all([getCurrentUser, projects]).then(function (response) {
        $scope.user = response[0].data;

        $scope.projects = response[1].data;
    });

    $scope.leads = [];
}]);

app.controller('UsersInCompanyController', ['$http', '$scope', function ($http, $scope) {

    $scope.getUsersInCompany = function (companyId) {

        $http.get('/user/company/'+companyId).then(function (response) {
           $scope.leads.push.apply($scope.leads, response.data);

           console.log($scope.leads);
        });

    }
}]);

app.controller('CreateProjectController', ['$scope', '$http', function ($scope, $http) {

    $scope.newproject = function () {
        var data = {
          name: $scope.projname,
          lead:{
              username: $scope.selected
          }
        };

        $http.post('/project', data).then(function (response) {
            $('#new-project-modal').modal('toggle');

            $http.get('/projects').then(function (response1) {
               $scope.projects.length = 0;

               $scope.projects.push.apply($scope.projects, response1.data);
            });
        });
    }
}]);

app.controller('NewStatusController', ['$scope', '$http', function ($scope, $http) {

    $scope.newstatus = function (projectId) {
      var data = {
        name: $scope.statusname,
        project: {
            id: projectId
        }
      };

      $http.post('/status', data).then(function (response) {

        $http.get('/status/'+projectId).then(function (resp) {

            $scope.taskStatuses.length = 0;
            $scope.taskStatuses.push.apply($scope.taskStatuses, resp.data);

        });
      });
    };

}]);

app.controller('CreateTaskController', ['$scope', '$http', '$q', '$rootScope', function ($scope, $http, $q, $rootScope) {

    $scope.createTask = function (projectId) {

        var data = {
            name: $scope.taskName,
            summary: $scope.taskSummary,
            taskStatus: {
                id: $scope.selectedStatus
            },
            priority: $scope.selectedPriority,
            project:{
                id : projectId
            }
        };

        $http.post('/task', data).then(function (response) {
            var getStatuses = $http.get('/status/'+projectId);
            var getTasks = $http.get('/project/'+projectId);

            $('#create-task').modal('toggle');

            $q.all([getStatuses, getTasks]).then(function (response) {

                $rootScope.taskStatuses = response[0].data;

                var ids = getStatIds(response[0].data);
                var sortedTasks = getSortedTasks(ids, response[1].data.tasks);

                console.log(sortedTasks);
                $rootScope.tasks = [];
                $rootScope.tasks.push.apply($rootScope.tasks, sortedTasks);
               // $rootScope.projectId = $routeParams.projectId;
            });
        });
    }

}]);