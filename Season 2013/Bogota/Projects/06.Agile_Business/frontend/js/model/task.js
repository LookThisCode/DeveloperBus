var User = function () {
	var self = this;

	this.goal = new Goal();
	this.title = ko.observable("");
	this.desciption = ko.observable("");
	this.state = ko.observable(0);
	this.taskType = ko.observable(0);
	this.user = new User();
	this.completed = ko.observable(false);
}