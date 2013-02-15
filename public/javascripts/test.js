$(function() {
    console.debug('Hello, this is test.js');

    // var Person = Backbone.Model.extend({
    //     defaults: {
    //         name: 'Fetus',
    //         age: 0
    //     },
    //     initialize: function() {
    //         console.debug('initializing Person...');
    //         this.on('change:name', function(model) {
    //             var name = model.get('name');
    //             console.debug('Changed my name to ' + name);
    //         });
    //     }
    // });
    // var person = new Person({ name: 'Thomas', age: 67 });
    // 
    // $('#button').click(function() {
    //     console.debug($('#text').val());
    //     person.set({name: $('#text').val()});
    //     console.debug(person.toJSON());
    // });

    // var View = Backbone.View.extend({
    //     initialize: function() {
    //         console.debug('initializing View...');
    //         this.render();
    //     },
    //     render: function() {
    //         var template = _.template($('#template').html(), { someData: 'data from render method.' });
    //         this.$el.html(template);
    //     },
    //     events: {
    //         "click input[type=button]": "doSomething"
    //     },
    //     doSomething: function(event) {
    //         console.debug(event);
    //     }
    // });
    // 
    // var view = new View({el: $('#viewContainer')});

    var Comment = Backbone.Model.extend({
        defaults: {
            content: '',
            created_at: 0
        },
        initialize: function() {
            console.debug('initializing Comment...');
        }
    });

    var CommentList = Backbone.Collection.extend({
        model: Comment,
        initialize: function() {
            console.debug('CommentList initialize');
            // this.bind('add', this._onAdd);
        }// ,
        // _onAdd: function() {
        //     console.debug('nanikore', this);
        // }
    });

    var CommentListView = Backbone.View.extend({
        el: $("#viewComment"),
        events: {
            'click #comment-button': '_onAddInputClick'
            // 'click input': '_onAddComment'
        },
        initialize: function() {
            // this.model.bind('add', this.render, this);
            this.model.bind('add', this.render);
            console.debug('CommentListView initialize', this.model);
        },
        render: function() {
            console.debug('rendering should be written here...');
        },
        _onAddInputClick: function() {
            var comment = new Comment({comment: $('#comment').val()});
            $('#comment').val('');

            this.model.add(comment);
            console.debug('Hey, yo-', this.model);
        }
    });

    // var commentListView = new CommentListView({ model: new CommentList() });
    window.App = new CommentListView({ model: new CommentList() });
    // $('#comment-button').click(function() {
    //     var commnet = new Comment({comment: $('#comment').val()});
    // });
});

