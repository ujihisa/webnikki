$(function() {
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
                $().toastmessage('showToast', {
                    text: 'コメントに成功しました！',
                    position: 'top-center',
                    type: 'success',
                    stayTime: 3000,
                    close: function() {
                        // TODO: コメントを同ページに追記
                        addComment($('#uname').val(),
                                   $('#content').val(),
                                   data.created_at);
                    }
                });
            }
        }).fail(function(data) {
            // TODO: toast を使ったものに書き換える
            alert('コメント投稿に失敗しちゃったみたい...');
        }).always(function(data) {});

        return false;
    });
});
