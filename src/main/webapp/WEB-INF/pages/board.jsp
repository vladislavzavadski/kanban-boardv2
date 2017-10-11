    <body>
        <header>
            <nav class="navbar navbar-default">
                <div class="container-fluid" style="background: linear-gradient(to right, #2731B4, #2790B4);">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <a class="navbar-brand" href="#" style="color:white">Kanban Board</a>
                    </div>
                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1" ng-controller="LoginController">
                        <button data-toggle="modal" data-target="#create-task" class="btn btn-default"><span class="glyphicon glyphicon-plus"></span>Create task</button>
                        <ul class="nav navbar-nav navbar-right">
                            <span ng-controller="MainPageController">{{user.firstName}} {{user.lastName}}</span>
                            <a ng-click="logout()" class="btn btn-primary btn-info">Logout</a>
                        </ul>
                    </div>
                </div>
            </nav>
        </header>
        <div class="stat">
            <div class="row tasks wrapper" >
                <div>
                    <div class="col-md-2 status" ng-controller = 'Basic'  ng-repeat="taskStatus in taskStatuses">
                        <span style="font-size: 30px; font-family: 'Love Ya Like A Sister', cursive;">{{taskStatus.name}}</span>
                        <div class="row containerVertical">
                            <div class="col-md-12 task" ng-repeat="task in tasks[$index]" style="background-color: {{color(task.priority)}};">
                               <span style="font-family: 'Love Ya Like A Sister', cursive; font-size: 20px;">{{task.name}}</span>
                               <span>
                                   <select ng-options="item.name for item in taskStatuses track by item.id" ng-change="changeStatus(task.id)" ng-model="sts">
                                   </select>
                               </span>
                               <div style="height: 114px; overflow-x:hidden;">
                                    {{task.summary}}
                               </div>
                               <span><span class="glyphicon glyphicon-paperclip" style="text-align: left; margin-top: 10px;"></span>123</span>
                               <span style="position: absolute; margin-top: 10px; margin-left: 10px; right: 10;"><span class="glyphicon glyphicon-comment"></span>12</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-2 status" ng-controller="NewStatusController">
                    <a style="font-family: 'Love Ya Like A Sister', cursive; font-size: 20px;" class="btn btn-block" data-toggle="collapse" data-target="#new-status">New status</a>
                    <div class="collapse" id="new-status">
                        <form ng-submit="newstatus(projectId)">
                          <div class="form-group">
                            <label for="stat">Name:</label>
                            <input type="text" autocomplete="false" class="form-control" id="stat" ng-model="statusname">
                          </div>
                          <button type="submit" class="btn btn-success">Create</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="create-task" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content"  ng-controller="CreateTaskController">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">New task</h4>
                    </div>
                    <form ng-submit="createTask(projectId)">
                        <div class="modal-body">

                                <div class="form-group">
                                    <label for="project-name" class="control-label">Name:</label>
                                    <input type="text" class="form-control" id="project-name" ng-model="taskName">
                                </div>
                                <div class="form-group">
                                    <label for="message-text" class="control-label">Description:</label>
                                    <textarea class="form-control" id="message-text" ng-model="taskSummary"></textarea>
                                </div>
                                <div class="form-group">
                                    <label for="sel1">Status:</label>
                                    <select class="form-control" id="sel1" ng-model="selectedStatus">
                                        <option ng-repeat="taskStatus in taskStatuses" value="{{taskStatus.id}}">{{taskStatus.name}}</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="sel2">Priority:</label>
                                    <select class="form-control" id="sel2" ng-model="selectedPriority">
                                        <option value="NORMAL">Normal</option>
                                        <option value="LOW">Low</option>
                                        <option value="HIGH">High</option>
                                        <option value="CRITICAL">Critical</option>
                                    </select>
                                </div>

                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                            <input type="submit" class="btn btn-success" value="Create task"/>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
