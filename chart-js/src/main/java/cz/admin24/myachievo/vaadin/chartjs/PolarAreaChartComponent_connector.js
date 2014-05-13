window.cz_admin24_myachievo_vaadin_chartjs_PolarAreaChartComponent = function(
		) {
	function log(msg){
		if(console&&console.log){
			console.log(msg);
		}
	}

	var element = this.getElement();
	element.innerHTML = '<canvas id="myChart" width="400" height="400"></canvas>';
	aaa = element;
	//Get the context of the canvas element we want to select
	var ctx = element.children[0].getContext("2d");
	//var myNewChart = new Chart(ctx).PolarArea(data);

//	log("pre init");
//	log(element);
//	abcd=element;
//	var timelineChart = new jQuery.TimeLineChart(element);
//	$(element).attr("style", "width:100%;height:100px");
//	$(element).addClass("v-timelineChart");
//	log("post init");

//	this.shutdown = function() {
//		log("shutdown begin");
//		timelineChart.shutdown();
//		log("shutdown finished");
//	};

	// var e = this.getElement();
	this.onStateChange = function() {
		log("state changed");
		var state = this.getState();
//		if (!state.config) {
//			return;
//		}
		log("timelineChart.draw"+state);
//		timelineChart.draw(jQuery.parseJSON(state.config));
		new Chart(ctx).PolarArea(state.data, state.options);

		log("timelineChart.draw finished");
	};

//	// RPC
//	var connector = this;
//	timelineChart.requestLayoutPhase = function() {
//		connector.requestLayoutPhase();
//	};
//	timelineChart.changeMainChartRanges = function() {
//		connector.changeMainChartRanges();
//	};
};
