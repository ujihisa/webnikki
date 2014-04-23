$(function() {
    var Comment = Backbone.Model.extend({
        defaults: {
			uname: 'no-name',
            content: '',
            created_at: 0
        },
        initialize: function() {}
    });

    var CommentList = Backbone.Collection.extend({
        model: Comment,
        initialize: function() {}
    });

    var CommentView = Backbone.View.extend({
        tagName: 'div',
        template: _.template($('#comment-template').html()),
        initialize: function() {},
        render: function() {
            var data = this.model.toJSON();
            var html = this.template({someData: data});

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
            this.model.bind('add', this.render, this);
        },
        render: function() {
            var commentListEl = $('#comment-list');
            commentListEl.empty();

            this.model.each(function(comment) {
                var view = new CommentView({model: comment});
                view.render();
                commentListEl.append(view.el);
            });
        },
        _onAddInputClick: function() {
            var comment = new Comment({content: $('#comment').val()});
            $('#comment').val('');

            this.model.add(comment);
        }
    });

    new CommentListView({ model: new CommentList() });
});

