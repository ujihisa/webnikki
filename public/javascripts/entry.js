$(function() {
    // console.debug(_.template($('#comment-template').text(), {name: "Yasu"}));

    // Backbone code begin
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
            console.debug('rendering in CommentView', $(this.el), html);
        }
    });

    var CommentListView = Backbone.View.extend({
        el: $('#view-comment'),
        events: {
            'click #comment-button': '_onAddInputClick'
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
        },
        _onAddInputClick: function() {
            var comment = new Comment({
                uname: 'uname-dayo',
                content: $('#comment').val(),
                timestamp: 'timestamp-dayo'
            });
            $('#comment').val('');

            this.model.add(comment);
        }
    });

    var commentListView = new CommentListView({ model: new CommentList() });
    // Backbone code end

    var addComment = function(uname, content, created_at) {
        if ($('#comments > .comments').length) {
            // 普通に追加する
            console.debug('hi');
            $('#comments').append('<div class="comments"><p>' + uname + '</p><p>' + content + '</p></div>');
        } else {
            // 中身を消してから追加する
            console.debug('ho');
        }
    };

    $('#submit').submit(function() {
        $.ajax({
            type: 'POST',
            url: '/entry',
            data: {
                post_id: $('#post_id').val(),
                uname: $('#uname').val(),
                content: $('#content').val()
            }
        }).done(function(data) {
            if (data.success) {
                // TODO: コメントを同ページに追記
                addComment($('#uname').val(),
                           $('#content').val(),
                           data.created_at);
                $().toastmessage('showToast', {
                    text: 'コメントに成功しました！',
                    position: 'top-center',
                    type: 'success',
                    stayTime: 3000,
                    close: function() {
                    }
                });
            }
        }).fail(function(data) {
            alert('コメント投稿に失敗しちゃったみたい...');
        }).always(function(data) {});

        return false;
    });
});
