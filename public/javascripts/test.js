$(function() {
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
        }
    });

    var CommentView = Backbone.View.extend({
        tagName: 'div',
        template: _.template($('#comment-template').html()),
        initialize: function() {
            console.log('initializing CommentView');
        },
        render: function() {
            var data = this.model.toJSON();
            var html = this.template({someData: 'Hello'});
            console.debug('data on CommentView', data);

            $(this.el).html(html);
            console.debug('rendering in CommentView', $(this.el), html);
        }
    });

    var CommentListView = Backbone.View.extend({
        el: $("#view-comment"),
        events: {
            'click #comment-button': '_onAddInputClick'
        },
        initialize: function() {
            this.model.bind('add', this.render, this); // ?? don't know about last this
            console.debug('CommentListView initialize', this.model);
        },
        render: function() {
            console.debug('rendering should be written here...');
            var commentListEl = $('#comment-list');
            commentListEl.empty();

            this.model.each(function(comment) {
                var view = new CommentView({model: comment});
                view.render();
                console.debug('view.render', view, view.render);
                commentListEl.append(view.el);
            });
        },
        _onAddInputClick: function() {
            var comment = new Comment({comment: $('#comment').val()});
            $('#comment').val('');

            this.model.add(comment);
            console.debug('Hey, yo-', this.model, this.model.each);
        }
    });

    // var commentListView = new CommentListView({ model: new CommentList() });
    new CommentListView({ model: new CommentList() });
    // $('#comment-button').click(function() {
    //     var commnet = new Comment({comment: $('#comment').val()});
    // });
});

