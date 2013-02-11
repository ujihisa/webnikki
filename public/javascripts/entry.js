$(function() {
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
