var Goal = function () {
	var self = this;

	this.title = ko.observable("");
	this.description = ko.observable("");
	this.state = ko.observable(0);
	this.user = new User();
}