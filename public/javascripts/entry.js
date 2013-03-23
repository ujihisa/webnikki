$(function() {
    var Comment = Backbone.Model.extend({
        defaults: {},
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
            var html = this.template({
                uname: data.uname,
                content: data.content,
                timestamp: data.timestamp
            });

            $(this.el).html(html);
        }
    });

    var CommentListView = Backbone.View.extend({
        el: $('#view-comment'),
        events: {
            'click #submit-button': '_onAddInputClick'
        },
        initialize: function() {
            this.model.bind('add', this.render, this);
            var self = this;
            $.each($('#initial-comments > .comments'), function(index, value) {
                self.model.add(new Comment({
                    uname: $(value).find('.comment-uname').html(),
                    content: $(value).find('.comment-content').html(),
                    timestamp: $(value).find('.comment-timestamp').html()
                }));
            });
        },
        render: function() {
            var commentListEl = $('#comment-list');
            commentListEl.empty();

            this.model.each(function(comment) {
                var view = new CommentView({ model: comment });
                view.render();
                commentListEl.append(view.el);
            });
            if (this.model.length) { $('#comment-form-separator').show(); }
        },
        _onAddInputClick: function() {
            var self = this;
            $.ajax({
                type: 'POST',
                url: '/entry',
                data: {
                    post_id: $('#post_id').val(),
                    uname: $('#comment-uname').val(),
                    content: $('#comment-content').val()
                }
            }).done(function(data) {
                self.model.add(new Comment({
                    uname: $('#comment-uname').val(),
                    content: $('#comment-content').val(),
                    timestamp: data.formatted_created_at
                }));
                $('#comment-uname').val('');
                $('#comment-content').val('');
                $().toastmessage('showToast', {
                    text: 'コメントに成功しました！',
                    position: 'top-center',
                    type: 'success',
                    stayTime: 3000,
                    close: function() {}
                });
            }).fail(function(data) {
                $().toastmessage('showToast', {
                    text: 'コメントに失敗しました...。',
                    position: 'top-center',
                    type: 'error',
                    stayTime: 3000,
                    close: function() {}
                });
            });

            return false;
        }
    });

    new CommentListView({ model: new CommentList() });
});
