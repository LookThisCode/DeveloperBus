AB = new Titan.App({
    
    launch: function() {
        var T = Titan,
            V = T.View,
            ab = AB;
        
        V.setViewportId("viewport");
        ab.views.start = new AB.StartViewModel();
        
        V.change("start", function() {
            ab.views.start.init();
        });
    },
    
    views: {}
    
});